import java.util.List;

/**
 * @Description:
 * @Author: yedong
 * @Date: 2020/2/20 22:38
 * @Modified by:
 */

public class Test {
    public static void main(String[] args) {

        //启动方式  java jar -Dpath=xxx  xxx.jar

        String path = System.getProperty("path");

        System.out.println(path);

        ExcelReader.readExcel(path);


//        // 设定Excel文件所在路径
//        String excelFileName = "C:\\Users\\yedong\\Desktop\\清理文档\\新一社区合并.xlsx";
//        // 读取Excel文件内容
//        ExcelReader.readExcel(excelFileName);

    }
}
