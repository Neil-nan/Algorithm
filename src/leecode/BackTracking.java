package leecode;

import java.util.*;
import java.util.LinkedList;

/**
 * 回溯算法
 * @author neil
 */
public class BackTracking {
    public static void main(String[] args) {
        //System.out.println(combine(4, 2));
        //System.out.println(combinationSum2(new int[]{2, 5, 2, 1, 2}, 5));
        //System.out.println(restoreIpAddresses("25525511135"));
    }

    /**
     * 题目：77 组合
     */
    static List<List<Integer>> result = new ArrayList<>();//存放符合条件结果法集合
    static List<Integer> path = new LinkedList<>();//用来存放符合条件结果
    public static List<List<Integer>> combine(int n, int k){
        backTracking1(n, k, 1);
        return result;
    }

    public static void backTracking1(int n, int k, int startIndex){
        //终止条件
        if(path.size() == k){
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = startIndex; i <= n - (k - path.size()) + 1; i++){
            path.add(i);
            backTracking1(n, k, i + 1);
            path.remove(path.size() - 1);
        }
    }

    /**
     * 题目：216 组合总和III
     */
    public static List<List<Integer>> combinationSum3(int k, int n){
        backTracking2(k, n, 1, 0);
        return result;
    }

    public static void backTracking2(int k, int n, int startIndex, int sum){
        //终止条件
        if(path.size() == k){
            if(sum == n){
                result.add(new ArrayList<>(path));
                return;
            }
        }

        for (int i = startIndex; i <= 9 - (k - path.size()) + 1; i++){//剪枝
            path.add(i);
            sum += i;
            if(sum > n){//剪枝
                sum -= i;
                path.remove(path.size() - 1);
                return;
            }
            backTracking2(k, n, i + 1, sum);
            sum -= i;//回溯
            path.remove(path.size() - 1);
        }
    }

    //换种表达方式
    public static void build(int k, int n, int startIndex, int sum) {

        if (sum > n) return;

        if (path.size() > k) return;

        if (sum == n && path.size() == k) {
            result.add(new ArrayList<>(path));
            return;
        }

        for(int i = startIndex; i <= 9; i++) {
            path.add(i);
            sum += i;
            build(k, n, i + 1, sum);
            sum -= i;
            path.remove(path.size() - 1);
        }
    }

    /**
     * 题目：17 电话号码的字母组合
     */
    //设置全局列表存储最后的结果
    static List<String> list = new ArrayList<>();
    static StringBuilder temp = new StringBuilder();//结果的字符串拼接
    public static List<String> letterCombinations(String digits){
        //判断digits的合法性
        if(digits == null || digits.length() == 0){
            return list;
        }
        for (int i = 0; i < digits.length(); i++){
            if(digits.charAt(i) == '1' || digits.charAt(i) == '*' || digits.charAt(i) == '#'){
                return list;
            }
        }
        //初试对应所有的数字，为了直接对应2-9，新增了两个无效的字符串""
        String[] numString = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        backTracking3(digits, numString, 0);
        return list;

    }

    //index表示digits中的指针，比如digits如果为"23",num 为0，则str表示2对应的 abc
    public static void backTracking3(String digits, String[] numString, int index){
        //终止条件
        if(index == digits.length()){
            list.add(new String(temp));
            return;
        }
        //str表示当前num对应的字符串
        String str = numString[digits.charAt(index) - '0'];
        for(int i = 0; i < str.length(); i++){
            temp.append(str.charAt(i));
            backTracking3(digits, numString, index + 1);
            temp.deleteCharAt(temp.length() - 1);
        }
    }

    /**
     * 题目：39 组合总和
     */
    //这里需要两个全员变量，之前的题中已经提到过了
    //static List<List<Integer>> result = new ArrayList<>();//存放符合条件结果法集合
    //static List<Integer> path = new LinkedList<>();//用来存放符合条件结果
    public static List<List<Integer>> combinationSum(int[] candidates, int target){
        Arrays.sort(candidates);//先进行排序
        backTracking4(candidates, target, 0, 0);
        return result;
    }

    public static void backTracking4(int[] candidates, int target, int sum, int index){
        //终止条件
        if(sum > target){
            return;
        }
        if(sum == target){
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = index; i < candidates.length; i++){
            if(sum + candidates[i] > target){//排序后再大于目标值后面的就不用遍历了，没有用了
                break;
            }
            sum += candidates[i];
            path.add(candidates[i]);
            backTracking4(candidates, target, sum, i);
            sum -= candidates[i];
            path.remove(path.size() - 1);//回溯
        }
    }

