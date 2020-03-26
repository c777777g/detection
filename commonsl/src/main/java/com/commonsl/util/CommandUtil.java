package com.commonsl.util;


import java.io.*;
import java.util.Scanner;

public class CommandUtil {

    public static String run(String command) throws IOException {
        Scanner input = null;
        String result = "";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            try {
                //等待命令执行完成
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            InputStream is = process.getInputStream();
            input = new Scanner(is);
            while (input.hasNextLine()) {
                result += input.nextLine() + "\n";
            }
            result = command + "\n" + result; //加上命令本身，打印出来
        } finally {
            if (input != null) {
                input.close();
            }
            if (process != null) {
                process.destroy();
            }
        }
        return result;
    }

    public static String run(String[] command) throws IOException {
        Scanner input = null;
        String result = "";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            try {
                //等待命令执行完成
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            InputStream is = process.getInputStream();
            input = new Scanner(is);
            while (input.hasNextLine()) {
                result += input.nextLine() + "\n";
            }
            result = command + "\n" + result; //加上命令本身，打印出来
        } finally {
            if (input != null) {
                input.close();
            }
            if (process != null) {
                process.destroy();
            }
        }
        return result;
    }

    public static String rebootLinux() {
        try {
            String password = PropertyUtil.getProperty("system.password");
            String cmds[] = {"/bin/bash", "-c", "echo " + password + " | sudo -S reboot"};
            return run(cmds);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String changeIP(String address, String gateway, String netmask, String dns) {
//        # This file describes the network interfaces available on your system
//        # and how to activate them. For more information, see interfaces(5).
//
//        # The loopback network interface
//        auto lo
//                        iface lo inet loopback
//
//        # The primary network interface
//        #auto eth0
//        #iface eth0 inet dhcp
//
//        auto eth0
//        iface eth0 inet static
//        address 192.168.1.52
//        gateway 192.168.1.1
//        netmask 255.255.255.0
//        dns-nameserver 114.114.114.114

        String content = "# This file describes the network interfaces available on your system\n" +
                "# and how to activate them. For more information, see interfaces(5).\n" +
                "\n" +
                "# The loopback network interface\n" +
                "auto lo\n" +
                "iface lo inet loopback\n" +
                "\n" +
                "# The primary network interface\n" +
                "#auto eth0\n" +
                "#iface eth0 inet dhcp\n" +
                "\n" +
                "auto eth0\n" +
                "iface eth0 inet static\n" +
                "        address " + address + "\n" +
                "        gateway " + gateway + "\n" +
                "        netmask " + netmask + "\n" +
                "        dns-nameserver " + dns + "\n";

        try {
            String password = PropertyUtil.getProperty("system.password");
            String cmds[] = {"/bin/bash", "-c", "echo " + password + " | sudo -S rm -r /etc/network/interfaces"};
            run(cmds);
        } catch (Exception e) {
            e.printStackTrace();
            return "删除网络配置文件失败";
        }

        File file = new File("/etc/network/interfaces");
        try {
            //如果文件不存在，则创建新的文件
            if (!file.exists()) {
                file.createNewFile();
                //创建文件成功后，写入内容到文件里
                writeFileContent("/etc/network/interfaces", content);
                rebootLinux();//重启
                return "修改网络配置成功等待重启";
            }else{
                return "删除网络配置文件失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "修改网络配置文件失败";
    }

    /**
     * 向文件中写入内容
     *
     * @param filepath 文件路径与名称
     * @param newstr   写入的内容
     * @return
     * @throws IOException
     */
    public static boolean writeFileContent(String filepath, String newstr) throws IOException {
        Boolean bool = false;
        String filein = newstr + "\r\n";//新写入的行，换行
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);//文件路径(包括文件名称)
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();

            //文件原有内容
            for (int i = 0; (temp = br.readLine()) != null; i++) {
                buffer.append(temp);
                // 行与行之间的分隔符 相当于“\n”
                buffer = buffer.append(System.getProperty("line.separator"));
            }
            buffer.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }

    /**
     * 文件内容替换
     *
     * @param filePath
     * @param oldstr
     * @param newStr
     */
    private static void autoReplace(String filePath, String oldstr, String newStr) {
        File file = new File(filePath);
        Long fileLength = file.length();
        byte[] fileContext = new byte[fileLength.intValue()];
        FileInputStream in = null;
        PrintWriter out = null;
        try {
            in = new FileInputStream(filePath);
            in.read(fileContext);
            // 避免出现中文乱码
            String str = new String(fileContext, "utf-8");
            str = str.replace(oldstr, newStr);
            out = new PrintWriter(filePath);
            out.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        String path = "D:\\Java\\gitworkspace\\Coding\\src\\com\\stono\\thread2";
        autoReplace("D:\\interfaces.txt", "192.168.1.53", "192.168.1.12");
        autoReplace("D:\\interfaces.txt", "192.168.1.1", "192.168.1.1");
        autoReplace("D:\\interfaces.txt", "192.168.1.53", "192.168.1.12");
    }


}
