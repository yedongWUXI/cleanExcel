import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: yedong
 * @Date: 2020/2/21 1:02
 * @Modified by:
 */

public class Utils {
    static List<String> area = new ArrayList<>();

    static {

        area.add("京");
        area.add("津");
        area.add("沪");
        area.add("渝");
        area.add("冀");
        area.add("豫");
        area.add("云");
        area.add("辽");
        area.add("黑");
        area.add("湘");
        area.add("皖");
        area.add("鲁");
        area.add("新");
        area.add("苏");
        area.add("浙");
        area.add("赣");
        area.add("鄂");
        area.add("桂");
        area.add("甘");
        area.add("晋");
        area.add("蒙");
        area.add("陕");
        area.add("吉");
        area.add("闽");
        area.add("贵");
        area.add("粤");
        area.add("青");
        area.add("藏");
        area.add("川");
        area.add("宁");
        area.add("琼");
        area.add("使");
        area.add("领");
    }


    /**
     * 去除特殊字符
     *
     * @param s
     * @return
     */
    public static String grep(String s) {
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(s);
        return m.replaceAll("");
    }


    public static String clean(String s) {

        if (s.contains("(")) {
            s = s.replace("(", "");
        }


        if (s.contains(")")) {
            s = s.replace(")", "");
        }


        if (s.contains("（")) {
            s = s.replace("（", "");
        }


        if (s.contains("）")) {
            s = s.replace("）", "");
        }


        if (s.contains("\"")) {
            s = s.replace("\"", "");
        }


        if (s.contains("“")) {
            s = s.replace("“", "");
        }


        if (s.contains("”")) {
            s = s.replace("”", "");
        }


        return s;
    }


    /**
     * 获取交通方式
     *
     * @param s
     * @return
     */
    static String getTrafficWay(String s) {
        for (String v : area) {
            if (s.contains(v)) {
                s = s.substring(0, s.indexOf(v));
            }
        }
        return s;

    }


    /**
     * 获取车牌号
     *
     * @param s
     * @return
     */
    static String getCarNum(String s) {
        for (String v : area) {
            if (s.contains(v)) {
                s = s.substring(s.indexOf(v), s.length());
            }
        }
        return s;

    }


    public static void main(String[] args) {
        String s = "苏EDC670";

        String method = "";
        String carNum = "";

        System.out.println(clean(s));

        for (String v : area) {
            if (s.contains(v)) {
                method = s.substring(0, s.indexOf(v));
                carNum = s.substring(s.indexOf(v), s.length());
            }
        }
        System.out.println(method);
        System.out.println(carNum);
    }
}
