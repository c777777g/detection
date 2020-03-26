package com.commonsl.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoPlayerMessage {


    int SplitWindow = 4;
//    int Scale = 0;
//    int Multiple = 1;
//    int FullScreen = 0;
//    int RecordingFileSize = 512;
    List<Channel>  Channel = new ArrayList<>();

    public List<VideoPlayerMessage.Channel> getChannel() {
        return Channel;
    }

    public void setChannel(List<VideoPlayerMessage.Channel> channel) {
        Channel = channel;
    }

//    public int getScale() {
//        return Scale;
//    }
//
//    public void setScale(int scale) {
//        Scale = scale;
//    }
//
//    public int getMultiple() {
//        return Multiple;
//    }
//
//    public void setMultiple(int multiple) {
//        Multiple = multiple;
//    }
//
//    public int getFullScreen() {
//        return FullScreen;
//    }
//
//    public void setFullScreen(int fullScreen) {
//        FullScreen = fullScreen;
//    }
//
//    public int getRecordingFileSize() {
//        return RecordingFileSize;
//    }
//
//    public void setRecordingFileSize(int recordingFileSize) {
//        RecordingFileSize = recordingFileSize;
//    }

    public int getSplitWindow() {
        return SplitWindow;
    }

    public void setSplitWindow(int splitWindow) {
        SplitWindow = splitWindow;
    }

    /**
     *
     * @param URL url
     * @param OSD 1为显示水印
     * @param Protocol
     * @param Cache
     * @param ShowToolbar
     * @param AutoPlay 是否自动播放
     * @param VideoName
     */
    public void addChannel(String URL,int OSD,int Protocol,int Cache,int ShowToolbar,int AutoPlay , String VideoName){
        Channel.add(new Channel(URL,OSD,Protocol,Cache,ShowToolbar,AutoPlay,VideoName));
        int i = Channel.size();
        if(i>9){
            SplitWindow =16;
        }else if(i>8){
            SplitWindow =9;
        }else if(i>4){
            SplitWindow =8;
        }
    }

    public String getJson(){
        Map<String,Object> map = new HashMap<>();
        map.put("SplitWindow",SplitWindow);
//        map.put("Scale",Scale);
//        map.put("Multiple",Multiple);
//        map.put("FullScreen",FullScreen);
//        map.put("RecordingFileSize",RecordingFileSize);
        List<Map> Channel = new ArrayList<>();
        for(Channel v : this.Channel){
            Map<String,Object> c = new HashMap<>();
            c.put("URL",v.URL);
//            c.put("OSD",v.OSD);
//            c.put("Protocol",v.Protocol);
//            c.put("Cache",v.Cache);
//            c.put("ShowToolbar",v.ShowToolbar);
//            c.put("AutoPlay",v.AutoPlay);
            c.put("VideoName",v.VideoName);
            Channel.add(c);
        }
        map.put("Channel",Channel);
        return JsonHelper.toJson(map);
    }


    class Channel{
        String URL = "";
//        int OSD = 1;
//        int Protocol = 1;
//        int Cache = 3;
//        int ShowToolbar = 1;
//        int AutoPlay =1;
        String VideoName = "";

        Channel(String URL,int OSD,int Protocol,int Cache,int ShowToolbar,int AutoPlay , String VideoName){
            this.URL = URL;
//            this.OSD = OSD;
//             this.Protocol = Protocol;
//            this.Cache = Cache;
//            this.ShowToolbar = ShowToolbar;
//            this.AutoPlay = AutoPlay;
            this.VideoName = VideoName;
        }
    }

} 