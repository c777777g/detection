package com.commonsl.util;

public class EkhoUtil {

    public boolean cantonese(String filePath, String msg) {
        try {
            String m = "ekho -t wav -o " + filePath + " -v Cantonese \"" + msg + "\"";
            String cmds[] = {"/bin/bash", "-c", m};

            CommandUtil.run(cmds);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean mandarin(String filePath, String msg) {
        try {
            String m = "ekho -t wav -o " + filePath + " \"" + msg + "\"";
            String cmds[] = {"/bin/bash", "-c", m};
            CommandUtil.run(cmds);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
} 