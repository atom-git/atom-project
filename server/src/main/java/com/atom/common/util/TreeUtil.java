package com.atom.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author zr
 * @description 树状结构工具类 提供通用树结构的平铺/聚合方法
 * @date 2021/4/22
 */
@SuppressWarnings("unused")
public class TreeUtil {

    /**
     * 遍历树结构
     *
     * @param root              节点树结构
     * @param loadChildrenNodes 加载树的子节点列表函数 接收一个节点 返回节点的子结构
     * @param judge             是否使用此节点
     * @param write             写入子节点
     * @param <T>               树节点对象
     */
    public static <T> List<T> filters(List<T> root, Function<T, List<T>> loadChildrenNodes, Predicate<T> judge, BiConsumer<T, List<T>> write) {
        List<T> result = new ArrayList<>();
        Stack<T> stack = new Stack<>();
        root.forEach(o -> {
            if (judge.test(o)) {
                stack.add(o);
                result.add(o);
            }
        });
        while (!stack.isEmpty()) {
            T o = stack.pop();
            List<T> childrens = loadChildrenNodes.apply(o);
            childrens = childrens == null ? new ArrayList<>() : childrens.stream()
                    .filter(judge)
                    .collect(Collectors.toList());
            if (childrens.size() > 0) {
                childrens.forEach(stack::push);
            }
            write.accept(o, childrens);
        }
        return result;
    }

    /**
     * 遍历树结构
     *
     * @param root              节点树结构
     * @param loadChildrenNodes 加载树的子节点列表函数 接收一个节点 返回节点的子结构
     * @param behavior          遍历到的节点行为
     * @param <T>               树节点对象
     */
    public static <T> void traversing(List<T> root, Function<T, List<T>> loadChildrenNodes, Consumer<T> behavior) {
        Stack<T> stack = new Stack<>();
        stack.addAll(root);
        while (!stack.isEmpty()) {
            T o = stack.pop();
            behavior.accept(o);
            List<T> childrens = loadChildrenNodes.apply(o);
            if (childrens != null && childrens.size() > 0) {
                childrens.forEach(stack::push);
            }
        }
    }

    /**
     * 平铺树结构
     *
     * @param root              节点树结构
     * @param loadChildrenNodes 加载树的子节点列表函数 接收一个节点 返回节点的子结构
     * @param <T>               树节点对象
     * @return 平铺结构
     */
    public static <T> List<T> tileTree(List<T> root, Function<T, List<T>> loadChildrenNodes) {
        List<T> list = new ArrayList<>();
        traversing(root, loadChildrenNodes, list::add);
        return list;
    }

    /**
     * 打印树信息
     *
     * @param list              树结构列表
     * @param loadChildrenNodes 加载树的子节点列表函数 接收一个节点 返回节点的子结构
     * @param <T>               树节点对象
     */
    public static <T> void printTree(List<T> list, Function<T, List<T>> loadChildrenNodes) {
        System.out.println("---------- Tree Nodes Print ----------");
        traversing(list, loadChildrenNodes, System.out::println);
        System.out.println("---------- Tree Nodes Print ----------");
    }

    /**
     * 聚合树结构
     *
     * @param list          节点列表结构
     * @param loadKey       节点唯一key读取 接收一个节点 返回节点的唯一key
     * @param loadParentKey 节点父节点key读取 接收一个节点 返回节点的父节点key
     * @param write         节点子项写入函数 接收待写入节点与节点子项 负责将子节点写入
     * @param <T>           节点对象
     * @param <R>           节点唯一key对象
     * @return 树结构
     */
    public static <T, R> List<T> polymerizationTree(List<T> list, Function<T, R> loadKey, Function<T, R> loadParentKey, BiConsumer<T, List<T>> write) {
        List<T> root = list.stream().filter(o -> loadParentKey.apply(o) == null).collect(Collectors.toList());
        Stack<T> stack = new Stack<>();
        root.forEach(stack::push);
        while (!stack.isEmpty()) {
            T o = stack.pop();
            R key = loadKey.apply(o);
            List<T> childrens = list.stream()
                    .filter(k -> key.equals(loadParentKey.apply(k)))
                    .collect(Collectors.toList());
            write.accept(o, childrens);
            if (childrens.size() > 0) {
                stack.addAll(childrens);
                childrens.forEach(stack::push);
            }
        }
        return root;
    }

    public static void main(String[] args) {
        List<Node> listNodes = new ArrayList<>();
        listNodes.add(new Node(1, "根节点1", null, null));
        listNodes.add(new Node(2, "根节点2", null, null));
        listNodes.add(new Node(3, "根节点3", null, null));
        listNodes.add(new Node(4, "1-1", 1, null));
        listNodes.add(new Node(5, "1-2", 1, null));
        listNodes.add(new Node(6, "2-1", 2, null));
        listNodes.add(new Node(7, "3-1", 3, null));
        listNodes.add(new Node(8, "1-1-1", 4, null));
        listNodes.add(new Node(9, "1-1-2", 4, null));
        printTree(listNodes, Node::getChildrens);
        // 聚合
        List<Node> treeNodes = polymerizationTree(listNodes, Node::getId, Node::getParentId, Node::setChildrens);
        printTree(treeNodes, Node::getChildrens);
        // 平铺
        listNodes = tileTree(treeNodes, Node::getChildrens);
        printTree(listNodes, Node::getChildrens);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class Node {
        private Integer id;
        private String name;
        private Integer parentId;
        private List<Node> childrens;

        @Override
        public String toString() {
            return "Node{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
