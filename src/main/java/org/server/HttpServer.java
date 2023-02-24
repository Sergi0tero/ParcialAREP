package org.server;

import org.Response.Response;

import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.io.*;
import java.util.Arrays;

public class HttpServer {
    public static void run() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ServerSocket serverSocket = null;
        Response response = Response.getInstance();
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        Socket clientSocket = null;

        boolean running = true;
        while(running){
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            String request = "";
            outputLine = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Recibí: " + inputLine);
                if (inputLine.contains("HTTP/1.1") && !(inputLine.equals("GET /favicon.ico HTTP/1.1") || inputLine.equals("GET / HTTP/1.1"))){
                    request = inputLine.replace("GET /req?request=", "").replace(" HTTP/1.1", "");
                }
                if (inputLine.equals("GET / HTTP/1.1")){
                    outputLine = "HTTP/1.1 200 OK\r\n"
                            + "Content-Type: text/html\r\n"
                            + "\r\n"
                            + "<!DOCTYPE html>\n"
                            + "<html>\n"
                            + "<head>\n"
                            + "<title>Chat GPT AREP</title>\n"
                            + "<meta charset='UTF-8'>\n"
                            + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>\n"
                            + "</head>\n"
                            + "<body>\n"
                            + "<h1>Welcome to ChatGPT AREP!</h1>\n"
                            + "<form action='/hello'>\n"
                            + "<label for='name'>Name:</label><br>\n"
                            + "<input type='text' id='name' name='name' value='John'><br><br>\n"
                            + "<input type='button' value='Submit' onclick='loadGetMsg()'>\n"
                            + "</form>\n"
                            + "<div id='getrespmsg'></div>\n"
                            + "\n"
                            + "<script>\n"
                            + "function loadGetMsg() {\n"
                            + "let nameVar = document.getElementById('name').value;\n"
                            + "const xhttp = new XMLHttpRequest();\n"
                            + "xhttp.onload = function() {\n"
                            + "    document.getElementById('getrespmsg').innerHTML =\n"
                            + "            this.responseText;\n"
                            + "} \n"
                            + "xhttp.open('GET', '/req?request='+nameVar);\n"
                            + "xhttp.send();\n"
                            + "}\n"
                            + "</script>\n"
                            + "</body>\n"
                            + "</html>\n";
                }
                if (!in.ready()) {
                    break;
                }
            }

            if (request.startsWith("Class")){
                // Class(java.lang.System)
                String searchClass = request.replace("Class(", "").replace(")", "");
                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n"
                        + "\r\n"
                        + Class.forName(searchClass) + "\n"
                        + response.getClassMethods(searchClass);
            } else if(request.startsWith("invoke")){
                // invoke([class name],[method name])
                // invoke(java.lang.Math, abs)
                request = request.replace("invoke", "").replace("(", "").replace(")", "").replace("%20", "");
                String searchClass = request.split(",")[0];
                String searchMethod = request.split(",")[1];
                System.out.println("invokePrueba " + searchClass + " " + searchMethod);
                System.out.println(response.invokeClassMethod(searchClass, searchMethod));
            } else if (request.startsWith("unaryInvoke")){
                System.out.println("unary" + request);
            }
            System.out.println(outputLine);
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

}
