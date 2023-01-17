package org.example.jvppeteer;

import com.ruiyun.jvppeteer.core.Puppeteer;
import com.ruiyun.jvppeteer.core.browser.Browser;
import com.ruiyun.jvppeteer.core.browser.BrowserFetcher;
import com.ruiyun.jvppeteer.core.page.Page;
import com.ruiyun.jvppeteer.options.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * @author arthur
 */
public class JvppeteerTest {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        //launchBrowser();
        //navigateToPage();
        //generatePDF();
        //tracing();
        screenshot();
    }


    //启动浏览器
    public static void launchBrowser() throws IOException, ExecutionException, InterruptedException {
        ArrayList<String> argList = new ArrayList<>();
        //自动下载，第一次下载后不会再下载
        BrowserFetcher.downloadIfNotExist(null);
        LaunchOptions options = new LaunchOptionsBuilder().withArgs(argList).withHeadless(false).build();
        argList.add("--no-sandbox");
        argList.add("--disable-setuid-sandbox");
        Puppeteer.launch(options);

    }

    //导航至某个页面
    public static void navigateToPage() throws IOException, ExecutionException, InterruptedException {
        //自动下载，第一次下载后不会再下载
        BrowserFetcher.downloadIfNotExist(null);
        ArrayList<String> argList = new ArrayList<>();
        LaunchOptions options = new LaunchOptionsBuilder().withArgs(argList).withHeadless(false).build();
        argList.add("--no-sandbox");
        argList.add("--disable-setuid-sandbox");
        Browser browser = Puppeteer.launch(options);
        Browser browser2 = Puppeteer.launch(options);
        Page page = browser.newPage();
        page.goTo("https://www.taobao.com/about/");
        browser.close();

        Page page1 = browser2.newPage();
        page1.goTo("https://www.taobao.com/about/");

    }

    //生成页面的PDF
    public static void generatePDF() throws IOException, ExecutionException, InterruptedException {
        //自动下载，第一次下载后不会再下载
        BrowserFetcher.downloadIfNotExist(null);
        ArrayList<String> arrayList = new ArrayList<>();
        //生成pdf必须在无厘头模式下才能生效
        LaunchOptions options = new LaunchOptionsBuilder().withArgs(arrayList).withHeadless(true).build();
        arrayList.add("--no-sandbox");
        arrayList.add("--disable-setuid-sandbox");
        Browser browser = Puppeteer.launch(options);
        Page page = browser.newPage();
        page.goTo("https://www.baidu.com/?tn=98012088_10_dg&ch=3");
        PDFOptions pdfOptions = new PDFOptions();
        pdfOptions.setPath("test.pdf");
        page.pdf(pdfOptions);
        page.close();
        browser.close();
    }

    //TRACING 性能分析
    public static void tracing() throws IOException, ExecutionException, InterruptedException {
        //自动下载，第一次下载后不会再下载
        BrowserFetcher.downloadIfNotExist(null);
        ArrayList<String> argList = new ArrayList<>();
        LaunchOptions options = new LaunchOptionsBuilder().withArgs(argList).withHeadless(true).build();
        argList.add("--no-sandbox");
        argList.add("--disable-setuid-sandbox");
        Browser browser = Puppeteer.launch(options);
        Page page = browser.newPage();
        //开启追踪
        page.tracing().start("C:\\Users\\howay\\Desktop\\trace.json");
        page.goTo("https://www.baidu.com/?tn=98012088_10_dg&ch=3");
        page.tracing().stop();
    }
    //页面截图
    public static void screenshot() throws IOException, ExecutionException, InterruptedException {
        BrowserFetcher.downloadIfNotExist(null);
        ArrayList<String> arrayList = new ArrayList<>();
        LaunchOptions options = new LaunchOptionsBuilder().withArgs(arrayList).withHeadless(true).build();
        arrayList.add("--no-sandbox");
        arrayList.add("--disable-setuid-sandbox");
        Browser browser = Puppeteer.launch(options);
        Page page = browser.newPage();
        page.goTo("https://www.baidu.com/?tn=98012088_10_dg&ch=3");
        ScreenshotOptions screenshotOptions = new ScreenshotOptions();
        //设置截图范围
        Clip clip = new Clip(1.0,1.56,400,400);
        screenshotOptions.setClip(clip);
        //设置存放的路径
        screenshotOptions.setPath("test.png");
        page.screenshot(screenshotOptions);
    }

}
