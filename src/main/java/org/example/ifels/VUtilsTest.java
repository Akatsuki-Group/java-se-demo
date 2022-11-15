package org.example.ifels;

/**
 * @author yuancetian
 * date 2022-11-15
 * @since v1.0
 */
public class VUtilsTest {
    public static void main(String[] args) {
        //VUtils.isTure(true).throwMessage("参数为true抛出异常");
        //VUtils.isTure(false).throwMessage("参数为false抛出异常");

        VUtils.isTureOrFalse(true).trueOrFalseHandle(() -> System.out.println("参数为true时执行的操作"), () -> System.out.println("参数为false时执行的操作"));
        VUtils.isTureOrFalse(false).trueOrFalseHandle(() -> System.out.println("参数为true时执行的操作"), () -> System.out.println("参数为false时执行的操作"));
        VUtils.isBlankOrNoBlank("123")
                .presentOrElseHandle(
                        (str) -> System.out.println("参数不为空时执行的操作" + str),
                        () -> System.out.println("参数为空时执行的操作"));
        VUtils.isBlankOrNoBlank("").presentOrElseHandle(
                (str) -> System.out.println("参数不为空时执行的操作" + str),
                () -> System.out.println("参数为空时执行的操作"));
    }
}
