package leecode;

/**
 * 字符串
 * @author neil
 */
public class StringChar {
    public static void main(String[] args) {
        System.out.println(repeatedSubstringPattern2("ababab"));
    }

    /**
     * 题目：344 反转字符串
     */
    public static void reverseString(char[] s){
        //判断
        if(s == null || s.length == 0){
            return;
        }

        //创建左右指针
        int len = s.length;
        int left = 0;
        int right = len - 1;
        char temp;

        while (left < right){
            temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
    }

    /**
     * 题目：541 反转字符串II
     */
    public static String reverseStr(String s, int k){

        char[] sChar = s.toCharArray();
        //计算完整的反转次数
        int count = sChar.length / (2 * k);
        for (int i = 0; i < count; i++) {
            reverse(sChar, 0 + 2 * k * i, k - 1 + 2 * k * i);
        }

        //计算剩余的字母个数
        int remain = sChar.length % (2 * k);
        //判断
        if(remain < k){
            //剩余部分全部反转
            reverse(sChar, count * 2 * k,sChar.length - 1);
        }else {
            reverse(sChar, count * 2 * k, count * 2 * k + k - 1);
        }

        //注意字符数组转换成字符串的形式
        return new String(sChar);

    }

    //创建一个利用双指针反转字符串的方法
    public static void reverse(char[] reverse, int left, int right){
        while (left < right){
            char temp = reverse[left];
            reverse[left] = reverse[right];
            reverse[right] = temp;
            left++;
            right--;
        }
    }


    /**
     * 题目：剑指offer 05 替换空格
     */
    public static String replaceSpace1(String s){
        if(s == null || s.length() == 0){
            return s;
        }

        StringBuffer resSb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == ' '){
                resSb.append("%20");
            }else {
                resSb.append(c);
            }
        }

