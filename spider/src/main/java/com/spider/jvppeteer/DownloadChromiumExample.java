package com.spider.jvppeteer;

import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.browser.BrowserFetcher;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * 参考：https://github.com/fanyong920/jvppeteer
 * DownloadChromiumExample
 * 展示下载最新的chromuim浏览器的例子
 * @author zhengchaohui
 * @date 2020/12/11 15:11
 */
public class DownloadChromiumExample {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

        Puppeteer puppeteer = new Puppeteer();
        //创建下载实例
        BrowserFetcher browserFetcher = puppeteer.createBrowserFetcher();
        //下载最新版本的chromuim
        browserFetcher.download();
        Browser browser = Puppeteer.launch(false);
        String version = browser.version();
        System.out.println(version);

    }
}
