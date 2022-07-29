package org.example.tree.test;

import org.example.tree.general.MenuVo;
import org.example.tree.general.TreeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @date: 2022/7/25
 * @FileName: TreeController
 * @author: yuanct
 * @Des:
 */
@RestController
@RequestMapping("/tree")
public class TreeController {

    public static List<Menu> getMenus() {
        List<Menu> menus = Arrays.asList(
                new Menu(1, "A公司", 0),
                new Menu(2, "a销售部", 14),
                new Menu(3, "财税部", 1),
                new Menu(4, "商务部", 1),
                new Menu(5, "综合部", 1),
                new Menu(6, "a销售1部", 2),
                new Menu(7, "a销售2部", 2),
                new Menu(8, "a销售3部", 2),
                new Menu(9, "a销售4部", 2),
                new Menu(10, "b销售部", 14),
                new Menu(11, "b销售1部", 10),
                new Menu(12, "b销售2部", 10),
                new Menu(13, "人事部", 1),
                new Menu(14, "销售部", 1));
        return menus;
    }

    @RequestMapping
    public List<Menu> getTree(){
        List<Menu> menus = getMenus();
        List<Menu> menusTree = menus.stream()
                .filter(menu -> menu.getParentId() == 0)
                .map(menu -> {
                    menu.setChildren(getChildrens(menu, menus));
                    return menu;
                })
                .collect(Collectors.toList());
        return menusTree;
    }

    public static List<Menu> getChildrens(Menu root, List<Menu> allMenus){
        List<Menu> childrenTree = allMenus.stream()
                .filter(menu -> Objects.equals(menu.getParentId(), root.getId()))
                .peek(menu -> menu.setChildren(getChildrens(menu, allMenus)))
                .collect(Collectors.toList());
        return childrenTree;
    }


    @GetMapping("/test")
    public List<Object> getTree2(){
        List<MenuVo> menusVoList = new ArrayList<>(Arrays.asList(
                new MenuVo(1, "A公司", 0),
                new MenuVo(2, "a销售部", 14),
                new MenuVo(3, "财税部", 1),
                new MenuVo(4, "商务部", 1),
                new MenuVo(5, "综合部", 1),
                new MenuVo(6, "a销售1部", 2),
                new MenuVo(7, "a销售2部", 2),
                new MenuVo(8, "a销售3部", 2),
                new MenuVo(9, "a销售4部", 2),
                new MenuVo(10, "b销售部", 14),
                new MenuVo(11, "b销售1部", 10),
                new MenuVo(12, "b销售2部", 10),
                new MenuVo(13, "人事部", 1),
                new MenuVo(14, "销售部", 1)));

        // 组装树形结构
        List<MenuVo> menuVotree = TreeUtils.generateTrees(menusVoList);

        // 遍历树，回调处理得出相应结果
        Map<Integer, String> result = TreeUtils.traverseTree(menuVotree, (treeNode, resultMap) -> {
            if (treeNode.getName().contains("a")) {
                resultMap.put(treeNode.getTreeNodeId(), treeNode.getName());
            }
        });
        List<Object> objects = new ArrayList<>();
        objects.add(result);

        // 按自定义条件组装树
//        List<MenuVo> menuVoTreeByCondition = TreeUtils.generateTrees(menusVoList,true);
//        menuVoTreeByCondition.forEach(node -> TreeUtils.getChildren(node, menusVoList, 1));

        // 获取对应结点的子树，true表示要留有父节点，false表示不留不父节点
//        List<MenuVo> childTreeByParent = TreeUtils.getTreeByNode(new MenuVo(14, "销售部", 1), menusVoList, true);

        // 通过父节点id获取其子树，true表示要留有父节点，false表示不留不父节点
//        List<MenuVo> treeNodesList = TreeUtils.getTreeByNodeId(14, menusVoList, true);


//        // 组装树形结构
//        List<MenuVo> menuVotree = TreeUtils.generateTrees(menusVoList);
//
//// 遍历树，回调处理得出相应结果
//        Map<Integer, String> result = TreeUtils.traverseTree(menuVotree, (treeNode, resultMap) -> {
//            // 找出部门名字含有或者b的部门名，存入结果集并返回
//            if (treeNode.getName().contains("b")) {
//                resultMap.put(treeNode.getTreeNodeId(), treeNode.getName());
//            }
//        });

//        // 组装树形结构
//        List<MenuVo> menuVotree = TreeUtils.generateTrees(menusVoList);
//
//// 遍历树，回调处理得出相应结果
//        Map<String, Integer> result = TreeUtils.traverseTree(menuVotree, (treeNode, resultMap) -> {
//            if (!treeNode.hasChild()){
//                if (treeNode.getName().contains("销售")){
//                    resultMap.put("销售部", resultMap.get("销售部") == null ? 1 : resultMap.get("销售部") + 1);
//                }
//                if (treeNode.getName().contains("财税")){
//                    resultMap.put("财税部", resultMap.get("财税部") == null ? 1 : resultMap.get("财税部") + 1);
//                }
//                if (treeNode.getName().contains("商务")){
//                    resultMap.put("商务部", resultMap.get("商务部") == null ? 1 : resultMap.get("商务部") + 1);
//                }
//                if (treeNode.getName().contains("综合")){
//                    resultMap.put("综合部", resultMap.get("综合部") == null ? 1 : resultMap.get("综合部") + 1);
//                }
//            }
//        });
        return objects;
    }
}