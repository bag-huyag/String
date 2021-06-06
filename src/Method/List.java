package Method;

import ListException.ListException;


public class List {
    private Node head;

    public List(String str) // КОНСТУРКТОР
    {
        int iterator = 1;
        int num = Node.SIZE;
        int nNode = (str.length() - 1) / Node.SIZE + 1; // количество блоков
        int start = Node.SIZE;
        int end = Node.SIZE * 2;
        char[] ar = new char[Node.SIZE];
        if (nNode == 1) // если всего один блок
        {
            str.getChars(0, str.length(), ar, 0);
            // ar =  str.toCharArray();
            head = new Node(str.length(), ar, null);
        } else { // создать блок, выделить память под символьный массив и его в качетстве параметра в getchars
            str.getChars(0, Node.SIZE, ar, 0);
            head = new Node(Node.SIZE, ar, null); // создали первый блок
            Node tail = head;
            while (iterator < nNode - 1) { // до последнего блока -1
                char[] arr = new char[Node.SIZE];
                str.getChars(start, end, arr, 0);  // отрезали нужный массив
                end += Node.SIZE;
                start += Node.SIZE;
                tail.next = new Node(Node.SIZE, arr, tail.next);
                tail = tail.next;
                num += Node.SIZE; // сумма реальной заполненности
                iterator++;

            }
            char[] arr2 = new char[Node.SIZE];
            str.getChars(start, str.length(), arr2, 0);
            tail.next = new Node(str.length() - num, arr2, tail.next); // создание последнего блока
        }
    }

    public List() { // создает пустой список
        head = null;
    }

    public List(List d) { // конструктор для копирования списка
        char[] cop = new char[Node.SIZE];
        copy(d.head.array, cop, 0, d.head.num_elements, 0);
        head = new Node(d.head, cop);
        Node h = d.head;
        h = h.next;
        Node k = head;
        while (h != null) {
            copy(h.array, cop, 0, h.num_elements, 0);
            k.next = new Node(h, cop);
            k = k.next;
            h = h.next;
        }
    }

    private static class PositionAndBlock {
        Node next;
        int position;

        private PositionAndBlock(int index, Node el) {
            next = el;
            position = index;
        }
    }


//    // здесь на*овнакодено. не повторять.
//    private int searchBlock() { // индекс последнего элемента последнего блока относительно первого элемента первого блока
//        Node h = head;
//        int k = 0;
//        while (h != null) {
//            k += h.num_elements;
//            h = h.next;
//        }
//        return k;
//    }

    private PositionAndBlock searchBlock(int in) { // поиск блока+позиция   //gtht[dfns jib,rb dvtcnh cf,f
        PositionAndBlock object = new PositionAndBlock(-1, null);
        Node h = head;
        int capacity = in;
        while (h != null) {
            if (capacity < h.num_elements) {  //<=
                object = new PositionAndBlock(capacity, h);
                return object;
            }
            capacity -= h.num_elements;
            h = h.next;
        }
        return object;
    }

    private Node searchEndBlock() { // метод поиска последнрего блока + слепка     // фото с доски
        Node h = head; // предыдущий блок относитально с
        Node c = h; // следующий блок
        while (c != null) {
//            if (h.num_elements + c.num_elements <= Node.SIZE) {
//                copy(c.array, h.array, 0, c.num_elements, h.num_elements);
//                h.num_elements+=c.num_elements;
//                c=c.next;
//                h.next=c;
//                for (int i = 0; i < c.num_elements; i++) {
//                    h.array[h.num_elements] = c.array[i];
//                    h.num_elements++;
//
//                }
//                h.num_elements += c.num_elements;
//                c = c.next;
//            } else { // передвигаем на следующую пару
            h = c;
            c = c.next;
//            }
        }
        return h;
    }

    public int len() { // сумма реальной занятости блоков    // перенести слепку в лен (перенес).    *
        Node h = head;
        int k = 0;
        while (h != null) {
            k += h.num_elements;
            h = h.next;


//            if (h.num_elements + h.num_elements <= Node.SIZE) {
//                copy(h.array, h.array, 0, h.num_elements, h.num_elements);
//                h.num_elements += h.num_elements;
//                h = h.next;
//                h.next = h;
//                for (int i = 0; i < h.num_elements; i++) {
//                    h.array[h.num_elements] = h.array[i];
//                    h.num_elements++;
//
//                }
//                h.num_elements += h.num_elements;
//                h = h.next;
//            } else { // передвигаем на следующую пару
//                k += h.num_elements;
//                h = h.next;
//            }

        }
        return k;
    }

    public void insert(String str, int index) {

        insert(new List(str), index);
    }

