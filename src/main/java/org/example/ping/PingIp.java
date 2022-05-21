package org.example.ping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

public class PingIp {

    /**
     * * 测试是否能ping通
     * * @param server
     * * @param timeout
     * * @return
     */
    public static boolean isReachable(String remoteIpAddress, int pingTimes, int timeOut) {
        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();
        // 将要执行的ping命令,此命令是windows格式的命令
        String pingCommand = "ping " + remoteIpAddress + " -n " + pingTimes + " -w " + timeOut;
        try {
            Process p = r.exec(pingCommand);
            if (p == null) {
                return false;
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream(), "gbk"));
            // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数
            int connectedCount = 0;
            String line = null;
            while ((line = in.readLine()) != null) {
                connectedCount += getCheckResult(line);
            }
            // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
            return connectedCount == pingTimes;


        } catch (Exception ex) {
            // 出现异常则返回假
            return false;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * * 若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
     * * @param line
     * * @return
     */
    private static int getCheckResult(String line) {
         System.out.println("控制台输出的结果为:"+line);
        Pattern pattern = compile("(\\d+ms)(\\s+)(TTL=\\d+)",
                CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        boolean result = isReachable("www.baidu.com", 1, 5000);
        System.out.println(result);
    }
}