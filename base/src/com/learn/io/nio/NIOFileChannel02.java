package com.learn.io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel的演示
 *      输入
 *
 * @author ZhengChaoHui
 * @Date 2020/7/19 9:23
 */
public class NIOFileChannel02 {

    public static void main(String[] args) throws IOException {

        // 获取输入流

        File file = new File("D:\\file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        // 获取通道
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();

        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        // 将通道的数据写入到byteBuffer
        fileInputStreamChannel.read(byteBuffer);

        System.out.println(new String(byteBuffer.array()));

        fileInputStream.close();
    }
}
