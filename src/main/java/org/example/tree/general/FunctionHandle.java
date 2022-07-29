package org.example.tree.general;

import java.util.Map;

/**
 * 泛型说明：
 *   N 表示传入回调函数的结点参数类型
 *   K 表示结果集的key
 *   V 表示结果集的value
 *
 * @date: 2022/7/28
 * @FileName: Handle
 * @author: yuanct
 * @Des: 定义一个函数式接口
 */
@FunctionalInterface
public interface FunctionHandle<N, K, V> {
    void callback(N node, Map<K, V> result);
}