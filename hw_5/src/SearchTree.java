import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.acos;
import static java.lang.Math.min;

public class SearchTree {
    public static class TreeNode {
        int data;
        TreeNode left;
        TreeNode right;

        public TreeNode() {
            this.data = 0;
            this.left = null;
            this.right = null;
        }

        public TreeNode(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }

        public TreeNode(int data, TreeNode left, TreeNode right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public TreeNode buildTree(ArrayList<Integer> arr, int i) {
            if (i >= arr.size()) {
                return null;
            }

            TreeNode root = new TreeNode(arr.get(i));
            root.left = buildTree(arr, 2*i+1);
            root.right = buildTree(arr, 2*i+2);

            return root;
        }
    }

    public static boolean isSymmetricBFS(TreeNode root) {
        if (root == null) {
            return true;
        }

        ArrayList<TreeNode> queue = new ArrayList<>(List.of(root));

        while (!queue.isEmpty()) {
            int queueLen = queue.size();
            for (int i = 0; i < queueLen; i++) {
                if (queue.get(i) == null && queue.get(queueLen-i-1) == null) {
                    continue;
                }
                if (queue.get(i) == null || queue.get(queueLen-i-1) == null) {
                    return false;
                }
                if (queue.get(i).data != queue.get(queueLen-i-1).data) {
                    return false;
                }
                queue.add(queue.get(i).left);
                queue.add(queue.get(i).right);
            }
            int currQueueLen = queue.size();
            queue = new ArrayList<>(queue.subList(queueLen, currQueueLen));
        }
        return true;
    }

    public static ArrayList<Integer> deepSearch(TreeNode root, ArrayList<Integer> res) {
        if (root == null) {
            return res;
        }

        deepSearch(root.left, res);
        res.add(root.data);
        deepSearch(root.right, res);

        return res;
    }

    public static boolean isSymmetricDFS(TreeNode root) {
        if (root == null) {
            return true;
        }

        ArrayList<Integer> data = deepSearch(root, new ArrayList<Integer>());
        int j;
        for (int i = 0; i < data.size()/2; i++) {
            j = data.size() - i - 1;
            if (!Objects.equals(data.get(i), data.get(j))) {
                return false;
            }
        }
        return true;
    }

    public static int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        if (root.left == null && root.right == null) {
            return 1;
        }

        if (root.left != null && root.right != null) {
            return 1 + min(minDepth(root.right), minDepth(root.left));
        }

        if (root.left != null) {
            return 1 + minDepth(root.left);
        }

        return 1 + minDepth(root.right);
    }

    public static int maxMinMultiplication(ArrayList<Integer> data) {
        if (data.size() < 3) {
            return data.getLast();
        }
        int min_ind = 1;
        int max_ind = 2;

        for (int i = 1; i < data.size(); i = i*2+1) {
            min_ind = i;
        }
        for (int i = 2; i < data.size(); i = i*2+2) {
            max_ind = i;
        }

        return data.get(min_ind) * data.get(max_ind);
    }

    public static boolean isSameTree(TreeNode a, TreeNode b) {
        if (a == null && b == null) {
            return true;
        }

        if (a == null || b == null) {
            return false;
        }

        if (a.data != b.data) {
            return false;
        }

        return isSameTree(a.left, b.left) && isSameTree(a.right, b.right);
    }


    public static void main(String[] args) {
        ArrayList<Integer> data = new ArrayList<>(List.of(3, 8, 8, 9, 6, 6, 9));
        TreeNode symmetric_tree = new TreeNode().buildTree(data, 0);
        System.out.println("isSymmetricBFS: " + isSymmetricBFS(symmetric_tree));
        System.out.println("isSymmetricDFS: " + isSymmetricDFS(symmetric_tree));
        System.out.println("minDepth: " + minDepth(symmetric_tree));
        ArrayList<Integer> sorted_data = new ArrayList<>(List.of(13, 9, 14, 8, 10, 13, 15));
        System.out.println("maxMinMultiplication: " + maxMinMultiplication(sorted_data));
        ArrayList<Integer> same_data = new ArrayList<>(List.of(3, 8, 8, 9, 6, 6, 9));
        System.out.println("isSameTree - false: " + isSameTree(new TreeNode().buildTree(data, 0),
                new TreeNode().buildTree(sorted_data, 0)));
        System.out.println("isSameTree - true: " + isSameTree(new TreeNode().buildTree(data, 0),
                new TreeNode().buildTree(same_data, 0)));

    }
}