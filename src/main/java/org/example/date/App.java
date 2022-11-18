package org.example.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuancetian
 * date 2022-11-18
 * @since v1.0.0
 */
public class App {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        List<MyDate> dateList = new ArrayList<>();

        MyDate myDate1 = new MyDate();
        myDate1.setStartTime(sdf.parse("2019/08/01"));
        myDate1.setEndTime(sdf.parse("2019/09/01"));

        MyDate myDate2 = new MyDate();
        myDate2.setStartTime(sdf.parse("2019/08/15"));
        myDate2.setEndTime(sdf.parse("2019/09/15"));


        MyDate myDate3 = new MyDate();
        myDate3.setStartTime(sdf.parse("2019/08/04"));
        myDate3.setEndTime(sdf.parse("2019/08/15"));

        dateList.add(myDate1);
        dateList.add(myDate2);

        System.out.println(MyDateUtil.getDateFormat(dateList));

    }
}
