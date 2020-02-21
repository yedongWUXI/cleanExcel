import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: yedong
 * @Date: 2020/2/21 0:46
 * @Modified by:
 */

public class Common {
    @org.junit.Test
    public void test(){

        String s = "(（   ";
//        System.out.println(s.indexOf("("));
//        String substring = s.substring(s.indexOf("("),s.indexOf("(")+1);
//        System.out.println(substring);

        String replace = s.replace("(", "");
        System.out.println(replace);



        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(s);
        String s1 = m.replaceAll("");
        System.out.println(s1);
    }
}
