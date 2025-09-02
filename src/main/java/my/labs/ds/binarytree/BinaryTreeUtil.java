package my.labs.ds.binarytree;


import java.util.*;

public class BinaryTreeUtil {
     static  int maxDepth = 0;
    static  int depth = 0;
    public static void traverse(TreeNode root) {
        if (root == null) {
            return;
        }
        traverse(root.left);
        traverse(root.right);
    }

    public static void traverse(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            traverse(arr, i);
        }
    }

    private static void traverse(int[] arr, int i) {
        if (i == arr.length) {
            return;
        }
        System.out.println("pre-order:" + arr[i]);
        traverse(arr, i + 1);
    }

    /**
     * BFS 广度优先遍历 （层次遍历）
     * Use a queue to store nodes
     * @param root
     * @return
     */
    public static List<Integer> levelOrder(TreeNode root) {
        List<Integer> treeOrder = new ArrayList<>();
        if (root == null){
            return treeOrder;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            treeOrder.add(node.value);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        return treeOrder;
    }

    public static List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> levelByLevelList = new ArrayList<>();
        if (root == null){
            return levelByLevelList;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> levelList = new ArrayList<>();
            for (int i = 0 ; i < size; i ++){
                TreeNode current = queue.poll();
                levelList.add(current.value);
                if (current.left != null) {
                    queue.offer(current.left);
                }
                if(current.right != null) {
                    queue.offer(current.right);
                }
            }
            levelByLevelList.add(levelList);
        }
        return levelByLevelList;
    }

    public static void traverseTree(TreeNode root) {
        if (root == null) {
            return;
        }

        System.out.println("On the tree node: " + root.value + ", before enter left tree node");
        traverseTree(root.left);
        if (root.left != null) {
            System.out.println("On the tree node: " + root.value + ", finish left tree node: " + root.left.value);
        } else {
            System.out.println("On the tree node: " + root.value + ", left tree node is null");
        }

        System.out.println("On the tree node: " + root.value + ", before enter right tree node");
        traverseTree(root.right);
        if (root.right != null) {
            System.out.println("On the tree node: " + root.value + ", finish right tree node: " + root.right.value);
        } else {
            System.out.println("On the tree node: " + root.value + ", right tree node is null");
        }
    }

    public static  void traverseInOrder (TreeNode root) {
        if (root == null) {
            return;
        }
        traverseInOrder(root.left);
        System.out.println("Node:"+ root.value);
        traverseInOrder(root.right);
    }

    // divide problem
    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int lefMaxDepth = maxDepth(root.left);
        int rightMaxDepth = maxDepth(root.right);
        return Math.max(lefMaxDepth,rightMaxDepth) + 1;
    }

    // travese tree
    public static int maxDepth2(TreeNode root) {
        maxDepth = 0;
        depth = 0;
        traverseTree(root, 0);
        System.out.println("MaxDepth:" + maxDepth + " depth:" + depth);
        return maxDepth;
    }

    public static void traverseTree(TreeNode root, int depth) {
        if (root == null) {
            return;
        }
        depth++;
        if (root.left == null && root.right == null) {
            maxDepth = Math.max(maxDepth, depth);
        }
        traverseTree(root.left, depth);
        traverseTree(root.right, depth);
        depth--;
    }



    // root left right
    //       1
    //     /  \
    //    2    3
    //  /  \
    // 4    5
    public static List<Integer> PreorderTraverseIteratively(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Stack<TreeNode> st = new Stack<>();
        st.push(root);

        while (!st.empty()) {
            TreeNode curr = st.pop();
            result.add(curr.value);
            if (curr.right != null) {
                st.push(curr.right);
            }
            if (curr.left != null) {
                st.push(curr.left);
            }
        }
        return result;
    }

    public static List<Integer> InorderTraverseIteratively(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Stack<TreeNode> st = new Stack<>();
        TreeNode curr = root;
        while (curr != null || !st.isEmpty()) {
            if (curr != null) {
                st.push(curr);
                curr = curr.left;
            }else {
                curr = st.pop();
                result.add(curr.value);
                curr = curr.right;
            }
        }
        return result;
    }

    /**
     *               1
     *            /     \
     *          2         3
     *        /  \      /   \
     *      4     5    6     7
     *    /  \   /
     *   8    9 10
     * @param args
     */
    public static void main(String[] args) {
        // 创建10个节点
        TreeNode root = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        TreeNode node8 = new TreeNode(8);
        TreeNode node9 = new TreeNode(9);
        TreeNode node10 = new TreeNode(10);

        // 构造一棵二叉树
        root.left = node2;
        root.right = node3;

        node2.left = node4;
        node2.right = node5;

        node3.left = node6;
        node3.right = node7;

        node4.left = node8;
        node4.right = node9;

        node5.left = node10;
        // 构造 10 个节点
//        TreeNode n1 = new TreeNode(1);
//        TreeNode n2 = new TreeNode(2);
//        TreeNode n3 = new TreeNode(3);
//        TreeNode n4 = new TreeNode(4);
//        TreeNode n5 = new TreeNode(5);
//        TreeNode n6 = new TreeNode(6);
//        TreeNode n7 = new TreeNode(7);
//        TreeNode n8 = new TreeNode(8);
//        TreeNode n9 = new TreeNode(9);
//        TreeNode n10 = new TreeNode(10);
//
//        // 构造 BST（尽量平衡）
//        TreeNode root = n6;    // 根节点
//        root.left = n3;
//        root.right = n9;
//
//        n3.left = n2;
//        n3.right = n5;
//        n2.left = n1;
//        n5.left = n4;
//
//        n9.left = n8;
//        n9.right = n10;
//        n8.left = n7;
        BinaryTreeUtil.traverseInOrder(root);

        List<Integer> preorderList = BinaryTreeUtil.PreorderTraverseIteratively(root);
        System.out.println("Preorder:" + preorderList);

        List<Integer> inorderList = BinaryTreeUtil.InorderTraverseIteratively(root);
        System.out.println("inorderList:" + inorderList);

        List<Integer> levelOrderList = BinaryTreeUtil.levelOrder(root);
        System.out.println("levelOrderList:" + levelOrderList);

        List<List<Integer>> levelByLevelOrderList = BinaryTreeUtil.levelOrder2(root);
        System.out.println("levelByLevelOrderList:" + levelByLevelOrderList);

        int maxDepth = BinaryTreeUtil.maxDepth(root);
        System.out.println("Max depth:"+ maxDepth);

        BinaryTreeUtil.maxDepth2(root);
    }
}
