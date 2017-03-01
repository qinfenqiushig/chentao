/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author chentao
 * @version $Id: Server.java, v 0.1 2016年2月29日 下午1:30:15 chentao Exp $
 */
public class Server {

    private ServerSocket                 serverSocket;
    private ExecutorService              threadPool;
    private HashMap<String, PrintWriter> allStuff;

    public Server() {
        try {
            threadPool = Executors.newFixedThreadPool(10);
            allStuff = new HashMap<String, PrintWriter>();
            serverSocket = new ServerSocket(8088);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void addAll(String ip, PrintWriter pw) {
        allStuff.put(ip, pw);
    }

    private synchronized void removeAll(String ip) {
        allStuff.remove(ip);
    }

    private void sendMessageToAll(String message) {
        for (PrintWriter pw : allStuff.values()) {
            pw.println(message);
        }
    }

    public void start() {
        System.out.println("等待客户端连接...");
        Socket socket;
        try {
            socket = serverSocket.accept();
            InetAddress address = socket.getInetAddress();
            String ip = address.getHostAddress();
            System.out.println(ip + "已连接！");
            Clienthander hander = new Clienthander(socket, ip);
            threadPool.execute(hander);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    class Clienthander implements Runnable {

        private Socket socket;
        private String ip;

        public Clienthander(Socket socket, String ip) {
            this.socket = socket;
            this.ip = ip;
        }

        /**
         *
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            BufferedReader br = null;
            PrintWriter pw = null;
            try {
                InputStreamReader isr = new InputStreamReader(socket.getInputStream(), "utf8");
                br = new BufferedReader(isr);
                OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream(), "utf8");
                pw = new PrintWriter(osw, true);
                addAll(ip, pw);
                sendMessageToAll(ip + "上线了");
                String message = null;
                while ((message = br.readLine()) != null) {
                    sendMessageToAll(ip + "说：" + message);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        removeAll(ip);
                        sendMessageToAll(ip + "下线了");
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