    public void insert(List str, int index) /*throws ListException*/ { //вставить в строку в позицию index строку str

//        Node qwerty = this.searchBlock();
//        int u = searchBlock(this);

        PositionAndBlock u = searchBlock(index);

//      if ((index > 0) && (index <= len())) {
//      if ((index > 0) && (index <= this.searchBlock()))
        if (u.position > -27) { // проверка на можно ли вставить
//                List st = new List(str);// переделали введенную строку в список
//                List a = new List(str)
            if (u.position == u.next.num_elements)
                u.position = 0;

            if (index != 0) {
                Node b = cutLine(u.position, u.next); // блок - вставка
                Node p = b.next;
                b.next = str.head;
                Node z = str.searchEndBlock();
                z.next = p;
            } else if (index == 1) { // для нулевой позиции ( без разрыва)
                Node p = head;
                Node z = str.searchEndBlock();
                z.next = p;
            } else {
                str.searchEndBlock().next = u.next.next;
                u.next.next = str.head;
            }
        } else {
            throw new ListException("Введеный номер символа превышает длину строки", index);
        }
    }

    // переделать
    // переделано
    private Node cutLine(int in, Node b) { // РАЗРЕЗ ( индекс + блок )
        if ((in != 0) && (in != b.num_elements)) {
            char[] arr = new char[Node.SIZE];
            char[] arr2 = new char[Node.SIZE];
            copy(b.array, arr, in + 1, b.num_elements, 0);
            copy(b.array, arr2, 0, in + 1, 0);
            Node n = new Node(b.num_elements - in - 1, arr, b.next); // создание второго блока
            b.array = arr2;
            b.num_elements = in;
            b.next = n;
        }
        return b;
    }


    private void copy(char[] from, char[] to, int start, int end, int g) { // УНИВЕРСАЛЬНОЕ КОПИРОВАНИЕ
        int i, j = g - 1;
        for (i = start; i < end; i++) {
            j++;
            to[j] = from[i];
        }
    }


    public void append(List list) { //добавить в конец строку
        Node h = searchEndBlock(); // нашли последний блок
        List a = new List(list);  // переделали введенную строку в список
        h.next = a.head;  //// сменили указателей
    }


    //
    public void append(String g) {
        List d = new List(g);
        append(d);
    }
    //

    public void append(char a) { //добавить в конец символ
        Node uk = searchEndBlock(); // нашли последний блок
        String str = String.valueOf(a); // переделали символ в строчку
        List symbol = new List(str);// переделали строку в список
        uk.next = symbol.head; // перебросили указатель
    }

    public char charAt(int index) /*throws ListException*/ {   // символ в строке в позиции index
        PositionAndBlock u = searchBlock(index);
        Node h = u.next;
        if (u.position > -1) {
            proverka(u);
//            for (int i = 0; i <= h.array.length; i++) {
//                if (i == u.position - 1) {
//                    return h.array[i];
//                }
//            }
            return u.next.array[u.position];
        }
//        return '\0';
        else {
            throw new ListException("Введеный номер символа превышает длину строки", index);
        }
    }

    public void setCharAt(int index, char symbol) /*throws ListException*/ { // заменить символ в позиции index на ch, не изменяет длину строки!
//        PositionAndBlock u = searchBlock(index);
//        Node h = u.next;
//        if ((index > 0) && (index <= len())) {
        PositionAndBlock qwe = searchBlock(index);
        if ((qwe.position > -1)) { //> -1
//            if ( (qwe.next.next != null) && (qwe.next.num_elements == qwe.position)){
//                qwe.next = qwe.next.next;
//                qwe.position = 0;
//            }
//            PositionAndBlock u = searchBlock(index);
            //Node h = qwe.next;
//            for (int i = 0; i <= h.array.length; i++) {
//                if (i == qwe.position - 1) {
            proverka(qwe);
            qwe.next.array[qwe.position] = symbol;
//                }
            //   }
        } else {
            throw new ListException("Введеный номер символа превышает длину строки.", index);
        }
    }


