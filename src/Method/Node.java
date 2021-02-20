package Method;

public class Node {
    int num_elements;
    final static int SIZE = 16;
    char[] array;
    Node next;


    public Node(int n, char[] ar, Node a)
    {
        next = a;
        array = ar;
        num_elements = n;
    }

    public Node (Node b, char[] a){ // КОПИРОВАНИЕ БЛОКОВ
        next = null;
        array = a;
        num_elements = b.num_elements;
    }
}


