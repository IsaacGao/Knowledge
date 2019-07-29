package com.isaac_gao.bio.server.concurrent;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 并发的 bio server，支持同时处理多个客户端其你去
 */
public class BioServer {
    /**
     * server
     * 绑定一个端口
     * 等待客户端接入
     * 等待客户端的输入流
     * 读取
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {

        ExecutorService threadPool = Executors.newCachedThreadPool();


        ServerSocket serverSocket = new ServerSocket(9956);
        System.out.println("bind port suc.");
        while (!serverSocket.isClosed()) {
            Socket request = serverSocket.accept();
            threadPool.submit(() -> {
                try {
                    doRead(request);
                    doWrite(request);
                    request.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void doWrite(Socket request) {
        try {
            OutputStream outputStream = request.getOutputStream();
            outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
            outputStream.write("Content-Length: 11\r\n\r\n".getBytes());
            outputStream.write("Hello World".getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void doRead(Socket request) throws IOException {
        InetAddress inetAddress = request.getInetAddress();
        System.out.println("client conn, address:" + inetAddress);

        InputStream inputStream = request.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        //这个地方阻塞了，第二轮的时候
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(Thread.currentThread().getName() + "|" + line);
        }
    }
}
