package leecode;

import java.util.*;
import java.util.LinkedList;

/**
 * 栈与队列
 * @author neil
 */
public class StackAndQueue {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 3, -1, -3, 5, 3, 6, 7};
        int[] ints = maxSlidingWindow(nums, 3);
        System.out.println(ints);

    }

    /**
     * 题目：20 有效的括号
     */
    public static boolean isValid(String s){
        Deque<Character> stack = new LinkedList<>();
        char ch;
        for (int i = 0; i < s.length(); i++) {
            ch = s.charAt(i);
            //碰到左括号，就把相应的右括号入栈
            if(ch == '('){
                stack.push(')');
            }else if(ch == '['){
                stack.push(']');
            }else if(ch == '{'){
                stack.push('}');
            }else if(stack.isEmpty() || ch != stack.peek()){
                return false;
            }else {//如果是右括号判断是否和栈顶元素匹配
                stack.pop();
            }
        }

        //最后判断栈中元素是否匹配
        return stack.isEmpty();
    }

    /**
     * 题目：1047 删除字符串中的所有相邻重复项
     */
    //使用Deque作为堆栈
    public static String removeDuplicates1(String s){
        //ArrayDeque会比LinkedList在除了删除元素这一点外会快一点
        //参考：https://stackoverflow.com/questions/6163166/why-is-arraydeque-better-than-linkedlist
        ArrayDeque<Character> stack = new ArrayDeque<>();
        char ch;
        for (int i = 0; i < s.length(); i++) {
            ch = s.charAt(i);
            //栈中没有相同的元素，入栈
            if(stack.isEmpty() || stack.peek() != ch){
                stack.push(ch);
            }else {//弹栈
                stack.pop();
            }
        }
        String str = "";
        //剩余的元素即为不重复的元素
        while (!stack.isEmpty()){
            str = stack.pop() + str;
        }

        return str;
    }

    //拿字符串直接作为栈，省去了栈还要转为字符串的操作
    public String removeDuplicates2(String s){
        //将res昂做栈
        StringBuilder res = new StringBuilder();
        //top为res的长度
        int top = -1;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if(top >= 0 && res.charAt(top) == ch){
                res.deleteCharAt(top);
                top--;
            }else {
                res.append(ch);
                top++;
            }
        }

        return res.toString();
    }

    //扩展：双指针
    public String removeDuplicates3(String s){
        char[] ch = s.toCharArray();
        int fastIndex = 0;
        int slowIndex = 0;
        while(fastIndex < s.length()){
            //直接用fastIndex指针覆盖slowIndex指针的值
            ch[slowIndex] = ch[fastIndex];
            //遇到前后相同值的，就跳过，即slowIndex指针后退一步，下次循环就可以直接被覆盖掉了
            if(slowIndex > 0 && ch[slowIndex] == ch[slowIndex - 1]){
                slowIndex--;
            }else {
                slowIndex++;
            }
            fastIndex++;
        }

        return new String(ch, 0, slowIndex);

    }

    /**
     * 题目：150 逆波兰表达式求值
     */
    public static int evalRPN(String[] tokens){
        Deque<Integer> stack = new LinkedList<>();
        for (String s : tokens) { //不能使用==进行判断
            if("+".equals(s)){//注意：-和/需要特殊处理
                stack.push(stack.pop() + stack.pop());
            }else if("-".equals(s)){
                stack.push(-stack.pop() + stack.pop());
            }else if("*".equals(s)){
                stack.push(stack.pop() * stack.pop());
            }else if("/".equals(s)){
                int temp1 = stack.pop();
                int temp2 = stack.pop();
                stack.push(temp2 / temp1);
            }else {
                stack.push(Integer.valueOf(s));
            }
        }
        return stack.pop();
    }

    /**
     * 题目：239 滑动窗口最大值
     */
    //利用双端队列手动实现单调队列
    //队列的头部是老元素，尾部是新元素，老元素永远都是要大于新元素的（这个只是在窗口之中的情况）
    public static int[] maxSlidingWindow(int[] nums, int k){
        Deque<Integer> deque = new ArrayDeque<>();
        int n = nums.length;
        int[] res = new int[n - k + 1];
        int idx = 0;
        for (int i = 0; i < n; i++) {
            //根据题意，i为nums下标，是要在[i - k + 1, i]中选到最大值，只需要保证两点
            //1.队列头节点需要在[i - k + 1, i]范围内，不符合则要弹出
            while (!deque.isEmpty() && deque.peek() < i - k + 1){
                deque.poll();
            }
            //2.既然是单调，就要保证每次放进去的数字要比末尾的都要大，否则也弹出(队列，先进先出)
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]){
                deque.pollLast();
            }

            deque.offer(i);

            // 因为单调，当i增长到符合第一个k范围的时候，每滑动一步都将队列头节点放入结果就行了
            if(i >= k - 1){
                res[idx++] = nums[deque.peek()];
            }
        }

        return res;
    }

    /**
     * 题目：347 前K个高频元素
     *
     * 不会，抄的
     */
    //大顶堆：每个结点的值都大于或等于其左右孩子结点的值
    //小顶堆：每个结点的值都小于或等于其左右孩子结点的值
    //解法1：基于大顶堆实现
    public static int[] topKFrequent1(int[] nums, int k){
        Map<Integer, Integer> map = new HashMap<>();//key为数组元素值，val为对应出现次数
        for (int num : nums) {
            map.put(num,map.getOrDefault(num, 0) + 1);
        }
        //在优先队列中存储二元组(num,cnt),cnt表示元素值num在数组中的出现次数
        //出现次数按从队头到队尾的顺序是从大到小排,出现次数最多的在队头(相当于大顶堆)
        //优先级队列，默认是从pair1 - pair2,形成小顶堆
        PriorityQueue<int[]> pq = new PriorityQueue<>((pair1, pair2)->pair2[1]-pair1[1]);
        for(Map.Entry<Integer,Integer> entry:map.entrySet()){//大顶堆需要对所有元素进行排序
            pq.add(new int[]{entry.getKey(),entry.getValue()});
        }
        int[] ans = new int[k];
        for(int i=0;i<k;i++){//依次从队头弹出k个,就是出现频率前k高的元素
            ans[i] = pq.poll()[0];
        }
        return ans;
    }


}

