package leecode;

import javafx.scene.control.skin.SliderSkin;

import javax.swing.*;
import java.util.List;

//单（双）向链表节点
class ListNode{
    int val;
    ListNode next;
    ListNode prev;

    ListNode(){}

    ListNode(int val){
        this.val = val;
    }
    ListNode(int val, ListNode next){
        this.val = val;
        this.next = next;
    }
}

/**
 * 链表
 * @author neil
 */
public class LinkedList {

    /**
     * 题目：203 移除链表元素
     * 添加虚节点方式
     * @param head
     * @param val
     * @return
     */
    public static ListNode removeElements(ListNode head, int val){
        if(head == null){
            return head;
        }
        //添加空节点
        ListNode dummy = new ListNode(-1, head);
        ListNode pre = dummy;
        ListNode cur = head;
        while (cur != null){
            //移除元素
            if(cur.val == val){
                pre.next = cur.next;
            }else {
                pre = cur;
            }
            cur = cur.next;
        }
        return dummy.next;
    }

    /**
     * 题目：206 反转链表
     * @param head
     * @return
     */
    //双指针法
    public static ListNode reverseList1(ListNode head){
        ListNode prev = null;
        ListNode cur = head;
        ListNode temp = null;
        while (cur != null){
            temp = cur.next;
            cur.next = prev;
            prev = cur;
            cur = temp;
        }
        return prev;
    }

    //递归
    public static ListNode reverseList2(ListNode head){
        return reverse(null, head);
    }

    public static ListNode reverse(ListNode prev, ListNode cur){
        if(cur == null){
            return prev;
        }
        ListNode temp = null;
        temp = cur.next;
        cur.next = prev;
        return reverse(cur, temp);
    }

    /**
     * 题目：26 两两交换链表中的节点
     */
    public static ListNode swapPairs(ListNode head){
        ListNode dummyNode = new ListNode(0);
        dummyNode.next = head;
        ListNode cur = dummyNode;

        while (cur.next != null && cur.next.next != null){
            //要按照结果的箭头顺序一个一个连接 &-> 2 -> 1 -> 3
            //记录临时节点
            ListNode temp = cur.next;
            ListNode temp1 = cur.next.next.next;

            //& -> 2
            cur.next = cur.next.next;
            //2 -> 1
            cur.next.next = temp;
            //1 -> 3
            cur.next.next.next = temp1;

            //跳入下一个阶段(cur是虚节点，应该在转换的节点前面一个)
            cur = cur.next.next;
        }

        return dummyNode.next;

    }

    /**
     * 题目：19 删除链表的倒数第N个节点
     */
    //扫描一遍的方法（快慢指针）
    public static ListNode removeNthFromEnd(ListNode head, int n){
        //虚拟节点
        ListNode dummyNode = new ListNode(0);
        dummyNode.next = head;

        //快慢指针
        ListNode fastIndex = dummyNode;
        ListNode slowIndex = dummyNode;

        //快的先走
        for (int i = 0; i < n; i++) {
            fastIndex = fastIndex.next;
        }

        while (fastIndex.next != null){
            fastIndex = fastIndex.next;
            slowIndex = slowIndex.next;
        }

        //这时slowIndex在要删除的前一个节点上
        slowIndex.next = slowIndex.next.next;

        return dummyNode.next;

    }

    /**
     * 题目：面试题 02.07. 链表相交
     */
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB){
        ListNode curA = headA;
        ListNode curB = headB;

        //分别求出A B 的长度
        int lenA = 0;
        int lenB = 0;
        while (curA != null){
            lenA++;
            curA = curA.next;
        }

        while (curB != null){
            lenB++;
            curB = curB.next;
        }

        //重新返回头节点
        curA = headA;
        curB = headB;

        //让curA为最长链表的头， lenA为其长度
        if(lenB > lenA){
            //长度交换
            int tempLen = lenA;
            lenA = lenB;
            lenB = tempLen;

            //链表交换
            ListNode tempNode = curA;
            curA = curB;
            curB = tempNode;
        }
        //默认A 比 B 长，将curA和curB放置在同一起跑线
        int len = lenA - lenB;
        while (len > 0){
            curA = curA.next;
            len--;
        }

        //查找相同的节点
        while (curA != null){
            //判断
            if(curA == curB){
                return curA;
            }
            curA = curA.next;
            curB = curB.next;
        }

        return null;
    }

    /**
     * 题目：142 环形链表II
     */
    //快慢zhen
    public static ListNode detectCycle(ListNode head){
        ListNode slow = head;
        ListNode fast = head;

        //如果fast.next == null 则不能跳到下一个fast.next.next
        while (fast != null && fast.next != null){
            //slow每次前进一步，fast每次前进两步
            slow = slow.next;
            fast = fast.next.next;

            //如果两指针相遇，则一定在循环内相遇
            if(slow == fast){//证明有环
                //一个指针在快慢指针的相遇节点上，一个指针在链表的头节点上
                ListNode index1 = fast;
                ListNode index2 = head;
                //两个节点每次都走一步，相遇的点即为环入口
                while (index1 != index2){
                    index1 = index1.next;
                    index2 = index2.next;
                }
                return index1;
            }
        }

        return null;
    }
}

