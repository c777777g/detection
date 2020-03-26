package com.commonsl.util;

import com.commonsl.json.ProtocolKey;
import net.sf.json.JSONObject;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class MultiCastUtil {

    //private static String ip = PropertyUtil.getProperty("spot.tcpIp");
    private static String port = PropertyUtil.getProperty("spot.tcpPort");
    private static int udpPort = Integer.parseInt(PropertyUtil.getProperty("spot.udpPort"));


    private static String getMsg() {
        String msg = null;
        String ip = "192.168.1.50";
        try {
            NetworkUtil networkUtil = new NetworkUtil();
            ip = networkUtil.getIpAdd();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject sendMessage = new JSONObject();
        sendMessage.put("opcode", ProtocolKey.spotServer);
        sendMessage.put("ip", ip);
//        sendMessage.put("ip", "192.168.1.137");
        sendMessage.put("port", port);
        msg = sendMessage.toString();
        return msg;
    }

    public static String send() throws Exception {
        // TODO Auto-generated method stub
        // 广播的实现 :由客户端发出广播，服务器端接收
        String host = "255.255.255.255";// 广播地址
        // 广播的目的端口
        String message = getMsg();// 用于发送的字符串

        InetAddress adds = InetAddress.getByName(host);
        DatagramSocket ds = new DatagramSocket();
        DatagramPacket dp = new DatagramPacket(message.getBytes(),
                message.length(), adds, udpPort);
        for (int i = 0; i < 3; i++) {
            ds.send(dp);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return message;
    }

    public static void main(String[] args) {
        try {
            MultiCastUtil multiCastUtil = new MultiCastUtil();
            multiCastUtil.send();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    public static String startSend() {
//        String msg =  getMsg();
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            public void run() {
//                try {
//
//                    if (!getMsg().contains("127.0.0.1")) {
//                        byte[] bs = msg.getBytes();
//                        DatagramPacket dp = new DatagramPacket(bs, bs.length,
//                                sendAddress, 8600);
//                        sendSocket.send(dp);
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 3000, 5000);
//        return msg;
//    }

}
