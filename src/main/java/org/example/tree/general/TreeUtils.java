package org.example.tree.general;

import java.util.*;

/**
 * @date: 2022/7/25
 * @FileName: TreeUtils
 * @author: Yan
 * @Des: TreeUtils用来生成树形结构，以及获取所有叶子节点等操作
 */
public class TreeUtils {

    /**
     * 根据所有树节点列表，按默认条件生成含有所有树形结构的列表
     * 主要用于组建简单树形结构
     * @param allData 树形节点列表
     * @param <E>   节点类型
     * @return 树形结构列表
     */
    public static <E extends TreeNode<?,?,?>> List<E> generateTrees(List<E> allData) {
        List<E> roots = new ArrayList<>();
        // 使用迭代器操作list元素
        for (Iterator<E> iterator = allData.iterator(); iterator.hasNext(); ) {
            E node = iterator.next();
            if (node.isRoot()) {
                node.setLevel(0);
                // 获取所有的根节点
                roots.add(node);
                // 从所有节点列表中删除该节点，以免后续重复遍历该节点
                iterator.remove();
            }
        }
        roots.forEach(r -> {
            getChildren(r, allData);
        });
        return roots;
    }

    /**
     * 根据所有树节点列表，按自定义条件---->获取符合条件的父节点
     * @param allData   所有树形结构结点
     * @param rootCondition 父节点的判定规则
     * @param <E> 结点类型
     * @param <RC> 父节点自定义判定方法的参数类型
     * @return 按自定义条件获取符合条件的父节点
     */
    public static <E extends TreeNode<?, RC, ?>, RC> List<E> generateTrees(List<E> allData, RC rootCondition) {
        List<E> roots = new ArrayList<>();
        // 使用迭代器操作list元素
        for (Iterator<E> iterator = allData.iterator(); iterator.hasNext(); ) {
            E node = iterator.next();
            // 按条件筛选根节点
            if (node.isRoot(rootCondition)) {
                node.setLevel(0);
                // 获取所有的根节点
                roots.add(node);
                // 从所有节点列表中删除该节点，以免后续重复遍历该节点
                iterator.remove();
            }
        }
        // 返回按条件查询到的父节点
        return roots;
    }

    /**
     * 给父节点填充叶子结点
     * @param parent    父节点
     * @param nodes 所有结点集合
     * @param <T>   父节点的对象类型
     * @param <E>   集合的类型
     */
    @SuppressWarnings("all")
    public static <T extends TreeNode, E extends TreeNode> void getChildren(T parent, List<E> allData) {
        List<E> children = new ArrayList<>();
        for (Iterator<E> ite = allData.iterator(); ite.hasNext(); ) {
            E node = ite.next();
            // 找出与当前父节点关联的叶子结点
            if (Objects.equals(node.getParentId(), parent.getTreeNodeId())) {
                node.setLevel(parent.getLevel() + 1);
                children.add(node);
                // 从所有节点列表中删除该节点，以免后续重复遍历该节点
                ite.remove();
            }
        }
        System.out.println(children);
        // 如果孩子为空，则直接返回,否则继续递归设置孩子的孩子
        if (children.isEmpty()) {
            return;
        }
        parent.setChildren(children);

        // 继续递归叶子结点的遍历子节点
        children.forEach(m -> {
            // 递归设置子节点
            getChildren(m, allData);
        });
    }

    /**
     * 按照自定义的规则，给父节点填充叶子结点
     * @param parent    父节点
     * @param allData   所有树形结构结点
     * @param leafConfition 叶子结点的自定义判定规则的参数类型
     * @param <T>   父节点对象类型
     * @param <E>   集合类型
     * @param <LC>  参数类型
     */
    @SuppressWarnings("all")
    public static <T extends TreeNode, E extends TreeNode<?, ?, LC>, LC> void getChildren(T parent, List<E> allData, LC leafConfition) {
        List<E> children = new ArrayList<>();
        Object parentId = parent.getTreeNodeId();
        for (Iterator<E> ite = allData.iterator(); ite.hasNext(); ) {
            E node = ite.next();
            // 按自定义条件筛选子节点,null则表示没有自定义条件
            if (Objects.isNull(leafConfition) || node.isChildren(leafConfition)){
                // 找出与当前父节点关联的叶子结点
                if (Objects.equals(node.getParentId(), parentId)) {
                    node.setLevel(parent.getLevel() + 1);
                    children.add(node);
                    // 从所有节点列表中删除该节点，以免后续重复遍历该节点
                    ite.remove();
                }
            }
        }
        // 如果孩子为空，则直接返回,否则继续递归设置孩子的孩子
        if (children.isEmpty()) {
            return;
        }
        parent.setChildren(children);

        // 继续递归叶子结点的遍历子节点
        children.forEach(m -> {
            // 递归设置子节点
            getChildren(m, allData);
        });
    }



    /**
     * 根据获取特定结点的子树，通过isParent来判断是否需要父节点
     * @param root  根节点（子节点）
     * @param allData   全部结点数据
     * @param isParent  是否需要父节点
     * @param <T>   结点的对象类型
     * @return
     */
    public static <T extends TreeNode<?, ?, ?>> List getTreeByNode(T root, List<T> allData, boolean isParent){
        List<T> tree = new ArrayList<>();
        if (isParent){
            root.setLevel(0);
            tree.add(root);
            // 包含父节点
            // 填充叶子结点
            tree.forEach(child -> getChildren(child, allData));
            return tree;
        } else {
            // 不含父节点
            // 填充叶子结点
            getChildren(root, allData);
            return root.getChildren();
        }
    }

