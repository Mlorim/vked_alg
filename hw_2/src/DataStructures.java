import java.util.List;
import java.util.Queue;

public class DataStructures {

    // Класс элемента односвязного списка
    public static class Node {
        int data;
        Node next;

        public Node() {
            data = 0;
            next = null;
        }

        public Node(int d) {
            data = d;
            next = null;
        }
    }

    // Класс односвязного списка
    public static class LinkedList {
        Node head;
        int size;

        public LinkedList(Node n) {
            head = n;
            size = 1;
            Node next = head.next;
            while (next != null && next != head) {
                size++;
                next = next.next;
            }
        }

        // Вставка в начало
        public void insert(Node n) {
            n.next = head;
            head = n;
        }

        // Проверка на цикличность
        public boolean isCycle() {
            if (head == null || head.next == null) {
                return false;
            }

            Node slow = head;
            Node fast = head.next;

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
        public Node reverse() {
            if (this.isCycle()) {
                System.out.println("Cant reverse cycle list!");
                return head;
            }

            Node prev = null;
            Node curr = head;

            while (curr != null) {
                Node temp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = temp;
            }

            head = prev;
            return head;
        }

        // Поиск средей ноды
        public Node middleNode() {
            if (this.isCycle()) {
                System.out.println("No middle node in cycle list!");
                return head;
            }

            Node slow = head;
            Node fast = head;

            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }

            return slow;
        }

        // Вырезание элемента по значанию
        public Node removeElement(int val) {
            if (this.isCycle()) {
                System.out.println("Cant remove from cycle list!");
                return head;
            }

            Node dummy = new Node();
            dummy.next = head;
            Node prev = dummy;
            Node curr = head;

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

    // Печсть односвязного списка (как цикличного, так и обычного)
    public static void printList(LinkedList list) {
        Node curr = list.head;
        for (int i = 0; i < list.size; i++) {
            System.out.print(curr.data);
            System.out.print(' ');
            curr = curr.next;
        }
        System.out.print('\n');
    }

    // Проверка на исходную строку
    public static boolean isSubsequence(String a, String b) {
//        Queue q = new Queue();
    }


    public static void main(String[] args) {

        final boolean isCycleTest = false;
        final boolean reverseTest = false;
        final boolean middleNodeTest = false;
        final boolean removeElementTest = false;

        if (isCycleTest) {
            System.out.println("isCycle test");
            Node a = new Node(1);
            Node b = new Node(2);
            Node c = new Node(3);

            a.next = b;
            b.next = c;
            c.next = a;

            LinkedList list = new LinkedList(b);
            System.out.println(list.isCycle());
        }

        if (reverseTest) {
            System.out.println("reverse test");
            Node a = new Node(1);
            Node b = new Node(2);
            Node c = new Node(3);
            Node d = new Node(4);

            a.next = b;
            b.next = c;
            c.next = d;

            LinkedList list = new LinkedList(a);
            printList(list);
            list.reverse();
            printList(list);
        }

        if (middleNodeTest) {
            System.out.println("reverse test");
            Node a = new Node(1);
            Node b = new Node(2);
            Node c = new Node(3);

            a.next = b;
            b.next = c;

            LinkedList list = new LinkedList(a);
            System.out.print(list.middleNode().data);
        }

        if (removeElementTest) {
            System.out.println("reverse test");
            Node a = new Node(1);
            Node b = new Node(2);
            Node c = new Node(3);

            a.next = b;
            b.next = c;

            LinkedList list = new LinkedList(a);
            printList(list);
            list.removeElement(1);
            printList(list);
        }
    }
}

