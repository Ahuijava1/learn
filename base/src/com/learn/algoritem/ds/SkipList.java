package com.learn.algoritem.ds;

import java.util.Random;

/**
 * SkipList
 * 跳表
 *  参考文章：https://mp.weixin.qq.com/s?__biz=MzIxMjE5MTE1Nw==&mid=2653211187&idx=1&sn=c062ab9598cf0af12acbf849478bb0d3&chksm=8c99b8e9bbee31ff9b1c86cfb32030b4cbabc0b98e9be850efe46fffb6eb6bac335f8b2b7b43&scene=126&sessionid=1598494016&key=c6895a4a7d7f12ef9cca2d336856d0ec49f247b55edf11b66c8a377c71ad80917842d30d442d40319256132772498a2c1ed002b1cb57ffd942d6f5fee80be5b1019aaa92e83fdf1ca6d325932cf947f5bdee162f71e08c7d718e88eac60037933acd1e51b83108b6b3047f0df73f494c17103d3f3a229540ef1ca09aaaa6c5ad&ascene=1&uin=NjUwMTY2OTI1&devicetype=Windows+10+x64&version=62090529&lang=zh_CN&exportkey=A0P1r%2F28XPsLxclpduvwfWo%3D&pass_ticket=jmQMn5tMyQTHyZKPKQSC1JUEfFKDyrGgkfcC7eS%2F5UrTrb6L%2Fl%2BNN5LaM4Bgz2ZA
 * @author zhengchaohui
 * @date 2020/8/27 9:43
 */
public class SkipList {

    /**
     * 结点“晋升”的概率
     */
    private static final double PROMOTE_RATE = 0.5;
    private Node head,tail;
    /**
     *当前链表最大层
     */
    private int maxLevel;

    public SkipList() {
        head = new Node(Integer.MIN_VALUE);
        tail = new Node(Integer.MAX_VALUE);
        head.right = tail;
        tail.left = head;
    }

    /**
     * 查找结点
     * @param data
     * @return
     */
    public Node search(int data){
        Node p= findNode(data);
        if(p.data == data){
            System.out.println("找到结点：" + data);
            return p;
        }
        System.out.println("未找到结点：" + data);
        return null;
    }

    /**
     * 找到值对应的前置结点
     * @param data
     * @return
     */
    private Node findNode(int data){
        Node node = head;
        while(true){
            // 先往右找到比目标值data小的或者等的节点
            while (node.right.data!=Integer.MAX_VALUE && node.right.data<=data) {
                node = node.right;
            }
            // node的down为null说明已经到了最底层的数据链表，不再往下走
            if (node.down == null) {
                break;
            }
            // 往下走
            node = node.down;
        }
        // 返回目标值对应的节点，如不存在，返回前一个节点
        return node;
    }

    /**
     * 插入结点
     * @param data
     */
    public void insert(int data){
        // 如果data不存在于链表，则返回的是preNode
        // 如果存在，返回该data对应的Node。此时不能继续加入（因为Node目前只有data一个值，更新也无益）
        Node preNode= findNode(data);
        //如果data相同，直接返回
        if (preNode.data == data) {
            return;
        }
        Node node=new Node(data);
        appendNode(preNode, node);
        int currentLevel=0;
        //随机决定结点是否“晋升”
        Random random = new Random();
        while (random.nextDouble() < PROMOTE_RATE) {
            //如果当前层已经是最高层，需要增加一层
            if (currentLevel == maxLevel) {
                addLevel();
            }
            //找到上一层的前置节点
            // preNode.up为空说明preNode并不是上层索引
            while (preNode.up==null) {
                // 找到离node最近的preNode的索引节点
                preNode=preNode.left;
            }
            preNode=preNode.up;
            //把“晋升”的新结点插入到上一层
            Node upperNode = new Node(data);
            appendNode(preNode, upperNode);
            upperNode.down = node;
            node.up = upperNode;
            node = upperNode;
            currentLevel++;
        }
    }

    /**
     * 在前置结点后面添加新结点
     * @param preNode
     * @param newNode
     */
    private void appendNode(Node preNode, Node newNode){
        newNode.left=preNode;
        newNode.right=preNode.right;
        preNode.right.left=newNode;
        preNode.right=newNode;
    }

    /**
     * 增加一层
     */
    private void addLevel(){
        maxLevel++;
        Node p1=new Node(Integer.MIN_VALUE);
        Node p2=new Node(Integer.MAX_VALUE);
        p1.right=p2;
        p2.left=p1;
        p1.down=head;
        head.up=p1;
        p2.down=tail;
        tail.up=p2;
        head=p1;
        tail=p2;
    }

    /**
     * 删除结点
     * @param data
     * @return
     */
    public boolean remove(int data){
        Node removedNode = search(data);
        if(removedNode == null){
            return false;
        }

        int currentLevel=0;
        while (removedNode != null){
            removedNode.right.left = removedNode.left;
            removedNode.left.right = removedNode.right;
            //如果不是最底层，且只有无穷小和无穷大结点，删除该层
            if(currentLevel != 0 && removedNode.left.data == Integer.MIN_VALUE && removedNode.right.data == Integer.MAX_VALUE){
                removeLevel(removedNode.left);
            }else {
                currentLevel ++;
            }
            removedNode = removedNode.up;
        }

        return true;
    }

    /**
     * 删除一层
     * @param leftNode
     */
    private void removeLevel(Node leftNode){
        Node rightNode = leftNode.right;
        //如果删除层是最高层
        if(leftNode.up == null){
            leftNode.down.up = null;
            rightNode.down.up = null;
        }else {
            leftNode.up.down = leftNode.down;
            leftNode.down.up = leftNode.up;
            rightNode.up.down = rightNode.down;
            rightNode.down.up = rightNode.up;
        }
        maxLevel --;
    }

    /**
     * 输出底层链表
     */
    public void printList() {
        Node node=head;
        while (node.down != null) {
            node = node.down;
        }
        while (node.right.data != Integer.MAX_VALUE) {
            System.out.print(node.right.data + " ");
            node = node.right;
        }
        System.out.println();
    }

    /**
     * 链表结点类
     */
    public class Node {
        public int data;
        //跳表结点的前后和上下都有指针
        public Node up, down, left, right;

        public Node(int data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        SkipList list=new SkipList();
        list.insert(50);
        list.insert(15);
        list.insert(13);
        list.insert(20);
        list.insert(100);
        list.insert(75);
        list.insert(99);
        list.insert(76);
        list.insert(83);
        list.insert(65);
        list.printList();
        list.search(50);
        list.remove(50);
        list.search(50);
    }
}
