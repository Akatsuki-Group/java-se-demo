package org.example.tree.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @date: 2022/7/25
 * @FileName: Menu
 * @author: Yan
 * @Des:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    private Integer id;

    private String name;

    private Integer parentId;

    private List<Menu> children;

    public Menu(Integer id, String name, Integer parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

}