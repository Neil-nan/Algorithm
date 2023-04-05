package leecode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 双指针
 * @author neil
 */
public class DoublePointer {
    public static void main(String[] args) {
        int i = removeElement2(new int[]{1, 2, 3, 4, 1}, 1);
        System.out.println(i);

    }

    /**
     * 题目：27 移除元素
     */
    //左右指针
    public static int removeElement1(int[] nums, int val){
        //判断
        if(nums == null || nums.length == 0){
            return 0;
        }
        //左右指针
        int left = 0;
        int right = nums.length - 1;
        //边界判断这里注意"="到底是加还是不加，左闭右闭还是左闭右开要有区分
        while (left <= right){
            //如果左指针碰到val，交换左右指针的数值
            if(nums[left] == val){
                nums[left] = nums[right];
                right--;
            }else {
                left++;
            }
        }
        return left;

    }

    //快慢指针
    public static int removeElement2(int[] nums, int val){
        int slowIndex = 0;
        for (int fastIndex = 0; fastIndex < nums.length; fastIndex++){
            //快指针进行探索，发现不是目标的数据给慢指针
            if (nums[fastIndex] != val){
                nums[slowIndex] = nums[fastIndex];
                slowIndex++;
            }
        }
        return slowIndex;
    }

    /**
     * 题目：26 删除排序数组中的重复项
     */
    //快慢指针
    public static int removeDuplicates(int[] nums){
        //判断
        //数组为空
        if(nums == null || nums.length == 0){
            return 0;
        }
        //只有一个字符
        if(nums.length == 1){
            return 1;
        }
        int slowIndex = 1;
        int fastIndex = 1;
        while (fastIndex < nums.length){
            if(nums[fastIndex] != nums[fastIndex - 1]){
                nums[slowIndex] = nums[fastIndex];
                slowIndex++;
            }
            fastIndex++;
        }
        return slowIndex;
    }

    /**
     * 题目：283 移动零
     */
    public static void moveZeroes(int[] nums){
        int slowIndex = 0;
        int fastIndex = 0;
        int n = nums.length;
        while (fastIndex < n){
            if(nums[fastIndex] != 0){
                //交换快慢指针
                int temp = nums[slowIndex];
                nums[slowIndex] = nums[fastIndex];
                nums[fastIndex] = temp;
                slowIndex++;
            }
            fastIndex++;
        }
    }

    /**
     * 题目：844 比较含退格的字符串
     */
    //从后向前进行遍历
    public static boolean backspaceCompare(String s, String t){
        //计算字符串的长度
        int sLen = s.length();
        int tLen = t.length();
        //字符串s和t的指针，从后向前
        int sIndex = sLen - 1;
        int tIndex = tLen - 1;

        //分别存储#的个数
        int skipS = 0;
        int skipT = 0;

        while (sIndex >= 0 || tIndex >= 0){

            //字符串s
            while (sIndex >= 0){
                if (s.charAt(sIndex) == '#'){
                    skipS++;
                    sIndex--;
                }else if (skipS > 0){
                    sIndex--;
                    skipS--;
                }else {
                    break;
                }
            }

            //字符串t
            while (tIndex >= 0){
                if (t.charAt(tIndex) == '#'){
                    skipT++;
                    tIndex--;
                }else if (skipT > 0){
                    tIndex--;
                    skipT--;
                }else {
                    break;
                }
            }

            //比较
            if(sIndex >= 0 && tIndex >= 0){
                if(s.charAt(sIndex) != t.charAt(tIndex)){
                    return false;
                }
            }else {
                //一个有一个没有
                if(sIndex >= 0 || tIndex >= 0){
                    return false;
                }
            }
            sIndex--;
            tIndex--;
        }
        return true;
    }

