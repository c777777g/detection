package com.commonsl.util;

import java.io.InputStream;
import java.util.Scanner;

public class FfmpegUtil {
    Scanner input = null;
    String result = "";
    Process process = null;
    boolean read = true;

    public String recordVideo(String filePath, String url) {
        String command = "ffmpeg -i " + url + " -acodec copy -vcodec copy -f mp4 -movflags frag_keyframe+empty_moov -b:v 400k " + filePath;
//        String command = "pwd";
        try {
            try {
                process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});
                //等待命令执行完成
                process.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //获取子进程的输入流
            InputStream is = process.getInputStream();
            input = new Scanner(is);
            while (input.hasNextLine() && read) {
                result += input.nextLine() + "\n";
                if (result.contains("kB") &&result.contains("kbits/s") &&result.contains("Stream") && result.contains("Metadata") && result.contains("frame") && result.contains("fps") && result.contains("time") && result.contains("bitrate")) {
                    read = false;
                    result += "spot start recording successful";
                }
            }

            //获取子进程的错误流
            InputStream is2 = process.getErrorStream();
            input = new Scanner(is2);
            while (input.hasNextLine() && read) {
                result += input.nextLine() + "\n";
                if (result.contains("kB") &&result.contains("kbits/s") &&result.contains("Stream") && result.contains("Metadata") && result.contains("frame") && result.contains("fps") && result.contains("time") && result.contains("bitrate")) {
                    read = false;
                    result += "spot start recording successful";
                }
            }


            result = command + "\n" + result; //加上命令本身，打印出来
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                input.close();
            }
            destroy();
            return result;
        }
    }

    public void destroy() {
        if (process != null) {
            process.exitValue();
            process.destroy();
        }
    }


} 