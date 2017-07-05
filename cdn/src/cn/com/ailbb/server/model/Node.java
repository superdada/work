package cn.com.ailbb.server.model;

/**
 * Created by xzl on 2017/5/25.
 */
public class Node {

    /**
     * 节点编号
     */
    public String id;
    /**
     * 节点内容
     */
    public String name;
    /**
     * 父节点编号
     */
    public String parentId;
    /**
     * 是否被选中
     */
    public boolean  checked =false;
    /**
     * 是否显示
     */
    public boolean  nocheck = false;
    /**
     * checkbox是否禁用
     */
    public boolean chkDisabled =true;
    /**
     * 孩子节点列表
     */
    private Children children = new Children();

    public Node(){

    }

    public Node(String id,String name,String parentId){
        this.id=id;
        this.name=name;
        this.parentId =parentId;
    }
    public Node(String id,String name,String parentId,boolean checked){
        this.id=id;
        this.name=name;
        this.parentId =parentId;
        this.checked =checked;
    }

    // 先序遍历，拼接JSON字符串
    public String toString() {
        String result = "{"
                + "id : '" + id + "'"
                + ", name : '" + name + "'";

        if (children != null && children.getSize() != 0) {
            result += ", children : " + children.toString();
        }
        /*else {
            result += ", leaf : true";
        }*/

        return result + "}";
    }
    //按需求拼接字符串
    public String toString2() {
        String result = "{"
                + "id : '" + id + "'"
                + ", name : '" + name + "'"
                +",checked : "+checked
                +",nocheck:" +nocheck
                +",chkDisabled:"+chkDisabled;

        if (children != null && children.getSize() != 0) {
            result += ", children : " + children.toString2();
        }
        /*else {
            result += ", leaf : true";
        }*/

        return result + "}";
    }
    // 兄弟节点横向排序
    public void sortChildren() {
        if (children != null && children.getSize() != 0) {
            children.sortChildren();
        }
    }

    // 添加孩子节点
    public void addChild(Node node) {
        this.children.addChild(node);
    }


}