    public static void main(String[] args) {
        Boolean test = null;
    }



    /**
     * 根据获取特定结点的子树，通过isParent来判断是否需要父节点,通过leafConfition来按照自定义的规则给父节点填充子节点
     * @param root  根节点（子节点）
     * @param allData   全部结点数据
     * @param isParent  是否需要父节点
     * @param leafCondition 定义叶子结点的判定参数
     * @param <T>   结点的对象类型
     * @param <LC>  自定义叶子结点的判定参数的对象类型
     * @return
     */
    public static <T extends TreeNode<?, ?, LC>, LC> List getTreeByNode(T root, List<T> allData, boolean isParent, LC leafCondition){
        List<T> tree = new ArrayList<>();
        if (isParent){
            root.setLevel(0);
            tree.add(root);
            // 包含父节点
            // 填充叶子结点
            tree.forEach(child -> getChildren(child, allData, leafCondition));
            return tree;
        } else {
            // 不含父节点
            // 填充叶子结点
            getChildren(root, allData, leafCondition);
            return root.getChildren();
        }
    }


    /**
     * 根据结点id获取特定结点的子树，通过isParent来判断是否需要父节点
     * @param rootId    结点id
     * @param allData   所有结点数据
     * @param isParent  是否需要父结点
     * @param <T>   结点id的对象类型
     * @param <E>   集合的类型
     * @return
     */
    public static <T, E extends TreeNode<?, ?, ?>> List getTreeByNodeId(T rootId, List<E> allData, boolean isParent){
        List<E> tree = new ArrayList<>();
        E root = null;
        for (Iterator<E> iterator = allData.iterator(); iterator.hasNext(); ){
            E node = iterator.next();
            if (rootId.equals(node.getTreeNodeId())){
                node.setLevel(0);
                tree.add(node);
                root = node;
                iterator.remove();
            }
        }
        System.out.println(root);
        // 是否需要父节点
        if (isParent){
            // 需要父节点
            // 填充叶子结点
            tree.forEach(child -> getChildren(child, allData));
            return tree;
        } else {
            // 不需要父节点
            // 填充叶子结点
            getChildren(root, allData);
            return root.getChildren();
        }
    }


    /**
     * 根据结点id获取特定结点的子树，通过isParent来判断是否需要父节点
     * @param rootId    结点id
     * @param allData   所有结点数据
     * @param isParent  是否需要父结点
     * @param leafCondition 定义叶子结点的判定参数
     * @param <T>   结点id的对象类型
     * @param <E>   集合的类型
     * @param <LC>  自定义叶子结点的判定参数的对象类型
     * @return
     */
    public static <T, E extends TreeNode<?, ?, LC>, LC> List getTreeByNodeId(T rootId, List<E> allData, boolean isParent, LC leafCondition){
        List<E> tree = new ArrayList<>();
        E root = null;
        for (Iterator<E> iterator = allData.iterator(); iterator.hasNext(); ){
            E node = iterator.next();
            if (Objects.equals(rootId, node.getTreeNodeId())){
                root.setLevel(0);
                tree.add(node);
                root = node;
                iterator.remove();
            }
        }
        System.out.println(root);
        // 是否需要父节点
        if (isParent){
            // 需要父节点
            // 填充叶子结点
            tree.forEach(child -> getChildren(child, allData, leafCondition));
            return tree;
        } else {
            // 不需要父节点
            // 填充叶子结点
            getChildren(root, allData, leafCondition);
            return root.getChildren();
        }
    }


    /**
     * 遍历树型结构，并且根据回调函数执行相应的操作处理
     * @param tree  树
     * @param handle    回调函数
     * @param <E>   集合类型
     * @param <K>   结果集的key
     * @param <V>   结果集的value
     * @return  返回一个结果集的map
     */
    public static <E extends TreeNode<?,?,?>, K, V> Map<K,V> traverseTree(List<E> tree, FunctionHandle<E, K, V> handle){
        Map<K, V> resultMap = new HashMap<>();
        for (Iterator<E> iterator = tree.iterator(); iterator.hasNext(); ){
            E node = iterator.next();
            if (handle != null){
                handle.callback(node, resultMap);
            }
            if (node.hasChild()){
                recursiveTree(node.getChildren(), resultMap, handle);
            }
        }
        return resultMap;
    }


    /**
     * 递归遍历子树，获取相应的处理结果
     * @param children  子树集合
     * @param resultMap 结果集
     * @param handle    回调函数
     * @param <E>   集合类型
     */
    public static <E extends TreeNode<?,?,?>> void recursiveTree(List<E> children, Map resultMap, FunctionHandle handle){
        for (Iterator<E> iterator = children.iterator(); iterator.hasNext(); ){
            E child = iterator.next();
            if (handle != null){
                handle.callback(child, resultMap);
            }
            if (child.hasChild()){
                recursiveTree(child.getChildren(), resultMap, handle);
            }
        }
    }

}