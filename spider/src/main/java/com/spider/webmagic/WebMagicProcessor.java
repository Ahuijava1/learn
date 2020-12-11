package com.spider.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * WebMagicProcessor
 * https://www.chinadaily.com.cn
 * http://www.chinadailyglobal.com/
 * @author zhengchaohui
 * @date 2020/12/11 16:11
 */
public class WebMagicProcessor implements PageProcessor {
    /**
     * 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
     */
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    /**
     * 处理数量
     */
    private static int count = 0;

    public static void main(String[] args) {
        long startTime, endTime;
        System.out.println("开始爬取...");
        startTime = System.currentTimeMillis();
        // 创建爬虫，设置站点地址为：https://www.cnblogs.com/，并启动5个线程
        Spider.create(new WebMagicProcessor())
                .addUrl("https://www.chinadaily.com.cn/china/governmentandpolicy/")
                .addPipeline(new JsonFilePipeline("D:\\temp\\"))
                .thread(5).run();
        endTime = System.currentTimeMillis();
        System.out.println("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒，抓取了"+count+"条记录");
    }

    /**
     * 获取站点page，并进行匹配
     * @param page
     */
    public void process(Page page) {
        // 加入满足条件的链接
        Html html = page.getHtml();
        page.addTargetRequests(
                html.xpath("//*span[@class=\"tw3_01_2_t\"]/h4/a/@href").all());
        //获取页面需要的内容
        String header = html.xpath("//div[@class=\"main_art\"]/div[@class=\"lft_art\"]/h1/text()").get();
        page.putField("标题", header);
        List<String> pics = html.xpath("//div[@id=\"content\"]/figure/img/@src").all();
        page.putField("图片", pics);
        List<String> contents = html.xpath("//div[@id=\"content\"]/p/text()").all();
        page.putField("内容", contents);
        count ++;
    }

    public Site getSite() {
        return site;
    }
}
