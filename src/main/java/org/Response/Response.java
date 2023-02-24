package org.Response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

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
        return classMethod.invoke(null).toString();
    }

    public String unaryInvoke(String classToSearch, String methodName, String paramType, String paramValue) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class className = Class.forName(classToSearch);
        Method classMethod = className.getMethod(methodName);
        if (returnType(paramType) == int.class){
            int param = Integer.parseInt(paramValue);
            return classMethod.invoke(param).toString();
        } else if (returnType(paramType) == Double.class){
            Double param = Double.parseDouble(paramValue);
            return classMethod.invoke(param).toString();
        } else if (returnType(paramType) == String.class){
            return classMethod.invoke(paramValue).toString();
        }
        return "";
    }

    //[class name],[method name],[paramtype 1],[param value], [paramtype 1],[param value]
    public String binaryInvoke(String classToSearch, String methodName, String paramType, String paramValue, String paramType2, String paramValue2){
        return "";
    }

    public Class returnType(String newType){
        if (newType == "String"){
            return String.class;
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
