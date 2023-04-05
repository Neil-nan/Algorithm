package leecode;

import com.sun.source.tree.Tree;

import java.util.*;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * 二叉树
 * @author neil
 */
public class BinaryTree {

    public static void main(String[] args) {

//        TreeNode root = new TreeNode(1);
//        //root.left = new TreeNode(5);
//        root.right = new TreeNode(2);
//        root.right.left = new TreeNode(2);
//        //root.right.right = new TreeNode(20);
//        System.out.println(findMode(root));
        int a = 5;
        List<Integer> res = new LinkedList<>();
        sum(a, res);
        System.out.println(a);
        System.out.println(res.toString());

    }

    public static void sum(int a, List<Integer> res){
        a++;
        res.add(a);
    }

    /**
     * 题目：144 二叉树的前序遍历
     */
    //递归
    public static List<Integer> preorderTraversal1(TreeNode root){
        List<Integer> result = new ArrayList<Integer>();
        preorder(root, result);
        return result;
    }

    public static void preorder(TreeNode root, List<Integer> result){
        if(root == null){
            return;
        }
        result.add(root.val);
        //递归
        preorder(root.left, result);
        preorder(root.right, result);

    }

    //迭代
    //用栈，前序遍历顺序：中-左-右，入栈顺序：中-右-左
    public static List<Integer> preorderTraversal2(TreeNode root){
        List<Integer> result = new ArrayList<>();
        if(root == null){
            return result;
        }
        //建栈
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()){
            TreeNode node = stack.pop();
            result.add(node.val);
            if(node.right != null){
                stack.push(node.right);
            }
            if(node.left != null){
                stack.push(node.left);
            }
        }