    /**
     * 题目：977 有序数组的平方
     */
    //左右指针
    public static int[] sortedSquares(int[] nums){
        //判断
        if(nums == null || nums.length == 0){
            return new int[0];
        }
        //创建左右指针
        int len = nums.length;
        int leftIndex = 0;
        int rightIndex = len - 1;
        //创建答案
        int[] res = new int[len];
        int count = len - 1;
        while (leftIndex <= rightIndex && count >= 0){
            //判断左右指针的大小
            if(nums[rightIndex] * nums[rightIndex] >= nums[leftIndex] * nums[leftIndex]){
                res[count] = nums[rightIndex] * nums[rightIndex];
                rightIndex--;
            }else {
                res[count] = nums[leftIndex] * nums[leftIndex];
                leftIndex++;
            }
            count--;
        }
        return res;
    }

    /**
     * 题目：344 反转字符串
     */
    //左右双指针
    public static void reverseString(char[] s){
        //判断
        if(s == null || s.length == 0){
            return;
        }
        //创建双指针
        int leftIndex = 0;
        int rightIndex = s.length - 1;
        while (leftIndex <= rightIndex){
            char temp = s[leftIndex];
            s[leftIndex] = s[rightIndex];
            s[rightIndex] = temp;
            leftIndex++;
            rightIndex--;
        }
    }

