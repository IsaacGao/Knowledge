package com.isaac_gao.bio.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 阻塞同步的 bio server，只能处理单个请求，会被阻塞
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
    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(9956);
            System.out.println("bind port suc.");
            while (!serverSocket.isClosed()) {
                Socket request = serverSocket.accept();
                InetAddress inetAddress = request.getInetAddress();
                System.out.println("client conn, address:" + inetAddress);

                InputStream inputStream = request.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line;
                //这个地方阻塞了，第二行的时候，需要 Writer 关闭后，才会继续执行
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.length() == 0) {
                        break;
                    }
                    System.out.println(line);
                }
                request.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