        return result;

    }

    /**
     * 题目：145 二叉树的后序遍历
     */
    //递归
    public static List<Integer> postorderTraversal1(TreeNode root){
        List<Integer> result = new ArrayList<>();
        postorder(root, result);
        return result;
    }

    public static void postorder(TreeNode root, List<Integer> result){
        if(root == null){
            return;
        }
        postorder(root.left, result);
        postorder(root.right, result);
        result.add(root.val);
    }

    //迭代
    public static List<Integer> postorderTraversal2(TreeNode root){
        List<Integer> result = new ArrayList<>();
        if(root == null){
            return result;
        }
        //建栈
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()){
            TreeNode node = stack.pop();
            result.add(node.val);
            if (node.left != null){
                stack.push(node.left);
            }
            if (node.right != null){
                stack.push(node.right);
            }
        }
        Collections.reverse(result);
        return result;
    }

    /**
     * 题目：94 二叉树的中序遍历
     */
    //递归
    public static List<Integer> inorderTraversal1(TreeNode root){
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    public static void inorder(TreeNode root, List<Integer> result){
        if (root == null){
            return;
        }
        inorder(root.left, result);
        result.add(root.val);
        inorder(root.right, result);
    }

    //迭代
    public List<Integer> inorderTraversal2(TreeNode root){
        List<Integer> result = new ArrayList<>();
        if(root == null){
            return result;
        }
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()){
            if(cur != null){
                stack.push(cur);
                cur = cur.left;
            }else {
                cur = stack.pop();
                result.add(cur.val);
                cur = cur.right;
            }
        }

        return result;
    }

    /**
     * 题目：144 二叉树的前序遍历
     *      145 二叉树的后序遍历
     *      94 二叉树的中序遍历
     *
     *      懒得看了以后再想
     */
    //迭代的统一写法(根据中序遍历的思路进行书写)
    public static List<Integer> preorderTraversal3(TreeNode root){
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> st = new Stack<>();
        if (root != null) st.push(root);
        while (!st.empty()) {
            TreeNode node = st.peek();
            if (node != null) {
                st.pop(); // 将该节点弹出，避免重复操作，下面再将右中左节点添加到栈中
                if (node.right!=null) st.push(node.right);  // 添加右节点（空节点不入栈）
                if (node.left!=null) st.push(node.left);    // 添加左节点（空节点不入栈）
                st.push(node);                          // 添加中节点
                st.push(null); // 中节点访问过，但是还没有处理，加入空节点做为标记。

            } else { // 只有遇到空节点的时候，才将下一个节点放进结果集
                st.pop();           // 将空节点弹出
                node = st.peek();    // 重新取出栈中元素
                st.pop();
                result.add(node.val); // 加入到结果集
            }
        }
        return result;

    }

    public static List<Integer> inorderTraversal3(TreeNode root){
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        if(root != null){
            stack.push(root);
        }
        while (!stack.isEmpty()){
            TreeNode node = stack.peek();
            if(node != null){
                stack.pop();//将该节点弹出，避免重复操作，下面再将右中左几点添加到栈中
                if (node.right != null){
                    stack.push(node.right);//添加右节点（空节点不入栈）
                }
                stack.push(node);//添加中节点
                stack.push(null);//中节点访问过，但是还没有处理，加入空节点作为标记
                if(node.left != null){
                    stack.push(node.left);//添加左节点（空节点不入栈）
                }
            }else {//只有遇到空节点的时候，才将下一个节点放进结果集
                stack.pop();//将空节点弹出
                node = stack.peek();//重新取出栈中元素
                stack.pop();
                result.add(node.val);//加入到结果集

            }
        }
        return result;
    }

    public static List<Integer> postorderTraversal(TreeNode root){
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        if(root != null){
            stack.push(root);
        }
        while (!stack.isEmpty()){
            TreeNode node = stack.peek();
            if(node != null){
                stack.pop();//避免重复操作
                stack.push(node);//添加头节点
                /**
                 * 这里不能插入null，因为ArrayDeque中的默认值就是null，插入后无法确定该位置是否存在元素，改成stack
                 */
                stack.push(null);//添加空节点
                if(node.right != null){
                    stack.push(node.right);
                }
                if(node.left != null){
                    stack.push(node.left);
                }
            }else {
                stack.pop();//弹出空节点
                node = stack.peek();
                stack.pop();
                result.add(node.val);
            }
        }
        return result;
    }

    /**
     * 题目：102 二叉树的层序遍历
     */
    //迭代法
    public static List<List<Integer>> levelOrder1(TreeNode root){
        List<List<Integer>> resList = new ArrayList<List<Integer>>();
        if(root == null){
            return resList;
        }
        //创建队列，借助队列
        Queue<TreeNode> que = new LinkedList<>();
        que.offer(root);

        while(!que.isEmpty()){
            //结果
            List<Integer> itemList = new ArrayList<Integer>();
            int len = que.size();

            while (len > 0){
                TreeNode tmpNode = que.poll();
                itemList.add(tmpNode.val);

                if(tmpNode.left != null){
                    que.add(tmpNode.left);
                }
                if(tmpNode.right != null){
                    que.add(tmpNode.right);
                }

                len--;
            }
            resList.add(itemList);
        }

        return resList;
    }

    //递归法
    public static List<List<Integer>> levelOrder2(TreeNode root){
        List<List<Integer>> resList = new ArrayList<>();
        checkFun(root, 0, resList);

        return resList;
    }

    public static void checkFun(TreeNode node, Integer deep, List<List<Integer>> resList){
        if(node == null){
            return;
        }
        deep++;

        if(resList.size() < deep){
            //当层数增加时，list的Item也增加，利用list的索引值进行层级界定
            List<Integer> item = new ArrayList<Integer>();
            resList.add(item);
        }
        resList.get(deep - 1).add(node.val);

        checkFun(node.left, deep, resList);
        checkFun(node.right, deep, resList);
    }

    /**
     * 题目：107 层序遍历II
     */
    public static List<List<Integer>> levelOrderBottom(TreeNode root){
        //创建答案结果
        List<List<Integer>> resList = new ArrayList<>();
        if(root == null){
            return resList;
        }
        //创建队列，借助队列
        Queue<TreeNode> que = new LinkedList<>();
        que.offer(root);

        while (!que.isEmpty()){
            //创建结果
            List<Integer> item = new ArrayList<>();
            //记录每一层的node个数
            int len = que.size();

            while (len > 0){
                TreeNode node = que.poll();
                //记录
                item.add(node.val);

                //添加子节点
                if(node.left != null){
                    que.add(node.left);
                }
                if(node.right != null){
                    que.add(node.right);
                }

                len--;
            }
            //添加记录
            resList.add(item);

        }
        List<List<Integer>> result = new ArrayList<>();
        for(int i = resList.size() - 1; i >= 0; i--){
            result.add(resList.get(i));
        }

        return result;
    }

    /**
     * 题目：199 二叉树的右视图
     */
    //迭代
    public static List<Integer> rightSideView(TreeNode root){
        List<Integer> res = new ArrayList<>();
        if(root == null){
            return res;
        }
        //创建队列
        Queue<TreeNode> que = new LinkedList<>();
        que.offer(root);
        while(!que.isEmpty()){
            int len = que.size();

            while (len > 0){
                TreeNode node = que.poll();

                if(node.left != null){
                    que.offer(node.left);
                }
                if(node.right != null){
                    que.offer(node.right);
                }

                if(len == 1){
                    res.add(node.val);
                }
                len--;
            }
        }
        return res;
    }

    /**
     * 题目：637 二叉树的层平均值
     */
    public static List<Double> averageOfLevels(TreeNode root){
        List<Double> res = new ArrayList<>();
        if(root == null){
            return res;
        }
        Deque<TreeNode> que = new LinkedList<>();
        que.addLast(root);

        while (!que.isEmpty()){
            int len = que.size();
            double sum = 0.0;

            for(int i = 0; i < len; i++){
                TreeNode node = que.poll();
                sum += node.val;

                if(node.left != null){
                    que.addLast(node.left);
                }
                if(node.right != null){
                    que.addLast(node.right);
                }
            }

            res.add(sum / len);
        }

        return res;
    }

    /**
     * 题目：429 N叉树的层序遍历
     */
    public static List<List<Integer>> levelOrder(Node root){
        //创建结果
        List<List<Integer>> res = new ArrayList<>();
        if(root == null){
            return res;
        }
        //创建队列
        Deque<Node> que = new LinkedList<>();
        que.addLast(root);

        while (!que.isEmpty()){
            List<Integer> item = new ArrayList<>();
            int len = que.size();

            for (int i = 0; i < len; i++) {
                Node node = que.poll();
                item.add(node.val);

                List<Node> children = node.children;
                if(children == null || children.size() == 0){
                    continue;
                }

                for (Node child : children) {
                    que.addLast(child);
                }
            }

            res.add(item);
        }
        return res;
    }

    /**
     * 题目：515 在每个树行中找最大值
     */
    public static List<Integer> largestValues(TreeNode root){
        List<Integer> res = new ArrayList<>();
        if(root == null){
            return res;
        }
        //创建队列
        Deque<TreeNode> que = new LinkedList<>();
        que.addLast(root);

        while (!que.isEmpty()){
            int len = que.size();
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < len; i++) {
                TreeNode node = que.poll();
                max = Math.max(node.val, max);

                if(node.left != null){
                    que.addLast(node.left);
                }
                if(node.right != null){
                    que.addLast(node.right);
                }
            }
            res.add(max);
        }
        return res;
    }

    /**
     * 题目：116 填充每个节点的下一个右侧节点指针
     */
    public static Node connect1(Node root){
        if(root == null){
            return root;
        }

        Deque<Node> que = new LinkedList<>();
        que.addLast(root);

        while (!que.isEmpty()){
            int len = que.size();
            while (len > 0){
                Node node = que.poll();
                if(len > 1){
                    node.next = que.peek();
                }else if(len == 1){
                    node.next = null;
                }

                //添加节点
                if(node.left != null){
                    que.addLast(node.left);
                }
                if(node.right != null){
                    que.addLast(node.right);
                }
                len--;
            }

        }
        return root;
    }

    /**
     * 题目：117 填充每个节点的下一个右侧节点指针II
     * 与116的代码相同
     */
    public static Node connect2(Node root){
        if(root == null){
            return root;
        }

        Deque<Node> que = new LinkedList<>();
        que.addLast(root);

        while (!que.isEmpty()){
            int len = que.size();
            while(len > 0){
                Node node = que.poll();
                if(len > 1){
                    node.next = que.peek();
                }else if(len == 1){
                    node.next = null;
                }

                //添加节点
                if(node.left != null){
                    que.addLast(node.left);
                }
                if(node.right != null){
                    que.addLast(node.right);
                }
                len--;
            }
        }
        return root;
    }

    /**
     * 题目：104 二叉树的最大深度
     */
    public static int maxDepth(TreeNode root){
        if(root == null){
            return 0;
        }
        Deque<TreeNode> que = new LinkedList<>();
        que.addLast(root);
        int depth = 0;
        while (!que.isEmpty()){
            depth++;
            int len = que.size();
            while (len > 0){
                TreeNode node = que.poll();
                if(node.left != null){
                    que.addLast(node.left);
                }
                if(node.right != null){
                    que.addLast(node.right);
                }
                len--;
            }
        }

        return depth;
    }

    /**
     * 题目：111 二叉树的最小深度
     */
    public static int minDepth(TreeNode root){
        if(root == null){
            return 0;
        }
        Deque<TreeNode> que = new LinkedList<>();
        que.offerLast(root);
        int depth = 0;

        while (!que.isEmpty()){
            int len = que.size();
            depth++;

            while (len > 0){
                TreeNode node = que.poll();
                if(node.left == null && node.right == null){
                    return depth;
                }

                if(node.left != null){
                    que.addLast(node.left);
                }
                if(node.right != null){
                    que.addLast(node.right);
                }
                len--;
            }
        }

        return depth;
    }

    /**
     * 题目：226 翻转二叉树
     */
    //递归法（前序遍历，先交换左右子节点，再交换左右子节点的子节点）
    public static TreeNode invertTree1(TreeNode root){
        if(root == null){
            return root;
        }

        //交换左右子节点
        swapChildren(root);
        invertTree1(root.left);
        invertTree1(root.right);
        return root;

    }

    private static void swapChildren(TreeNode node){
        TreeNode temp = node.left;
        node.left = node.right;
        node.right = temp;
    }

    //迭代法(深度遍历，前序,左右或是右左的顺序应该都没有问题)
    public static TreeNode invertTree2(TreeNode root){
        if(root == null){
            return root;
        }
        //创建栈
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.add(root);

        while (!stack.isEmpty()){
            TreeNode node = stack.peek();
            stack.pop();
            swapChildren(node);

            if(node.left != null){
                stack.add(node.left);
            }
            if(node.right != null){
                stack.add(node.right);
            }
        }

        return root;

    }

    //使用前中后序迭代方式的统一写法（前序遍历）
    public static TreeNode invertTree3(TreeNode root){
        Stack<TreeNode> stack = new Stack<>();
        if(root == null){
            return root;
        }
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode node = stack.peek();
            if(node != null){
                //向栈中装节点
                stack.pop();
                if(node.right != null){
                    stack.push(node.right);//右
                }
                if(node.left != null){
                    stack.push(node.left);//左
                }
                stack.push(node);         //中
                stack.push(null);
            }else {
                stack.pop();
                node = stack.peek();
                stack.pop();
                swapChildren(node);
            }
        }
        return root;
    }

    //使用层序遍历
    public static TreeNode invertTree4(TreeNode root){
        if(root == null){
            return root;
        }
        //创建队列，借助队列
        Deque<TreeNode> que = new ArrayDeque<>();
        que.offer(root);

        while (!que.isEmpty()){
            //记录每一层的node个数
            int len = que.size();

            while (len > 0){
                TreeNode node = que.poll();
                //转换
                swapChildren(node);
                //添加子节点
                if(node.left != null){
                    que.add(node.left);
                }
                if(node.right != null){
                    que.add(node.right);
                }
                len--;
            }
        }

        return root;
    }

    /**
     * 题目：101 对称二叉树
     */
    //递归法
    public static boolean isSymmetric1(TreeNode root){
        if(root == null){
            return true;
        }

        return compare(root.left, root.right);
    }

    //这里的left和right不是指左右子节点，而是左边和右边的节点
    private static boolean compare(TreeNode left, TreeNode right){

        //判断空节点的情况
        if(left == null && right == null){
            return true;
        }else if(left == null && right != null){
            return false;
        }else if(left != null && right == null){
            return false;
        }else if(left.val != right.val){//剩下就是讨论左右都有值的情况
            return false;
        }
        //比较外侧
        boolean compareOutside = compare(left.left, right.right);//左子树的左节点  右子树的右节点
        //比较内侧
        boolean compareInside = compare(left.right, right.left);

        return compareOutside && compareInside;
    }

    //迭代法
    //使用双端队列，相当于两个栈
    public static boolean isSymmetric2(TreeNode root){
        if(root == null){
            return true;
        }
        //进行频繁的增减元素操作更适合使用LinkedList
        Deque<TreeNode> que = new LinkedList<>();
        que.offerFirst(root.left);
        que.offerLast(root.right);

        while (!que.isEmpty()){
            TreeNode leftNode = que.pollFirst();
            TreeNode rightNode = que.pollLast();
            //判断空值
            if(leftNode == null && rightNode == null){
                continue;
            }
            if(leftNode == null && rightNode != null){
                return false;
            }
            if(leftNode != null && rightNode == null){
                return false;
            }
            //都不为空
            if(leftNode.val != rightNode.val){
                return false;
            }
            //添加新的
            que.offerFirst(leftNode.left);
            que.offerLast(rightNode.right);
            que.offerFirst(leftNode.right);
            que.offerLast(rightNode.left);
        }

        return true;

    }

    //迭代法
    //使用普通队列
    public static boolean isSymmetric3(TreeNode root){
        if(root == null){
            return true;
        }
        Queue<TreeNode> que = new LinkedList<>();
        que.offer(root.left);
        que.offer(root.right);
        while (!que.isEmpty()){
            TreeNode leftNode = que.poll();
            TreeNode rightNode = que.poll();
            //讨论空值
            if(leftNode == null && rightNode == null){
                continue;
            }
            if(leftNode == null && rightNode != null){
                return false;
            }
            if(leftNode != null && rightNode == null){
                return false;
            }
            //都非空值讨论
            if(leftNode.val != rightNode.val){
                return false;
            }

            //进一步讨论
            que.offer(leftNode.left);
            que.offer(rightNode.right);
            que.offer(leftNode.right);
            que.offer(rightNode.left);
        }

        return true;
    }

    /**
     * 题目：100 相同的树
     */
    //尝试递归
    public static boolean isSameTree1(TreeNode p, TreeNode q){
        return compareSame(p, q);
    }

    private static boolean compareSame(TreeNode p, TreeNode q){
        //先判断空值
        if(p == null && q == null){
            return true;
        }
        if(p == null && q != null){
            return false;
        }
        if(p != null && q == null){
            return false;
        }
        //两树的节点上都有值的情况
        if(p.val != q.val){
            return false;
        }

        //比较两树的左侧
        boolean compareLeft = compareSame(p.left, q.left);
        //比较两树的右侧
        boolean compareRight = compareSame(p.right, q.right);

        return compareLeft && compareRight;
    }

    //使用迭代法
    //使用双端队列，相当于两个栈
    public static boolean isSameTree2(TreeNode p, TreeNode q){
        //讨论空值
        if(p == null && q == null){
            return true;
        }
        if(p != null && q == null){
            return false;
        }
        if(p == null && q != null){
            return false;
        }
        Deque<TreeNode> que = new LinkedList<>();
        que.offerFirst(p);
        que.offerLast(q);
        while (!que.isEmpty()){
            TreeNode nodeP = que.pollFirst();
            TreeNode nodeQ = que.pollLast();
            //判断空值
            if(nodeP == null && nodeQ == null){
                continue;
            }
            if(nodeP == null && nodeQ != null){
                return false;
            }
            if(nodeP != null && nodeQ == null){
                return false;
            }
            //都非空值讨论
            if(nodeP.val != nodeQ.val){
                return false;
            }

            //进一步进行讨论
            que.offerFirst(nodeP.left);
            que.offerLast(nodeQ.left);
            que.offerFirst(nodeP.right);
            que.offerLast(nodeQ.right);
        }

        return true;
    }

    //使用迭代法
    //使用普通队列
    public static boolean isSameTree3(TreeNode p, TreeNode q){
        //判断空值
        if(p == null && q == null){
            return true;
        }
        if(p == null && q != null){
            return false;
        }
        if(p != null && q == null){
            return false;
        }

        //都非空后进行下一步的讨论
        //创建普通队列
        Queue<TreeNode> que = new LinkedList<>();
        que.offer(p);
        que.offer(q);

        while (!que.isEmpty()){
            TreeNode nodeP = que.poll();
            TreeNode nodeQ = que.poll();
            //讨论空值
            if(nodeP == null && nodeQ == null){
                continue;
            }
            if(nodeP == null && nodeQ != null){
                return false;
            }
            if(nodeP != null && nodeQ == null){
                return false;
            }
            //非空值进行讨论
            if(nodeP.val != nodeQ.val){
                return false;
            }

            //进行下一步的讨论
            que.offer(nodeP.left);
            que.offer(nodeQ.left);
            que.offer(nodeP.right);
            que.offer(nodeQ.right);
        }

        return true;
    }


    /**
     * 题目：572 另一个数的子树
     */
    //尝试使用迭代法
    public static boolean isSubtree1(TreeNode root, TreeNode subRoot){
        //讨论空值
        if(root == null && subRoot == null){
            return true;
        }
        if(root == null || subRoot == null){
            return false;
        }

        //遍历主树，看有没有和子树的头节点对应的
        Deque<TreeNode> que = new LinkedList<>();
        que.offer(root);

        while (!que.isEmpty()){
            int len = que.size();

            while (len > 0){
                TreeNode tmpNode = que.poll();
                //判断和子树的节点是否对应
                if(tmpNode.val == subRoot.val){
                    if(isSameTree2(tmpNode, subRoot)){
                        return true;
                    }
                }

                if(tmpNode.left != null){
                    que.add(tmpNode.left);
                }
                if(tmpNode.right != null){
                    que.add(tmpNode.right);
                }

                len--;

            }
        }

        return false;

    }

    //使用递归法(抄的题解)
    public static boolean isSubtree2(TreeNode root, TreeNode subRoot){
        if(subRoot == null){
            return true;//如果subRoot为null，一定都是true
        }
        if(root == null){
            return false;//在subRoot不为null的前提下，root为null一定是false
        }
        return isSubtree2(root.left, subRoot) || isSubtree2(root.right, subRoot) || check(root, subRoot);
    }



    //简写，详细见题目100
    public static boolean check(TreeNode s, TreeNode t){
        if(s == null && t == null){
            return true;
        }
        if(s == null || t == null || s.val != t.val){
            return false;
        }

        return check(s.left, t.left) && check(s.right, t.right);
    }

    /**
     * 题目：104 二叉树的最大深度
     */
    //层序遍历
    public static int maxDepth1(TreeNode root){
        int depth = 0;
        if(root == null){
            return depth;
        }
        //创建队列
        Queue<TreeNode> que = new LinkedList<>();
        que.offer(root);

        while (!que.isEmpty()){
            int len = que.size();
            depth++;
            while (len > 0){
                TreeNode tmpNode = que.poll();

                if(tmpNode.left != null){
                    que.offer(tmpNode.left);
                }
                if(tmpNode.right != null){
                    que.offer(tmpNode.right);
                }
                len--;
            }

        }

        return depth;
    }

    static int result;

    //递归法(非精简版)
    public static int maxDepth2(TreeNode root){
         result = 0;
         if(root == null){
             return result;
         }
         getDepth(root, 1);
         return result;

    }

    public static void getDepth(TreeNode node, int depth){
        result = depth > result ? depth : result;//中
        if(node.left == null && node.right == null){
            return;
        }
        if(node.left != null){
            getDepth(node.left, depth + 1);
        }
        if(node.right != null){
            getDepth(node.right, depth + 1);
        }

        return;
    }

    /**
     * 题目：559 n叉树的最大深度
     */
    //迭代法，层序遍历
    public static int maxDepth1(Node root){
        int depth = 0;
        if(root == null){
            return depth;
        }

        Queue<Node> que = new LinkedList<>();
        que.offer(root);
        while (!que.isEmpty()){
            int len = que.size();
            depth++;

            while (len > 0){
                Node tmpNode = que.poll();

                for (Node child : tmpNode.children) {
                    if(child != null){
                        que.offer(child);
                    }
                }

                len--;
            }
        }

        return depth;
    }

    //递归法
    public static int maxDepth2(Node root){
        result = 0;
        if(root == null){
            return 0;
        }

        getDepth2(root, 1);
        return result;
    }

    public static void getDepth2(Node node, int depth){
        result = depth > result ? depth : result;

        if(node.children == null){
            return;
        }

        if(node.children != null){
            for (Node child : node.children) {
                getDepth2(child, depth + 1);
            }
        }

        return;
    }

    /**
     * 题目：111 二叉树的最小深度
     */
    //使用迭代法，层序遍历
    public static int minDepth1(TreeNode root){
        if(root == null){
            return 0;
        }

        int depth = 0;
        Deque<TreeNode> que = new LinkedList<>();
        que.offer(root);
        while(!que.isEmpty()){
            int len = que.size();
            depth++;

            while (len > 0){
                TreeNode tmpNode = que.poll();

                //判断情况
                if(tmpNode.left == null && tmpNode.right == null){
                    return depth;
                }

                if(tmpNode.left != null){
                    que.offer(tmpNode.left);
                }
                if(tmpNode.right != null){
                    que.offer(tmpNode.right);
                }

                len--;
            }
        }

        return depth;

    }

    //递归法
    public static int minDepth2(TreeNode root){
        return getDepth3(root);

    }

    public static int getDepth3(TreeNode node){
        if(node == null){
            return 0;
        }
        int leftDepth = getDepth3(node.left);
        int rightDepth = getDepth3(node.right);

        //当一个左子树为空，右不为空，这时并不是最低点
        if(node.left == null && node.right != null){
            return 1 + rightDepth;
        }

        //当一个右子树为空，左不为空，这时并不是最低点
        if(node.left != null && node.right == null){
            return 1 + leftDepth;
        }

        int result = 1 + Math.min(leftDepth, rightDepth);

        return result;
    }

    /**
     * 题目：222 完全二叉树的节点个数
     */
    //按照普通二叉树
    //层序遍历
    public static int countNodes1(TreeNode root){
        if(root == null){
            return 0;
        }
        int result = 0;
        Deque<TreeNode> que = new LinkedList<>();
        que.offer(root);

        while (!que.isEmpty()){
            int len = que.size();

            while (len > 0){
                TreeNode tmpNode = que.poll();
                result++;

                if(tmpNode.left != null){
                    que.offer(tmpNode.left);
                }
                if(tmpNode.right != null){
                    que.offer(tmpNode.right);
                }
                len--;
            }
        }

        return result;
    }

    //按照普通二叉树进行处理
    //递归法(前序遍历)
    public static int countNode2(TreeNode root){
        return getNodesNum(root);
    }

    public static int getNodesNum(TreeNode cur){
        if(cur == null){
            return 0;
        }

        int leftNum = getNodesNum(cur.left);
        int rightNum = getNodesNum(cur.right);
        int treeNum = leftNum + rightNum + 1;
        return treeNum;
    }

    //针对完全二叉树的解法
    //满二叉树的节点数为 2^depth - 1
    public static int countNode3(TreeNode root){
        if(root == null){
            return 0;
        }
        //开始根据左深度和右深度是否相同来判断该子树是不是满二叉树
        TreeNode leftNode = root.left;
        TreeNode rightNode = root.right;

        int leftDepth = 1;//这里我不会使用移位，采用的幂次，所以左右深度的初始值设为1
        int rightDepth = 1;

        while (leftNode != null){//求左子树的深度
            leftNode = leftNode.left;
            leftDepth++;
        }
        while (rightNode != null){//求右子树的深度
            rightNode = rightNode.right;
            rightDepth++;
        }

        if(leftDepth == rightDepth){//满足满二叉树
            return (int) (Math.pow(2, leftDepth) - 1);
        }

        //进行递归
        return getNodesNum1(root);

    }

    public static int getNodesNum1(TreeNode node){
        if(node == null){
            return 0;
        }

        int leftNode = getNodesNum1(node.left);
        int rightNode = getNodesNum1(node.right);

        return leftNode + rightNode + 1;
    }

    /**
     * 题目：110 平衡二叉树
     */
    //递归法
    public static boolean isBalanced1(TreeNode root){
        return getHeight(root) != -1;
    }

    public static int getHeight(TreeNode node){
        if(node == null){
            return 0;
        }

        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);

        if(leftHeight == -1){
            return -1;
        }
        if(rightHeight == -1){
            return -1;
        }

        if(Math.abs(leftHeight - rightHeight) > 1){
            return -1;
        }else {
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    //使用迭代法

    /**
     * 迭代法不太看得懂
     * @param root
     * @return
     */
    public static boolean isBalanced2(TreeNode root){
        if (root == null) {
            return true;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode pre = null;
        while (root!= null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            TreeNode inNode = stack.peek();
            // 右结点为null或已经遍历过
            if (inNode.right == null || inNode.right == pre) {
                // 比较左右子树的高度差，输出
                if (Math.abs(getHeight2(inNode.left) - getHeight2(inNode.right)) > 1) {
                    return false;
                }
                stack.pop();
                pre = inNode;
                root = null;// 当前结点下，没有要遍历的结点了
            } else {
                root = inNode.right;// 右结点还没遍历，遍历右结点
            }
        }
        return true;
    }

    /**
     * 层序遍历，求结点的高度
     */
    public static int getHeight2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Deque<TreeNode> deque = new LinkedList<>();
        deque.offer(root);
        int depth = 0;
        while (!deque.isEmpty()) {
            int size = deque.size();
            depth++;
            for (int i = 0; i < size; i++) {
                TreeNode poll = deque.poll();
                if (poll.left != null) {
                    deque.offer(poll.left);
                }
                if (poll.right != null) {
                    deque.offer(poll.right);
                }
            }
        }
        return depth;
    }

    //优化迭代方法、利用TreeNode.val来保存当前节点的高度，这样就不会有重复遍历
    //获取高度算法时间复杂度可以降到O(1)，总的时间复杂度降为O(n)。
    //时间复杂度：O(n)
    //进行前序遍历
    public static boolean isBalanced3(TreeNode root){
        if(root == null){
            return true;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode pre = null;
        while (root != null || !stack.isEmpty()){
            while (root != null){
                stack.push(root);
                root = root.left;
            }

            TreeNode inNode = stack.peek();
            //右节点为null或已经遍历过
            if(inNode.right == null || inNode.right == pre){
                //输出
                if(Math.abs(getHeight1(inNode.left) - getHeight1(inNode.right)) > 1){
                    return false;
                }
                stack.pop();
                pre = inNode;
                root = null;//当前节点下，没有要遍历的节点了
            }else {
                root = inNode.right;//右节点还没有遍历，遍历右节点
            }
        }

        return true;

    }

    //求结点的高度
    public static int getHeight1(TreeNode root){
        if(root == null){
            return 0;
        }

        int leftHeight = root.left != null ? root.left.val : 0;
        int rightHeight = root.right != null ? root.right.val : 0;
        int height = Math.max(leftHeight, rightHeight) + 1;
        root.val = height;

        return height;
    }

    /**
     * 题目：257 二叉树的所有路径
     */
    //递归
    public static List<String> binaryTreePaths(TreeNode root){
        List<String> res = new ArrayList<>();
        if(root == null){
            return res;
        }
        List<Integer> paths = new ArrayList<>();
        traversal(root, paths, res);
        return res;
    }

    /**
     *
     * @param root
     * @param paths 记录路径
     * @param res 将路径转化为结果
     */
    public static void traversal(TreeNode root, List<Integer> paths, List<String> res){
        paths.add(root.val);
        //叶子节点
        if(root.left == null && root.right == null){
            //输出
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < paths.size() - 1; i++){
                sb.append(paths.get(i)).append("->");
            }
            sb.append(paths.get(paths.size() - 1));
            res.add(sb.toString());
            return;
        }
        if(root.left != null){
            traversal(root.left, paths, res);
            paths.remove(paths.size() - 1);
            //回溯(这里的path是traversal处理过后的结果，也就是加入了左子节点的，所以如果想要顺利的进行右子节点就要抹去左子节点)
        }
        if(root.right != null){
            traversal(root.right, paths, res);
            paths.remove(paths.size() - 1);//回溯
        }
    }

    /**
     * 题目：404 左叶子之和
     */
    //递归
    public static int sumOfLeftLeaves1(TreeNode root){
        if(root == null){
            return 0;
        }
        if(root.left == null && root.right == null){
            return 0;
        }

        int midValue = 0;
        if(root.left != null && root.left.left == null && root.left.right == null){//中
            midValue = root.left.val;
        }
        int leftValue = sumOfLeftLeaves1(root.left);//左
        int rightValue = sumOfLeftLeaves1(root.right);//右

        int sum = midValue + leftValue + rightValue;

        return sum;

    }

    //迭代法
    public static int sumOfLeftLeaves2(TreeNode root){
        if(root == null){
            return 0;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        int result = 0;

        while (!stack.isEmpty()){
            TreeNode tmp = stack.pop();
            //判断
            if(tmp.left != null && tmp.left.left == null && tmp.left.right == null){
                result += tmp.left.val;
            }

            //进栈
            if(tmp.left != null){
                stack.add(tmp.left);
            }
            if(tmp.right != null){
                stack.add(tmp.right);
            }
        }

        return result;
    }

    //使用层序遍历的迭代法
    public static int sumOfLeftLeaves3(TreeNode root){
        if(root == null){
            return 0;
        }
        Deque<TreeNode> que = new LinkedList<>();
        que.offer(root);
        int result = 0;

        while (!que.isEmpty()){
            int len = que.size();
            while (len > 0){
                TreeNode tmpNode = que.poll();
                if(tmpNode.left != null && tmpNode.left.left == null && tmpNode.left.right == null){
                    result += tmpNode.left.val;
                }

                if(tmpNode.left != null){
                    que.offer(tmpNode.left);
                }
                if(tmpNode.right != null){
                    que.offer(tmpNode.right);
                }
                len--;
            }
        }

        return result;
    }

    /**
     * 题目：513 找树左下角的值
     */
    //使用层序遍历
    public static int findBottomLeftValue1(TreeNode root){
        if(root == null){
            return 0;
        }
        Deque<TreeNode> que = new LinkedList<>();
        que.offer(root);
        int result = 0;

        while (!que.isEmpty()){
            int len = que.size();
            result = que.peek().val;
            while (len > 0){
                TreeNode tmp = que.poll();
                if(tmp.left != null){
                    que.offer(tmp.left);
                }
                if(tmp.right != null){
                    que.offer(tmp.right);
                }
                len--;
            }
        }

        return result;

    }

    //使用递归法
    static int maxDepth = -1;
    static int bottomResult = 0;

    public static int findBottomLeftValue2(TreeNode root){
        if (root == null){
            return 0;
        }

        traversal2(root, 0);
        return bottomResult;
    }

    public static void traversal2(TreeNode root, int depth){
        //这里因为每一次递归的时候都是先从左子节点开始的，所以最先机理的一定是当前最深一层最左子节点的数值，并记录了最深的深度，
        //所以相同深度下其他的子节点是不能记录在结果上的，所以最终的返回的结果一定是最下面一层的最左面的值
        if(root.left == null && root.right == null){
            if(depth > maxDepth){
                maxDepth = depth;//更新最大深度
                bottomResult = root.val;//最大深度最左面的数值
            }
            return;
        }
        if(root.left != null){
            depth++;
            traversal2(root.left, depth);
            depth--;//回溯
        }
        if(root.right != null){
            depth++;
            traversal2(root.right, depth);
            depth--;//回溯
        }
        return;
    }

    /**
     * 题目：112 路径总和
     */
    //使用递归
    public static boolean hasPathSum1(TreeNode root, int targetSum){
        if(root == null){
            return false;
        }

        return traversal3(root, targetSum - root.val);
    }

    public static boolean traversal3(TreeNode root, int count){
        if(root.left == null && root.right == null && count == 0){
            return true;
        }
        if(root.left == null && root.right == null){
            return false;
        }

        if(root.left != null){
            if(traversal3(root.left, count - root.left.val)){
                return true;
            }
        }
        if(root.right != null){
            if(traversal3(root.right, count - root.right.val)){
                return true;
            }
        }

        return false;
    }

    //迭代法
    public static boolean hasPathSum2(TreeNode root, int targetSum){
        if(root == null){
            return false;
        }
        //节点指针
        Stack<TreeNode> stack1 = new Stack<>();
        //路径数值
        Stack<Integer> stack2 = new Stack<>();

        stack1.push(root);
        stack2.push(root.val);
        while (!stack1.isEmpty()){
            int size = stack1.size();

            for(int i = 0; i < size; i++){
                TreeNode tmp = stack1.pop();
                int sum = stack2.pop();

                //如果该节点是叶子节点，同时该节点的路径数值等于sum，那么就返回true
                if(tmp.left == null && tmp.right == null && sum == targetSum){
                    return true;
                }
                //左节点，压进去一个节点的时候，将该节点的路径数值也记录下来
                if(tmp.left != null){
                    stack1.push(tmp.left);
                    stack2.push(sum + tmp.left.val);
                }
                //右节点，咽进去一个节点的时候，将该节点的路径数值也记录下来
                if(tmp.right != null){
                    stack1.push(tmp.right);
                    stack2.push(sum + tmp.right.val);
                }

            }

        }
        return false;
    }

    /**
     * 题目：113 路径总和ii
     */
    public static List<List<Integer>> pathSum(TreeNode root, int targetSum){
        List<List<Integer>> res = new ArrayList<>();
        if(root == null){
            return res;//非空判断
        }
        List<Integer> path = new LinkedList<>();
        traversal4(root, targetSum, res, path);
        return res;
    }

    public static void traversal4(TreeNode root, int targetSum, List<List<Integer>> res, List<Integer> path){
        path.add(root.val);
        //遇到叶子节点
        if(root.left == null && root.right == null){
            //找到了和为targetSum的路径
            if(targetSum - root.val == 0){
                res.add(new ArrayList<>(path));
            }
            return;
        }

        if(root.left != null){
            traversal4(root.left, targetSum - root.val, res, path);
            path.remove(path.size() - 1);
        }
        if(root.right != null){
            traversal4(root.right, targetSum - root.val, res, path);
            path.remove(path.size() - 1);//回溯
        }
    }

    /**
     * 题目：106 从中序与后序遍历序列构造二叉树
     */
    //思路
    //第一步：如果数组大小为零的话，说明是空节点了。
    //第二步：如果不为空，那么取后序数组最后一个元素作为节点元素。
    //第三步：找到后序数组最后一个元素在中序数组的位置，作为切割点
    //第四步：切割中序数组，切成中序左数组和中序右数组 （顺序别搞反了，一定是先切中序数组）
    //第五步：切割后序数组，切成后序左数组和后序右数组
    //第六步：递归处理左区间和右区间
    public static TreeNode buildTree1(int[] inorder, int[] postorder){
        if(inorder.length == 0 || postorder.length == 0){
            return null;
        }
        //前闭后开
        return traversal5(inorder, 0, inorder.length, postorder, 0, postorder.length);
    }

    public static TreeNode traversal5(int[] inorder, int inBegin, int inEnd, int[] postorder, int postBegin, int postEnd){
        //第一步，判断非空
        if(postorder.length == 0){
            return null;
        }
        if(inBegin >= inEnd || postBegin >= postEnd){// 不满足左闭右开，说明没有元素，返回空树
            return null;
        }

        //第二步 取出二叉树的中间节点
        int rootValue = postorder[postEnd - 1];
        TreeNode root = new TreeNode(rootValue);

        //叶子节点
        if(postorder.length == 1){
            return root;
        }

        //第三步,分割中序数组
        int delimiterIndex;
        for (delimiterIndex = 0; delimiterIndex < inorder.length; delimiterIndex++){
            if(inorder[delimiterIndex] == rootValue){
                break;
            }
        }

        int lenOfLeft = delimiterIndex - inBegin;
        //原中序排列数组的左半部分（仍然是中序排列）
        //左闭右开

        //分割后序数组
        //排列仍然按照后序进行排列（个数同中序排列相同）

        root.left = traversal5(inorder, inBegin, inBegin + lenOfLeft,
                postorder, postBegin, postBegin + lenOfLeft);
        root.right = traversal5(inorder, delimiterIndex + 1, inEnd,
                postorder, postBegin + lenOfLeft, postEnd - 1);
        return root;
    }

    /**
     * 题目：105 从前序与中序遍历序列构造二叉树
     */
    public static TreeNode buildTree2(int[] preorder, int[] inorder){
        if(preorder.length == 0 || inorder.length == 0){
            return null;
        }

        return findNode(preorder, 0, preorder.length, inorder, 0, inorder.length);
    }

    public static TreeNode findNode(int[] preorder, int preBegin,  int preEnd, int[] inorder, int inBegin,int inEnd){
        //先判断非空
        if(preorder.length == 0){
            return null;
        }
        if(preBegin >= preEnd || inBegin >= inEnd){
            return null;
        }

        //找到中间节点
        int rootValue = preorder[preBegin];
        TreeNode root = new TreeNode(rootValue);

        //叶子节点
        if(preorder.length == 1){
            return root;
        }

        //找到中间节点在中序遍历数组中的位置
        int delimiterIndex;//中间节点在中序遍历数组中的位置
        for(delimiterIndex = 0; delimiterIndex < inorder.length; delimiterIndex++){
            if(inorder[delimiterIndex]== rootValue){
                break;
            }
        }

        //左大节点的长度
        int lenOfLeft = delimiterIndex - inBegin;

        //前闭后开
        root.left = findNode(preorder, preBegin + 1, preBegin + 1 + lenOfLeft,
                inorder, inBegin, delimiterIndex);
        root.right = findNode(preorder, preBegin + 1 + lenOfLeft, preEnd,
                inorder, delimiterIndex + 1, inEnd);

        return root;

    }

    /**
     * 题目：654 最大二叉树
     */
    //使用递归法
    public static TreeNode constructMaximumBinaryTree(int[] nums){
        if(nums == null || nums.length == 0){
            return null;
        }

        return constructMaximumBinaryTree1(nums, 0, nums.length);
    }

    //前闭后开
    public static TreeNode constructMaximumBinaryTree1(int[] nums, int leftIndex, int rightIndex){
        //空值判断
        if(leftIndex >= rightIndex){//没有空值了
            return null;
        }
        if(rightIndex - leftIndex == 1){//只有一个元素了
            return new TreeNode(nums[leftIndex]);
        }

        //找到最大数值的位置和值
        int maxIndex = leftIndex;
        for (int i = leftIndex + 1; i < rightIndex; i++){
            if(nums[i] > nums[maxIndex]){
                maxIndex = i;
            }
        }
        int maxValue = nums[maxIndex];

        TreeNode root = new TreeNode(maxValue);
        root.left = constructMaximumBinaryTree1(nums, leftIndex, maxIndex);
        root.right = constructMaximumBinaryTree1(nums, maxIndex + 1, rightIndex);

        return root;
    }

    /**
     * 题目：617 合并二叉树
     */
    //迭代法 使用层序遍历
    public static TreeNode mergeTrees1(TreeNode root1, TreeNode root2){
        //空值判断
        if(root1 == null){
            return root2;
        }
        if(root2 == null){
            return root1;
        }

        //使用队列
        Deque<TreeNode> que = new LinkedList<>();
        que.offer(root1);
        que.offer(root2);
        while (!que.isEmpty()){
            TreeNode node1 = que.poll();
            TreeNode node2 = que.poll();
            //此时两个节点一定不为空，val相加
            node1.val = node1.val + node2.val;
            //如果两棵树左节点都不为空，加入队列
            if(node1.left != null && node2.left != null){
                que.offer(node1.left);
                que.offer(node2.left);
            }
            //如果node1的左节点为空，将node2直接赋值
            if(node1.left == null && node2.left != null){
                node1.left = node2.left;
            }
            //如果两棵树右节点不为空，加入队列
            if(node1.right != null && node2.right != null){
                que.offer(node1.right);
                que.offer(node2.right);
            }
            //如果node1的右节点为空，将node2直接赋值
            if(node1.right == null && node2.right != null){
                node1.right = node2.right;
            }
        }

        return root1;
    }

    //递归法
    public static TreeNode mergeTrees2(TreeNode root1, TreeNode root2){
        if(root1 == null){
            return root2;
        }
        if(root2 == null){
            return root1;
        }

        root1.val += root2.val;

        root1.left = mergeTrees2(root1.left, root2.left);
        root1.right = mergeTrees2(root1.right, root2.right);

        return root1;
    }

    /**
     * 题目：700 二叉搜索树中的搜索
     */
    //递归法
    public static TreeNode searchBST1(TreeNode root, int val){
        //终止条件
        if(root.val == val || root == null){
            return root;
        }

        //单层递归逻辑
        if(root.val > val){
            return searchBST1(root.left, val);
        }
        if(root.val < val) {
            return searchBST1(root.right, val);
        }
        return null;
    }

    //迭代法
    public static TreeNode searchBST2(TreeNode root, int val){
        while (root != null){
            if(root.val > val){
                root = root.left;
            }else if(root.val < val){
                root = root.right;
            }else {
                return root;
            }
        }

        return null;
    }

    /**
     * 题目：98 验证二叉搜索树
     */
    static TreeNode max;
    //递归法
    //思路：递归的单次循环顺序按照中序遍历给出，这样遍历永远是从小到大是顺序，那么左节点就要大于上一个中间节点，右节点要大于中间节点
    //所以在中间节点处理完后，改变max的值
    public static boolean isValidBST1(TreeNode root){
        if (root == null) {
            return true;
        }
        // 左
        boolean left = isValidBST1(root.left);
        if (!left) {
            return false;
        }
        // 中
        if (max != null && root.val <= max.val) {
            return false;
        }
        max = root;
        // 右
        boolean right = isValidBST1(root.right);
        return right;
    }

    //递归法
    //使用左右边界来判断节点的大小
    public static boolean isValidBST2(TreeNode root){
        return validBST(Long.MIN_VALUE, Long.MAX_VALUE, root);
    }

    public static boolean validBST(long lower, long upper, TreeNode root){
        if(root == null){
            return true;
        }
        //判断
        if(root.val <= lower || root.val >= upper){
            return false;
        }
        return validBST(lower, root.val, root.left) && validBST(root.val, upper, root.right);

    }

    //迭代法
    //参考中序遍历
    public static boolean isValidBST3(TreeNode root){
        if(root == null){
            return true;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode pre = null;
        while (root != null || !stack.isEmpty()){
            while (root != null){
                stack.push(root);
                root = root.left;//左
            }
            //中 处理
            TreeNode pop = stack.pop();
            if(pre != null && pop.val <= pre.val){
                return false;
            }
            pre = pop;

            root = pop.right;//右
        }

        return true;
    }

    /**
     * 题目：530 二叉搜素数的最小绝对差
     */
    //迭代 中序遍历
    public static int getMinimumDifference1(TreeNode root){
        if(root == null){
            return 0;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode pre = null;
        TreeNode cur = root;
        int res = Integer.MAX_VALUE;
        while (cur != null || !stack.isEmpty()){
            while (cur != null){
                stack.push(cur);
                cur = cur.left;//左
            }
            //中 处理
            cur = stack.pop();
            if(pre != null){
                res = Math.min(res, cur.val - pre.val);
            }
            pre = cur;
            cur = cur.right;
        }

        return res;
    }

    //递归

    /**
     *递归函数中局部变量和全局变量
     */
    //有时候会因为不注意递归函数中局部变量和全局变量，而导致结果和我们期望的不一致，递归中，在递归中的局部变量和全局变量，可以类似的看成函数调用时传递方式的按值传递（局部变量）和引用传递（全局变量）
    //局部变量：可以看成两个值，即虽然名字相同，但每次递归时是重新创建的变量，不会覆盖掉上次创建的值；（类似形参与实参不同）
    //全局变量：每一次对于全局变量进行操作，就会改变它的值。
    static int res = Integer.MAX_VALUE;
    static TreeNode pre;
    public static int getMinimumDifference2(TreeNode root){
        if(root == null){
            return 0;
        }
        getMin1(root);
        return res;
    }

    public static void getMin1(TreeNode root){
        //终止条件
        if(root == null){
            return;
        }
        getMin1(root.left);
        if(pre != null){
            res = Math.min(res, root.val - pre.val);
        }
        pre = root;

        getMin1(root.right);
    }

    public static int getMinimumDifference3(TreeNode root){
        if(root == null){
            return 0;
        }
        TreeNode pre = null;
        int res = Integer.MAX_VALUE;
        getMin2(root, pre, res);
        return res;
    }

    public static void getMin2(TreeNode root, TreeNode pre, int res){
        //终止条件
        if(root == null){
            return;
        }
        getMin2(root.left, pre, res);
        if(pre != null){
            res = Math.min(res, root.val - pre.val);
        }
        pre = root;
        getMin2(root.right, pre, res);
    }



    /**
     * 题目：501 二叉树搜索树中的众数
     */
    //暴力法，没有利用搜素树
    static Map<Integer, Integer> map;
    public static int[] findMode1(TreeNode root){
        map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        if (root == null) return list.stream().mapToInt(Integer::intValue).toArray();
        // 获得频率 Map
        searchBST(root);
        List<Map.Entry<Integer, Integer>> mapList = map.entrySet().stream()
                .sorted((c1, c2) -> c2.getValue().compareTo(c1.getValue()))
                .collect(Collectors.toList());
        list.add(mapList.get(0).getKey());
        // 把频率最高的加入 list
        for (int i = 1; i < mapList.size(); i++) {
            if (mapList.get(i).getValue() == mapList.get(i - 1).getValue()) {
                list.add(mapList.get(i).getKey());
            } else {
                break;
            }
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    //前序遍历
    public static void searchBST(TreeNode curr) {
        if (curr == null) return;
        map.put(curr.val, map.getOrDefault(curr.val, 0) + 1);
        searchBST(curr.left);
        searchBST(curr.right);
    }

    //递归法(使用中序遍历)，利用二叉树的特性
    static ArrayList<Integer> resList;
    static int maxCount;//最大频率
    static int count;//统计频率
    static TreeNode preNode;

    public static int[] findMode2(TreeNode root){
        resList = new ArrayList<>();
        maxCount = 0;
        count = 0;
        preNode = null;
        findAllMode(root);
        int[] res = new int[resList.size()];
        for(int i = 0; i < resList.size(); i++){
            res[i] = resList.get(i);
        }
        return res;

    }

    //中序遍历
    public static void findAllMode(TreeNode root){
        if(root == null){
            return;
        }
        //左
        findAllMode(root.left);
        //中 进行处理
        int rootValue = root.val;
        //计数
        if(preNode == null || rootValue != preNode.val){
            count = 1;
        }else {
            count++;
        }
        //更新结果以及maxCount
        if(count > maxCount){
            resList.clear();
            resList.add(rootValue);
            maxCount = count;
        }else if(count == maxCount){
            resList.add(rootValue);
        }
        preNode = root;

        //右
        findAllMode(root.right);
    }

    //迭代法
    public static int[] findMode3(TreeNode root){
        TreeNode pre = null;
        List<Integer> result = new ArrayList<>();
        int maxCount = 0;
        int count = 0;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()){
            if(cur != null){
                stack.push(cur);
                cur = cur.left;//左
            }else {
                cur = stack.pop();
                //计数
                if(pre == null || cur.val != pre.val){
                    count = 1;
                }else {
                    count++;
                }
                //更新结果
                if(count > maxCount){
                    result.clear();
                    result.add(cur.val);
                    maxCount = count;
                }else if(count == maxCount){
                    result.add(cur.val);
                }

                pre = cur;
                cur = cur.right;//右
            }
        }
        int[] res = new int[result.size()];
        for(int i = 0; i < result.size(); i++){
            res[i] = result.get(i);
        }
        return res;

    }

    /**
     * 题目：236 二叉树的最近公共祖先
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q){
        //递归结束条件
        if(root == null || root == p || root == q){//递归结束条件（找到了指定的节点）
            return root;
        }

        //后序遍历
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if(left != null && right != null){//目标节点是父节点的左右护法
            return root;
        }else if(left == null && right != null){//找到一个节点
            return right;
        }else if(left != null && right == null){//找到一个节点
            return left;
        }else {//两个节点一个也没找到
            return null;
        }
    }

    /**
     * 题目：235 二叉搜索树的最近公共祖先
     */
    //使用普通二叉树的方法
    public static TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q){
        //终止条件
        if(root == null || root == p || root == q){
            return root;
        }
        //先处理左右子节点，后处理中间节点，符合后序遍历
        TreeNode left = lowestCommonAncestor1(root.left, p, q);
        TreeNode right = lowestCommonAncestor1(root.right, p, q);

        //处理中间节点和左右节点的情况
        if(left != null && right != null){//两个点都找到 返回父节点
            return root;
        }else if(left == null && right != null){
            return right;
        }else if(left != null && right == null){
            return left;
        }else {
            return null;
        }
    }

    //利用二叉搜索树的特性
    //递归法
    public static TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q){
        return getLowest(root, p, q);
    }

    public static TreeNode getLowest(TreeNode cur, TreeNode p, TreeNode q){
        if(cur == null){
            return cur;
        }

        //公共祖先必是在p和q的数值之间，如果在两边就要往中间靠
        //太大了，往左靠
        if(cur.val > p.val && cur.val > q.val){
            TreeNode left = lowestCommonAncestor2(cur.left, p, q);
            if(left != null){
                return left;
            }
        }

        //太小了，向右靠
        if(cur.val < p.val && cur.val < q.val){
            TreeNode right = lowestCommonAncestor2(cur.right, p, q);
            if(right != null){
                return right;
            }
        }

        return cur;
    }

    //迭代法
    public static TreeNode lowestCommonAncestor3(TreeNode root, TreeNode p, TreeNode q){
        if(root == null){
            return root;
        }

        while (root != null){
            if(root.val > p.val && root.val > q.val){
                root = root.left;
            }else if(root.val < p.val && root.val < q.val){
                root = root.right;
            }else {
                break;
            }
        }

        return root;
    }

    /**
     * 题目：701 二叉搜索树中的插入操作
     */
    //递归法
    public static TreeNode insertIntoBST1(TreeNode root, int val){
        //终止条件
        if(root == null){//找到了
            return new TreeNode(val);
        }

        //单层递归逻辑
        //寻找位置
        if(root.val > val){
            root.left = insertIntoBST1(root.left, val);
        }
        if(root.val < val){
            root.right = insertIntoBST1(root.right, val);
        }

        return root;
    }

    //迭代法
    public static TreeNode insertIntoBST2(TreeNode root, int val){
        if(root == null){
            return new TreeNode(val);
        }
        TreeNode cur = root;
        TreeNode pre = root;//记录上一个节点
        while (cur != null){
            pre = cur;
            if(cur.val > val){
                cur = cur.left;
            }else {
                cur = cur.right;
            }
        }

        if(pre.val > val){
            pre.left = new TreeNode(val);
        }else {
            pre.right = new TreeNode(val);
        }
        return root;
    }

    /**
     * 题目：450 删除二叉搜索树中的节点
     */
    //递归法
    //一共有5中情况
    //第一种情况：没找到删除的节点，遍历到空节点直接返回了
    //第二种情况：左右孩子都为空（叶子节点），直接删除节点， 返回NULL为根节点
    //第三种情况：删除节点的左孩子为空，右孩子不为空，删除节点，右孩子补位，返回右孩子为根节点
    //第四种情况：删除节点的右孩子为空，左孩子不为空，删除节点，左孩子补位，返回左孩子为根节点
    //第五种情况：左右孩子节点都不为空，则将删除节点的左子树头结点（左孩子）放到删除节点的右子树的最左面节点的左孩子上，返回删除节点右孩子为新的根节点
    public static TreeNode deleteNode(TreeNode root, int key){
        //终止条件
        if(root == null){//第一种情况
            return root;
        }
        if(root.val > key){
            root.left = deleteNode(root.left, key);
        }
        if(root.val < key){
            root.right = deleteNode(root.right, key);
        }
        //处理单层逻辑
        if(root.val == key){
            //第二种情况，左右孩子都为空
            if(root.left == null && root.right == null){
                return null;
            }else if(root.left == null && root.right != null){//第三种情况
                return root.right;
            }else if(root.left != null && root.right == null){//第四种情况
                return root.left;
            }else {//第五种情况
                TreeNode tmpNode = root.right;
                //找到右子树最左面的节点
                while (tmpNode.left != null){
                    tmpNode = tmpNode.left;
                }
                tmpNode.left = root.left;//把要删除的节点root左子树放在cur的左孩子的位置
                root = root.right;//返回旧root的右孩子作为新root
                return root;
            }
        }

        return root;
    }

    //使用二叉树的另一种删除方式
    //二叉树中序排序后比目标节点大一点的那个值就是其右子树最左面的节点，所以将其和目标节点进行交换就不会改变二叉树的顺序
    //代码中目标节点（要删除的节点）被操作了两次
    //第一次是和目标节点的右子树最左面节点交换
    //第二次直接被null覆盖了
    public static TreeNode deleteNode1(TreeNode root, int key){
        //终止条件
        if(root == null){//第一种情况
            return root;
        }
        if(root.val > key){
            root.left = deleteNode1(root.left, key);
        }
        if(root.val < key){
            root.right = deleteNode1(root.right, key);
        }
        //处理单层逻辑
        if(root.val == key){
            //前面的情况和上一种方法一样
            if(root.left == null && root.right == null){
                return null;
            }else if(root.left == null && root.right != null){
                return root.right;
            }else if(root.left != null && root.right == null){
                return root.left;
            }else {
                TreeNode tmp = root.right;
                while (tmp.left != null){
                    tmp = tmp.left;
                }
                root.val = tmp.val;
                //删除目标节点（相当于删除右子树的最左节点）
                root.right = deleteNode1(root.right, tmp.val);
            }
        }

        return root;
    }

    /**
     * 题目：669 修剪二叉搜索树
     */
    //递归
    public static TreeNode trimBST(TreeNode root, int low, int high){
        if(root == null){
            return null;
        }

        //root不在范围内
        if(root.val < low){
            return trimBST(root.right, low, high);
        }
        if(root.val > high){
            return trimBST(root.left, low, high);
        }
        //root在区间内
        root.left = trimBST(root.left, low, high);
        root.right = trimBST(root.right, low, high);

        return root;
    }

    /**
     * 题目：108 将有序数组转换为二叉搜索树
     */
    //递归（左闭右开）
    public static TreeNode sortedArrayToBST1(int[] nums){
        return sortedArrayToBST(nums, 0, nums.length);
    }

    public static TreeNode sortedArrayToBST(int[] nums, int left, int right){
        //终止条件
        if(left >= right){
            return null;
        }
        if(right - left == 1){
            return new TreeNode(nums[left]);
        }
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedArrayToBST(nums, left, mid);
        root.right = sortedArrayToBST(nums, mid + 1, right);
        return root;
    }

    //迭代法
    //左闭右闭
    public static TreeNode sortedArrayToBST2(int[] nums){
        if(nums.length == 0){
            return null;
        }
        //根节点初始化
        TreeNode root = new TreeNode(-1);
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        Queue<Integer> leftQueue = new LinkedList<>();
        Queue<Integer> rightQueue = new LinkedList<>();

        //根节点入队列
        nodeQueue.offer(root);
        //0为左区间下标初始位置
        leftQueue.offer(0);
        //nums.size - 1为右区间下标初始位置
        rightQueue.offer(nums.length - 1);

        while (!nodeQueue.isEmpty()){
            TreeNode currNode = nodeQueue.poll();
            int left = leftQueue.poll();
            int right = rightQueue.poll();
            int mid = left + (right - left) / 2;

            //将mid对应的元素给中间节点
            currNode.val = nums[mid];

            //处理左区间
            if(left <= mid - 1){
                currNode.left = new TreeNode(-1);
                nodeQueue.offer(currNode.left);
                leftQueue.offer(left);
                rightQueue.offer(mid - 1);
            }

            //处理右区间
            if(right >= mid + 1){
                currNode.right = new TreeNode(-1);
                nodeQueue.offer(currNode.right);
                leftQueue.offer(mid + 1);
                rightQueue.offer(right);
            }
        }
        return root;
    }

    /**
     * 题目：538 把二叉搜索树转换为累加树
     */
    //递归法（遍历整个树，返回void）
    static int preVal;//记录前一个节点的数值
    public static TreeNode convertBST(TreeNode root){
        preVal = 0;
        convertBST1(root);
        return root;
    }

    public static void convertBST1(TreeNode root){
        //终止条件
        if(root == null){
            return;
        }
        //按照右中左的顺序进行遍历，并处理
        convertBST1(root.right);
        root.val += preVal;
        preVal = root.val;
        convertBST1(root.left);
    }



}

/**
 * 二叉树
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

/**
 * N叉树
 * 完美二叉树
 */
class Node{
    int val;
    List<Node> children;
    Node left;
    Node right;
    Node next;

    public Node() {
    }

    public Node(int val) {
        this.val = val;
    }

    public Node(int val, List<Node> children) {
        this.val = val;
        this.children = children;
    }

    public Node(int val, Node left, Node right, Node next) {
        this.val = val;
        this.left = left;
        this.right = right;
        this.next = next;
    }
}

/**
 * 完美二叉树节点
 */
//class PerNode{
//    int val;
//    PerNode left;
//    PerNode right;
//    PerNode next;
//
//    public PerNode() {
//    }
//
//    public PerNode(int val) {
//        this.val = val;
//    }
//
//    public PerNode(int val, PerNode left, PerNode right, PerNode next) {
//        this.val = val;
//        this.left = left;
//        this.right = right;
//        this.next = next;
//    }
//}