/**
 * 题目：707 设计链表
 */
//单向链表
class MyLinkedList1{

    //size存储链表元素的个数
    int size;
    //虚拟头节点
    ListNode head;

    //初始化自己的链表
    public MyLinkedList1(){
        size = 0;
        head = new ListNode(0);
    }

    //获取第index个节点的值，没有返回-1
    public int get(int index){
        //如果index非法，返回-1
        if(index < 0 || index >= size){
            return -1;
        }

        ListNode current = head;
        //包含一个虚拟头节点，所以查找index+1个节点
        for(int i = 0; i <= index; i++){
            current = current.next;
        }
        return current.val;
    }

    //在链表的第一个元素之前添加val节点
    public void addAtHead(int val){
        addAtIndex(0, val);
    }

    //在链表最后添加val值的节点
    public void addAtTail(int val){
        addAtIndex(size, val);
    }

    //在第index个节点之前添加val节点
    // 在第 index 个节点之前插入一个新节点，例如index为0，那么新插入的节点为链表的新头节点。
    // 如果 index 等于链表的长度，则说明是新插入的节点为链表的尾结点
    // 如果 index 大于链表的长度，则返回空
    public void addAtIndex(int index, int val){
        if(index > size){
            return;
        }
        if(index < 0){
            index = 0;
        }
        size++;
        //找到要插入的节点的前驱
        ListNode pred = head;
        for (int i = 0; i < index; i++){
            pred = pred.next;
        }
        ListNode toAdd = new ListNode(val);
        toAdd.next = pred.next;
        pred.next = toAdd;
    }

    //若index有效，删除第index节点
    public void deleteAtIndex(int index){
        if(index < 0 || index >= size){
            return;
        }
        size--;
        if(index == 0){
            head = head.next;
            return;
        }
        ListNode pred = head;
        for (int i = 0; i < index ; i++) {
            pred = pred.next;
        }
        pred.next = pred.next.next;
    }

}

//双向链表
class MyLinkedList2{
    //记录链表元素的数量
    int size;
    //记录链表的虚拟头节点和尾节点
    ListNode head;
    ListNode tail;

    //初始化自己的链表
    public MyLinkedList2(){
        size = 0;
        head = new ListNode(0);
        tail = new ListNode(0);
        //头尾相连
        head.next = tail;
        tail.prev = head;
    }

    //获取第index个节点的值，没有返回-1
    public int get(int index){
        //判断index是否有效
        if(index < 0 || index >= size){
            return -1;
        }
        ListNode cur = head;
        //判断是哪一边遍历时间更短
        if(index >=size / 2){
            //从尾开始
            cur = tail;
            for(int i = 0; i < size - index; i++){
                cur = cur.prev;
            }
        }else {
            for(int i = 0; i <= index; i++){
                cur = cur.next;
            }
        }
        return cur.val;
    }

    //在链表的第一元素之前添加val节点
    public void addAtHead(int val){
        //等价于在第0个元素前添加
        addAtIndex(0, val);
    }

    //在链表最后添加val值的节点
    public void addAtTail(int val){
        //等价于在最后一个元素（null）前添加
        addAtIndex(size, val);
    }

    //在第index个节点之前添加val节点
    // 在第 index 个节点之前插入一个新节点，例如index为0，那么新插入的节点为链表的新头节点。
    // 如果 index 等于链表的长度，则说明是新插入的节点为链表的尾结点
    // 如果 index 大于链表的长度，则返回空
    public void addAtIndex(int index, int val){
        //index大于链表长度
        if(index > size){
            return;
        }
        //index小于0
        if(index < 0){
            index = 0;
        }
        size++;
        //找到前驱(前一个)
        ListNode pre = head;
        for(int i = 0; i < index; i++){
            pre = pre.next;
        }
        //新建节点
        ListNode newNode = new ListNode(val);
        newNode.next = pre.next;
        pre.next.prev = newNode;
        newNode.prev = newNode;
        pre.next = newNode;
    }

    //若index有效，删除第index节点
    public void deleteAtIndex(int index){
        //判断索引是否有效(如果只有一个节点，删除也是空值)
        if(index < 0 || index >=size){
            return;
        }
        //删除
        size--;
        ListNode pre = head;
        for(int i = 0; i < index; i++){
            pre = pre.next;
        }
        pre.next.next.prev = pre;
        pre.next = pre.next.next;
    }
}