    /**
     * 题目：40 组合总和II
     */
    //不使用辅助数组的方法以后再说
    static boolean[] used;
    public static List<List<Integer>> combinationSum2(int[] candidates, int target){
        used = new boolean[candidates.length];
        //加标志数组，用来辅助判断同层节点是否已经遍历
        Arrays.fill(used, false);
        //为了将重复法数字都放到一起，所以先进行排序
        Arrays.sort(candidates);
        backTracking5(candidates, target, 0, 0);
        return result;
    }

    public static void backTracking5(int[] candidates, int target, int sum, int startIndex){
        //终止条件
        if(sum == target){
            result.add(new ArrayList<>(path));
            return;
        }

        for(int i = startIndex; i < candidates.length; i++){
            if(sum + candidates[i] > target){
                break;
            }
            //出现重复节点，同层的第一个节点已经被访问过，所以直接跳过
            if(i > 0 && candidates[i] == candidates[i - 1] && !used[i - 1]){
                continue;
            }
            used[i] = true;
            sum += candidates[i];
            path.add(candidates[i]);
            //每个节点仅能选一次，所以从下一位
            backTracking5(candidates, target, sum, i + 1);
            used[i] = false;
            sum -= candidates[i];
            path.remove(path.size() - 1);

        }
    }

    /**
     * 题目：131 分割回文串
     */
    static List<List<String>> lists = new ArrayList<>();//存放符合条件结果法集合
    static Deque<String> deque = new LinkedList<>();
    public static List<List<String>> partition(String s){
        backTracking6(s, 0);
        return lists;
    }

    public static void backTracking6(String s, int startIndex){
        //终止条件
        if(startIndex >= s.length()){
            lists.add(new ArrayList(deque));
            return;
        }
        for(int i = startIndex; i < s.length(); i++){
            //如果是回文子串，则记录
            if(isPalindrome(s, startIndex, i)){
                String str = s.substring(startIndex, i + 1);
                deque.addLast(str);
            }else {
                continue;
            }
            //起始位置后移
            backTracking6(s, i + 1);
            deque.removeLast();
        }
    }

    //判断回文子串的方法
    //双指针
    public static boolean isPalindrome(String s, int start, int end){
        for(int i = start, j = end; i < j; i++, j--){
            if(s.charAt(i) != s.charAt(j)){
                return false;
            }
        }
        return true;
    }

    /**
     * 题目：93 复原IP地址
     */
    static List<String> res = new ArrayList<>();
    public static List<String> restoreIpAddresses(String s){
        if(s.length() > 12){
            return res;
        }
        backTracking7(s, 0, 0);
        return res;
    }

    //startIndex搜索的起始位置 pointNum添加逗点的数量
    public static void backTracking7(String s, int startIndex, int pointNum){
        //终止条件
        if(pointNum == 3){//逗点数量为3时，分隔结束
            //判断第四段子字符串是否合法，如果合法就放进result中
            if(isValid(s, startIndex, s.length() - 1)){
                res.add(s);
            }
            return;
        }

        for(int i = startIndex; i < s.length(); i++){
            if(isValid(s, startIndex, i)){
                s = s.substring(0, i + 1) + '.' + s.substring(i + 1);//插入逗点
                pointNum++;
                backTracking7(s, i + 2, pointNum);//插入逗点之后下一个子串的起始位置为i+2
                pointNum--;
                s = s.substring(0, i+ 1) + s.substring(i +2);//回溯去掉逗点
            }else {
                break;
            }
        }
    }

    //判断字符串s在左闭右闭区间所组成的数字是否合法
    public static boolean isValid(String s, int start, int end){
        if(start > end){
            return false;
        }
        if(s.charAt(start) == '0' && start != end){//0开头的数字不合法
            return false;
        }
        int num = 0;
        for(int i = start; i <= end; i++){
            if(s.charAt(i) > '9' || s.charAt(i) < '0'){//遇到非数字字符不合法
                return false;
            }
            num = num * 10 + (s.charAt(i) - '0');
            if(num > 255){//如果大于255了不合法
                return false;
            }
        }
        return true;
    }

    /**
     * 题目：78 子集
     */
    //static List<List<Integer>> result = new ArrayList<>();//存放符合条件结果法集合
    //static List<Integer> path = new LinkedList<>();//用来存放符合条件结果
    public static List<List<Integer>> subsets(int[] nums){
        subsetsHelper(nums, 0);
        return result;
    }

    public static void subsetsHelper(int[] nums, int startIndex){
        result.add(new ArrayList<>(path));//把所有节点记录下来
        //终止条件
        if(startIndex >= nums.length){
            return;
        }
        for(int i = startIndex; i < nums.length; i++){
            path.add(nums[i]);
            subsetsHelper(nums, i + 1);
            path.remove(path.size() - 1);
        }

    }

