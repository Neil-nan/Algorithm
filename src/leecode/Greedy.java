package leecode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * 贪心算法
 * @author neil
 */
public class Greedy {
    public static void main(String[] args) {
        System.out.println(jump(new int[]{2, 3, 1, 1, 4}));
        System.out.println(largestSumAfterKNegations(new int[]{2, -3, -1, 5, -4}, 2));
        System.out.println(monotoneIncreasingDigits(100));
    }

    /**
     * 题目：455 分发饼干
     */
    //思路1 优先考虑饼干，小饼干先喂饱小胃口
    //g 小孩的胃口 s 饼干的大小
    public static int findContentChildren1(int[] g, int[] s){
        Arrays.sort(s);
        Arrays.sort(g);
        int start = 0;
        int count = 0;
        for(int i = 0; i < s.length && start < g.length; i++){
            if(s[i] >= g[start]){
                start++;
                count++;
            }
        }
        return count;
    }

    //思路2 优先考虑胃口
    public static int findContentChildren(int[] g, int[] s){
        Arrays.sort(g);
        Arrays.sort(s);
        int start = s.length - 1;
        int count = 0;
        for(int i = g.length - 1; i >= 0; i--){
            if(start >= 0 && s[start] >= g[i]){
                start--;
                count++;
            }
        }
        return count;
    }

    /**
     * 题目：376 摆动序列
     */
    public static int wiggleMaxLength(int[] nums){
        if(nums.length <= 1){
            return nums.length;
        }
        //当前差值
        int curDiff = 0;
        //上一个差值
        int preDiff = 0;
        int count = 1;
        for(int i = 1; i < nums.length; i++){
            //得到当前差值
            curDiff = nums[i] - nums[i - 1];
            //如果当前差值和上一个差值为一正一负
            //等于0的情况表示初始值的perDiff
            if((curDiff > 0 && preDiff < 0) || (curDiff < 0 && preDiff >= 0)){
                count++;
                preDiff = curDiff;
            }
        }
        return count;
    }

    /**
     * 题目：53 最大子序和
     */
    //贪心算法
    public static int maxSubArray(int[] nums){
        if(nums.length == 1){
            return nums[0];
        }
        int sum = Integer.MIN_VALUE;
        int count = 0;
        for(int i = 0; i < nums.length; i++){
            count += nums[i];
            sum = Math.max(count, sum);
            if(count <= 0){
                count = 0;//相当于重置最大子序其实位置，因为遇到负数一定是拉低总和
            }
        }
        return sum;
    }

    /**
     * 题目：122 买卖股票的最佳时机
     */
    public static int maxProfit(int[] prices){
        int result = 0;
        int count = 0;
        for(int i = 1; i < prices.length; i++){
            count = prices[i] - prices[i - 1];
            if(count > 0){
                result += count;
            }
        }
        return result;
    }

    /**
     * 题目：55 跳跃游戏
     */
    public static boolean canJump(int[] nums){
        if(nums.length == 1){
            return true;
        }
        //覆盖范围，初始覆盖范围应该是0， 因为下面的迭代是从下标0开始的
        int coverRange = 0;
        //在覆盖范围内更新最大的覆盖范围
        for(int i = 0; i <= coverRange; i++){
            coverRange = Math.max(coverRange, i + nums[i]);//能达到的最远距离
            if(coverRange >= nums.length - 1){
                return true;
            }
        }
        return false;
    }

    /**
     * 题目：45 跳跃游戏II
     */
    //写一下自己理解的思路：从头到尾进行遍历（前提是一定可以到达最后），当到达i == curDistance时
    //说明i到达了当前的最大目的，这时候更新下一步能达到的最大距离，并在这个距离里继续进行遍历，找到达到目标的最短步数
    public static int jump(int[] nums){
        if (nums == null || nums.length == 0 || nums.length == 1) {
            return 0;
        }
        //记录跳跃的次数
        int count=0;
        //当前的覆盖最大区域
        int curDistance = 0;
        //最大的覆盖区域
        int maxDistance = 0;
        for (int i = 0; i < nums.length; i++) {
            //在可覆盖区域内更新最大的覆盖区域
            maxDistance = Math.max(maxDistance,i+nums[i]);
            //说明当前一步，再跳一步就到达了末尾
            if (maxDistance>=nums.length-1){
                count++;
                break;
            }
            //走到当前覆盖的最大区域时，更新下一步可达的最大区域
            if (i==curDistance){
                curDistance = maxDistance;
                count++;
            }
        }
        return count;
    }

