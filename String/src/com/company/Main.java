package com.company;
import String.*;
public class Main {
    public static void main(String[] args) {
        String1 j=new String1("DDDaa addddd adadad");
        System.out.println(j.charAt(6));
        System.out.println(j.toString());
        j.insert("hghhuijj kjiji", 7);
        System.out.println(j.toString());
        j.setcharAt(18, 'k');
        System.out.println(j.toString());
        String1 b=j.substring(3, 20);
        System.out.println(b.toString());
    }
}
