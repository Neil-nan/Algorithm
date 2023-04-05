package leecode;

import java.util.*;

/**
 * 哈希表
 * @author neil
 */
public class HashTab {
    public static void main(String[] args) {
        List<List<Integer>> lists = fourSum1(new int[]{1,-2,-5,-4,-3,3,3,5},-11);
        System.out.println(lists);
    }

    /**
     * 题目：242 有效的字母
     */
    //使用数组
    public static boolean isAnagram1(String s, String t){
        //先判断长度
        if(s.length() != t.length()){
            return false;
        }

        int[] record = new int[26];

        //一个字符串录入
        for (int i = 0; i < s.length(); i++) {
            record[s.charAt(i) - 'a']++;
        }

        //另一个字符串输出
        for(int i = 0; i < t.length(); i++){
            record[t.charAt(i) - 'a']--;
        }

        //检查
        for (int count : record) {
            if(count != 0){
                return false;
            }
        }

        return true;
    }

    //排序
    public static boolean isAnagram2(String s, String t){
        if(s.length() != t.length()){
            return false;
        }

        char[] str1 = s.toCharArray();
        char[] str2 = t.toCharArray();

        //排序
        Arrays.sort(str1);
        Arrays.sort(str2);

        return Arrays.equals(str1, str2);
    }

    //使用hash表
    public static boolean isAnagram3(String s, String t){
        //判断
        if(s.length() != t.length()){
            return false;
        }

        //创建hash表
        Map<Character, Integer> table = new HashMap<Character, Integer>();
        //录入一个字符串
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            table.put(ch, table.getOrDefault(ch, 0) + 1);
        }
        //输出一个字符串
        for (int i = 0; i < t.length(); i++) {
            char ch = t.charAt(i);
            table.put(ch, table.getOrDefault(ch, 0) - 1);
            if(table.get(ch) < 0){
                return false;
            }
        }

