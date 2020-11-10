package com.learn.java8.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * RandomWords
 * 文本文件提供字符串对象
 * @author zhengchaohui
 * @date 2020/11/9 16:31
 */
public class RandomWords implements Supplier<String> {

    public static final String WORD_SPLIT_REGEX = "[ .?,]+";
    List<String> words = new ArrayList<>();
    Random rand = new Random(10086);

    public RandomWords (String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));
        for (String line : lines) {
            for (String word : line.split(WORD_SPLIT_REGEX)) {
                words.add(word.toLowerCase());
            }
        }
    }

    @Override
    public String get() {
        // 随机返回一个
        return words.get(rand.nextInt(words.size()));
    }

    @Override
    public String toString() {
        return words.stream().collect(Collectors.joining(" "));
    }

    public static void main(String[] args) throws IOException {
        System.out.println(Stream.generate(new RandomWords("D:\\gitData\\learn\\base\\src\\com\\learn\\java8\\stream\\Cheese.dat")).limit(10).collect(Collectors.joining(" ")));
    }
}