    /**
     * 题目：1005 K次取反后最大化的数组和
     */
    public static int largestSumAfterKNegations(int[] nums, int k){
        if(nums.length == 1){
            return k % 2 == 0 ? nums[0] : -nums[0];
        }
        Arrays.sort(nums);
        int sum = 0;
        int index = 0;
        for(int i = 0; i < k; i++){
            if(i < nums.length - 1 && nums[index] < 0){
                nums[index] = -nums[index];
                //如果正好在正负之间，且负数绝对值小，index指着不移动，把K消耗掉
                if(nums[index] >= Math.abs(nums[index + 1])){
                    index++;
                }
                continue;
            }
            //这是已经将所有的负数都处理完的情况
            nums[index] = -nums[index];
        }
        //求和
        for(int i = 0; i < nums.length; i++){
            sum += nums[i];
        }
        return sum;
    }

    /**
     * 题目：134 加油站
     */
    //倾向于使用方法二
    public static int canCompleteCircuit(int[] gas, int[] cost){
        int curSum = 0;
        int totalSum = 0;
        int index = 0;
        for(int i = 0; i < gas.length; i++){
            curSum += gas[i] - cost[i];
            totalSum += gas[i] - cost[i];
            if(curSum < 0){//当前累加rest[i]和curSum一旦小于0
                index = (i + 1) % gas.length;//初试位置更新为i+1，从i开始是一定不行的
                curSum = 0;//curSum从0开始
            }
        }
        if(totalSum < 0){
            return -1;//说明怎么走也不能跑一圈了
        }
        return index;
    }

    /**
     * 题目：135 分发糖果
     */
    //采用两次贪心
    //一次是从左到右遍历， 只比较右孩子评分比左边大的情况
    //一次是从右边到左边遍历，只比较左边孩子评分比右边大的情况
    public static int candy(int[] ratings){
        /**
         分两个阶段
         1、起点下标1 从左往右，只要 右边 比 左边 大，右边的糖果=左边 + 1
         2、起点下标 ratings.length - 2 从右往左， 只要左边 比 右边 大，此时 左边的糖果应该 取本身的糖果数（符合比它左边大） 和 右边糖果数 + 1 二者的最大值，这样才符合 它比它左边的大，也比它右边大
         */
        int len = ratings.length;
        int[] candyVec = new int[len];
        candyVec[0] = 1;
        //从左向右进行遍历
        for(int i = 1; i < len; i++){
            candyVec[i] = ratings[i] > ratings[i - 1] ? candyVec[i - 1] + 1 : 1;
        }
        //从右向左进行遍历
        for(int i = len - 2; i >= 0; i--){
            if(ratings[i] > ratings[i + 1]){
                candyVec[i] = Math.max(candyVec[i], candyVec[i + 1] + 1);
            }
        }

        int ans = 0;
        for(int num : candyVec){
            ans += num;
        }
        return ans;
    }

    /**
     * 题目：860 柠檬水找零
     */
    public static boolean lemonadeChange(int[] bills){
        int five = 0;
        int ten = 0;
        for (int bill : bills) {
            if(bill == 5){
                five++;
            }else if(bill == 10){
                five--;
                ten++;
            }else if(bill == 20){
                if(ten > 0){
                    ten--;
                    five--;
                }else {
                    five -=3;
                }
            }
            if(five < 0 || ten < 0){
                return false;
            }
        }
        return true;
    }

    /**
     * 题目：406 根据身高重建队列
     */
    public static int[][] reconstructQueue(int[][] people){
        //身高从大到小排（身高相同k小的站前面）
        Arrays.sort(people, (a, b) -> {
            if(a[0] == b[0]){
                return a[1] - b[1];//升序
            }
            return b[0] - a[0];//降序
        });

        LinkedList<int[]> que = new LinkedList();

        for (int[] p : people) {
            que.add(p[1], p);//这块贼神奇，按照方法debug后确实是这样
        }
        return que.toArray(new int[people.length][]);
    }

    /**
     * 题目：452 用最少数量的箭引爆气球
     */
    public static int findMinArrowShots(int[][] points){
        //根据气球直径的开始坐标从小到大排序
        /**
         * 这里这么写会有数组溢出
         */
//        Arrays.sort(points, (a, b) -> {
//            return a[0] - b[0];//使用Lambda表达式的方式对Comparator比较器进行简写（注意要求在JDK1.8以上使用）
//        });
        Arrays.sort(points, (a, b) -> Integer.compare(a[0], b[0]));

        int count = 1;//points不为空至少需要一支箭
        for(int i = 1; i < points.length; i++){
            if(points[i][0] > points[i - 1][1]){//气球i和i - 1不挨着，注意这里不是>=
                count++;//需要加一支箭
            }else {//气球i和气球i-1挨着
                points[i][1] = Math.min(points[i][1], points[i - 1][1]);//更新重叠气球的最小右边界
            }
        }
        return count;
    }

