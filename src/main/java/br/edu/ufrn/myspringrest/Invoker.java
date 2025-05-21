package br.edu.ufrn.myspringrest;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import br.edu.ufrn.myspringrest.annotations.RequestMapping;
import br.edu.ufrn.myspringrest.annotations.RestController;
import br.edu.ufrn.myspringrest.annotations.params.BodyParam;
import br.edu.ufrn.myspringrest.annotations.params.PathParam;
import br.edu.ufrn.myspringrest.enums.HttpMethod;
import br.edu.ufrn.myspringrest.exceptions.RequestMethodNotFound;
import br.edu.ufrn.myspringrest.models.HttpRequest;

public class Invoker {
    private static final Logger logger = Logger.getLogger(Invoker.class.getName());

    private Reflections reflections;

    HashMap<String, Object> controllers = new HashMap<String, Object>();

    public Invoker(String basePackage) {
        this.reflections = new Reflections(
            new ConfigurationBuilder()
                .forPackages(basePackage)
                .addScanners(Scanners.TypesAnnotated)
                .addScanners(Scanners.MethodsAnnotated)
        );
    }

    private String getPathRegex(String path) {
        return "^" + path.replaceAll("\\{[^/]+}", "([^/]+)") + "$";
    }


    private boolean pathMatches(String requestMappingPath, String incomingPath) { 
        String regex = this.getPathRegex(requestMappingPath);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(incomingPath);

        return matcher.matches();
    }

    private boolean httpMethodMatches(HttpMethod requestMappingMethod, HttpMethod incomingMethod) {
        return requestMappingMethod.equals(incomingMethod);
    }

    private Method matchesRequestMethod(HttpRequest request, Set<Method> foundMethods) throws RequestMethodNotFound {
        Method matchedMethod = null;
        
        for (Method method : foundMethods) {
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);

            boolean pathMatches = this.pathMatches(annotation.path(), request.getPath());

            if (!pathMatches) {
                continue;
            }

            boolean httpMethodMatches = this.httpMethodMatches(annotation.method(), request.getMethod());

            if (!httpMethodMatches) {
                continue;
            }

            matchedMethod = method;
        }

        if (matchedMethod == null) {
            throw new RequestMethodNotFound();
        }

        return matchedMethod;
    }


    private Method findMethod(HttpRequest request) throws RequestMethodNotFound {
        Set<Method> methods = this.reflections.getMethodsAnnotatedWith(
            RequestMapping.class
        );
        Method method = this.matchesRequestMethod(request, methods);

        return method;
    }

    private Object findController(Method method) {
        String controllerClassName = method.getDeclaringClass().getName();
        Object controller = this.controllers.get(controllerClassName);

        return controller;
    }

    private Queue<String> getPathParamValues(Method method, String incomingPath) {
        String requestMappingPath = method.getAnnotation(RequestMapping.class).path();

        String regex = this.getPathRegex(requestMappingPath);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(incomingPath);

        matcher.matches();

        Queue<String> pathValues = new LinkedList<String>();

        for (int i = 1; i <= matcher.groupCount(); i++) {
            logger.info(matcher.toString());
            pathValues.add(matcher.group(i));
        }

        return pathValues;
    }

    private ArrayList<Object> extractArgs(HttpRequest request, Method method) {
        Parameter[] parameters = method.getParameters();
        
        Queue<String> pathValues = this.getPathParamValues(method, request.getPath());
        Map<String, Object> body = Marshaller.decode(request.getBody());

        ArrayList<Object> args = new ArrayList<Object>();
        
        for (Parameter parameter : parameters) {
            Object value = null;
            Class<?> parameterType = parameter.getType();
            
            if (parameter.isAnnotationPresent(PathParam.class)) {
                Object rawValue = pathValues.poll();
                if (!(rawValue instanceof String)) {
                    throw new IllegalArgumentException("Expected String from pathValues, got: " + rawValue);
                }
                String valueStr = (String) rawValue;

                PropertyEditor editor = PropertyEditorManager.findEditor(parameterType);
                
                if (editor == null) {
                    throw new IllegalArgumentException("No PropertyEditor for type: " + parameterType.getName());
                }

                editor.setAsText(valueStr);
                value = editor.getValue();

            } else if (parameter.isAnnotationPresent(BodyParam.class)) {
                BodyParam annotation = parameter.getAnnotation(BodyParam.class);
                String key = annotation.value();
                value = body.get(key);
            }

            args.add(value);
        }

        return args;
    }


    public void invoke(HttpRequest request) throws RequestMethodNotFound, Exception {
        Method method = this.findMethod(request);

        Object controller = this.findController(method);

        ArrayList<Object> args = this.extractArgs(request, method);

        logger.info("args: " + args);

        method.getReturnType();

        Object response = method.invoke(controller, args.toArray());
    }

    public void register() throws Exception {
        Set<Class<?>> foundControllerClasses = this.reflections.getTypesAnnotatedWith(
            RestController.class
        );

        logger.info(
            "Controllers successfully found: " + foundControllerClasses
        );

        for (Class<?> controllerClass : foundControllerClasses) {
            String name = controllerClass.getName();
            Object instance = controllerClass.getDeclaredConstructor().newInstance();

            this.controllers.put(name, instance);
        }

        logger.info(
            "Controllers successfully registered into controllers hash map: " + this.controllers);
    }

}
