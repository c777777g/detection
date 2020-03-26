package com.commonsl.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class GuartzTask {
    private static String payPicturePath = PropertyUtil.getProperty("payPicturePath");
    private Logger logger =Logger.getLogger(GuartzTask.class);
    private static int payPictureBackBay =  Integer.valueOf(PropertyUtil.getProperty("payPictureBackBay")).intValue() ;

    @Autowired

    public static  void main(String[] args) {
        try {
            new GuartzTask().deleteFiles(payPicturePath,null,1,false);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public  void doDelPayUpPicture(){
        System.out.println("kai00000000000000000000000000000000");
//        //加一天
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        cal.add(Calendar.DATE,payPictureBackBay);
//
//
//        //删除文件
//        deleteFiles(payPicturePath,null,payPictureBackBay,false);
    }

    /**
     * 清理目录中过期的文件
     *
     * @param days
     *            ：保存时间天数
     * @param dirPath
     *            ：目录路径
     * @return
     */
    @SuppressWarnings("unchecked")
    private static boolean deleteFiles(String dirPath, String backupPath, int days, boolean isBackup) {

        // 计算备份日期，备份该日期之前的文件
        Date pointDate = new Date();
        long timeInterval = pointDate.getTime() - convertDaysToMilliseconds(days);
        pointDate.setTime(timeInterval);

        // 是否进行备份
        if (isBackup) {
            if (!backUpFiles(dirPath, backupPath, pointDate)) {
                System.out.println(" Backup failed: " + dirPath);
                return false;
            }
        }

        // 设置文件过滤条件
        IOFileFilter timeFileFilter = FileFilterUtils.ageFileFilter(pointDate, true);
        IOFileFilter fileFiles = FileFilterUtils.andFileFilter(FileFileFilter.FILE, timeFileFilter);

        // 删除符合条件的文件
        File deleteRootFolder = new File(dirPath);
        Iterator itFile = FileUtils.iterateFiles(deleteRootFolder, fileFiles, TrueFileFilter.INSTANCE);
        while (itFile.hasNext()) {
            File file = (File) itFile.next();
            boolean result = file.delete();
            if (!result) {

                return false;
            }
        }

        // 清理空的文件夹
        File[] forderList = deleteRootFolder.listFiles();
        if (forderList != null && forderList.length > 0) {
            for (int i = 0; i < forderList.length; i++) {
                deleteEmptyDir(forderList[i]);
            }
        }

        return true;
    }
    /**
     * 备份删除文件到指定的目录 ，目录格式：yyyy_MM_dd_bak
     *
     * @param srcDir
     *            ：源文件路径
     * @param destDir
     *            ：目标文件路径
     * @param pointDate
     *            ：时间间隔，备份该时间之前的数据
     * @return
     */
    private static boolean backUpFiles(String srcDir, String destDir, Date pointDate) {
        try {
            // 设置备份文件夹格式YYYY_MM_dd_bak
            SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");
            String folderName = format.format(new Date()) + "_bak";

            File resFile = new File(srcDir);
            File distFile = new File(destDir + File.separator + folderName);

            // 文件过滤条件
            IOFileFilter timeFileFilter = FileFilterUtils.ageFileFilter(pointDate, true);
            IOFileFilter fileFiles = FileFilterUtils.andFileFilter(FileFileFilter.FILE, timeFileFilter);

            // 复制文件目录
            FileFilter filter = FileFilterUtils.orFileFilter(DirectoryFileFilter.DIRECTORY, fileFiles);
            FileUtils.copyDirectory(resFile, distFile, filter, true);

        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }
        return true;
    }

    /**
     * 天与毫秒的转换
     *
     * @param days
     * @return
     */
    private static long convertDaysToMilliseconds(int days) {
        return days * 24L * 3600 * 1000;
    }

    /**
     * 循环删除空的文件夹
     *
     * @param dir
     */
    private static void deleteEmptyDir(File dir) {
        if (dir.isDirectory()) {
            File[] fs = dir.listFiles();
            if (fs != null && fs.length > 0) {
                for (int i = 0; i < fs.length; i++) {
                    File tmpFile = fs[i];
                    if (tmpFile.isDirectory()) {
                        deleteEmptyDir(tmpFile);
                    }
                    if (tmpFile.isDirectory() && tmpFile.listFiles().length <= 0) {
                        tmpFile.delete();
                    }
                }
            }
            if (dir.isDirectory() && dir.listFiles().length == 0) {
                dir.delete();
            }
        }
    }
}