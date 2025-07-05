package pers.zkx.algo.base.structures;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhangkuixing
 * @date: 2025/7/5 23:39
 */
@Getter
public class Node<T> {
    private final T value;
    private final List<Node<T>> children;

    public Node(final T value) {
        this.value = value;
        this.children = new ArrayList<>();
    }

    public void addChild(Node<T> child) {
        children.add(child);
    }
}
