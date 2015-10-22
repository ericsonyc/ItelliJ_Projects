package CrazyJava;

import java.io.FilterOutputStream;
import java.net.Socket;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aiqiyi.LCS;

/**
 * Created by ericson on 2015/10/21 0021.
 */

public class Fanxin {
    public static void main(String[] args) {
        String str="Betty Butter bought better butter better butter";
        Pattern p1=Pattern.compile("butter.*?");
        Matcher m1=p1.matcher(str);
        str=m1.replaceAll("Cream");
        System.out.println(str);
        HashMap test=new HashMap();
        Set te=test.keySet();
        SortedSet tests=new TreeSet(te);
    }
}
