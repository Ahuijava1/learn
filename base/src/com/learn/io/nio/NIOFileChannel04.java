package com.learn.io.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 使用transferFrom拷贝文件
 *
 * @author ZhengChaoHui
 * @Date 2020/7/19 9:55
 */
public class NIOFileChannel04 {

    public static void main(String[] args) throws IOException {
        // 获取输入流
        FileInputStream fileInputStream = new FileInputStream("D:\\郑朝辉的简历.pdf");

        // 获取输出流
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\2.txt");

        // 绑定通道
        FileChannel inputStreamChannel = fileInputStream.getChannel();
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();

        // 复制
        fileOutputStreamChannel.transferFrom(inputStreamChannel, 0 , inputStreamChannel.size());

        // 关闭流
        fileInputStream.close();
        fileOutputStream.close();
    }
}
