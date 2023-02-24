package org.Response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Response {
    static Response instance = null;
    public static Response getInstance() throws ClassNotFoundException {
        if(instance == null){
            return new Response();
        }
        return instance;
    }

    private Response() throws ClassNotFoundException {
    }

    public String getClassMethods(String classToSearch) throws ClassNotFoundException {
        Class className = Class.forName(classToSearch);
        return Arrays.toString(className.getMethods());
    }

    public String invokeClassMethod(String classToSearch, String method) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class className = Class.forName(classToSearch);
        Method classMethod = className.getMethod(method);
        System.out.println("class" + classMethod);
        return classMethod.invoke(null).toString();
    }

    public String unaryInvoke(String classToSearch, String methodName, String paramType, String paramValue) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class className = Class.forName(classToSearch);
        Method classMethod = className.getMethod(methodName);
        return classMethod.invoke(null).toString();
    }

    public Class returnType(String newType){
        if (newType == "String"){
            return String.class;
        } else if(newType == "boolean"){
            return boolean.class;
        } else if (newType == "int"){
            return int.class;
        } else if (newType == "double"){
            return double.class;
        }
        return null;
    }

    public String binaryInvoke(){
        return "";
    }
}
