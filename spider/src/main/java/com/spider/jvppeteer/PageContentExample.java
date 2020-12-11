package com.spider.jvppeteer;

import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.LaunchOptions;
import com.ruiyun.jvppeteer.options.LaunchOptionsBuilder;


import java.io.IOException;
import java.util.ArrayList;

/**
 * PageContentExample
 * 爬取整个页面的内容
 * @author zhengchaohui
 * @date 2020/12/11 15:12
 */
public class PageContentExample {
    public static void main(String[] args) throws InterruptedException, IOException {
        String path = new String("D:\\gitData\\learn\\.local-browser\\win64-818858\\chrome-win\\chrome.exe".getBytes(),"UTF-8");

//       String  path ="D:\\develop\\project\\toString\\chrome-win\\chrome.exe";
        ArrayList<String> arrayList = new ArrayList<String>();
        LaunchOptions options = new LaunchOptionsBuilder().withArgs(arrayList).withHeadless(false).withExecutablePath(path).build();
        arrayList.add("--no-sandbox");
        arrayList.add("--disable-setuid-sandbox");
        Browser browser = Puppeteer.launch(options);
        Page page = browser.newPage();
        page.goTo("https://www.chinadaily.com.cn/a/202012/11/WS5fd2c475a31024ad0ba9b291.html");
        String content = page.content();
        System.out.println("=======================content=============="+content);
    }
}
