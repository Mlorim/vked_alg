import java.awt.desktop.AppReopenedEvent;
import java.util.Stack;

public class DataStructures {

    // Класс элемента односвязного списка
    public static class Node<T> {
        T data;
        Node<T> next;

        public Node() {
            data = null;
            next = null;
        }

        public Node(T d) {
            data = d;
            next = null;
        }
    }

    // Класс односвязного списка
    public static class LinkedList<T> {
        Node<T> head;
        int size;

        public LinkedList(Node<T> n) {
            head = n;
            size = 1;
            Node<T> next = head.next;
            while (next != null && next != head) {
                size++;
                next = next.next;
            }
        }

        // Вставка в начало
        public void insert(Node<T> n) {
            n.next = head;
            head = n;
        }

        // Проверка на цикличность
        public boolean isCycle() {
            if (head == null || head.next == null) {
                return false;
            }

            Node<T> slow = head;
            Node<T> fast = head.next;

            while (slow != fast) {
                if (fast == null || fast.next == null) {
                    return false;
                }
                slow = slow.next;
                fast = fast.next.next;
            }

            return true;
        }

        // Разворот списка
        public Node<T> reverse() {
            if (this.isCycle()) {
                System.out.println("Cant reverse cycle list!");
                return head;
            }

            Node<T> prev = null;
            Node<T> curr = head;

            while (curr != null) {
                Node<T> temp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = temp;
            }

            head = prev;
            return head;
        }

        // Поиск средей ноды
        public Node<T> middleNode() {
            if (this.isCycle()) {
                System.out.println("No middle node in cycle list!");
                return head;
            }

            Node<T> slow = head;
            Node<T> fast = head;

            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }

            return slow;
        }

