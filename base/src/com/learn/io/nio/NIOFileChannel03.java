package com.learn.io.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 用一个buffer实现文件的复制
 *
 * @author ZhengChaoHui
 * @Date 2020/7/19 9:41
 */
public class NIOFileChannel03 {

    public static void main(String[] args) throws IOException {
        // 获取输入流
        FileInputStream fileInputStream = new FileInputStream("D:\\1.txt");

        // 获取输出流
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\2.txt");

        // 绑定通道
        FileChannel inputStreamChannel = fileInputStream.getChannel();
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();

        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);

        // 循环读取
        while (true){
            // 复位
            byteBuffer.clear();

            // 读取数据到缓冲区
            int read = inputStreamChannel.read(byteBuffer);
            if( read <= -1 ){
                break;
            }

            byteBuffer.flip();
            fileOutputStreamChannel.write(byteBuffer);

        }

        // 关闭流
        fileInputStream.close();
        fileOutputStream.close();
    }
}
