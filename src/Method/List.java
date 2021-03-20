package Method;

import ListException.*;


public class List {
    private Node head;

    public List(String str) // КОНСТУРКТОР
    {
        int iterator = 1;
        int num = Node.SIZE;
        int nNode = (str.length() - 1) / Node.SIZE + 1; // количество блоков
        int start = Node.SIZE;
        int end = Node.SIZE*2;
        char[] ar = new char[Node.SIZE];
        if (nNode == 1) // если всего один блок
        {
            str.getChars(0,str.length(),ar,0);
            // ar =  str.toCharArray();
            head = new  Node(str.length(), ar,null);
        }
        else { // создать блок, выделить память под символьный массив и его в качетстве параметра в getchars
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

   public List(){ // создает пустой список
       head=null;
   }

   public List (List d) { // конструктов для копирования списка
       char [] cop = new char[Node.SIZE];
       copy(d.head.array, cop, 0, d.head.num_elements, 0);
        head = new Node(d.head, cop);
        Node h=d.head;
        h=h.next;
        Node k=head;
        while (h!=null)
        {
            copy(h.array, cop, 0, h.num_elements, 0);
            k.next=new Node(h, cop);
            k=k.next;
            h=h.next;
        }
    }

    private static class PositionAndBlock{
        Node next;
        int position;
        private PositionAndBlock(int index, Node el){
            next=el;
            position=index;
        }
    }

    private  PositionAndBlock searchBlock(int in){ // поиск блока+позиция
        PositionAndBlock object = null;
        Node h = head;
        int capacity=in;
        while (h!=null)
        {
            if (capacity<=h.num_elements) {
                object = new PositionAndBlock(capacity, h);
                break;
            }
            capacity-=h.num_elements;
            h = h.next;
        }
        return object;
    }

        private Node searchEndBlock(){ //метод поиска последнрего блока + слепка  дабавить склейку
        Node h = head; // предыдущий блок относитально с
        Node c = h.next; // следующий блок
        while (c!=null){
            if (h.num_elements+c.num_elements<=Node.SIZE){
                copy(c.array, h.array, 0, c.num_elements, h.num_elements);
                h.num_elements+=c.num_elements;
                c=c.next;
                h.next=c;
            }
            else{ // передвигаем на следующую пару
                c=c.next;
                h=h.next;
            }
        }
        return h;
    }

    public int len(){ // сумма реальной занятости блоков
        Node h = head;
        int k=0;
        while (h!=null)
        {
            k+=h.num_elements;
            h = h.next;
        }
        return k;
    }

    public void insert (String str, int index) throws ListException{
            if ((index>0)&&(index<=len())) {
                List st = new List(str);// переделали введенную строку в список
                List a = new List(st);
                if (index==1){ // для нулевой позиции ( без разрыва)
                    Node p=head;
                    Node z = a.searchEndBlock();
                    z.next=p;
                }
                else {
                    PositionAndBlock u = searchBlock(index);
                    Node b = cutLine(u.position, u.next); // блок - вставка
                    Node p = b.next;
                    b.next = a.head;
                    Node z = a.searchEndBlock();
                    z.next = p;
                }
            }
            else {
                throw new ListException("Введеный номер символа превышает длину строки", index);
            }
        }

    private Node cutLine(int in, Node b){ // РАЗРЕЗ ( индекс + блок )
        if ((in!=0)&&(in!=b.num_elements)) {
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


    private void copy (char [] from, char [] to, int start, int end, int g){ // УНИВЕРСАЛЬНОЕ КОПИРОВАНИЕ
        int i,j=g-1;
        for (i=start;i<end;i++){
            j++;
            to[j]=from[i];
        }
    }

    public void append(String list) {
        Node h = searchEndBlock(); // нашли последний блок
        List a = new List (list);  // переделали введенную строку в список
        h.next = a.head;  //// сменили указателей
    }


    public void append(char a){
        Node uk = searchEndBlock(); // нашли последний блок
        String str = String.valueOf(a); // переделали символ в строчку
        List symbol = new List (str);// переделали строку в список
        uk.next = symbol.head; // перебросили указатель
    }

    public char charAt (int index) throws ListException {
        PositionAndBlock u= searchBlock(index);
        Node h = u.next;
        if ((index>0)&&(index<=len())) {
            for (int i = 0; i <= h.array.length; i++) {
                if (i == u.position - 1) {
                    return h.array[i];
                }
            }
            return '\0';
        }
        else {
            throw new ListException("Введеный номер символа превышает длину строки", index);
        }
    }

    public void setCharAt(int index,char symbol) throws ListException {
//        PositionAndBlock u = searchBlock(index);
//        Node h = u.next;
            if ((index>0)&&(index<=len())) {
                PositionAndBlock u = searchBlock(index);
                Node h = u.next;
                for (int i = 0; i <= h.array.length; i++) {
                    if (i == u.position - 1) {
                        h.array[i] = symbol;
                    }
                }
            }
            else {
                throw new ListException("Введеный номер символа превышает длину строки.", index);
            }
    }


    public List subString (int start, int end) throws ArrayIndexOutOfBoundsException{ // ВЫРЕЗКА СТРОКИ
        List k = new List(); // создали пустой список
        if ((start>0)&&(start<=len())&&((end>0)&&(end<=len()))) {
            PositionAndBlock u = searchBlock(--start); // блок со стартовой позицией
            Node h = u.next; // блок со стартовой позицией
            PositionAndBlock e = searchBlock(end); // блок с конечной позиции
            if (h==e.next) { // проверка на совпадение указателей
                char[] c = new char[Node.SIZE];
                copy(h.array, c, u.position,e.position, 0);
                k.head = new Node(end - start, c, null);
            }
            else{
                Node en = cutLine(e.position, e.next);
                en = en.next;
//создание первого блока вырезки
                char[] arr = new char[Node.SIZE];
                copy(h.array, arr, u.position, h.num_elements, 0); // массив с старотовый блок
                k.head = new Node(h.num_elements - u.position, arr, null);
                h=h.next;
//создание блоков вырезки до последнего-1
                Node tail = k.head;
                while (h != en) {
                    char[] cop = new char[Node.SIZE];
                    copy(h.array, cop, 0, h.num_elements, 0);
                    tail.next = new Node(h, cop);
                    tail = tail.next;
                    h = h.next;
                }
            }
        }
        else {
            throw new ArrayIndexOutOfBoundsException("Введеный номер символа превышает длину строки");
        }
        return k;
    }

    public String tostring(){ // преобразование в строку
        StringBuilder str = new StringBuilder();
        Node h = head;
        while (h != null) { // если мы дошли до последнего блока
                String newstr = new String(h.array, 0, h.num_elements);
                str.append(newstr);
            h = h.next;
        }
        return str.toString();
    }


    public void print()
    {
        Node h = head;
        while (h!=null)
        {
            System.out.print(h.array);
            System.out.println("   "+h.num_elements);
            h = h.next;
        }
        System.out.println();
    }


}
