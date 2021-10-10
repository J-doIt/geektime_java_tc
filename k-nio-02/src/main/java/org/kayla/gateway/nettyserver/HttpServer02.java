package org.kayla.gateway.nettyserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 每个请求一个线程
 *
 * @author Kayla(J - doIt)
 * @date 2021/09/24 01:06
 **/
public class HttpServer02 {

    private static boolean isStop = false;

    public static void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8802);
        while (!isStop) {
            try {
                // final Socket
                final Socket socket = serverSocket.accept();
                new Thread(() -> {
                    service(socket);
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void service(Socket socket) {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello,nio2";
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        isStop = true;
    }

}