        // Вырезание элемента по значанию
        public Node<T> removeElement(T val) {
            if (this.isCycle()) {
                System.out.println("Cant remove from cycle list!");
                return head;
            }

            Node<T> dummy = new Node<T>();
            dummy.next = head;
            Node<T> prev = dummy;
            Node<T> curr = head;

            while (curr != null) {
                if (curr.data == val) {
                    prev.next = curr.next;
                    size--;
                } else {
                    prev = curr;
                }
                curr = curr.next;
            }

            return dummy.next;
        }
    }

    // Печать односвязного списка (как цикличного, так и обычного)
    public static void printIntList(LinkedList<Integer> list) {
        Node<Integer> curr = list.head;
        for (int i = 0; i < list.size; i++) {
            System.out.print(curr.data + " ");
            curr = curr.next;
        }
        System.out.print('\n');
    }

    // Очередь на односвязном списку
    public static class LLQueue<T> {
        Node<T> start;
        Node<T> end;
        int size;

        public LLQueue() {
            start = null;
            end = null;
            size = 0;
        }

        public boolean isEmpty() {
            return start == null;
        }

        // Добавление в конец очереди
        public void push(T data) {
            Node<T> newNode = new Node<T>(data);
            if (this.isEmpty()) {
                start = end = newNode;
            } else {
                end.next = newNode;
                end = newNode;
            }
            size++;
        }

        // Удаление из начала очереди
        public Node<T> pop() {
            if (this.isEmpty()) {
                throw new RuntimeException("Can't pop from empty queue!");
            }
            Node<T> temp = start;
            start = start.next;
            if (start == null) {
                end = null;
            }
            size--;

            return temp;
        }

        public Node<T> peek() {
            if (this.isEmpty()) {
                throw new RuntimeException("Can't peek from empty queue!");
            }

            return start;
        }

    }

    public static void printCharQueue(LLQueue<Character> q) {
        if (q.isEmpty()) {
            System.out.println("Queue is empty!");
            return;
        }

        Node<Character> curr = q.start;
        while (curr != null) {
            System.out.print(curr.data + " ");
            curr = curr.next;
        }
        System.out.print('\n');
    }

    // Проверка на исходную строку
    public static boolean isSubsequence(String a, String b) {
        LLQueue<Character> q = new LLQueue<Character>();
        for (int i = 0; i < a.length(); i++) {
            q.push(a.charAt(i));
        }
        for (int i = 0; i < b.length(); i++) {
            if (!q.isEmpty() && q.peek().data == b.charAt(i)) {
                q.pop();
            }
        }
        return q.size == 0;
    }

    // Проверка на исходную строку (метод двух указателей)
    public static boolean isSubsequencePnt(String a, String b) {
        int slow = 0;
        int fast = 0;
        while (slow < a.length() && fast < b.length()) {
            if (b.charAt(fast) == a.charAt(slow)) {
                slow++;
            }
            fast++;
        }
        return slow == a.length();
    }

    public static boolean isPalindrome(String s) {
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < s.length(); i++) {
            stack.push(s.charAt(i));
        }

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != stack.pop()) {
                return false;
            }
        }

        return true;
    }

    public static boolean isPalindromePnt(String s) {
        int left = 0;
        int right = s.length()-1;

        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    public static void mergeLLists(LinkedList<Integer> list1, LinkedList<Integer> list2) {
        Node<Integer> dummy = new Node<Integer>();
        Node<Integer> main = dummy;

        Node<Integer> curr1 = list1.head;
        Node<Integer> curr2 = list2.head;

        while (curr1 != null && curr2 != null) {
            if (curr1.data <= curr2.data) {
                main.next = curr1;
                curr1 = curr1.next;
            } else {
                main.next = curr2;
                curr2 = curr2.next;
            }
            main = main.next;
        }

        while (curr1 != null) {
            main.next = curr1;
            curr1 = curr1.next;
            main = main.next;
        }

        while (curr2 != null) {
            main.next = curr2;
            curr2 = curr2.next;
            main = main.next;
        }

        list1.size = list1.size + list2.size;
        list1.head = dummy.next;
        list2.head = null;
    }

    public static void main(String[] args) {

        final boolean isCycleTest = false;
        final boolean reverseTest = false;
        final boolean middleNodeTest = false;
        final boolean removeElementTest = false;
        final boolean isSubsequenceTest = false;
        final boolean isSubsequencePntTest = false;
        final boolean isPalindromeTest = false;
        final boolean isPalindromePntTest = false;
        final boolean mergeLListTest = true;

        if (isCycleTest) {
            System.out.println("isCycle test");
            Node<Integer> a = new Node<Integer>(1);
            Node<Integer> b = new Node<Integer>(2);
            Node<Integer> c = new Node<Integer>(3);

            a.next = b;
            b.next = c;
            c.next = a;

            LinkedList<Integer> list = new LinkedList<Integer>(b);
            System.out.println(list.isCycle());
        }

        if (reverseTest) {
            System.out.println("reverse test");
            Node<Integer> a = new Node<Integer>(1);
            Node<Integer> b = new Node<Integer>(2);
            Node<Integer> c = new Node<Integer>(3);
            Node<Integer> d = new Node<Integer>(4);

            a.next = b;
            b.next = c;
            c.next = d;

            LinkedList<Integer> list = new LinkedList<Integer>(a);
            printIntList(list);
            list.reverse();
            printIntList(list);
        }

        if (middleNodeTest) {
            System.out.println("reverse test");
            Node<Integer> a = new Node<Integer>(1);
            Node<Integer> b = new Node<Integer>(2);
            Node<Integer> c = new Node<Integer>(3);

            a.next = b;
            b.next = c;

            LinkedList<Integer> list = new LinkedList<Integer>(a);
            System.out.print(list.middleNode().data);
        }

        if (removeElementTest) {
            System.out.println("reverse test");
            Node<Integer> a = new Node<Integer>(1);
            Node<Integer> b = new Node<Integer>(2);
            Node<Integer> c = new Node<Integer>(3);

            a.next = b;
            b.next = c;

            LinkedList<Integer> list = new LinkedList<Integer>(a);
            printIntList(list);
            list.removeElement(1);
            printIntList(list);
        }

        if (isSubsequenceTest) {
            System.out.println("isSubsequence test");
            String a = "abc";
            String b = "axbxcx";
            String c = "axbxxx";
            System.out.println(isSubsequence(a, b));
            System.out.println(isSubsequence(a, c));
        }

        if (isSubsequencePntTest) {
            System.out.println("isSubsequencePnt test");
            String a = "abc";
            String b = "axbxcx";
            String c = "axbxxx";
            System.out.println(isSubsequencePnt(a, b));
            System.out.println(isSubsequencePnt(a, c));
        }

        if (isPalindromeTest) {
            System.out.println("isPalindromeTest test");
            String s1 = "abcba";
            String s2 = "abcabc";
            System.out.println(isPalindrome(s1));
            System.out.println(isPalindrome(s2));
        }

        if (isPalindromePntTest) {
            System.out.println("isPalindromePntTest test");
            String s1 = "abcba";
            String s2 = "abcabc";
            System.out.println(isPalindromePnt(s1));
            System.out.println(isPalindromePnt(s2));
        }

        if (mergeLListTest) {
            System.out.println("mergeLListTest test");
            Node<Integer> a1 = new Node<Integer>(3);
            Node<Integer> b1 = new Node<Integer>(6);
            Node<Integer> c1 = new Node<Integer>(8);

            a1.next = b1;
            b1.next = c1;
            LinkedList<Integer> list1 = new LinkedList<Integer>(a1);

            Node<Integer> a2 = new Node<Integer>(4);
            Node<Integer> b2 = new Node<Integer>(7);
            Node<Integer> c2 = new Node<Integer>(9);
            Node<Integer> d2 = new Node<Integer>(11);

            a2.next = b2;
            b2.next = c2;
            c2.next = d2;

            LinkedList<Integer> list2 = new LinkedList<Integer>(a2);
            System.out.println("First list");
            printIntList(list1);
            System.out.println("Second list");
            printIntList(list2);

            mergeLLists(list1, list2);
            System.out.println("Merged list");
            printIntList(list1);
        }
    }
}