    /**
     * 题目：90子集II
     */
    //static List<List<Integer>> result = new ArrayList<>();//存放符合条件结果法集合
    //static List<Integer> path = new LinkedList<>();//用来存放符合条件结果
    //static boolean[] used;
    public static List<List<Integer>> subsetWithDup(int[] nums){
        if(nums.length == 0){
            result.add(path);
            return result;
        }
        //排序
        Arrays.sort(nums);
        used = new boolean[nums.length];
        subsetWithDupHelper(nums, 0);
        return result;
    }

    public static void subsetWithDupHelper(int[] nums, int startIndex){
        result.add(new ArrayList<>(path));
        //终止条件
        if(startIndex >= nums.length){
            return;
        }
        for(int i = startIndex; i < nums.length; i++){
            if(i > 0 && nums[i] == nums[i - 1] && !used[i - 1]){
                continue;
            }
            path.add(nums[i]);
            used[i] = true;
            subsetWithDupHelper(nums, i + 1);
            path.remove(path.size() - 1);
            used[i] = false;//回溯
        }
    }

    /**
     * 题目：491 递增子序列
     */
    //static List<List<Integer>> result = new ArrayList<>();//存放符合条件结果法集合
    //static List<Integer> path = new LinkedList<>();//用来存放符合条件结果
    public static List<List<Integer>> findSubsequences(int[] nums){
        findSubsequencesHelper(nums, 0);
        return result;
    }

    public static void findSubsequencesHelper(int[] nums, int startIndex){
        if(path.size() > 1){
            result.add(new ArrayList<>(path));
        }
        //终止条件
        if(startIndex >= nums.length){
            return;
        }
        int[] used = new int[201];
        for(int i = startIndex; i < nums.length; i++){//for是管没一层的，递归是管每一条线的
            if(!path.isEmpty() && nums[i] < path.get(path.size() - 1) || (used[nums[i] + 100]) == 1){
                continue;//path不为空，但是最新的节点比前面的小（不是递增），或者同一层中已经使用过该元素，略过
            }
            used[nums[i] + 100] = 1;
            path.add(nums[i]);
            findSubsequencesHelper(nums, i + 1);
            path.remove(path.size() - 1);
        }

    }

    /**
     * 题目：46 全排列
     */
    //static List<List<Integer>> result = new ArrayList<>();//存放符合条件结果法集合
    //static List<Integer> path = new LinkedList<>();//用来存放符合条件结果
    //static boolean[] used;
    public static List<List<Integer>> permute(int[] nums){
        if(nums.length == 0){
            return result;
        }
        used = new boolean[nums.length];
        permuteHelper(nums);
        return result;
    }

    public static void permuteHelper(int[] nums){
        //终止条件
        if(path.size() == nums.length){//找到一个
            result.add(new ArrayList<>(path));
            return;
        }

        for(int i = 0; i < nums.length; i++){
            if(used[i]){
                continue;
            }
            used[i] = true;
            path.add(nums[i]);
            permuteHelper(nums);
            used[i] = false;
            path.remove(path.size() - 1);
        }
    }

    /**
     * 题目：47 全排列II
     */
    //static List<List<Integer>> result = new ArrayList<>();//存放符合条件结果法集合
    //static List<Integer> path = new LinkedList<>();//用来存放符合条件结果
    //static boolean[] used;
    public static List<List<Integer>> permuteUnique(int[] nums){
        used = new boolean[nums.length];
        //排序
        Arrays.sort(nums);
        permuteUniqueHelper(nums);
        return result;
    }

    public static void permuteUniqueHelper(int[] nums){
        //终止条件
        if(path.size() == nums.length){
            result.add(new ArrayList<>(path));
            return;
        }
        for(int i = 0; i < nums.length; i++){
            if(used[i]){
                continue;
            }
            // used[i - 1] == true，说明同⼀树⽀nums[i - 1]使⽤过
            // used[i - 1] == false，说明同⼀树层nums[i - 1]使⽤过
            // 如果同⼀树层nums[i - 1]使⽤过则直接跳过
            if(i > 0 && nums[i] == nums[i - 1] && used[i - 1] == false){
                continue;
            }
            used[i] = true;
            path.add(nums[i]);
            permuteUniqueHelper(nums);
            used[i] = false;
            path.remove(path.size() - 1);
        }
    }

    /**
     * 题目：332 重新安排行程
     */
    //这里只写一种方法（只看懂一种）
    //Map<出发机场, Map<到达机场, 航班次数>> map
    static Map<String, Map<String, Integer>> map;
    //static Deque<String> res;
    public static List<String> findItinerary(List<List<String>> tickets){
        map = new HashMap<String, Map<String, Integer>>();
        res = new LinkedList<>();
        for (List<String> t : tickets) {
            Map<String, Integer> temp;
            if(map.containsKey(t.get(0))){
                temp = map.get(t.get(0));
                temp.put(t.get(1), temp.getOrDefault(t.get(1), 0) + 1);
            }else {
                temp = new TreeMap<>();//升序map
                temp.put(t.get(1), 1);
            }
            map.put(t.get(0), temp);
        }
        res.add("JFK");
        findItineraryHelper(tickets.size());
        return new ArrayList<>(res);
    }