/**
 * 题目:232 用栈实现队列
 */
class MyQueue{
    //创建一个输入栈，一个输出栈
    Stack<Integer> stackIn;
    Stack<Integer> stackOut;

    //初始数据方法
    public MyQueue() {
        stackIn = new Stack<>();//负责进栈
        stackOut = new Stack<>();//负责出栈
    }

    //将元素x堆到队列的后面
    public void push(int x) {
        stackIn.push(x);
    }

    //从队列前面移除元素并返回该元素
    public int pop() {
        dumpstackIn();
        return stackOut.pop();
    }

    //得到前面的元素
    public int peek() {
        dumpstackIn();
        return stackOut.peek();
    }

    //判断队列是否为空
    public boolean empty() {
        return stackIn.isEmpty() && stackOut.isEmpty();
    }

    //如果stackOut为空，那么将stackIn中的元素全部放到stackOut
    private void dumpstackIn(){
        if(!stackOut.isEmpty()){
            return;
        }
        while (!stackIn.isEmpty()){
            stackOut.push(stackIn.pop());
        }
    }
}

/**
 * 题目：225 用队列实现栈
 */
//使用两个Deque实现（Deque可以实现队列，双端队列和堆栈）
//使用双端队列
class MyStack1 {
    Deque<Integer> que1;//和占中保持一样元素的队列
    Deque<Integer> que2;//辅助队列

    //初试数据结构的方法
    public MyStack1() {
        que1 = new ArrayDeque<>();
        que2 = new ArrayDeque<>();
    }

    //入栈
    public void push(int x) {
        que1.addLast(x);
    }

    //弹栈并返回所弹栈帧
    public int pop() {
        int size = que1.size();
        size--;
        //将que1导入que2，但留下最后一个值
        while (size-- > 0){
            que2.addLast(que1.peekFirst());
            que1.pollFirst();
        }

        int res = que1.pollFirst();
        //将que2随想的引用赋给了que1，此时que1，que2指向同一个队列
        que1 = que2;
        que2 = new ArrayDeque<>();
        return res;
    }

    public int top() {
        return que1.peekLast();
    }

    public boolean empty() {
        return que1.isEmpty();
    }
}

//使用一个queue进行实现
class MyStack2{

    Queue<Integer> queue;

    public MyStack2(){
        queue = new LinkedList<>();
    }

    //没offer一个数A进来，都重新排列，把这个数A放到队列的队首
    public void push(int x){
        queue.offer(x);
        int size = queue.size();
        //移动除了A的其他数
        while(size-- > 1){
            queue.offer(queue.poll());
        }
    }

    public int pop(){
        return queue.poll();
    }

    public int top(){
        return queue.peek();
    }

    public boolean empty(){
        return queue.isEmpty();
    }
}

