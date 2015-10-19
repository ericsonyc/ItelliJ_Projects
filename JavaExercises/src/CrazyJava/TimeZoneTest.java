package CrazyJava;

import java.util.Arrays;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ericson on 2015/10/15 0015.
 */
public class TimeZoneTest {
    public static void main(String[] args) {
        String[] ids = TimeZone.getAvailableIDs();
        System.out.println(Arrays.toString(ids));
        TimeZone my = TimeZone.getDefault();
        System.out.println(my.getID());
        System.out.println(my.getDisplayName());
        System.out.println(TimeZone.getTimeZone("CNT").getDisplayName());
        Pattern p = Pattern.compile("a*b");
        Matcher m = p.matcher("aaaab");
        boolean b = m.matches();
        System.out.println(b);
        String[] mails = {
                "kongyeeku@163.com",
                "kongyeeku@gmail.com",
                "ligang@crazyit.org",
                "wawa@abc.xx"
        };
        String mailRegEx="\\w{3,20}@\\w+\\.{com|org|cn|net|gov}";
        Pattern mailPattern=Pattern.compile(mailRegEx);
        Matcher matcher=null;
        for(String mail:mails){
            if(matcher==null){
                matcher=mailPattern.matcher(mail);
            }else{
                matcher.reset(mail);
            }
            String result=mail+(matcher.matches()?"是":"不是");
            System.out.println(result);
        }
    }
}