    /**
     * 题目：435 无重叠区间
     */
    //自己改编的
    public static int eraseOverlapIntervals(int[][] intervals){
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        int count = 0;
        for(int i = 1; i < intervals.length; i++){
            if(intervals[i][0] < intervals[i - 1][1]){
                intervals[i][1] = Math.min(intervals[i - 1][1], intervals[i][1]);
                count++;
            }
        }
        return count;
    }

    /**
     * 题目：763 划分字母区
     */
    //方法一
    public static List<Integer> partitionLabels(String s){
        List<Integer> list = new LinkedList<>();
        int[] edge = new int[26];
        char[] chars = s.toCharArray();
        for(int i = 0; i < chars.length; i++){//统计每一个字符最后出现的位置
            edge[chars[i] - 'a'] = i;
        }
        int right = 0;//分割字符的左右边界
        int left = -1;
        for(int i = 0; i < chars.length; i++){//找到字符出现的最远边界
            right = Math.max(right, edge[chars[i] - 'a']);
            if(i == right){
                //添加结果
                list.add(right - left);
                left = right;
            }
        }
        return list;
    }

    /**
     * 题目：56 合并区间
     */
    public static int[][] merge(int[][] intervals){
        List<int[]> res = new LinkedList<>();
        //按照左边界进行排序
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        //左右边界的初试值
        int left = intervals[0][0];
        int right = intervals[0][1];
        for(int i = 1; i < intervals.length; i++){
            //如果左边界小于等于右边界
            if(intervals[i][0] <= right){
                //更新最大右边界
                right = Math.max(right, intervals[i][1]);
            }else {//写入答案
                res.add(new int[]{left, right});
                //更新left right
                left = intervals[i][0];
                right = intervals[i][1];
            }
        }
        res.add(new int[]{left, right});
        return res.toArray(new int[res.size()][]);
    }

    /**
     * 题目：738 单调递增的数字
     */
    //从前向后进行遍历，遇到num[i - 1] > num[i] 就让num[i - 1]减1，然后num[i]给9
    public static int monotoneIncreasingDigits(int n){
        String s = String.valueOf(n);
        char[] chars = s.toCharArray();
        int start = s.length();
        for(int i = chars.length - 2; i >= 0; i--){
            if(chars[i] > chars[i + 1]){
                chars[i]--;
                start = i + 1;
            }
        }
        for(int i = start; i < s.length(); i++){
            chars[i] = '9';
        }
        return Integer.parseInt(String.valueOf(chars));
    }

    /**
     * 题目：714 买卖股票的最佳时机含手续费
     */
    public static int maxProfit(int[] prices, int fee){
        int result = 0;
        int minPrice = prices[0];//记录最低价格
        for(int i = 1; i < prices.length; i++){
            //情况二 相当于买入
            if(prices[i] < minPrice){
                minPrice = prices[i];
            }
            //情况三 保持原有状态（因为此时买则不便宜，卖则亏本）
            if(prices[i] >= minPrice && prices[i] <= minPrice + fee){
                continue;
            }
            //计算利润，可能有多次计算利润，最后一次极端利润才是真正意义的卖出
            if(prices[i] > minPrice + fee){
                result += (prices[i] - minPrice - fee);
                minPrice = prices[i] - fee;//情况一 并不是最后一次卖出（还有一个累加的过程，过程中只需减去一次手续钱）

            }
        }

        return result;
    }

    /**
     * 题目：968 监控二叉树
     */
    static int res = 0;
    public static int minCameraCover(TreeNode root){
        //对根节点的状态做检验，防止根节点是无覆盖状态
        if(minCamer(root) == 0){
            res++;
        }
        return res;
    }

    /**
     * 节点的状态值
     * 0 表示无覆盖
     * 1 表示 有摄像头
     * 2 表示有覆盖
     * 后序遍历 根据左右节点的情况，来判断自己的状态
     */
    public static int minCamer(TreeNode root){
        if(root == null){
            //空节点默认为有覆盖状态，避免在叶子节点上放摄像头
            return 2;
        }
        int left = minCamer(root.left);
        int right = minCamer(root.right);

        //如果左右节点都覆盖了的话，那么本节点的状态就应该是无覆盖，没有摄像头
        if(left == 2 && right == 2){
            //(2,2)
            return 0;
        }else if(left == 0 || right == 0){
            //左右节点都是无覆盖状态，那根节点此时应该放一个摄像头
            // (0,0) (0,1) (0,2) (1,0) (2,0)
            res++;
            return 1;
        }else {
            // 左右节点的 状态为 (1,1) (1,2) (2,1) 也就是左右节点至少存在 1个摄像头，
            // 那么本节点就是处于被覆盖状态
            return 2;
        }
    }

}
