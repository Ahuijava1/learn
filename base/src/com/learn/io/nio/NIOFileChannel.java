package com.learn.io.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel的演示
 *      输出
 *
 * @author ZhengChaoHui
 * @Date 2020/7/19 9:00
 */
public class NIOFileChannel {

    public static void main(String[] args) throws IOException {
        String str = "hello, 世界！";

        // 获取输出流
        FileOutputStream fileOutputStream = new FileOutputStream("D://file01.txt");

        // 获取FileChannel
        FileChannel fileChannel = fileOutputStream.getChannel();

        // 获取缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 把string放到缓冲区ByteBuffer中
        byteBuffer.put(str.getBytes());

        // 对byteBuffer进行flip
        byteBuffer.flip();

        // 将byteBuffer的数据写入到FileChannel
        fileChannel.write(byteBuffer);

        // 关闭流
        fileOutputStream.close();

    }
}
