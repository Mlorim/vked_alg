import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.max;

public class SearchTrees {
    public static class TreeNode {
        int data;
        TreeNode left;
        TreeNode right;
        int balanceFactor;

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

    public static class MinHeap {
        private ArrayList<Integer> data;
        private int size;

        public MinHeap(int size) {
            this.size = size;
            data = new ArrayList<>();
        }

        public MinHeap(ArrayList<Integer> arr) {
            this.size = arr.size();
            data = arr;
        }

        public void shftdown(int i) {
            ArrayList<Integer> data = this.data;
            int n = this.size;

            int smallest = i;
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < n && data.get(left) < data.get(smallest)) {
                smallest = left;
            }

            if (right < n && data.get(right) < data.get(smallest)) {
                smallest = right;
            }

            if (smallest != i) {
                int temp = data.get(i);
                data.set(i, data.get(smallest));
                data.set(smallest, temp);
                this.shftdown(smallest);
            }

        }

        public int top() {
            return this.data.getFirst();
        }

        public int right(int i) {
            return 2*i+1;
        }

        public int left(int i) {
            return 2*i+2;
        }

        public int parent(int i) {
            if (i == 0) {
                return i;
            }
            return (i - 1) / 2;
        }

        public void buildMinTree() {
            int n = this.size;
            for (int i = n/2 - 1; i >= 0; i--) {
                this.shftdown(i);
            }
        }

        public void up(int i) {
            ArrayList<Integer> data = this.data;
            while (i > 0 && data.get(i) < data.get(parent(i))) {
                int temp = data.get(i);
                data.set(i, data.get(parent(i)));
                data.set(parent(i), temp);

                i = parent(i);
            }
        }

        public void push(int var) {
            this.data.add(var);
            this.size++;
            this.up(this.size-1);
        }

        public int delete() {
            if (this.data.isEmpty()) {
                return 0;
            }

            int n = this.size;
            int result = this.data.getFirst();

            this.data.set(0, data.get(n-1));
            this.data.removeLast();

            this.size--;
            this.shftdown(0);

            return result;
        }

        public boolean isEmpty() {
            return this.size == 0;
        }
    }

    private static class HeapNode {
        int value;
        int arrayIndex;
        int elementIndex;

        HeapNode(int value, int arrayIndex, int elementIndex) {
            this.value = value;
            this.arrayIndex = arrayIndex;
            this.elementIndex = elementIndex;
        }
    }

