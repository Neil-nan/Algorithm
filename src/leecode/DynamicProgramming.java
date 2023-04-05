package leecode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class DynamicProgramming {
    public static void main(String[] args) {
        System.out.println(uniquePathsWithObstacles(new int[][]{{0, 1, 0}, {1, 0, 0}, {0, 0, 0}}));
    }

    /**
     * 题目：509 斐波那契数
     */
    //使用递归方法
    public static int fib1(int n){
        if(n < 2){
            return n;
        }
        return fib1(n - 1) + fib1(n - 2);
    }

    //使用动态规划
    public static int fib2(int n){
        if(n < 2){
            return n;
        }
        int[] dp = new int[2];
        dp[0] = 0;
        dp[1] = 1;
        for(int i = 2; i <= n; i++){
            int tmp = dp[0] + dp[1];
            dp[0] = dp[1];
            dp[1] = tmp;
        }
        return dp[1];
    }

    /**
     * 题目：70 爬楼梯
     */
    public static int climbStairs(int n){
        int[] dp = new int[n + 1];//里面有一个0层台阶，所以要加一个
        dp[0] = 1;
        dp[1] = 1;
        for(int i = 2; i <= n; i++){
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    /**
     * 题目：746 使用最小花费爬楼梯
     */
    public static int minCostClimbingStairs(int[] cost){
        int len = cost.length;
        int[] dp = new int[len + 1];

        //从下标为0 或下标为1 的台阶开始，因此支付费用为0
        dp[0] = 0;
        dp[1] = 0;

        //计算到达每一层台阶的最小费用
        for(int i = 2; i <= len; i++){
            dp[i] = Math.min(dp[i - 2] + cost[i - 2], dp[i - 1] + cost[i - 1]);
        }

        return dp[len];
    }

    /**
     * 题目：62 不同路径
     * 1. 确定dp数组下标含义 dp[i][j] 到每一个坐标可能的路径种类
     * 2. 递推公式 dp[i][j] = dp[i-1][j] dp[i][j-1]
     * 3. 初始化 dp[i][0]=1 dp[0][i]=1 初始化横竖就可
     * 4. 遍历顺序 一行一行遍历
     * 5. 推导结果 。。。。。。。。
     */
    public static int uniquePaths(int m, int n){
        int[][] dp = new int[m][n];
        //初始化
        for(int i = 0; i < m; i++){
            dp[i][0] = 1;//到两边的路径是唯一的
        }
        for(int i = 0; i < n; i++){
            dp[0][i] = 1;
        }

        for(int i = 1; i < m; i++){
            for(int j = 1; j < n; j++){
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m - 1][n - 1];
    }

    /**
     * 题目：63 不同路径II
     */
    public static int uniquePathsWithObstacles(int[][] obstacleGrid){
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];

        //如果在起点或终点出现了障碍，直接返回0
        if(obstacleGrid[0][0] == 1 || obstacleGrid[m - 1][n - 1] == 1){
            return 0;
        }

        //初始化时，障碍物之后也都是0
        for(int i = 0; i < m && obstacleGrid[i][0] == 0; i++){//这里之前判断错误，当判断了obstacleGrid是错误的之后就直接跳出了循环
            dp[i][0] = 1;
        }
        for(int j = 0; j < n && obstacleGrid[0][j] == 0; j++){
            dp[0][j] = 1;
        }

        for(int i = 1; i < m; i++){
            for(int j = 1; j < n; j++){
                dp[i][j] = (obstacleGrid[i][j] == 0) ? dp[i - 1][j] + dp[i][j - 1] : 0;
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * 题目：343 整数拆分
     */
    public static int integerBreak(int n){
        //dp[i]为正整数i拆分后的结果最大乘积
        int[] dp = new int[n + 1];
        dp[2] = 1;
        for(int i = 3; i <= n; i++){
            for(int j = 1; j <= i / 2; j++){//遍历后半段没有意义
                //并且，在本题中，我们分析 dp[0], dp[1]都是无意义的，
                //j 最大到 i-j,就不会用到 dp[0]与dp[1]
                dp[i] = Math.max(dp[i], Math.max(j * (i - j), j * dp[i - j]));
                // j * (i - j) 是单纯的把整数 i 拆分为两个数 也就是 i,i-j ，再相乘
                //而j * dp[i - j]是将 i 拆分成两个以及两个以上的个数,再相乘。
            }
        }
        return dp[n];
    }

    /**
     * 题目：96 不同的二叉搜索树
     */
    public static int numTrees(int n){
        //初试化dp数组
        int[] dp = new int[n + 1];
        //初始化0个节点和1个节点的情况
        dp[0] = 1;
        dp[1] = 1;
        for(int i = 2; i <= n; i++){
            for(int j = 1; j <= i; j++){
                //对于第i个节点，需要考虑1作为根节点直到i作为根节点的情况，所以需要累加
                //一共i个节点，对于根节点j时,左子树的节点个数为j-1，右子树的节点个数为i-j
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }


    /**
     * 题目：背包理论基础
     * @param weight  物品的重量
     * @param value   物品的价值
     * @param bagSize 背包的容量
     */
    public static void testWeightBagProblem(int[] weight, int[] value, int bagSize){
        //创建dp数组
        int goods = weight.length;//获得物品的数量
        int[][] dp = new int[goods][bagSize + 1];//i代表物品的种类，j代表背包的容量

        //初试化dp数组
        //创建数组后，其中默认的值就是0
        for(int j = weight[0]; j <= bagSize; j++){//相当于第一行只能放weight[0],但是要大于背包的容量才能放进去
            dp[0][j] = value[0];
        }

        //填充dp
        for(int i = 1; i < goods; i++){
            for(int j = 1; j <= bagSize; j++){
                if(j < weight[i]){
                    /**
                     * 当前背包的容量都没有当前物品i大的时候，是不放物品i的
                     * 那么前i-1个物品能放下的最大价值就是当前情况的最大价值
                     */
                    dp[i][j] = dp[i - 1][j];
                }else {
                    /**
                     * 当前背包的容量可以放下物品i
                     * 那么此时分两种情况
                     *  1 不放物品i
                     *  2 放物品i
                     *  比较这两种情况下，哪种背包中的物品的最大价值最大
                     */
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);
                }
            }
        }
    }

    /**
     * 题目：背包问题的一维dp数组
     */
    public static void testWeightBagProblem1(int[] weight, int[] value, int bagWeight){
        int wLen = weight.length;
        //定义dp数组：dp[j]表示背包容量为j时，能获得的最大价值
        int[] dp = new int[bagWeight + 1];
        //遍历顺序：先遍历物品，再遍历背包容量
        for(int i = 0; i < wLen; i++){
            for(int j = bagWeight; j >= weight[i]; j--){//这里从后下向前遍历，防止重复累加，上面的二维数组没有这样做是因为累加的是i-1，不会发生重复
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
        }
    }

    /**
     * 题目：416 分割等和子集
     */
    //一维数组版本
    //j代表背包的容量
    public static boolean canPartition1(int[] nums){
        if(nums == null || nums.length == 0){
            return false;
        }
        int n = nums.length;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        //总和为奇数时，不能平均分
        if(sum % 2 != 0){
            return false;
        }
        //平分的目标值
        int target = sum / 2;
        int[] dp = new int[target + 1];
        for(int i = 0; i < n; i++){
            for(int j = target; j >= nums[i]; j--){
                //物品 i 的重量是 nums[i]，其价值也是 nums[i]
                dp[j] = Math.max(dp[j], dp[j - nums[i]] + nums[i]);
            }
        }

        return dp[target] == target;
    }

    //使用二维数组版本
    public static boolean canPartition2(int[] nums){
        int len = nums.length;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        //如果是奇数不满足要求
        if(sum % 2 != 0){
            return false;
        }

        int target = sum / 2;
        int[][] dp = new int[len][target + 1];
        // 创建二维状态数组，行：物品索引，列：容量（包括 0）
        /*
        dp[i][j]表示从数组的 [0, i] 这个子区间内挑选一些正整数
          每个数只能用一次，使得这些数的和恰好等于 j。
        */
        //初始化数组
        for(int j = nums[0]; j <= target; j++){
            dp[0][j] = nums[0];
        }

        //填充表格
        //外层遍历物品
        for(int i = 1; i < len; i++){
            //内层遍历背包
            for(int j = 1; j <= target; j++){
                if(j < nums[i]){
                    dp[i][j] = dp[i - 1][j];
                }else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - nums[i]] + nums[i]);
                }
            }
        }

        return dp[len - 1][target] == target;
    }

    /**
     * 题目：1049 最后一块石头的重量II
     */
    //一维数组版本
    public static int lastStoneWeightII1(int[] stones){
        int sum = 0;
        for (int stone : stones) {
            sum += stone;
        }
        int target = sum / 2;
        //初始化数组
        int[] dp = new int[target + 1];

        for(int i = 0; i < stones.length; i++){
            for(int j = target; j >= stones[i]; j--){
                //两种情况，要么放，要么不放
                dp[j] = Math.max(dp[j], dp[j - stones[i]] + stones[i]);
            }
        }

        return (sum - dp[target]) - dp[target];
    }

    //尝试使用二维数组版本
    public static int lastStoneWeightII2(int[] stones){
        int sum = 0;
        for (int stone : stones) {
            sum += stone;
        }
        int target = sum / 2;
        //dp数组初始化
        int[][] dp = new int[stones.length][target + 1];
        for(int j = stones[0]; j <= target; j++){
            dp[0][j] = stones[0];
        }

        //填充数组
        for(int i = 1; i < stones.length; i++){
            for(int j = 1; j <= target; j++){
                if(j < stones[i]){
                    dp[i][j] = dp[i - 1][j];
                }else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - stones[i]] + stones[i]);
                }
            }
        }

        return (sum - dp[stones.length - 1][target]) - dp[stones.length - 1][target];
    }

    /**
     * 题目：494 目标和
     */
    //思路看详解
    public static int findTargetSumWays(int[] nums, int target){
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        //如果target过大，sum则无法满足
        if(Math.abs(target) > sum){
            return 0;
        }
        //(target + sum) / 2 必须整除，否则无解
        if((target + sum) % 2 != 0){
            return 0;
        }

        int size = (target + sum) / 2;
        //初始化dp数组
        int[] dp = new int[size + 1];
        dp[0] = 1;//举例：只有一个元素，目标值和sum都是0的情况只有一个
        for(int i = 0; i < nums.length; i++){
            for(int j = size; j >= nums[i]; j--){
                dp[j] += dp[j - nums[i]];
            }
        }
        return dp[size];
    }

    /**
     * 题目：474 一和零
     */
    public static int findMaxForm(String[] strs, int m, int n){
        //dp[i][j]表示i个0和j个1时的最大子集
        int[][] dp = new int[m + 1][n + 1];
        int oneNum;
        int zeroNum;
        for (String str : strs) {
            oneNum = 0;
            zeroNum = 0;
            for (char c : str.toCharArray()) {
                if(c == '0'){
                    zeroNum++;
                }else {
                    oneNum++;
                }
            }
            //倒序遍历
            for(int i = m; i >= zeroNum; i--){
                for(int j = n; j >= oneNum; j--){
                    dp[i][j] = Math.max(dp[i][j], dp[i - zeroNum][j - oneNum] + 1);
                }
            }
        }

        return dp[m][n];
    }

    /**
     * 题目：完全背包问题
     */
    public static void testCompletePackAnotherWay(int[] weight, int[] value, int bagWeight){
        int[] dp = new int[bagWeight + 1];
        for(int i = 0; i < weight.length; i++){//遍历物品
            for(int j = weight[i]; j <= bagWeight; j++){//遍历背包容量
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
        }
    }

    /**
     * 题目：518 零钱兑换II
     */
    public static int change(int amount, int[] coins){
        int[] dp = new int[amount + 1];
        //初始化dp数组，表示金额为0时只有一种情况，也就是什么都不装
        dp[0] = 1;
        for(int i = 0; i < coins.length; i++){//先遍历零钱
            for(int j = coins[i]; j <= amount; j++){
                dp[j] += dp[j - coins[i]];
            }
        }
        return dp[amount];
    }

    /**
     * 题目：377 组合总和IV
     */
    public static int combinationSum4(int[] nums, int target){
        int[] dp = new int[target + 1];
        dp[0] = 1;
        //要求排列（注意外循环和内循环）
        for(int i = 0; i <= target; i++){//外循环背包
            for(int j = 0; j < nums.length; j++){//内循环物品
                if(i >= nums[j]){
                    dp[i] += dp[i - nums[j]];
                }
            }
        }

        return dp[target];
    }

    /**
     * 题目：70 爬楼梯
     */
    //使用动态规划
    public static int climbStairs1(int n){
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for(int i = 2; i <= n; i++){
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    //稍加修改题目
    public static int climbStairs2(int n, int m){
        int[] dp = new int[n + 1];
        dp[0] = 1;
        //要求排列 注意外循环和内循环
        for(int i= 0; i <= n; i++){//先遍历背包
            for(int j = 0; j <= m; j++){//再遍历物品
                if(i >= j){
                    dp[i] += dp[i - j];
                }
            }
        }
        return dp[n];
    }

    /**
     * 题目：322 零钱兑换
     */
    public static int coinChange(int[] coins, int amount){
        int max = Integer.MAX_VALUE;
        int[] dp = new int[amount +1];
        //初始化dp数组为最大值
        for(int j = 0; j < dp.length; j++){
            dp[j] = max;
        }

        //当金额为0时需要的硬币数目为0
        dp[0] = 0;
        //没有排列的需求，无需内外循环
        for(int i = 0; i < coins.length; i++){//先遍历物品
            //正序遍历，完全背包每个硬币可以选择多次
            for(int j = coins[i]; j <= amount; j++) {
                //只有dp[j-coins[i]]不是初始最大值时，该位才有选择的必要
                if (dp[j - coins[i]] != max) {
                    //选择硬币数目最小的情况
                    dp[j] = Math.min(dp[j], dp[j - coins[i]] + 1);
                }
            }
        }

        return dp[amount] == max ? -1 : dp[amount];
    }

    /**
     * 题目：279 完全平方数
     */
    public static int numSquares(int n){
        //先遍历物品，再遍历
        int max = Integer.MAX_VALUE;
        int[] dp = new int[n + 1];
        //初始化
        for(int j = 0; j <= n; j++){
            dp[j] = max;
        }
        //当和为0时，组合的个数为0
        dp[0] = 0;
        //没有排列要求
        for(int i = 1; i * i <= n; i++){//遍历物品
            for(int j = i * i; j <= n; j++){//遍历背包
                dp[j] = Math.min(dp[j], dp[j - i * i] + 1);
            }
        }
        return dp[n];
    }

    /**
     * 题目：139 单词拆分
     */
    public static boolean wordBreak(String s, List<String> wordDict){
        //hashset会快一点
        HashSet<String> set = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;

        //这里有排列要求，所以先遍历背包再遍历物品
        for(int i = 1; i <= s.length(); i++){//遍历背包
            for(int j = 0; j < i; j++){//遍历物品
                //判断逻辑：如果确定dp[j] 是true，且 [j, i] 这个区间的子串出现在字典里，
                // 那么dp[i]一定是true。（j < i ）
                String word = s.substring(j, i);//注意不同函数的指针对应，sub函数下标从0开始
                if(set.contains(word) && dp[j] == true){
                    dp[i] = true;
                }
            }
        }

        return dp[s.length()];
    }

    /**
     * 题目：多重背包问题
     * @param weight 重量
     * @param value 价值
     * @param nums 数量
     */
    //改变物品数量为01背包格式
    public static void testMultiPack(int[] weight, int[] value, int[] nums, int bagWeight){
        List<Integer> weightList = Arrays.stream(weight).boxed().collect(Collectors.toList());
        // Arrays.stream(arr) 可以替换成IntStream.of(arr)。
        // 1.使用Arrays.stream将int[]转换成IntStream。
        // 2.使用IntStream中的boxed()装箱。将IntStream转换成Stream<Integer>。
        // 3.使用Stream的collect()，将Stream<T>转换成List<T>，因此正是List<Integer>。
        List<Integer> valueList = Arrays.stream(value).boxed().collect(Collectors.toList());
        List<Integer> numsList = Arrays.stream(nums).boxed().collect(Collectors.toList());

        for(int i = 0; i < numsList.size(); i++){
            while (numsList.get(i) > 1){//把物品展开为i
                weightList.add(weightList.get(i));
                valueList.add(valueList.get(i));
                numsList.set(i, numsList.get(i) - 1);
            }
        }

        int[] dp = new int[bagWeight + 1];
        for(int i = 0; i < weightList.size(); i++){//遍历物品
            for(int j = bagWeight; j >= weightList.get(i); j--){//遍历背包容量
                dp[j] = Math.max(dp[j], dp[j - weightList.get(i)] + valueList.get(i));
            }
        }

    }

    /**
     * 题目：198 打家劫舍
     */
    public static int rob(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }
        if(nums.length == 1){
            return nums[0];
        }

        int[] dp = new int[nums.length + 1];
        dp[1] = nums[0];
        dp[2] = Math.max(dp[1], nums[1]);
        for(int i = 3; i <= nums.length; i++){
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);//偷或者不偷
        }

        return dp[nums.length];
    }

    /**
     * 题目：213 打家劫舍II
     */
    //将打家劫舍的代码抽离出来
    public static int robII(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }
        if(nums.length == 1){
            return nums[0];
        }
        //将环拆成两个部分进行考虑（含头不含尾，含尾不含头）
        int result1 = robRange(nums, 0, nums.length - 2);//含头不含尾情况
        int result2 = robRange(nums, 1, nums.length - 1);//含尾不含头情况

        return Math.max(result1, result2);
    }

    public static int robRange(int[] nums, int start, int end){
        if(end == start){
            return nums[start];
        }
        int[] dp = new int[nums.length];
        dp[start] = nums[start];
        dp[start + 1] = Math.max(dp[start], nums[start + 1]);
        for(int i = start + 2; i <= end; i++){
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }
        return dp[end];
    }

    /**
     * 题目：337 打家劫舍III
     */
    //使用递归和动态规划相结合
    public static int robIII(TreeNode root){
        int[] result = robTree(root);
        return Math.max(result[0], result[1]);
    }

    //返回的dp数组，下标为0记录不偷该节点所得到的最大金钱，下标为1记录偷该节点所得到的最大金钱
    public static int[] robTree(TreeNode root){
        //终止条件
        if(root == null){
            return new int[]{0, 0};
        }

        //递归时采用后序遍历（通过递归函数的返回值来做下一步计算）
        int[] left = robTree(root.left);
        int[] right = robTree(root.right);
        //偷root节点，就不能偷左右子节点
        int val1 = root.val + left[0] + right[0];
        //不偷root节点，那么可以偷也可以不偷左右节点，则取较大的情况
        int val2 = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);

        return new int[]{val2, val1};
    }

    /**
     * 题目：121 买卖股票的最佳时机
     */
    //使用贪心算法
    public static int maxProfit1(int[] prices){
        //取最左最小值和最右的最大值
        int low = Integer.MAX_VALUE;
        int result = 0;
        for(int i = 0; i < prices.length; i++){
            low = Math.min(low, prices[i]);//取最左最小值
            result = Math.max(result, prices[i] - low);//直接取最大区间利润
        }
        return result;
    }

    //使用动态规划
    public static int maxProfit2(int[] prices){
        if(prices == null || prices.length == 0){
            return 0;
        }
        int len = prices.length;
        //dp[i][0]代表第i天持有股票的最大收益
        //dp[i][1]代表第i天不持有股票的最大收益
        int[][] dp = new int[len][2];
        //初始化dp
        dp[0][0] = -prices[0];
        dp[0][1] = 0;
        for(int i = 1; i < len; i++){
            //两种状态
            //第一种：第i - 1天就持有股票，保持现状
            //第二种：第i天买入股票，所得现金就是买入今天的股票后所得现金
            dp[i][0] = Math.max(dp[i - 1][0], -prices[i]);
            //两种情况
            //第一种：第i - 1天就不持有股票，保持现状
            //第二种：第i天卖出股票
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] + prices[i]);
        }

        return dp[len - 1][1];
    }

    /**
     * 题目：122 买卖股票的最佳时机II
     */
    //使用贪心算法
    public static int maxProfitII1(int[] prices){
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

    //使用动态规划（和上一题类似）
    public static int maxProfitII2(int[] prices){
        if(prices == null || prices.length == 0){
            return 0;
        }
        int len = prices.length;
        //初始化
        int[][] dp = new int[len][2];
        dp[0][0] = -prices[0];
        dp[0][1] = 0;
        for(int i = 1; i < len; i++){
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i]);//这里是和121，买股票的最佳时机唯一不同的地方
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] + prices[i]);
        }

        return dp[len - 1][1];
    }

    /**
     * 题目：123 买卖股票的最佳时机III
     */
    public static int maxProfitIII(int[] prices){
        if(prices == null || prices.length == 0){
            return 0;
        }
        int len = prices.length;
        //初始化
        //dp[i][j]中，i表示第i天，j为0~4五个状态，dp[i][j]表示第i天状态j所剩最大现金
        //0 没有操作 1 第一次买入 2 第一次卖出 3 第二次买入 4 第二次卖出
        int[][] dp = new int[len][5];
        dp[0][1] = -prices[0];
        //这里初试第二次买入状态是根据第一次卖出的状态给出的（不用过分纠结）
        dp[0][3] = -prices[0];

        for(int i = 1; i < len; i++){
            dp[i][0] = dp[i - 1][0];
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
            dp[i][2] = Math.max(dp[i - 1][2], dp[i - 1][1] + prices[i]);
            dp[i][3] = Math.max(dp[i - 1][3], dp[i - 1][2] - prices[i]);
            dp[i][4] = Math.max(dp[i - 1][4], dp[i - 1][3] + prices[i]);
        }

        return dp[len - 1][4];
    }

    /**
     * 题目：188 买卖股票的最佳时机IV
     */
    //使用二维数组，类比上一题
    public static int maxProfitIV(int k, int[] prices){
        if(prices == null || prices.length == 0){
            return 0;
        }
        int len = prices.length;
        //初始化
        int[][] dp = new int[len][2 * k + 1];
        for(int i = 1; i < k * 2; i+=2){
            dp[0][i] = -prices[0];
        }

        for(int i = 1; i < len; i++){
            for(int j = 0; j < 2 * k - 1; j += 2){
                dp[i][j + 1] = Math.max(dp[i - 1][j + 1], dp[i - 1][j] - prices[i]);
                dp[i][j + 2] = Math.max(dp[i - 1][j + 2], dp[i - 1][j + 1] + prices[i]);
            }
        }

        return dp[len - 1][2 * k];
    }

    /**
     * 题目：309 最佳买卖股票时机含冷冻期
     */
    public static int maxProfit(int[] prices){
        if(prices == null || prices.length == 0){
            return 0;
        }
        int len = prices.length;
        //初始化
        //0 持有股票状态 1 保持卖出股票的状态（度过一天冷冻期） 2 卖出股票 3 冷冻期
        int[][] dp = new int[len][4];
        dp[0][0] = -prices[0];//持有股票
        for(int i = 1; i < len; i++){
            //三种状态 前一天持有股票 前一天是冷冻期，今天买入 前一天是保持卖出股票状态，今天买入
            dp[i][0] = Math.max(dp[i - 1][0], Math.max(dp[i - 1][3] - prices[i], dp[i - 1][1] - prices[i]));
            //两种状态 前一天是冷冻期 前一天是卖出股票状态（状态二）
            dp[i][1] = Math.max(dp[i - 1][3], dp[i - 1][1]);
            //前一天一定是持有股票状态
            dp[i][2] = dp[i - 1][0] + prices[i];
            //前一天卖出股票
            dp[i][3] = dp[i - 1][2];
        }

        return Math.max(dp[len - 1][1], Math.max(dp[len - 1][2], dp[len - 1][3]));
    }

    /**
     * 题目：714 买卖股票的最佳时机含手续费
     */
    //使用贪心算法
    public static int maxProfit1(int[] prices, int fee){
        int result = 0;
        int minPrice = prices[0];//记录最低价格
        for(int i = 1; i < prices.length; i++){
            if(prices[i] < minPrice){
                minPrice = prices[i];
            }
            //买不便宜，卖就亏本
            if(prices[i] > minPrice && prices[i] <= minPrice + fee){
                continue;
            }
            if(prices[i] > minPrice + fee){
                result += prices[i] - minPrice - fee;
                minPrice = prices[i] - fee;
            }
        }
        return result;
    }
    //使用动态规划（类比122题）
    //dp[i][0] 表示第i天持有股票所省最多现金。 dp[i][1] 表示第i天不持有股票所得最多现金
    public static int maxProfit2(int[] prices, int fee){
        if(prices == null || prices.length == 0){
            return 0;
        }
        int len = prices.length;
        //初始化
        int[][] dp = new int[len][2];
        //注意减去手续费
        dp[0][0] = -prices[0] - fee;
        //买入交手续费
        for(int i = 1; i < len; i++){
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i] - fee);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] + prices[i]);
        }

        return dp[len - 1][1];
    }

    /**
     * 题目：300 最长递增子序列
     */
    public static int lengthOfLIS(int[] nums){
        //dp[i]表示i之前包括i的以nums[i]结尾的最长递增子序列的长度
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        for(int i = 1; i < nums.length; i++){
            for(int j = 0; j < i; j++){
                if(nums[i] > nums[j]){
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        //速度会比排列快一点
        int res = 0;
        for (int i = 0; i < dp.length; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    /**
     * 题目：674 最长连续递增序列
     */
    //贪心算法
    public static int findLengthOfLCIS1(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }
        int res = 1;
        int count = 1;
        for(int i = 1; i < nums.length; i++){
            if(nums[i] > nums[i - 1]){//连续记录
                count++;
            }else {//不连续，count从头开始
                count = 1;
            }
            if(count > res){
                res = count;
            }
        }
        return res;
    }

    //使用动态规划
    public static int findLengthOfLCIS2(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }
        int res = 1;
        int len = nums.length;
        //dp[i]代表当前下标最大连续值
        int[] dp = new int[len];
        Arrays.fill(dp, 1);
        for(int i = 1; i < len; i++){
            if(nums[i] > nums[i - 1]){
                dp[i] = dp[i - 1] + 1;
            }
            if(dp[i] > res){
                res = dp[i];
            }
        }
        return res;
    }

    /**
     * 题目：718 最长重复子数组
     */
    //使用二维数组
    public static int findLength1(int[] nums1, int[] nums2){
        //dp数组的含义
        //dp[i][j]：以下标i- 1为结尾的A，和以下标j - 1为结尾的B，最长重复子数组长度为dp[i][j]
        //特别注意： “以下标i - 1为结尾的A” 标明一定是 以A[i-1]为结尾的字符串 ）
        //如果是以i为结尾，那么就需要对第一行和第一列进行初始化
        int res = 0;
        int[][] dp = new int[nums1.length + 1][nums2.length + 1];

        for(int i = 1; i < nums1.length + 1; i++){
            for(int j = 1; j < nums2.length + 1; j++){
                if(nums1[i - 1] == nums2[j - 1]){
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    res = Math.max(res, dp[i][j]);
                }
            }
        }
        return res;
    }

    //滚动数组版（一维数组）
    public static int findLength2(int[] nums1, int[] nums2){
        int[] dp = new int[nums2.length + 1];
        int res = 0;

        for(int i = 1; i <= nums1.length; i++){
            //从后先前遍历
            for(int j = nums2.length; j > 0; j--){
                if(nums1[i - 1] == nums2[j - 1]){
                    dp[j] = dp[j - 1] + 1;
                }else {
                    dp[j] = 0;
                }
                res = Math.max(res, dp[j]);
            }
        }
        return res;
    }

    /**
     * 题目：1143 最长公共子序列
     */
    //二维数组
    public static int longestCommonSubsequence(String text1, String text2){
        if(text1 == null || text2 == null){
            return 0;
        }
        //初始化
        //dp[i][j]：长度为[0, i - 1]的字符串text1与长度为[0, j - 1]的字符串text2的最长公共子序列为dp[i][j]
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        for(int i = 1; i <= text1.length(); i++){
            char char1 = text1.charAt(i - 1);
            for(int j = 1; j <= text2.length(); j++){
                char char2 = text2.charAt(j - 1);
                if(char1 == char2){
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[text1.length()][text2.length()];
    }

    /**
     * 题目：1035 不相交的线
     */
    //和上面的最长公共子序列一样
    public static int maxUncrossedLines(int[] nums1, int[] nums2){
        if(nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0){
            return 0;
        }
        int len1 = nums1.length;
        int len2 = nums2.length;
        //初始化
        int[][] dp = new int[len1 + 1][len2 + 1];

        for(int i = 1; i <= len1; i++){
            for(int j = 1; j <= len2; j++){
                if(nums1[i - 1] == nums2[j - 1]){
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[len1][len2];
    }

    /**
     * 题目：53 最大子序和
     */
    //使用贪心算法
    public static int maxSubArray1(int[] nums){
        int res = Integer.MIN_VALUE;
        int count = 0;
        for(int i = 0; i < nums.length; i++){
            count += nums[i];
            if(count > res){//取区间累计的最大值（相当于不断确定最大子序终止位置）
                res = count;
            }
            if(count <= 0){//相当于重置最大子序起始位置，因为遇到负数一定是拉低总和
                count = 0;
            }
        }

        return res;
    }

    //使用动态规划
    public static int maxSubArray2(int[] nums){
        if(nums == null || nums.length == 0){
            return 0;
        }
        if(nums.length == 1){
            return nums[0];
        }
        //初始化
        int res = nums[0];
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for(int i = 1; i < nums.length; i++){
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            if(dp[i] > res){
                res = dp[i];//res保存dp[i]的最大值
            }
        }
        return res;
    }

    /**
     * 题目：392 判断子序列
     */
    //使用双指针法
    public static boolean isSubsequence1(String s, String t){
        int sLen = s.length();
        int tLen = t.length();
        if(sLen > tLen){
            return false;
        }
        int i = 0;
        int j = 0;
        while(i < sLen && j < tLen){
            if(s.charAt(i) == t.charAt(j)){
                i++;
            }
            j++;
        }

        return i == sLen;
    }

    //使用动态规划
    public static boolean isSubsequence2(String s, String t){
        int sLen = s.length();
        int tLen = t.length();
        if(sLen > tLen){//确保字符串s比t小
            return false;
        }
        //初始化
        //dp[i][j] 表示以下标i-1为结尾的字符串s，和以下标j-1为结尾的字符串t，相同子序列的长度为dp[i][j]
        int[][] dp = new int[sLen + 1][tLen + 1];
        for(int i = 1; i <= sLen; i++){
            for(int j = 1; j <= tLen; j++){
                if(s.charAt(i - 1) == t.charAt(j - 1)){
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }else {
                    dp[i][j] = dp[i][j - 1];
                }
            }
        }
        if(dp[sLen][tLen] == sLen){
            return true;
        }
        return false;
    }

    /**
     * 题目：115 不同的子序列
     */
    public static int numDistinct(String s, String t){
        int sLen = s.length();
        int tLen = t.length();
        if(sLen < tLen){
            return 0;
        }
        //初始化
        //dp[i][j]：以i-1为结尾的s子序列中出现以j-1为结尾的t的个数为dp[i][j]
        int[][] dp = new int[sLen + 1][tLen + 1];
        //dp[i][0] 表示：以i-1为结尾的s可以随便删除元素，出现空字符串的个数。
        //dp[0][j]：空字符串s可以随便删除元素，出现以j-1为结尾的字符串t的个数。
        for(int i = 0; i <= sLen; i++){
            dp[i][0] = 1;
        }

        for(int i = 1; i <= sLen; i++){
            for(int j = 1; j <= tLen; j++){
                if(s.charAt(i - 1) == t.charAt(j - 1)){
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                }else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[sLen][tLen];
    }

    /**
     * 题目：583 两个字符串的删除操作
     */
    //方法一 模仿题目115
    //dp中存储需要删除的字符个数
    public static int minDistance1(String word1, String word2){
        int len1 = word1.length();
        int len2 = word2.length();
        //初始化
        //dp[i][j]：以i-1为结尾的字符串word1，和以j-1位结尾的字符串word2，
        //想要达到相等，所需要删除元素的最少次数
        int[][] dp = new int[len1 + 1][len2 + 1];
        //dp[i][0] = i dp[0][j] = j
        for(int i = 0; i <= len1; i++){
            dp[i][0] = i;
        }
        for(int j = 0; j <= len2; j++){
            dp[0][j] = j;
        }

        for(int i = 1; i <= len1; i++){
            for(int j = 1; j <= len2; j++){
                if(word1.charAt(i - 1) == word2.charAt(j - 1)){
                    dp[i][j] = dp[i - 1][j - 1];
                }else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + 2, Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
                }
            }
        }

        return dp[len1][len2];
    }

    //方法二 模仿题目1143
    //dp中存储最长相同子序列的长度
    public static int minDistance2(String word1, String word2){
        int len1 = word1.length();
        int len2 = word2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];

        for(int i = 1; i <= len1; i++){
            for(int j = 1; j <= len2; j++){
                if(word1.charAt(i - 1) == word2.charAt(j - 1)){
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }
        return len1 + len2 - dp[len1][len2] * 2;
    }

    /**
     * 题目：72 编辑距离
     */
    public static int minDistance(String word1, String word2){
        int len1 = word1.length();
        int len2 = word2.length();
        //初始化
        //dp[i][j] 表示以下标i-1为结尾的字符串word1，和以下标j-1为结尾的字符串word2，最近编辑距离为dp[i][j]。
        int[][] dp = new int[len1 + 1][len2 + 1];
        //dp[i][0] ：以下标i-1为结尾的字符串word1，和空字符串word2，最近编辑距离为dp[i][0]。
        for(int i = 0; i <= len1; i++){
            dp[i][0] = i;
        }
        for(int j = 0; j <= len2; j++){
            dp[0][j] = j;
        }

        for(int i = 1; i <= len1; i++){
            for(int j = 1; j <= len2; j++){
                if(word1.charAt(i - 1) == word2.charAt(j - 1)){
                    dp[i][j] = dp[i - 1][j - 1];
                }else {
                    //增和删是一种情况，替换是另一种情况
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + 1, Math.min(dp[i][j - 1] + 1, dp[i - 1][j] + 1));
                }
            }
        }

        return dp[len1][len2];
    }

    /**
     * 题目：647 回文子串
     */
    //动态规划法
    public static int countSubstrings1(String s){
        int len = s.length();
        int result = 0;
        //初始化
        //dp[i][j]：s字符串下标i到下标j的字串是否是一个回文串，即s[i, j]
        boolean[][] dp = new boolean[len][len];

        //从左向右 从下到上进行遍历
        //情况一：下标i 与 j相同，同一个字符例如a，当然是回文子串
        //情况二：下标i 与 j相差为1，例如aa，也是回文子串
        //情况三：下标：i 与 j相差大于1的时候，例如cabac，此时s[i]与s[j]已经相同了，
        // 我们看i到j区间是不是回文子串就看aba是不是回文就可以了，那么aba的区间就是 i+1 与 j-1区间，这个区间是不是回文就看dp[i + 1][j - 1]是否为true。
        for(int i = len - 1; i >= 0; i--){
            //注意看定义，所以只有右上角的一半是值得讨论的，及i>j
            for(int j = i; j < len; j++){
                if(s.charAt(i) == s.charAt(j)){
                    if(j - i < 3){
                        dp[i][j] = true;
                    }else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }else {
                    dp[i][j] = false;
                }
            }
        }

        for(int i = 0; i < len; i++){
            for(int j = 0; j < len; j++){
                if(dp[i][j]){
                    result++;
                }
            }
        }
        return result;
    }

    public static int countSubstrings(String s){
        int len = s.length();
        int res = 0;
        for(int i = 0; i < 2 * len - 1; i++){//一共有2 * len - 1个中心点
            res += extend(s, i, i, len);//以i为中心
            res += extend(s, i, i + 1, len);//以i和i+1为中心
        }
        return res;
    }

    public static int extend(String s, int i, int j, int n){
        int res = 0;
        while (i >= 0 && j < n && s.charAt(i) == s.charAt(j)){
            i--;
            j++;
            res++;
        }
        return res;
    }

    /**
     * 题目：5 最长回文子串
     */
    //与上一题一样
    public static String longestPalindrome(String s){
        String res = s.substring(0,1);
        int len = s.length();
        //初始化
        boolean[][] dp = new boolean[len][len];
        for(int i = len - 1; i >= 0; i--){
            for(int j = i; j < len; j++){
                if(s.charAt(i) == s.charAt(j)){
                    if(j - i < 3){
                        dp[i][j] = true;
                    }else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }else {
                    dp[i][j] = false;
                }
            }
        }

        for(int i = 0; i < len; i++){
            for(int j = i; j < len; j++){
                if(dp[i][j]){
                    String cur = s.substring(i, j + 1);
                    if(cur.length() > res.length()){
                        res = cur;
                    }
                }
            }
        }
        return res;
    }

    /**
     * 题目：516 最长回文子序列
     */
    public static int longestPalindromeSubseq(String s){
        int len = s.length();
        //初始化
        //dp[i][j]：字符串s在[i, j]范围内最长的回文子序列的长度为dp[i][j]
        int[][] dp = new int[len][len];
//        for(int i = 0; i < len; i++){
//            dp[i][i] = 1;
//        }

        //从下往上 从左往右进行遍历
        for(int i = len - 1; i >= 0; i--){
            dp[i][i] = 1;
            for(int j = i + 1; j < len; j++){
                if(s.charAt(i) == s.charAt(j)){
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                }else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[0][len - 1];
    }
}