        return new String(resSb);
    }

    //方法二 双指针法
    public static String replaceSpace2(String s){
        if(s == null || s.length() == 0){
            return s;
        }
        //扩充空间，空格数量2倍
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == ' '){
                str.append("  ");
            }
        }
        //若是没有空格直接返回
        if(str.length() == 0){
            return s;
        }
        //有空格情况，定义两个指针
        int left = s.length() - 1;//左指针，指向原始字符串左后一个位置
        s += str.toString();
        int right = s.length() - 1;//右指针，指向扩展字符串的最后一个位置
        char[] chars = s.toCharArray();
        while (left >= 0){
            if(chars[left] == ' '){
                chars[right--] = '0';
                chars[right--] = '2';
                chars[right] = '%';
            }else {
                chars[right] = chars[left];
            }
            right--;
            left--;
        }

        return new String(chars);
    }

    /**
     * 题目：151 反转字符串里的单词
     */
    //看答案的
    //去除首尾以及中间多余空间
    //反转整个字符串
    //反转各个单词
    public static String reverseWords(String s){
        // System.out.println("ReverseWords.reverseWords2() called with: s = [" + s + "]");
        // 1.去除首尾以及中间多余空格
        StringBuffer sb = removeSpace(s);
        // 2.反转整个字符串
        reverseString(sb, 0, sb.length() - 1);
        // 3.反转各个单词
        reverseEachWord(sb);
        return sb.toString();
    }

    //去除首尾以及中间多余空格
    public static StringBuffer removeSpace(String s){
        int start = 0;
        int end = s.length() - 1;
        while (s.charAt(start) == ' '){
            start++;
        }
        while (s.charAt(end) == ' '){
            end--;
        }
        StringBuffer sb = new StringBuffer();
        while (start <= end){
            char c = s.charAt(start);
            if(c != ' ' || sb.charAt(sb.length() - 1) != ' '){
                sb.append(c);
            }
            start++;
        }
        return sb;
    }

    //反转字符串
    public static void reverseString (StringBuffer sb, int start, int end){
        while (start < end){
            char temp = sb.charAt(start);
            sb.setCharAt(start, sb.charAt(end));
            sb.setCharAt(end, temp);
            start++;
            end--;
        }
    }

    //反转单词
    public static void reverseEachWord(StringBuffer sb){
        int start = 0;
        int end = 1;
        int n = sb.length();
        while (start < n) {
            while (end < n && sb.charAt(end) != ' ') {
                end++;
            }
            reverseString(sb, start, end - 1);
            start = end + 1;
            end = start + 1;
        }
    }


    /**
     * 题目：剑指offer58-II左旋转字符串
     */
    public static String reverseLeftWords(String s, int n){
        char[] sChar = s.toCharArray();
        //先进行全字符串反转
        reverse2(sChar, 0, s.length() - 1);
        //分别反转两端字符
        reverse2(sChar, 0, s.length() - 1 - n);
        reverse2(sChar, s.length() - n, s.length() - 1);
        return new String(sChar);
    }

    //反转方法
    public static void reverse2(char[] sChar, int start, int end){
        while (start < end){
            char temp = sChar[start];
            sChar[start] = sChar[end];
            sChar[end] = temp;
            start++;
            end--;
        }
    }

    /**
     * 题目：28 找出字符串中第一个匹配项的下标
     */
    //KMP算法
    public static int strstr1(String haystack, String needle){
        if(needle.length() == 0){
            return 0;
        }
        int[] next = new int[needle.length()];
        getNext(next, needle);

        int j = 0;//小字符串的指针
        for (int i = 0; i < haystack.length(); i++) {
            while(j > 0 && needle.charAt(j) != haystack.charAt(i)){
                j = next[j - 1];
            }
            if(needle.charAt(j) == haystack.charAt(i)){
                j++;
            }
            if(j == needle.length()){
                return i - needle.length() + 1;
            }
        }
        return -1;
    }

    //求next数组
    //i 后缀末尾 j前缀末尾
    public static void getNext(int[] next, String s){
        int j = 0;
        next[0] = 0;
        for (int i = 1; i < s.length(); i++) {
            //详解见知乎：https://www.zhihu.com/question/21923021/answer/1032665486
            //缩小到使前缀的前缀等会后缀的后缀
            while(j > 0 && s.charAt(i) != s.charAt(j)){
                j = next[j - 1];
            }
            if(s.charAt(i) == s.charAt(j)){
                j++;
            }
            next[i] = j;
        }
    }

    //暴力匹配
    public static int strstr2(String haystack, String needle){
        int n = haystack.length();
        int m = needle.length();
        for (int i = 0; i + m <= n; i++) {
            boolean flag = true;
            for(int j = 0; j < m; j++){
                if(haystack.charAt(i + j) != needle.charAt(j)){
                    flag = false;
                    break;
                }
            }
            if(flag){
                return i;
            }
        }
        return -1;
    }

    /**
     * 题目：459 重复的子字符串
     */
    //枚举，全走一遍
    public static boolean repeatedSubstringPattern1(String s){
        int len = s.length();
        //i为可能子字符串中的元素个数
        for (int i = 1; i < len / 2; i++) {
            //如果可以被字符串整除,则继续，否则跳过
            if(len % i == 0){
                boolean match = true;
                for(int j = i; j < len; j++){
                    if (s.charAt(j) != s.charAt(j - 1)){
                        match = false;
                        break;
                    }
                }
                if (match){
                    return true;
                }
            }
        }
        return false;
    }

    //kmp算法
    public static boolean repeatedSubstringPattern2(String s){
        //空字符串
        if (s.equals("")){
            return false;
        }

        int len = s.length();
        //原串加个空格（哨兵），使下标从1开始，这样j从0开始，也不用初始化了
        s = " " + s;
        char[] chars = s.toCharArray();
        int[] next = new int[len + 1];

        //构造next数组过程，j从0开始（空格），i从2开始
        for(int i = 2, j = 0; i <= len; i++){
            //匹配不成功，j回到前一位置next数组所对应的值
            while (j > 0 && chars[i] != chars[j + 1]){
                j = next[j];
            }
            //匹配成功，j往后移
            if(chars[i] == chars[j +1]){
                j++;
            }
            //更新next数组的值
            next[i] = j;
        }

        //最后判断是否是重复的子字符串，这里next[len]即代表next数组末尾的值
        //最长相等前后缀不包含的子串就是最小重复子串
        if(next[len] > 0 && len % (len - next[len]) == 0){
            return true;
        }

        return false;
    }


}