    //只需要找到一个行程，所以不选void
    public static boolean findItineraryHelper(int ticketNum){
        if(res.size() == ticketNum + 1){
            return true;
        }
        String last = res.get(res.size() - 1);
        if(map.containsKey(last)){//防止出现null
            for (Map.Entry<String, Integer> target : map.get(last).entrySet()) {
                int count = target.getValue();
                if(count > 0){
                    res.add(target.getKey());
                    target.setValue(count - 1);
                    if(findItineraryHelper(ticketNum)){
                        return true;
                    }
                    res.remove(res.size() - 1);
                    target.setValue(count);
                }
            }
        }
        return false;
    }

    /**
     * 题目：51 N皇后
     */
    static List<List<String>> que = new ArrayList<>();
    public static List<List<String>> solveNQueens(int n){
        char[][] chessboard = new char[n][n];
        for (char[] c : chessboard) {
            Arrays.fill(c, '.');
        }
        solveNQueensHelper(n, 0, chessboard);
        return que;
    }

    //n为输入棋盘大小 row为当前递归到棋盘的第几行
    public static void solveNQueensHelper(int n, int row, char[][] chessboard){
        //终止条件
        if(row == n){
            que.add(ArrayToList(chessboard));
            return;
        }

        for(int col = 0; col < n; col++){
            if(isValid(row, col, n, chessboard)){
                chessboard[row][col] = 'Q';
                solveNQueensHelper(n, row + 1, chessboard);
                chessboard[row][col] = '.';
            }
        }
    }

    public static List ArrayToList(char[][] chessboard){
        List<String> list = new ArrayList<>();

        for (char[] c : chessboard) {
            list.add(String.copyValueOf(c));
        }
        return list;
    }

    public static boolean isValid(int row, int col, int n, char[][] chessboard){
        //检查列
        for(int i = 0; i < row; i++){
            if(chessboard[i][col] == 'Q'){
                return false;
            }
        }

        //检查45度角
        for(int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--){
            if(chessboard[i][j] == 'Q'){
                return false;
            }
        }

        //检查135度角
        for(int i = row - 1, j = col + 1; i >= 0 && j <= n - 1; i--, j++){
            if(chessboard[i][j] == 'Q'){
                return false;
            }
        }
        return true;
    }

    /**
     * 题目：37 解数独
     */
    public static void solveSudoku(char[][] board){
        solveSudokuHelper(board);
    }

    public static boolean solveSudokuHelper(char[][] board){
        //一个for循环遍历棋盘的行，一个for循环遍历棋盘的列
        //一行一列确定下来之后，递归遍历这个位置放9个数字的可能性
        for(int i = 0; i < 9; i++){//遍历行
            for(int j = 0; j < 9; j++){//遍历列
                if(board[i][j] != '.'){//跳过原始数字
                    continue;
                }
                for(char k = '1'; k <= '9'; k++){//(i, j)这个位置放k是否合适
                    if(isValidSudoku(i, j, k, board)){
                        board[i][j] = k;
                        if(solveSudokuHelper(board)){//如果找到合适的一组立刻返回
                            return true;
                        }
                        board[i][j] = '.';
                    }
                }
                // 9个数都试完了，都不行，那么就返回false
                return false;
                // 因为如果一行一列确定下来了，这里尝试了9个数都不行，说明这个棋盘找不到解决数独问题的解！
                // 那么会直接返回， 「这也就是为什么没有终止条件也不会永远填不满棋盘而无限递归下去！」
            }
        }
        //遍历完没有返回false，说明找到了合适的棋盘位置
        return true;
    }

    //判断棋盘是否合法
    //1 同行是否重复
    //2 同列是否重复
    //3 9宫格里是否重复
    public static boolean isValidSudoku(int row, int col, char val, char[][] board){
        //同行是否重复
        for(int i = 0; i < 9; i++){
            if(board[row][i] == val){
                return false;
            }
        }
        //同列是否重复
        for(int j = 0; j < 9; j++){
            if(board[j][col] == val){
                return false;
            }
        }
        //9宫格里是否重复
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for(int i = startRow; i < startRow + 3; i++){
            for(int j = startCol; j < startCol + 3; j++){
                if(board[i][j] == val){
                    return false;
                }
            }
        }

        return true;
    }

}
