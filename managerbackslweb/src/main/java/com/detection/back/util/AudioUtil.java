package com.detection.back.util;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.File;

public class AudioUtil {

    public static File toWav(File source, String desFileName) throws Exception {
        File target = new File(desFileName);
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("pcm_s16le");
        audio.setBitRate(new Integer(128000));
        audio.setChannels(new Integer(1));
        audio.setSamplingRate(new Integer(48000));
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("wav");
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        encoder.encode(source, target, attrs);
        return target;
    }

    public static long getAudioDuration(File source) {
        long duration = 0;
        try {
            Encoder encoder = new Encoder();
            MultimediaInfo m = encoder.getInfo(source);
            long ls = m.getDuration();
            duration = ls / 1000;
            System.out.println("此视频时长为:" + ls / 60000 + "分" + (ls / 1000 - ls / 60000 * 60) + "秒！");
        } catch (Exception e) {

        }
        return duration;
    }

}