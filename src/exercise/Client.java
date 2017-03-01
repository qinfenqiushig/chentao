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
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author chentao
 * @version $Id: Client.java, v 0.1 2016年2月29日 下午1:30:03 chentao Exp $
 */
public class Client {

    private Socket socket;

    public Client() {
        try {
            socket = new Socket("localhost", 8088);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Serverhandler handler = new Serverhandler();
        Thread t = new Thread(handler);
        t.start();
        PrintWriter pw = null;
        try {
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream(), "utf8");
            pw = new PrintWriter(osw, true);
            Scanner scan = new Scanner(System.in);
            System.out.println("开始聊天吧！");
            while (true) {
                pw.println(scan.nextLine());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    class Serverhandler implements Runnable {

        /**
         *
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            BufferedReader br = null;
            try {
                InputStreamReader isr = new InputStreamReader(socket.getInputStream(), "utf8");
                br = new BufferedReader(isr);
                String message = null;
                while ((message = br.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
