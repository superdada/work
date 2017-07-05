package cn.com.ailbb.server.model;

import java.util.*;

/**
 * Created by xzl on 2017/5/25.
 */
/**
 * 节点比较器
 */
class NodeIdComparator implements Comparator {
    // 按照节点编号比较
    public int compare(Object o1, Object o2) {
        int j1 = Integer.parseInt(((Node)o1).id);
        int j2 = Integer.parseInt(((Node)o2).id);
        return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));
    }
}