    public static boolean isMaxHeap(ArrayList<Integer> arr) {
        int n = arr.size();
        int left, right;
        for (int i = 0; i < (n - 2) / 2; i++) {
            left = 2 * i + 1;
            right = 2 * i + 2;

            if (left < n && arr.get(i) < arr.get(left)) {
                return false;
            }
            if (right < n && arr.get(i) < arr.get(right)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isMaxHeapBFS(TreeNode root) {
        if (root == null) {
            return true;
        }

        LinkedList<TreeNode> queue = new LinkedList<>(List.of(root));
        boolean shouldBeLeaf = false;

        while (!queue.isEmpty()) {
            TreeNode current = queue.pop();
            if (current.left != null) {
                if (shouldBeLeaf || current.data < current.left.data) {
                    return false;
                }
                queue.push(current.left);
            } else {
                shouldBeLeaf = true;
            }

            if (current.right != null) {
                if (shouldBeLeaf || current.data < current.right.data) {
                    return false;
                }
                queue.push(current.right);
            } else {
                shouldBeLeaf = true;
            }
        }

        return true;
    }

    public static boolean isCompleteTree(TreeNode root) {
        if (root == null) {
            return true;
        }

        LinkedList<TreeNode> queue = new LinkedList<>(List.of(root));
        boolean shouldBeLeaf = false;

        while (!queue.isEmpty()) {
            TreeNode node = queue.pop();

            if (node == null) {
                shouldBeLeaf = true;
            } else {
                if (shouldBeLeaf) {
                    return false;
                }
                queue.push(node.left);
                queue.push(node.right);
            }
        }

        return true;
    }

    public static ArrayList<Integer> mergeKSortedArrays(ArrayList<ArrayList<Integer>> sortedArrays) {
        ArrayList<Integer> mergedArray = new ArrayList<>();

        // Создаем список узлов и минимальную кучу
        ArrayList<HeapNode> heapNodes = new ArrayList<>();
        MinHeap minHeap = new MinHeap(0);

        // Инициализируем кучу с первым элементом каждого массива
        for (int i = 0; i < sortedArrays.size(); i++) {
            ArrayList<Integer> currentArray = sortedArrays.get(i);
            if (!currentArray.isEmpty()) {
                int value = currentArray.get(0);
                heapNodes.add(new HeapNode(value, i, 0));
                minHeap.push(value); // Просто добавляем значение в кучу
            }
        }

        // Перестраиваем кучу после добавления всех начальных элементов
        minHeap.buildMinTree();

        while (!minHeap.isEmpty()) {
            int minValue = minHeap.delete();
            mergedArray.add(minValue);

            // Находим узел с этим значением
            HeapNode minNode = null;
            for (int i = 0; i < heapNodes.size(); i++) {
                if (heapNodes.get(i).value == minValue) {
                    minNode = heapNodes.get(i);
                    heapNodes.remove(i);
                    break;
                }
            }

            if (minNode != null) {
                // Добавляем следующий элемент из того же массива
                ArrayList<Integer> sourceArray = sortedArrays.get(minNode.arrayIndex);
                if (minNode.elementIndex + 1 < sourceArray.size()) {
                    int nextValue = sourceArray.get(minNode.elementIndex + 1);
                    HeapNode nextNode = new HeapNode(nextValue, minNode.arrayIndex, minNode.elementIndex + 1);
                    heapNodes.add(nextNode);
                    minHeap.push(nextValue);
                }
            }
        }

        return mergedArray;
    }

    public static Integer inorderMin(TreeNode node, int k, int[] counter) {
        if (node == null) {
            return null;
        }

        Integer leftResult = inorderMin(node.left, k, counter);
        if (leftResult != null) {
            return leftResult;
        }

        counter[0]++;
        if (counter[0] == k) {
            return node.data;
        }

        return inorderMin(node.right, k, counter);
    }

    // Вспомогательная функция для inorderMin
    public static Integer findKthSmallest(TreeNode root, int k) {
        int[] counter = new int[1];
        counter[0] = 0;
        return inorderMin(root, k, counter);
    }

    public static int calculateHeightsAndBalance(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = calculateHeightsAndBalance (node.left);
        int rightHeight = calculateHeightsAndBalance (node.right);

        node.balanceFactor = leftHeight - rightHeight;

        return 1 + max(leftHeight, rightHeight );
    }

    public static TreeNode mirrorTreeIterative(TreeNode node) {
        if (node == null) {
            return null;
        }
        LinkedList<TreeNode> queue = new LinkedList<>(List.of(node));
        while (!queue.isEmpty()) {
            TreeNode current = queue.pop();

            TreeNode temp = current.left;
            current.left = current.right;
            current.right = temp;

            if (current.left != null) {
                queue.push(current.left);
            }
            if (current.right != null) {
                queue.push(current.right);
            }

        }
        return node;
    }

    public static void main(String[] args) {
        System.out.println(" Тестирование TreeNode ");

        // Создаем дерево вручную
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(15);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(7);

        System.out.println("root = " + root.data);
        System.out.println("left = " + root.left.data);
        System.out.println("right = " + root.right.data);

        // Тест buildTree
        ArrayList<Integer> treeData = new ArrayList<>();
        treeData.add(1);
        treeData.add(2);
        treeData.add(3);
        treeData.add(4);
        treeData.add(5);

        TreeNode builtTree = new TreeNode().buildTree(treeData, 0);
        System.out.println("Дерево построено из массива: root = " + builtTree.data);

        System.out.println("\n Тестирование MinHeap ");

        // Тест MinHeap
        ArrayList<Integer> heapData = new ArrayList<>();
        heapData.add(3);
        heapData.add(1);
        heapData.add(4);
        heapData.add(1);
        heapData.add(5);

        MinHeap heap = new MinHeap(heapData);
        heap.buildMinTree();

        System.out.println("MinHeap:");
        System.out.println("Вершина: " + heap.top());

        heap.push(0);
        System.out.println("После добавления 0, вершина: " + heap.top());

        int deleted = heap.delete();
        System.out.println("Удален элемент: " + deleted);
        System.out.println("Новая вершина: " + heap.top());

        System.out.println("\n Тестирование isMaxHeap ");

        ArrayList<Integer> maxHeapArray = new ArrayList<>();
        maxHeapArray.add(10);
        maxHeapArray.add(8);
        maxHeapArray.add(9);
        maxHeapArray.add(5);
        maxHeapArray.add(6);
        maxHeapArray.add(7);

        ArrayList<Integer> notMaxHeapArray = new ArrayList<>();
        notMaxHeapArray.add(5);
        notMaxHeapArray.add(10);
        notMaxHeapArray.add(3);

        System.out.println("isMaxHeap([10,8,9,5,6,7]): " + isMaxHeap(maxHeapArray));
        System.out.println("isMaxHeap([5,10,3]): " + isMaxHeap(notMaxHeapArray));

        // Тест isMaxHeapBFS
        TreeNode maxHeapTree = new TreeNode(10);
        maxHeapTree.left = new TreeNode(8);
        maxHeapTree.right = new TreeNode(9);
        maxHeapTree.left.left = new TreeNode(5);
        maxHeapTree.left.right = new TreeNode(6);

        TreeNode notMaxHeapTree = new TreeNode(5);
        notMaxHeapTree.left = new TreeNode(10);
        notMaxHeapTree.right = new TreeNode(3);

        System.out.println("isMaxHeapBFS(макс-куча): " + isMaxHeapBFS(maxHeapTree));
        System.out.println("isMaxHeapBFS(не макс-куча): " + isMaxHeapBFS(notMaxHeapTree));

        System.out.println("\n Тестирование isCompleteTree ");

        TreeNode completeTree = new TreeNode(1);
        completeTree.left = new TreeNode(2);
        completeTree.right = new TreeNode(3);
        completeTree.left.left = new TreeNode(4);
        completeTree.left.right = new TreeNode(5);

        TreeNode incompleteTree = new TreeNode(1);
        incompleteTree.left = new TreeNode(2);
        incompleteTree.right = new TreeNode(3);
        incompleteTree.left.left = new TreeNode(4);
        incompleteTree.left.right = new TreeNode(5);
        incompleteTree.right.left = new TreeNode(6);
        incompleteTree.right.right = null; // Пропущен правый потомок

        System.out.println("isCompleteTree(полное): " + isCompleteTree(completeTree));
        System.out.println("isCompleteTree(неполное): " + isCompleteTree(incompleteTree));

        System.out.println("\n Тестирование mergeKSortedArrays ");

        ArrayList<ArrayList<Integer>> arrays = new ArrayList<>();

        ArrayList<Integer> arr1 = new ArrayList<>();
        arr1.add(1);
        arr1.add(4);
        arr1.add(7);

        ArrayList<Integer> arr2 = new ArrayList<>();
        arr2.add(2);
        arr2.add(5);
        arr2.add(8);

        ArrayList<Integer> arr3 = new ArrayList<>();
        arr3.add(3);
        arr3.add(6);
        arr3.add(9);

        arrays.add(arr1);
        arrays.add(arr2);
        arrays.add(arr3);

        ArrayList<Integer> merged = mergeKSortedArrays(arrays);
        System.out.println("Слияние отсортированных массивов:");
        System.out.println("Результат: " + merged);

        System.out.println("\n Тестирование inorderMin ");

        TreeNode bst = new TreeNode(5);
        bst.left = new TreeNode(3);
        bst.right = new TreeNode(7);
        bst.left.left = new TreeNode(2);
        bst.left.right = new TreeNode(4);
        bst.right.left = new TreeNode(6);
        bst.right.right = new TreeNode(8);

        // Теперь работает корректно!
        Integer kthSmallest = findKthSmallest(bst, 3);
        System.out.println("3-й наименьший элемент: " + kthSmallest);

        kthSmallest = findKthSmallest(bst, 1);
        System.out.println("1-й наименьший элемент: " + kthSmallest);

        kthSmallest = findKthSmallest(bst, 6);
        System.out.println("6-й наименьший элемент: " + kthSmallest);

        System.out.println("\n Тестирование mirrorTreeIterative ");

        TreeNode originalTree = new TreeNode(1);
        originalTree.left = new TreeNode(2);
        originalTree.right = new TreeNode(3);
        originalTree.left.left = new TreeNode(4);
        originalTree.left.right = new TreeNode(5);

        System.out.println("Оригинальное дерево: корень=1, левый=2, правый=3");
        TreeNode mirrored = mirrorTreeIterative(originalTree);
        System.out.println("После зеркалирования:");
        System.out.println("Корень=" + mirrored.data);
        System.out.println("Левый потомок=" + (mirrored.left != null ? mirrored.left.data : "null"));
        System.out.println("Правый потомок=" + (mirrored.right != null ? mirrored.right.data : "null"));

        System.out.println("\n Все тесты завершены ");
    }
}