        return true;

    }

    /**
     * 题目：383 赎金信
     */
    //使用hash
    public static boolean canConstruct1(String ransomNote, String magazine){
        //判断长度
        if(ransomNote.length() > magazine.length()){
            return false;
        }

        //创建hash表
        Map<Character, Integer> table = new HashMap<>();

        //输入magazine
        for(int i = 0; i < magazine.length(); i++){
            char mag = magazine.charAt(i);
            table.put(mag, table.getOrDefault(mag, 0) + 1);
        }

        //输出ransomNote
        for(int i = 0; i < ransomNote.length(); i++){
            char ran = ransomNote.charAt(i);
            table.put(ran, table.getOrDefault(ran, 0) - 1);
            if(table.get(ran) < 0){
                return false;
            }
        }

        return true;
    }

    //使用数组
    public static boolean canConstruct2(String ransomNote, String magazine){
        //判断长度
        if(ransomNote.length() > magazine.length()){
            return false;
        }

        //创建数组
        int[] record = new int[26];

        //输入magazine
        for(int i = 0; i < magazine.length(); i++){
            char mag = magazine.charAt(i);
            record[mag - 'a']++;
        }

        //输出ransomNote
        for(int i = 0; i < ransomNote.length(); i++){
            char ran = ransomNote.charAt(i);
            record[ran - 'a']--;
        }

        for (int count : record) {
            if(count < 0){
                return false;
            }
        }

        return true;
    }

    /**
     * 题目：49 字母异位词分组
     */
    //排序
    public static List<List<String>> groupAnagrams1(String[] strs){
        //String:排序后的字母 List<String>可能出现的异位词集合
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String str : strs) {
            //将str进行排序
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            //生成key
            String key = new String(chars);
            //查找并判断是否是新的可以
            List<String> list = map.getOrDefault(key, new ArrayList<String>());
            list.add(str);
            map.put(key, list);
        }

        //括号里填写新建list的指定数值
        return new ArrayList<List<String>>(map.values());
    }

    //使用数组计数
    public static List<List<String>> groupAnagrams2(String[] strs){
        //String:排序后的字母 List<String>可能出现的异位词集合
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String str : strs) {
            int[] counts = new int[26];
            //生成key
            for (int i = 0; i < str.length(); i++) {
                counts[str.charAt(i) - 'a']++;
            }
            //过滤出大于0的字母，按顺序拼接
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 26; i++) {
                if(counts[i] > 0){
                    //记录字母
                    sb.append((char)('a' + i));
                    //记录次数
                    sb.append(counts[i]);
                }
            }

            String key = sb.toString();
            List<String> list = map.getOrDefault(key, new ArrayList<String>());
            list.add(str);
            map.put(key, list);
        }
        return new ArrayList<List<String>>(map.values());
    }


    /**
     * 题目：438 找到字符串中所有字母异位词
     */
    //自己做的
    public static List<Integer> findAnagrams1(String s, String p){
        //Map<String, List<String>> map = new HashMap<String, List<String>>();
        List<Integer> list = new ArrayList<Integer>();
        //录入key
        char[] chars = p.toCharArray();
        Arrays.sort(chars);
        //key
        String key = new String(chars);
        //System.out.println("key = " + key);
        //List<String> list = map.getOrDefault(key, new ArrayList<String>());
        //扫描s字符串
        for (int i = 0; i <= s.length() - p.length(); i++) {
            //创建子字符串
            String substring = s.substring(i, i + p.length());
            char[] charArray = substring.toCharArray();
            Arrays.sort(charArray);
            String str = new String(charArray);
            //System.out.println("str =" + str);
            if(key.equals(str)){
                list.add(i);
            }
        }

        return list;
    }

    //使用数组
    public static List<Integer> findAnagrams2(String s, String p) {
        int n = s.length();
        int m = p.length();
        List<Integer> res = new ArrayList<>();
        if(n < m) return res;
        int[] pCnt = new int[26];
        int[] sCnt = new int[26];
        //先判断第一组
        for(int i = 0; i < m; i++){
            pCnt[p.charAt(i) - 'a']++;
            sCnt[s.charAt(i) - 'a']++;
        }
        if(Arrays.equals(sCnt, pCnt)){
            res.add(0);
        }
        //然后每次将S字符串+1，-1并进行判断
        for(int i = m; i < n; i++){
            //s数组去一个元素
            sCnt[s.charAt(i - m) - 'a']--;
            //s数组添加一个元素
            sCnt[s.charAt(i) - 'a']++;
            //对新的子字符串进行判断
            if(Arrays.equals(sCnt, pCnt)){
                res.add(i - m + 1);
            }
        }
        return res;
    }

    /**
     * 题目：349 两个数组的交集
     */
    public static int[] intersection(int[] nums1, int[] nums2){
        //判断
        if(nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0){
            return new int[0];
        }

        //set集合无序不可重复
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> resSet = new HashSet<>();
        //遍历数组1
        for (int i : nums1) {
            set1.add(i);
        }
        //遍历数组2
        for (int i : nums2) {
            if(set1.contains(i)){
                resSet.add(i);
            }
        }
        //转数组
        int[] resArr = new int[resSet.size()];
        int index = 0;
        for (Integer integer : resSet) {
            resArr[index++] = integer;
        }

        return resArr;
    }

    /**
     * 题目：350 两个数组的交集II
     */
    //使用哈希表
    public static int[] intersect1(int[] nums1, int[] nums2){
        //判断
        if(nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0){
            return new int[0];
        }
        //创建两个hashmap表 key:数 value：重复次数
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        //Map<Integer, Integer> resMap = new HashMap<Integer, Integer>();
        //使用数组接收结果
        int[] res = new int[nums1.length > nums2.length ? nums1.length : nums2.length];
        int index = 0;
        
        //遍历数组1
        for (int i : nums1) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        //遍历数组2
        for (int i : nums2) {
            if(map.getOrDefault(i, 0) > 0){
                res[index++] = i;
                map.put(i, map.get(i) - 1);
            }
        }

        return Arrays.copyOfRange(res, 0, index);
    }

    //使用排序 + 双指针
    public static int[] intersect2(int[] nums1, int[] nums2){
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int len1 = nums1.length;
        int len2 = nums2.length;
        int[] ans = new int[Math.max(len1, len2)];
        int index = 0;
        int index1 = 0;
        int index2 = 0;
        while (index1 < len1 && index2 < len2){
            if(nums1[index1] < nums2[index2]){
                index1++;
            }else if(nums1[index1] > nums2[index2]){
                index2++;
            }else {
                ans[index] = nums1[index1];
                index++;
                index1++;
                index2++;
            }
        }

        return Arrays.copyOfRange(ans, 0, index);
    }


    /**
     * 题目：202 快乐数
     */
    public static boolean isHappy(int n){
        Set<Integer> record = new HashSet<Integer>();
        while (n != 1 && !record.contains(n)){
            record.add(n);
            n = getNextNum(n);
        }

        return n == 1;
    }

    public static int getNextNum(int n){
        int res = 0;
        while (n > 0){
            int temp = n % 10;
            res += temp * temp;
            n = n / 10;
        }
        return res;
    }

    /**
     * 题目：1 两数之和
     */
    public static int[] twoSum(int[] nums, int target){
        int[] res = new int[2];
        if(nums == null || nums.length == 0){
            return res;
        }
        //创建hash表 key:数值 value：位置
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            int temp = target - nums[i];
            //证明找到了
            if(map.containsKey(temp)){
                res[1] = i;
                res[0] = map.get(temp);
            }

            //没有就记录到map中
            map.put(nums[i], i);
        }

        return res;
    }

    /**
     * 题目：454 四数相加II
     */
    //不会。。。
    public static int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4){
        //key 两数之和  value 两数之和所出现的次数
        Map<Integer, Integer> map = new HashMap<>();
        int temp;
        int res = 0;
        //统计前两个数组中的元素之和
        for (int i : nums1) {
            for (int j : nums2) {
                temp = i + j;
                map.put(temp, map.getOrDefault(temp, 0) + 1);
            }
        }
        //统一剩余两个元素的和，在map中找是否存在相加为0的情况，同时记录次数
        for (int i : nums3) {
            for (int j : nums4) {
                temp = i + j;
                if(map.containsKey(0 - temp)){
                     res += map.get(0 - temp);
                }
            }
        }

        return res;
    }

    /**
     * 题目：383 赎金信
     */
    //使用hash表解答
    public static boolean canConstruct3(String ransomNote, String magazine){
        if(magazine == null || magazine.length() == 0){
            return false;
        }
        if(magazine.length() < ransomNote.length()){
            return false;
        }
        //记录magazine key 字母 value 出现的次数
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < magazine.length(); i++) {
            char t = magazine.charAt(i);
            map.put(t, map.getOrDefault(t, 0) + 1);
        }

        //扣除ransomNote,并检查
        for (int i = 0; i < ransomNote.length(); i++) {
            char t = ransomNote.charAt(i);
            if(map.getOrDefault(t, 0) > 0){
                map.put(t, map.get(t) - 1);
            }else {
                return false;
            }
        }
        return true;
    }

    //创建数组解答
    public static boolean canConstruct4(String ransomNote, String magazine){
        //判断
        if(ransomNote.length() > magazine.length()){
            return false;
        }
        //创建数组
        int[] record = new int[26];

        //输入magazine
        for(int i = 0; i < magazine.length(); i++){
            char mag = magazine.charAt(i);
            record[mag - 'a']++;
        }

        //输出ransomNote
        for(int i = 0; i < ransomNote.length(); i++){
            char ran = ransomNote.charAt(i);
            record[ran - 'a']--;
        }

        for (int count : record) {
            if(count < 0){
                return false;
            }
        }
        return true;
    }

    /**
     * 题目：15 三数之和
     */
    //本题不需要返回索引下标，所以可以使用双指针法，而两数之和那道题需要返回结果的下标，所以不能排序也就不能使用双指针
    //看答案做的
    public static List<List<Integer>> threeSum(int[] nums){
        List<List<Integer>> result = new ArrayList<>();
        //对数组进行排序
        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {
            //去重：如果第一个就大于零，那么就没有继续的必要了
            if(nums[i] > 0){
                return result;
            }

            //去重：和前一个重复，已经讨论过的情况，不重复讨论
            if(i > 0 && nums[i] == nums[i - 1]){
                continue;
            }

            //创建左右指针
            int left = i + 1;
            int right = nums.length - 1;
            //去重：如果右指针小于0 最大小于零，没有讨论意义
            if(nums[right] < 0){
                return result;
            }

            while (left < right){
                int sum = nums[i] + nums[left] + nums[right];
                //和大于零，右指针向里收一收
                if(sum > 0){
                    right--;
                }else if(sum < 0){//和小于零，左指针向外走一走
                    left++;
                }else {//有结果
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    //去重,将已有结果重复在数组中的数值过滤掉
                    while (right > left && nums[right] == nums[right - 1]){
                        right--;
                    }
                    while (right > left && nums[left] == nums[left + 1]){
                        left++;
                    }

                    //下一步探索
                    left++;
                    right--;
                }
            }
        }

        return result;
    }

    /**
     * 题目：18 四数之和
     */
    public static List<List<Integer>> fourSum1(int[] nums, int target){
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {
            //去重,目标值大于零的情况下
            if(nums[i] > 0 && nums[i] > target){
                return result;
            }
            if(i > 0 && nums[i - 1] == nums[i]){
                continue;
            }

            for (int j = i + 1; j < nums.length; j++){
                //去重
                if(j > i + 1 && nums[j - 1] == nums[j]){
                    continue;
                }

                //创建左右指针
                int left = j + 1;
                int right = nums.length - 1;
                while (right > left){
                    long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];
                    //判断
                    if(sum > target){
                        right--;
                    }else if(sum < target){
                        left++;
                    }else {//找到结果
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

                        //去重
                        while (right > left && nums[right] == nums[right - 1]){
                            right--;
                        }
                        while (right > left && nums[left] == nums[left + 1]){
                            left++;
                        }

                        right--;
                        left++;
                    }
                }
            }
        }

        return result;
    }

}