    public List subString(int start, int end) /*throws ListException*/ { // ВЫРЕЗКА СТРОКИ   // взятие подстроки, от start до end, не включая end

        //Стив Макконнелл в книге "Совершенный код" писал о важности занесения входных значений в отдельные переменные
        int tempStart = start;
        int tempEnd = end;
        List k = new List(); // создали пустой список
//        if ((start > 0) && (start <= len()) && ((end > 0) && (end <= len()))) {
//        if ((start > 0) && (start <= this.searchBlock()) && ((end > 0) && (end <= this.searchBlock()))) {
        PositionAndBlock endBlock = searchBlock(--tempEnd); // блок с конечной позиции
        PositionAndBlock startBlock = searchBlock(--tempStart);
        if (startBlock.position > -1) {
//            PositionAndBlock u = searchBlock(--start); // блок со стартовой позицией
            Node h = startBlock.next; // блок со стартовой позицией

            proverka(endBlock);
            proverka(startBlock);
            //// if через searchBlock  // сделано

//            PositionAndBlock e = searchBlock(end); // блок с конечной позиции
            if (h == endBlock.next) { // проверка на совпадение указателей
                char[] c = new char[Node.SIZE];
                copy(h.array, c, startBlock.position, endBlock.position + 1, 0);
                k.head = new Node(tempEnd - tempStart + 1, c, null);
            } else {
//                Node en = cutLine(endBlock.position, endBlock.next);
//                en = en.next;
// создание первого блока вырезки
                char[] arr = new char[Node.SIZE];
                copy(h.array, arr, startBlock.position, h.num_elements, 0); // массив с старотовый блоk
                k.head = new Node(h.num_elements - startBlock.position, arr, null);
                Node g = k.head;
                h = h.next;
                tempStart += Node.SIZE - startBlock.position;
                while (tempStart + Node.SIZE < tempEnd)
                {
                    char [] arr3 = new char[Node.SIZE];
                    copy(h.array, arr3, 0, h.num_elements, 0); // массив с старотовый блок
                    g.next = new Node(h.num_elements, arr3, null);
                    g = g.next;
                    tempStart += Node.SIZE;
                    h = h.next;
                }
                char [] arr2 = new char[tempEnd - tempStart + 1];
                copy(h.array, arr2, 0, tempEnd - tempStart + 1, 0);
                g.next = new Node(tempEnd - tempStart + 1, arr2, null);

//                h = h.next;
// создание блоков вырезки до последнего-1
//                Node tail = startBlock.next;
            //    Node tail2 = startBlock.next;

//                while (h != null) {
////                    char[] cop = new char[Node.SIZE];
////                    copy(h.array, cop, 0, h.num_elements, 0);
////                    tail.next = new Node(h, cop);
////                    tail = tail.next;
////                    h = h.next;
//
//                    k.head.next = h;
//                    h = h.next;
//
//                }

            }
        } else {
            throw new ListException("Введеный номер символа превышает длину строки", start, end);
        }
        return k;
    }

//    public String tostring(){ // преобразование в строку
//        StringBuilder str = new StringBuilder();           /////////исправить
//        Node h = head;
//        while (h != null) { // если мы дошли до последнего блока
//                String newstr = new String(h.array, 0, h.num_elements);
//                str.append(newstr);
//                h = h.next;
//        }
//        return str.toString();
//    }


    public String toString() {
        String Nodes = new String(head.array, 0, head.num_elements);
        Node headnext = head.next;
        while (headnext != null) {
            String _list = new String(headnext.array, 0, headnext.num_elements);
            Nodes += _list;
            headnext = headnext.next;
        }
        return Nodes;
    }


    public void print() {
        Node h = head;
        while (h != null) {
            System.out.print(h.array);
            System.out.println("   " + h.num_elements);
            h = h.next;
        }
        System.out.println();
    }


    //спасибо Дмитрию за метод проверки
    private void proverka(PositionAndBlock qwe) {

        if ((qwe.next.next != null) && (qwe.next.num_elements == qwe.position)) {
            qwe.next = qwe.next.next;
            qwe.position = 0;
        }

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // НОДА
    private class Node {
        int num_elements;
        final static int SIZE = 16;
        char[] array;
        Node next;

        //СОЗДАНИЕ БЛОКА
        public Node(int n, char[] ar, Node a) {
            next = a;
            array = ar;
            num_elements = n;
        }

        public Node(int n) {
            next = null;
            array = null;
            num_elements = n;
        }

        // КОПИРОВАНИЕ БЛОКОВ
        public Node(Node b, char[] a) {
            next = null;
            array = a;
            num_elements = b.num_elements;
        }


        //КОПИРОВАНИЕ БЛОКА В СТРОКЕ  *
        public Node(Node z) {


            String str = String.valueOf(z); // переделали ноду в строчку

            List b = new List(str);// переделали строку в список


            char[] cop = new char[Node.SIZE];
            copy(b.head.array, cop, 0, b.head.num_elements, 0);
            head = new Node(b.head, cop);
            Node h = b.head;
            h = h.next;
            Node k = head;
            while (h != null) {
                copy(h.array, cop, 0, h.num_elements, 0);
                k.next = new Node(h, cop);
                k = k.next;
                h = h.next;
            }


        }
    }

}






