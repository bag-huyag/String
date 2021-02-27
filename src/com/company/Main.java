
//import java.util.LinkedList;

import Method.*;

public class Main {

    public static void main(String[] args) {
        String start = "WinRAR это архиватор RAR для Windows — мощный инструмент для архивирования и управления архивами. Помимо Windows, существуют версии RAR для других операционных систем — Linux, FreeBSD, macOS, Android.";
        List b = new List (start);
//        System.out.print("Исходный \n");

//        b.print();
//
//            System.out.println("charAt: "+b.charAt(13));
//            System.out.println( );
//            System.out.print("setCharAt: \n");
//            b.setCharAt(1,'M');
//            b.print();
//
//            System.out.print("substring:\n");
//            List sub=b.subString(10, 48);
//            sub.print();
//
//            System.out.println("Insert: " );
//            b.insert("hey", 12);
//            b.print();
//
//        System.out.println(" len: "+b.len());
//        System.out.println();


        System.out.println("Apened String: " );
        b.append("bebebebebebeqwertyuiopasdfghjklzxcvbnm");
        b.print();

       System.out.println("Apened Symbol: " );
        b.append('a');
        b.print();

//        System.out.println("ToString: "+b.tostring());


    }
}
