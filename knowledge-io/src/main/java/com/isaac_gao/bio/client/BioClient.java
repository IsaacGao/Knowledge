package com.isaac_gao.bio.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

@SuppressWarnings("ALL")
public class BioClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 9956);
            if (socket.isConnected()) {
                System.out.println("连接客户端成功.");
                OutputStream outputStream = socket.getOutputStream();
                System.out.print("请输入：");
                Scanner scanner = new Scanner(System.in);
                String msg = scanner.nextLine();
                System.out.println("msg:" + msg);
                //!!!! 这个地方是阻塞的，只有写出成功才会继续
                outputStream.write(msg.getBytes());
                System.out.println("写出完成");
                scanner.close();
                outputStream.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