    /**
     * 题目：剑指offer 05 替换空格
     */
    public static String replaceSpace1(String s){
        //判断
        if(s == null || s.length() == 0){
            return s;
        }
        //创建res
        StringBuffer res = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == ' '){
                res.append("%20");
            }else {
                res.append(s.charAt(i));
            }
        }

        return res.toString();

    }

    //不重新申请新的数组，只进行数组的扩容
    public static String replaceSpace2(String s){
        //判断
        if(s == null || s.length() == 0){
            return s;
        }
        //扩充空间，空格数量的两倍
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == ' '){
                str.append("  ");
            }
        }
        //判断
        //若是没有空格直接返回
        if(str.length() == 0){
            return s;
        }
        //若是有空格，创建两个指针
        int leftIndex = s.length() - 1;//左指针指向的是原始字符串的最后一个的位置
        s += str.toString();
        int rightIndex = s.length() - 1;//右指针指向的是结果字符串的最后一个的位置
        char[] chars = s.toCharArray();
        while (leftIndex >= 0){
            //进行判断
            if(chars[leftIndex] == ' '){
                chars[rightIndex--] = '0';
                chars[rightIndex--] = '2';
                chars[rightIndex] = '%';
            }else {
                chars[rightIndex] = chars[leftIndex];
            }
            leftIndex--;
            rightIndex--;
        }
        return new String(chars);
    }

    /**
     * 题目：151 翻转字符串里的单词
     */
    //思路：
    //1.去除首尾以及中间多余空格
    //2.反转整个字符串
    //3.反转各个单词
    public static String reverseWords(String s){
        //去除首尾以及中间多余空格
        StringBuilder sb = removeSpace(s);
        //反转整个字符串
        reverseString(sb, 0, sb.length() - 1);
        //反转各个单词
        reverseEachWord(sb);
        return sb.toString();
    }

    //去除首尾以及中间多余空格
    private static StringBuilder removeSpace(String s){
        int start = 0;
        int end = s.length() - 1;
        //先去除首尾两端的空格
        while(s.charAt(start) == ' '){
            start++;
        }
        while(s.charAt(end) == ' '){
            end--;
        }
        //创建结果
        StringBuilder sb = new StringBuilder();
        while (start <= end){
            char c = s.charAt(start);
            //字符不是空格或者结果的最后一个字符不是空格时，在结果上进行添加
            if(c != ' ' || sb.charAt(sb.length() - 1) != ' '){
                sb.append(c);
            }
            start++;
        }
        return sb;
    }

    //反转整个字符串
    private static void reverseString(StringBuilder sb, int start, int end){
        while (start <= end){
            char temp = sb.charAt(start);
            sb.setCharAt(start, sb.charAt(end));
            sb.setCharAt(end, temp);
            start++;
            end--;
        }
    }

    //反转各个单词
    private static void reverseEachWord(StringBuilder sb){
        //找单词
        int start = 0;
        int end = 1;
        while (end < sb.length()){
            while (end < sb.length() && sb.charAt(end) != ' '){
                end++;
            }
            //找到交换
            reverseString(sb, start, end - 1);
            //重新定位双指针
            start = end + 1;
            end = start + 1;
        }
    }

    /**
     * 题目：剑指offer58-II.左旋转字符串
     */
    //思路：整体反转，然后进行对应的数字反转
    //用数组要比StringBuilder快一些
    public static String reverseLeftWords(String s, int n){
        int len = s.length();
        char[] sChar = s.toCharArray();
        //整体反转
        reverse(sChar, 0, len - 1);
        //根据数值进行分别反转
        reverse(sChar, 0, len - n - 1);
        reverse(sChar, len - n, len - 1);
        return new String(sChar);
    }

    private static void reverse(char[] sChar, int start, int end){
        while (start <= end){
            char temp = sChar[start];
            sChar[start] = sChar[end];
            sChar[end] = temp;
            start++;
            end--;
        }
    }

    /**
     * 题目：206 反转链表
     */
    //双指针法
    public static ListNode reverseList1(ListNode head){
        //双指针
        ListNode prev = null;
        ListNode cur = head;
        ListNode temp = null;
        while (cur != null){
            temp = cur.next;//保存下一个节点
            cur.next = prev;
            prev = cur;
            cur = temp;

        }
        return prev;
    }
    //递归法(抄的)
    public static ListNode reverseList2(ListNode head){
        return reverse1(null, head);
    }

    private static ListNode reverse1(ListNode prev, ListNode cur){
        if(cur == null){
            return prev;
        }
        ListNode temp = null;
        temp = cur.next;//先保存下一个节点
        cur.next = prev;//反转
        //更新prev、cur位置
        //prev = cur;
        //cur = temp;
        return reverse1(cur, temp);
    }

    /**
     * 题目：19 删除链表的倒数第N个节点
     */
    //快慢指针，技巧记住，先创建一个虚节点
    public static ListNode removeNthFromEnd(ListNode head, int n){
        ListNode dummyNode = new ListNode(0);
        dummyNode.next = head;

        //创建快慢指针
        ListNode fastIndex = dummyNode;
        ListNode slowIndex = dummyNode;

        //快指针先跑n个
        for (int i = 0; i < n; i++) {
            fastIndex = fastIndex.next;
        }
        //一起跑
        while (fastIndex.next != null){
            fastIndex = fastIndex.next;
            slowIndex = slowIndex.next;
        }

        //删除节点
        //此时 slowIndex的位置就是待删除元素的前一个
        slowIndex.next = slowIndex.next.next;
        return dummyNode.next;
    }

    /**
     * 题目：面试题 02.07 链表相交
     */
    //思路：将两个链表的尾端对齐，然后对节点进行比较
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB){
        ListNode curA = headA;
        ListNode curB = headB;
        //分别求链表A B的长度
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
        //指针还原
        curA = headA;
        curB = headB;
        //让curA为最长链表的头，lenA为其长度
        if(lenB > lenA){
            //交换长度
            int tmpLen = lenA;
            lenA = lenB;
            lenB = tmpLen;
            //交换指针
            ListNode tmpNode = curA;
            curA = curB;
            curB = tmpNode;
        }
        //让两个链表的尾端对齐，指针在同一起点上
        int gap = lenA - lenB;
        for (int i = 0; i < gap; i++) {
            curA = curA.next;
        }

        //进行比较
        while (curA != null){
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
    //快慢指针
    public static ListNode detectCycle(ListNode head){
        ListNode fastIndex = head;
        ListNode slowIndex = head;
        //快指针一次跳两格，慢指针一次跳一格
        while (fastIndex != null && fastIndex.next != null){
            slowIndex = slowIndex.next;
            fastIndex = fastIndex.next.next;
            //快慢指针相遇，此时从head和相遇点，同时查找直至相遇
            if(slowIndex == fastIndex){//有环
                ListNode index1 = fastIndex;
                ListNode index2 = head;
                //两个指针，从头节点和相遇节点，各走一步，直到相遇，相遇点即为环入口
                while (index1 != index2){
                    index1 = index1.next;
                    index2 = index2.next;
                }
                return index1;
            }
        }
        return null;
    }

    /**
     * 题目：15 三数之和
     */
    public static List<List<Integer>> threeSum(int[] nums){
        List<List<Integer>> result = new ArrayList<>();
        //对数组进行排序
        Arrays.sort(nums);

        //找出a+b+c=0
        //a = nums[i], b = nums[left], c = nums[right]
        for (int i = 0; i < nums.length; i++) {
            //排序后如果第一个元素已经大于零，那么无论如何组合都不可能凑成三元组，直接返回结果
            if(nums[i] > 0){
                return result;
            }

            //去重（结果中可以有重复的元素，但不能有重复的结果）
            //对a进行去重
            if(i > 0 && nums[i] == nums[i - 1]){
                continue;
            }

            //创建左右指针
            int leftIndex = i + 1;
            int rightIndex = nums.length - 1;
            while (leftIndex < rightIndex){
                int sum = nums[i] + nums[leftIndex] + nums[rightIndex];
                if(sum > 0){//右指针左移
                    rightIndex--;
                }else if(sum < 0){//左指针右移
                    leftIndex++;
                }else {//找到结果
                    result.add(Arrays.asList(nums[i], nums[leftIndex], nums[rightIndex]));
                    //去重逻辑应该放在找到一个三元组之后，对B和c进行去重
                    //对b c进行去重
                    while (rightIndex > leftIndex && nums[rightIndex] == nums[rightIndex - 1]){
                        rightIndex--;
                    }
                    while (rightIndex > leftIndex && nums[leftIndex] == nums[leftIndex + 1]){
                        leftIndex++;
                    }

                    //找到答案时，双指针同时收缩
                    rightIndex--;
                    leftIndex++;
                }
            }
        }
        return result;
    }

    /**
     * 题目：18 四数之和
     */
    //左右指针
    public static List<List<Integer>> fourSum(int[] nums, int target){
        //创建结果
        List<List<Integer>> result = new ArrayList<>();
        //对数组进行排序
        Arrays.sort(nums);

        //找出a + b + c + d = target
        //a = nums[i], b = nums[j], c = nums[leftIndex], d = nums[rightIndex]
        for (int i = 0; i < nums.length; i++) {
            //判断
            if(nums[i] > target){
                return result;
            }
            //去重
            if(i > 0 && nums[i] == nums[i - 1]){
                continue;
            }
            for (int j = i + 1; j < nums.length; j++){
                //对B进行去重
                if(j > i + 1 && nums[j] == nums[j - 1]){
                    continue;
                }
                //创建左右指针
                int leftIndex = j + 1;
                int rightIndex = nums.length - 1;
                while (leftIndex < rightIndex){
                    long sum = (long)nums[i] + nums[j] + nums[leftIndex] + nums[rightIndex];
                    if(sum > target){//缩右指针
                        rightIndex--;
                    }else if(sum < target){//左指针右移
                        leftIndex++;
                    }else {//找到结果，进行记录
                        result.add(Arrays.asList(nums[i], nums[j], nums[leftIndex], nums[rightIndex]));
                        //对nums[leftIndex]和nums[rightIndex]去重
                        while(leftIndex < rightIndex && nums[rightIndex] == nums[rightIndex - 1]){
                            rightIndex--;
                        }
                        while(leftIndex < rightIndex && nums[leftIndex] == nums[leftIndex + 1]){
                            leftIndex++;
                        }

                        leftIndex++;
                        rightIndex--;
                    }
                }
            }
        }
        return result;
    }


}

//创建链表节点
class LinkNode{
    int val;
    ListNode next;

    public LinkNode() {
    }

    public LinkNode(int val) {
        this.val = val;
    }

    public LinkNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
