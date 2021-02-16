package String;

public class String1 {
    public class StringItem
    {
            private  int len;
            private StringItem next;
            final static int SIZE=16;
            private char [] symbol;
            public StringItem (int v, char [] c, StringItem n)
            {
                len=v;
                symbol=c;
                next=n;
            }
            public StringItem (StringItem b, char[] c, StringItem f)
            {
                len=b.len;
                symbol=c;
                next=f;
            }
    }
    public class Pos
    {
        private  int num;
        private StringItem a;
        private Pos (StringItem b, int c)
        {
            a=b;
            num=c;
        }
    }
    public StringItem head;
    public String1(String b)
    {
        head=toStr(b);
    }
    public String1()
    {
        head=null;
    }
    public String1(String1 s)
    {
        StringItem h=s.head;
        head=new StringItem(h, copy (0,h.len, h.symbol), null);
        StringItem p=head;
        h=h.next;
        while(h!=null)
        {
            p=add(h.len, copy (0,h.len, h.symbol), p);
            h=h.next;
        }
    }
    public void append(char d)
    {
        String r=Character.toString(d);
        String1 b= new String1(r);
        StringItem s=TheEnd();
        s.next=b.head;
    }
    public void append(String1 d)
    {
        StringItem s=TheEnd();
        String1 c=new String1(d);
        s.next=c.head;
    }
    public void append(String g)
    {
        String1 d=new String1(g);
        append(d);
    }
    public void setcharAt(int index, char d)
    {
        Pos b=findblock(index);
        b.a.symbol[b.num]=d;
    }
    public char charAt(int index)
    {
        Pos c=findblock(index);
        return c.a.symbol[c.num];
    }
    public String1 substring(int s, int e)
    {
         StringItem ss=cutblock(findblock(s)), ee=cutblock(findblock(e));
         String1 res= new String1();
         res.head = new StringItem(ss.len, copy (0,ss.len, ss.symbol), null);
         StringItem p=res.head;
         ss=ss.next;
         while (ss!=ee.next)
         {
             p = add(ss.len, copy (0,ss.len, ss.symbol), p);
             ss=ss.next;
         }
         return res;
     }
    public int length()
    {
        int k=0;
        StringItem h=head;
        while (h!=null)
        {
            k+=h.len;
            h=h.next;
        }
        return k;
    }
    public void insert (String c, int k)
    {
        insert(new String1(c), k);
    }
    public void insert (String1 c, int k)

    {
        vstavka(new String1(c), cutblock(findblock(k)));
    }
    public  String toString()
    {
        String str=new String(head.symbol, 0, head.len);
        StringItem h=head.next;
        while (h!=null)
        {
            String n= new String(h.symbol, 0, h.len);
            str+=n;
            h=h.next;
        }
        return str;
    }
    private void vstavka (String1 h, StringItem fr)
    {
        StringItem p=fr.next, k;
        fr.next=h.head;
        k=h.TheEnd();
        k.next=p;
    }
    private StringItem cutblock(Pos dr)
    {
        int k=dr.num, h=dr.a.len-k;
        if (dr.a.len!=k)
        {
            char[] c1 = copy(0, dr.num, dr.a.symbol);
            char[] c2 = copy(dr.num, dr.a.len, dr.a.symbol);
            dr.a.symbol=c1;
            dr.a.len=k;
            dr.a.next= new StringItem(h, c2, dr.a.next);
        }
        return dr.a;
    }
    private Pos findblock(int index)
    {
        StringItem h=head;
        while (h.len<index)
        {
            index-=h.len;
            h=h.next;

        }
        return new Pos(h, index);
    }
    private StringItem toStr(String b)
    {
        char[] mk=b.toCharArray();
        StringItem h=null;
        StringItem p=h;
        int i=0;
        while (i<mk.length)
        {
            int j=0;
            char[] am=new char[16];
            while ((j<16)&& (i<mk.length))
            {
                am[j]=mk[i];
                i++;
                j++;
            }
            if (h==null) {
                h = new StringItem(j,am, h);
                p=h;
            }
            else p=add(j,am, p);
        }
        return h;
    }
    private StringItem add(int val, char [] m ,StringItem b)
    {
        b.next=new StringItem(val, m,  b.next);
        return b.next;
    }
    private StringItem TheEnd ()
    {
        StringItem h=head;
        StringItem p=h.next;
        while (p!=null)
        {
            if (p.len+h.len<=StringItem.SIZE)
            {
                int i=h.len, j;
                for (j=0; j<p.len; j++)
                {
                    h.symbol[i]=p.symbol[j];
                    i++;
                }
                h.len+=p.len;
                p=p.next;
                h.next=p;
            }
            else
            {
                p=p.next;
                h=h.next;
            }
        }
        return h;
    }
    private char[] copy (int s, int e, char[] a)
    {
        char[] v=new char[StringItem.SIZE];
        int i, j=-1;
        for(i=s; i<e; i++)
        {
            j++;
            v[j]=a[i];
        }
        return v;
    }
}