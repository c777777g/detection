package com.detection.interfaces.start;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class StartupListener {
    private Logger logger =Logger.getLogger(StartupListener.class);



    @PostConstruct
    public void otherWork() {
        logger.info("开始启动");
        Timer timer = new Timer();
        timer.schedule(new MinaTask(1), 5000);// 5秒后启动任务
    }

    public class MinaTask extends TimerTask {

        private int id;
        public MinaTask(int id){
            this.id = id;
        }

        @Override
        public void run() {
            logger.info("线程"+ id +":  正在 执行。。");
//            try {
//                IoAcceptor accept = new NioSocketAcceptor();
//                DefaultIoFilterChainBuilder chain = accept.getFilterChain();
//                accept.getSessionConfig().setReadBufferSize(2048);
//                accept.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,10);
//                accept.setHandler(new ServerHandler());
//                LoggingFilter loggingFilter = new LoggingFilter();
//                chain.addLast("logging", loggingFilter);
//                chain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(
//                        Charset.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(),
//                        LineDelimiter.WINDOWS.getValue())));
//                accept.bind(new InetSocketAddress(9123));
//                logger.info("server成功启动");
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                logger.info("错");
//                logger.info( e.getMessage());
//            }
        }
    }


}