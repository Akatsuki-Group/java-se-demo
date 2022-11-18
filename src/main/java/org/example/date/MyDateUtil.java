package org.example.date;

import cn.hutool.core.collection.CollUtil;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 计算多个时间的交集
 * @author yuancetian
 * date 2022-11-18
 * @since v1.0.0
 */
public class MyDateUtil {
    public static String getDateFormat(List<MyDate> dateList){
        //求出多个时间的并集
        HashMap<Date, Date> dateMap = new HashMap<>();
        for (MyDate mydate : dateList) {
            Date startTime = mydate.getStartTime();
            Date endTime = mydate.getEndTime();
            //第一个时间
            if (CollUtil.isEmpty(dateMap)) {
                dateMap.put(startTime, endTime);
            }
            HashMap<Date, Date> tempMap = new HashMap<>();
            Iterator<Map.Entry<Date, Date>> iterator = dateMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Date, Date> entry = iterator.next();
                Date start1 = entry.getKey();
                Date end1 = entry.getValue();
                if (startTime.after(end1) || endTime.before(start1)) {
                    tempMap.put(startTime, endTime);
                } else if (startTime.before(start1) && endTime.before(end1)) {
                    tempMap.put(startTime, end1);
                    iterator.remove();
                } else if (startTime.before(start1) && endTime.before(end1)) {
                    tempMap.put(startTime, endTime);
                    iterator.remove();
                } else if (startTime.before(end1) && endTime.after(end1)) {
                    tempMap.put(start1, endTime);
                }
            }
            dateMap.putAll(tempMap);
        }
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        dateMap.forEach((k, v) -> {
            sb.append(sdf.format(k) + "-" + sdf.format(v) + ",");
        });
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}
