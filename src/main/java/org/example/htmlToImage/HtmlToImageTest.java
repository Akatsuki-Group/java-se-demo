package org.example.htmlToImage;

import gui.ava.html.parser.HtmlParser;
import gui.ava.html.parser.HtmlParserImpl;
import gui.ava.html.renderer.ImageRenderer;
import gui.ava.html.renderer.ImageRendererImpl;

import java.io.File;


/**
 * @author yuanct
 * @since 1.2
 */
public class HtmlToImageTest {


    public static void main(String[] args) {
        String HtmlTemplateStr = "\t<div style=\"height: 500px;width: 500px;background: #aee0ff;\">\n" +
                "\t\t这个是一个div\n" +
                "\t\t<h1>标题</h1>\n" +
                "\t\t<ol>\n" +
                "\t\t\t<li>a</li>\n" +
                "\t\t</ol>\n" +
                "\t\t<img style=\"margin-left: 1500px;\" width=\"300px\" height=\"200px\" src=\"https://inews.gtimg.com/newsapp_bt/0/11911825373/1000\">\n" +
                "\t</div>";

        HtmlParser htmlParser = new HtmlParserImpl();
        htmlParser.load(new File("/home/tian/Documents/code/study/java-workspace/Spring Boot 系列/Docker(13) docker-compose安装Graylog实现日志监控/Docker(13) docker-compose安装Graylog实现日志监控.html"));
        // html 是我的html代码
        ImageRenderer imageRenderer = new ImageRendererImpl(htmlParser);
        imageRenderer.saveImage("/home/tian/Pictures/Screenshots/lcxq1.png");

        ImageRenderer imageRenderer2 = new ImageRendererSubImpl(htmlParser);
        imageRenderer2.saveImage("/home/tian/Pictures/Screenshots/lcxq2.png");
    }
}
