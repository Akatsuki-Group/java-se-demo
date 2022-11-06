package org.example.jvm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {


    private Integer id;
    private String name;


    //User类需要重写finalize方法
    @Override
    protected void finalize() throws Throwable {
        OOMTest.list.add(this);
        System.out.println("关闭资源，userid=" + id + "即将被回收");
    }
}
