package com.detection.back.controller.webSocket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;


public class SocketClient extends Socket {

    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 7775;

    private Socket client;
    private PrintWriter out;
    private BufferedReader in;

    private static SourceDataLine sdl = null;

    Thread oneThread = new Thread(new SourceRunnable());
    ConcurrentLinkedQueue<byte[]> sourceByte = new ConcurrentLinkedQueue<>();


    /**
     * 与服务器连接，并输入发送消息
     */
    public SocketClient() throws Exception {
        super(SERVER_IP, SERVER_PORT);

        float sampleRate = 44100f;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        // sampleRate - 每秒的样本数
        // sampleSizeInBits - 每个样本中的位数
        // channels - 声道数（单声道 1 个，立体声 2 个）
        // signed - 指示数据是有符号的，还是无符号的
        // bigEndian - 指示是否以 big-endian 字节顺序存储单个样本中的数据（false 意味着
        // little-endian）。
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        SourceDataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        sdl = (SourceDataLine) AudioSystem.getLine(info);
        sdl.open(format);
        sdl.start();


        client = this;
        out = new PrintWriter(this.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(this.getInputStream()));
        new readLineThread();
        out.println("我已经连接服务器");

        while (true) {
            in = new BufferedReader(new InputStreamReader(System.in));
            String input = in.readLine();
            out.println(input);

        }
    }

    /**
     * 用于监听服务器端向客户端发送消息线程类
     */
    class readLineThread extends Thread {
        private InputStream inputStream;
        private BufferedReader bufferedReader;
        private InputStreamReader inputStreamReader;
        private DataInputStream in;

        public readLineThread() {
            try {
                inputStream = client.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                in = new DataInputStream(client.getInputStream());
                start();
            } catch (Exception e) {
            }
        }

        @Override
        public void run() {
//            try {
//                while (true) {
//                    String result = bufferedReader.readLine();
//                    if ("byeClient".equals(result)) {//客户端申请退出，服务端返回确认退出
//                        break;
//                    } else {//输出服务端发送消息
//                        System.out.println(result);
//                    }
//                }
//                in.close();
//                out.close();
//                client.close();
//            } catch (Exception e) {
//            }
            while (true) {
                try {

// 定义一个byte数组用来存放读取到的数据，byte数组的长度要足够大。
                    byte[] bytes = new byte[1024 * 4];
                    in.read(bytes);
//                    System.out.println(bytes);
//                    sdl.write(bytes, 0, bytes.length);
                    sourceByte.offer(bytes);
                    if(!oneThread.isAlive()){
                        oneThread.start();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Time out, No response");
                }
            }
        }
    }

    class SourceRunnable implements Runnable {
        public void run() {
            while (client.isConnected()) {
                System.out.println(sourceByte.size());
                if (sourceByte.size() > 0) {
                    byte[] bytes =  sourceByte.poll();
                    if(bytes!=null){
                        sdl.write(bytes, 0, bytes.length);
                    }
                }else{
                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        }
    }


    public static void main(String[] args) {
        try {
            new SocketClient();//启动客户端
        } catch (Exception e) {
        }
    }
}
