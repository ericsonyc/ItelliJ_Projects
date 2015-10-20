package CrazyJava;

import java.util.LinkedList;

/**
 * Created by ericson on 2015/10/19 0019.
 */
public class LinkedListTest {
    public static void main(String[] args){
        LinkedList books=new LinkedList();
        books.offer("疯狂Java讲义");
        books.push("轻量级Java EE企业应用实战");
        books.offerFirst("疯狂Android讲义");
        for(int i=0;i<books.size();i++){
            System.out.println(books.get(i));
        }
        System.out.println(books.peekFirst());
        System.out.println(books.peekLast());
        System.out.println(books.pop());
        System.out.println(books);
        System.out.println(books.pollLast());
        System.out.println(books);
    }
}
