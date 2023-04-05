package leecode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数组
 * @author neil
 */
public class Array {

    public static void main(String[] args) {
        int rightBorder = getRightBorder(new int[]{5, 7, 7, 8, 8, 10}, 8);
        //System.out.println(rightBorder);

        int i = removeElement2(new int[]{1, 2, 3, 4, 1}, 1);
        //System.out.println(i);

        int num = removeDuplicates(new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4});
        System.out.println(num);

        int res = totalFruit2(new int[]{3,3,3,1,2,1,1,2,3,3,4});

        String s = minWindow("ADOBECODEBANC", "ABC");

        int[][] ints = generateMatrix(5);
    }

    /**
     * 题目：704 二分查找
     */
    public static int search(int[] nums, int target){
        //判断资格
        if(target < nums[0] || target > nums[nums.length - 1]){
            return -1;
        }

        int left = 0;
        int right = nums.length - 1;
        while (left <= right){
            int mid = (right - left) / 2 + left;
            if(nums[mid] < target){
                left = mid + 1;
            }else if (nums[mid] > target){
                right = mid - 1;
            }else if(nums[mid] == target){
                return mid;
            }
        }

        return -1;
    }

    /**
     * 题目：35 搜索插入位置
     */
    public int searchInsert(int[] nums, int target){
        int left = 0;
        int right = nums.length - 1;
        while (left <= right){//左闭右闭区间
            int mid = (right - left) / 2 + left;
            if(nums[mid] > target){
                right = mid - 1;//target在左区间，[left, mid - 1]
            }else if(nums[mid] < target){
                left = mid +1;//target在右区间，[mid + 1, right]
            }else {
                return mid;
            }
        }
        // 分别处理如下四种情况
        // 目标值在数组所有元素之前  return 0 (right = -1)
        // 目标值等于数组中某一个元素  return middle;
        // 目标值插入数组中的位置 [left, right]，return  right + 1
        // 目标值在数组所有元素之后的情况 [left, right]， 因为是右闭区间，所以 return right + 1
        //或者返回left，因为如果没有找到那么左一定在右的加一位置
        return right +1;
    }

    /**
     * 题目：34 在排序数组中查找元素的第一个和最后一个位置
     */
    public int[] searchRange(int[] nums, int target) {
        int leftBorder = getLeftBorder(nums, target);
        int rightBorder = getRightBorder(nums, target);
        // 情况一
        if (leftBorder == -2 || rightBorder == -2)
            return new int[]{-1, -1};
        // 情况三
        if (rightBorder - leftBorder > 1)
            return new int[]{leftBorder + 1, rightBorder - 1};
        // 情况二
        return new int[]{-1, -1};
    }

    //个人的理解，这里避开了寻找数字，也就是没有了中间值等于目标值的这个情况，
    // 那么最后左右指针必然会左右交叉，将等于的情况放在哪个情况使左指针控制右边界
    //右指针控制左边界
    //可以类比35题，35最后如果数组中没有目标值，right指针会指向目标的左边界，目标值会在right + 1
    //找右边界
    private static int getRightBorder(int[] nums, int target){
        int left = 0;
        int right = nums.length - 1;
        int rightBorder = -2;//记录rightBorder没有被赋值的情况
        while (left <= right){
            int mid = (right - left) / 2 + left;
            if(target < nums[mid]){
                right = mid - 1;
            }else {
                left = mid + 1;
                rightBorder = left;
            }
        }
        return rightBorder;
    }

    //找左边界
    private int getLeftBorder(int[] nums, int target){
        int left = 0;
        int right = nums.length - 1;
        int leftBorder = -2;
        while (left <= right){
            int mid = (right - left) / 2 + left;
            if(target <= nums[mid]){
                right = mid - 1;
                leftBorder = right;
            }else {
                left = mid + 1;
            }
        }

        return leftBorder;
    }

    /**
     * 题目：69 x的平方根
     */
    //二分法
    public static int mySqrt(int x) {
//        if(x == 1){
//            return 1;
//        }
        int left = 0;
//        int right = x / 2;
        int right = x;
        int ans = -1;
        while (left <= right){
            int mid = (right - left) / 2 + left;
            if((long)mid * mid <= x){
                ans = mid;
                left = mid + 1;
            }else {
                right = mid - 1;
            }
        }

        return ans;
    }

    /**
     * 题目：367 有效的完全平方根
     */
    //middle 是int类型
    //(long long)(middle * middle)是先计算middle * middle结果还是int类型,如果这时结果超出int类型范围就直接变成负数.之后再转为long long类型也是没用的
    //而(long long)middle * middle是先把前面的middle转成long long类型,再与后面的middle相乘结果就是long long类型. 也就不会因为超出int类型范围而变成负数了
    public static boolean isPerfectSquare(int num){
        int left = 0;
        int right = num;
        while (left <= right){
            int mid = (right - left) / 2 + left;
            if ((long)mid * mid == num){
                return true;
            }else if ((long)mid * mid < num){
                left = mid + 1;
            }else {
                right = mid - 1;
            }
        }

        return false;
    }

    /**
     * 题目：27 移除元素
     */
    //快慢指针
    public static int removeElement1(int[] nums, int val){
        int slowIndex = 0;
        for(int fastIndex = 0; fastIndex < nums.length; fastIndex++){
            if(nums[fastIndex] != val){
                nums[slowIndex] = nums[fastIndex];
                slowIndex++;
            }
        }
        return slowIndex;
    }

    //双指针优化
    public static int removeElement2(int[] nums, int val){
        int left = 0;
        int right = nums.length;
        while (left < right){
            if(nums[left] == val){
                nums[left] = nums[right - 1];
                right--;
            }else {
                left++;
            }
        }

        return left;
    }

    /**
     * 题目：26 删除有序数组中的重复项
     */
    public static int removeDuplicates(int[] nums){
        int n = nums.length;
        if(nums == null || n == 0){
            return 0;
        }
        if(n == 1){
            return 1;
        }
        int fast = 1;
        int slow = 1;
        while (fast < n){
            if(nums[fast] != nums[fast - 1]){
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;
    }

    /**
     * 题目：283 移动零
     */
    public static void moveZeroes(int[] nums){
        int slow = 0;
        int fast = 0;
        int n = nums.length;
        while (fast < n){
            if(nums[fast] != 0){
                //左右交换
                int temp = nums[slow];
                nums[slow] = nums[fast];
                nums[fast] = temp;

                slow++;
            }
            fast++;
        }

    }

    /**
     * 题目：844 比较含退格的字符串
     */
    //解法1 重新整合数组
    public static boolean backspaceCompare1(String s, String t){
        return build(s).equals(build(t));
    }

    public static String build(String str){
        StringBuilder sb = new StringBuilder();
        int len = str.length();

        for (int i = 0; i < len; i++) {
            if(str.charAt(i) != '#'){
                //添加字符
                sb.append(str.charAt(i));
            }else {
                //删除字符
                if(sb.length() > 0){
                    sb.deleteCharAt(sb.length() - 1);
                }
            }
        }

        return sb.toString();
    }

    //解法2 双指针(从后向前遍历)
    public static boolean backspaceCompare2(String s, String t){
        int i = s.length() - 1;
        int j = t.length() - 1;
        int skipS = 0;
        int skipT = 0;

        while (i >= 0 || j >= 0){
            while (i >= 0){
                if(s.charAt(i) == '#'){
                    skipS++;
                    i--;
                }else if(skipS > 0){
                    skipS--;
                    i--;
                }else {
                    break;
                }
            }

            while (j >= 0){
                if(t.charAt(j) == '#'){
                    skipT++;
                    j--;
                }else if(skipT > 0){
                    skipT--;
                    j--;
                }else {
                    break;
                }
            }

            //比较
            if(i >= 0 && j >= 0){
                if(s.charAt(i) != t.charAt(j)){
                    return false;
                }
            }else {
                if(i >= 0 || j >= 0){
                    return false;
                }
            }
            i--;
            j--;
        }
        return true;
    }

    /**
     * 题目：977 有序数组的平方
     */
    //左右双指针
    public static int[] sortedSquares1(int[] nums){
        int len = nums.length;
        int left = 0;
        int right = len - 1;
        int[] ans = new int[len];
        int n = len - 1;
        while (left <= right){
            if(nums[right] * nums[right] >= nums[left] * nums[left]){
                ans[n] = nums[right] * nums[right];
                right--;
            }else {
                ans[n] = nums[left] * nums[left];
                left++;
            }
            n--;
        }
        return ans;
    }

    //直接排序
    public static int[] sortedSquares2(int[] nums){
        int len = nums.length;
        int[] ans = new int[len];
        for(int i = 0; i < len; i++){
            ans[i] = nums[i] * nums[i];
        }
        Arrays.sort(ans);
        return ans;
    }

    /**
     * 题目：209 长度最小的子数组
     */
    public static int minSubArrayLen(int target, int[] nums){
        //数组长度
        int ans = Integer.MAX_VALUE;
        int sum = 0;
        int left = 0;
        for(int right = 0; right < nums.length; right++){
            sum += nums[right];
            while (sum >= target){
                ans = Math.min(ans, right - left + 1);
                //左指针进一位
                sum -= nums[left++];
            }
        }

        return ans == Integer.MAX_VALUE ? 0 : ans;

    }

    /**
     * 题目：904 水果成篮
     */
    public static int totalFruit1(int[] fruits){
        //当fruit数组长度<=2时，直接返回数组长度
        if (fruits.length < 3){
            return fruits.length;
        }
        //结果：最大长度
        int res = 0;
        //j为遍历fruits数组的指针，也是滑动窗口的右边界，
        //f1,f2分别为第一个和第二个篮子的起始索引，f1同时也是滑动窗口的左边界，(f1永远是最老的)
        //t为未来两个篮子里的第一个篮子的起始索引
        for(int j = 0, f1 = 0, f2 = 0, t = 0; j < fruits.length; j++){
            //当f1=f2时，fruits[j]!=fruits[f1]&&fruits[j]!=fruits[f2]说明遇到的是第二个篮子要装的第二种水果；
            //当f1!=f2时，fruits[j]!=fruits[f1]&&fruits[j]!=fruits[f2]说明遇到的是第三种水果
            if(fruits[j] != fruits[f1] && fruits[j] != fruits[f2]){
                //当f1=f2时，说明第一个篮子已经装了一种水果，第二个篮子里还没有装，不更新f1，只更新f2，
                //即往第二个篮子里装第二种水果；
                //当f1!=f2时，fruits[j]!=fruits[f1]&&fruits[j]!=fruits[f2]说明遇到的是第三种水果，则更新f1和f2，
                //即当前两个篮子的两种水果已装满，更新f1和f2为未来两个篮子里的两种水果
                if (f1 != f2){
                    f1 = t;
                    //f2 = j;
                }
                f2 = j;
            }
            //t寻找未来两个篮子中第一个篮子要装的一种水果的起始索引，每次t与当前的j作对比，当fruits[t]!=fruits[j]时，更新t=j
            if(fruits[t] != fruits[j]){
                t = j;
            }
            res = res > (j - f1 + 1) ? res : (j - f1 + 1);
        }
        return res;
    }

    //这个方法是我自己想的，有漏洞，有的重复查找
    public static int totalFruit2(int[] fruits){
        int left = 0;//left mid 指针分别指向两个不同的数，right寻找两数的最大集
        int mid = 0;
        int right = 0;
        int maxFruit = 0;

        while(right < fruits.length ){
            while(left < fruits.length && mid < fruits.length && fruits[left] == fruits[mid]){
                mid++;
            }
            right = mid;
            while(right < fruits.length && (fruits[right] == fruits[mid] || fruits[right] == fruits[left])){
                right++;
            }

            maxFruit = Math.max(maxFruit, right - left);

            left = mid;
        }

        return maxFruit;

    }


    /**
     * 题目：76 最小覆盖子串
     */
    public static String minWindow(String s, String t){
        if (s == null || s == "" || t == null || t == "" || s.length() < t.length()) {
            return "";
        }
        //维护两个数组，记录已有字符串指定字符的出现次数，和目标字符串指定字符的出现次数
        //ASCII表总长128
        int[] need = new int[128];
        int[] have = new int[128];

        //将目标字符串指定字符的出现次数记录
        for (int i = 0; i < t.length(); i++) {
            need[t.charAt(i)]++;
        }

        //分别为左指针，右指针，最小长度(初始值为一定不可达到的长度)
        //已有字符串中目标字符串指定字符的出现总频次以及最小覆盖子串在原字符串中的起始位置
        int left = 0, right = 0, min = s.length() + 1, count = 0, start = 0;
        while (right < s.length()) {
            char r = s.charAt(right);
            //说明该字符不被目标字符串需要，此时有两种情况
            // 1.循环刚开始，那么直接移动右指针即可，不需要做多余判断
            // 2.循环已经开始一段时间，此处又有两种情况
            //  2.1 上一次条件不满足，已有字符串指定字符出现次数不满足目标字符串指定字符出现次数，那么此时
            //      如果该字符还不被目标字符串需要，就不需要进行多余判断，右指针移动即可
            //  2.2 左指针已经移动完毕，那么此时就相当于循环刚开始，同理直接移动右指针
            if (need[r] == 0) {
                right++;
                continue;
            }
            //当且仅当已有字符串目标字符出现的次数小于目标字符串字符的出现次数时，count才会+1
            //是为了后续能直接判断已有字符串是否已经包含了目标字符串的所有字符，不需要挨个比对字符出现的次数
            if (have[r] < need[r]) {
                count++;
            }
            //已有字符串中目标字符出现的次数+1
            have[r]++;
            //移动右指针
            right++;
            //当且仅当已有字符串已经包含了所有目标字符串的字符，且出现频次一定大于或等于指定频次
            while (count == t.length()) {
                //挡窗口的长度比已有的最短值小时，更改最小值，并记录起始位置
                if (right - left < min) {
                    min = right - left;
                    start = left;
                }
                char l = s.charAt(left);
                //如果左边即将要去掉的字符不被目标字符串需要，那么不需要多余判断，直接可以移动左指针
                if (need[l] == 0) {
                    left++;
                    continue;
                }
                //如果左边即将要去掉的字符被目标字符串需要，且出现的频次正好等于指定频次，那么如果去掉了这个字符，
                //就不满足覆盖子串的条件，此时要破坏循环条件跳出循环，即控制目标字符串指定字符的出现总频次(count）-1
                if (have[l] == need[l]) {
                    count--;
                }
                //已有字符串中目标字符出现的次数-1
                have[l]--;
                //移动左指针
                left++;
            }
        }
        //如果最小长度还为初始值，说明没有符合条件的子串
        if (min == s.length() + 1) {
            return "";
        }
        //返回的为以记录的起始位置为起点，记录的最短长度为距离的指定字符串中截取的子串
        return s.substring(start, start + min);
    }

    /**
     * 题目：59 螺旋矩阵II
     */
    public static int[][] generateMatrix(int n){
        int[][] res = new int[n][n];
        int loop = n / 2;
        int row = 0;
        int column = 0;
        int l = n - 1;
        int dot = 0;
        for(int i = 0; i < loop; i++){
            int a = row;
            int b = column;
            int count = 0;
            //上
            for(int j = 0; j < l; j++){
                res[a][b++] = j + 1 + l * count + dot;
            }
            count++;
            //右
            for(int j = 0; j < l; j++){
                res[a++][b] = j + 1 + l * count + dot;
            }
            count++;
            //下
            for(int j = 0; j < l; j++){
                res[a][b--] = j + 1 + l * count + dot;
            }
            count++;
            //左
            for(int j = 0; j < l; j++){
                res[a--][b] = j + 1 + l * count + dot;
            }

            row++;
            column++;
            l -= 2;
            dot = res[++a][b];
        }

        if(n % 2 == 1){
            int i = (n - 1) / 2;
            res[i][i] = n * n;
        }

        return res;
    }

    /**
     * 题目 54 螺旋矩阵
     */
    public static List<Integer> spiralOrder1(int[][] matrix){
        List<Integer> list = new ArrayList<Integer>();
        //判断
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return list;
        }

        //边界
        int left = 0;
        int right = matrix[0].length - 1;
        int top = 0;
        int bottom = matrix.length - 1;
        int numEle = matrix.length * matrix[0].length;
        while (numEle > 0){
            //上
            for(int i = left; i <= right && numEle > 0; i++){
                list.add(matrix[top][i]);
                numEle--;
            }
            top++;
            //右
            for(int i = top; i <= bottom && numEle > 0; i++){
                list.add(matrix[i][right]);
                numEle--;
            }
            right--;
            //下
            for(int i = right; i >= left && numEle > 0; i--){
                list.add(matrix[bottom][i]);
                numEle--;
            }
            bottom--;
            //左
            for(int i = bottom; i >= top && numEle > 0; i--){
                list.add(matrix[i][left]);
                numEle--;
            }
            left++;
        }
        return list;
    }

    /**
     * 题目：剑指offer 29 顺时针打印矩阵
     */
    public static int[] spiralOrder2(int[][] matrix){
        //判断
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return new int[0];
        }
        int left = 0;
        int right = matrix[0].length - 1;
        int top = 0;
        int bottom = matrix.length - 1;
        int numEle = matrix.length * matrix[0].length;
        int[] ans = new int[numEle];
        int index = 0;
        while (index < numEle){
            //上
            for(int i = left; i <= right && index < numEle; i++){
                ans[index++] = matrix[top][i];
            }
            top++;
            //右
            for(int i = top; i <= bottom && index < numEle; i++){
                ans[index++] = matrix[i][right];
            }
            right--;
            //下
            for(int i = right; i >= left && index < numEle; i--){
                ans[index++] = matrix[bottom][i];
            }
            bottom--;
            //左
            for(int i = bottom; i >= top && index < numEle; i--){
                ans[index++] = matrix[i][left];
            }
            left++;
        }
        return ans;
    }
}
