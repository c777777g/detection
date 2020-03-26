import javax.sound.sampled.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AudioTest{
    private static FileOutputStream os;
    //采样率
    private static float RATE = 44100f;
    //编码格式PCM
    private static AudioFormat.Encoding ENCODING = AudioFormat.Encoding.PCM_SIGNED;
    //帧大小 16
    private static int SAMPLE_SIZE = 16;
    //是否大端
    private static boolean BIG_ENDIAN = true;
    //通道数
    private static int CHANNELS = 1;

    Thread oneThread = new Thread(new SourceRunnable());
    ConcurrentLinkedQueue<byte[]> sourceByte = new ConcurrentLinkedQueue<>();
    private static SourceDataLine sdl = null;

    public static void main(String[] args) throws Exception{
        AudioTest audioTest = new AudioTest();
        audioTest.save("E:/audio/123.pcm");
    }

    public  void save(String path) throws Exception {
        File file = new File(path);

        if(file.isDirectory()) {
            if(!file.exists()) {
                file.mkdirs();
            }
            file.createNewFile();
        }
//
//        float sampleRate = 44100f;
//        int sampleSizeInBits = 16;
//        int channels = 1;
//        boolean signed = true;
//        boolean bigEndian = false;
//        // sampleRate - 每秒的样本数
//        // sampleSizeInBits - 每个样本中的位数
//        // channels - 声道数（单声道 1 个，立体声 2 个）
//        // signed - 指示数据是有符号的，还是无符号的
//        // bigEndian - 指示是否以 big-endian 字节顺序存储单个样本中的数据（false 意味着
//        // little-endian）。
//        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
//        SourceDataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
//        sdl = (SourceDataLine) AudioSystem.getLine(info);
//        sdl.open(format);
//        sdl.start();
//
//        oneThread.start();


        AudioFormat audioFormat = new AudioFormat(ENCODING,RATE, SAMPLE_SIZE, CHANNELS, (SAMPLE_SIZE / 8) * CHANNELS,
                RATE, BIG_ENDIAN);
        TargetDataLine targetDataLine = AudioSystem.getTargetDataLine(audioFormat);
        targetDataLine.open();
        targetDataLine.start();
        byte[] b = new byte[256];
        int flag = 0;
        os = new FileOutputStream(file);
        while((flag = targetDataLine.read(b, 0, b.length))>0) {//从声卡中采集数据
            os.write(b);
            sourceByte.offer(b);
            System.out.println(flag);
        }
    }

    class SourceRunnable implements Runnable {
        public void run() {
            while (true) {
//                System.out.println(sourceByte.size());
                if (sourceByte.size() > 0) {
                    byte[] bytes =  sourceByte.poll();
                    if(bytes!=null){
                        sdl.write(bytes, 0, bytes.length);
                    }

                }
            }
        }
    }


}