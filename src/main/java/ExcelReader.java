import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @Description:
 * @Author: yedong
 * @Date: 2020/2/20 22:42
 * @Modified by:
 */

public class ExcelReader {
    private static Logger logger = Logger.getLogger(ExcelReader.class.getName()); // 日志打印类

    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";

    /**
     * 根据文件后缀名类型获取对应的工作簿对象
     *
     * @param inputStream 读取文件的输入流
     * @param fileType    文件后缀名类型（xls或xlsx）
     * @return 包含文件数据的工作簿对象
     * @throws IOException
     */
    public static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
        Workbook workbook = null;
        if (fileType.equalsIgnoreCase(XLS)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileType.equalsIgnoreCase(XLSX)) {
            workbook = new XSSFWorkbook(inputStream);
        }
        return workbook;
    }

    /**
     * 读取Excel文件内容
     *
     * @param fileName 要读取的Excel文件所在路径
     * @return 读取结果列表，读取失败时返回null
     */
    public static void readExcel(String fileName) {

        Workbook workbook = null;
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;


        // 获取Excel后缀名
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        // 获取Excel文件
        File excelFile = new File(fileName);
        try {

            if (!excelFile.exists()) {
                logger.warning("指定的Excel文件不存在！");
            }

            // 获取Excel工作簿
            inputStream = new FileInputStream(excelFile);

            workbook = getWorkbook(inputStream, fileType);

            // 读取excel中的数据
            parseExcel(workbook);

            //修改后保存
            outputStream = new FileOutputStream(excelFile);
            workbook.write(outputStream);
        } catch (Exception e) {
//            logger.warning("解析Excel失败，文件名：" + fileName + " 错误信息：" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {

                if (null != inputStream) {
                    inputStream.close();
                }

                if (null != outputStream) {
                    outputStream.close();
                }

                if (null != workbook) {
                    workbook.close();
                }

            } catch (Exception e) {
                logger.warning("关闭数据流出错！错误信息：" + e.getMessage());
            }
        }
    }

    /**
     * 解析Excel数据
     *
     * @param workbook Excel工作簿对象
     * @return 解析结果
     */
    private static void parseExcel(Workbook workbook) {
        // 解析sheet
        for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
            Sheet sheet = workbook.getSheetAt(sheetNum);

            // 校验sheet是否合法
            if (sheet == null) {
                continue;
            }

            // 获取第一行数据
            int firstRowNum = sheet.getFirstRowNum();
            Row firstRow = sheet.getRow(firstRowNum);
            if (null == firstRow) {
                logger.warning("解析Excel失败，在第一行没有读取到任何数据！");
            }

            // 解析每一行的数据，构造数据对象
            int rowStart = firstRowNum + 1;
            int rowEnd = sheet.getPhysicalNumberOfRows();
            for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                Row row = sheet.getRow(rowNum);

                if (null == row) {
                    continue;
                }

                trafficWay(row);
            }
        }

    }

    /**
     * 将单元格内容转换为字符串
     *
     * @param cell
     * @return
     */
    private static String convertCellValueToString(Cell cell) {
        if (cell == null) {
            return null;
        }
        String returnValue = null;
        switch (cell.getCellType()) {
            case NUMERIC:   //数字
                Double doubleValue = cell.getNumericCellValue();

                // 格式化科学计数法，取一位整数
                DecimalFormat df = new DecimalFormat("0");
                returnValue = df.format(doubleValue);
                break;
            case STRING:    //字符串
                returnValue = cell.getStringCellValue();
                break;
            case BOOLEAN:   //布尔
                Boolean booleanValue = cell.getBooleanCellValue();
                returnValue = booleanValue.toString();
                break;
            case BLANK:     // 空值
                break;
            case FORMULA:   // 公式
                returnValue = cell.getCellFormula();
                break;
            case ERROR:     // 故障
                break;
            default:
                break;
        }
        return returnValue;
    }

    /**
     * @param row 行数据
     * @return 解析后的行数据对象，行数据错误时返回null
     */
    private static void trafficWay(Row row) {


        String trafficWay = convertCellValueToString(row.getCell(11));
        String carNum = convertCellValueToString(row.getCell(10));


        if (!Objects.isNull(trafficWay)) {
//            System.out.println(trafficWay);
            String clean = Utils.clean(trafficWay);


            String trafficWayAfter = Utils.getTrafficWay(clean);
            String carNumAfter = Utils.getCarNum(clean);
//            System.out.println(clean);
            row.getCell(11).setCellValue(carNumAfter);
            if (StringUtils.isBlank(carNum)) {
                row.getCell(10).setCellValue(trafficWayAfter);
            }

            System.out.print(convertCellValueToString(row.getCell(10)));
            System.out.print("--");
            System.out.println(convertCellValueToString(row.getCell(11)));

        }

//        System.out.println(convertCellValueToString(row.getCell(1)));
//        System.out.println(convertCellValueToString(row.getCell(2)));
//        System.out.println(convertCellValueToString(row.getCell(3)));
//        System.out.println(convertCellValueToString(row.getCell(4)));
//        System.out.println(convertCellValueToString(row.getCell(5)));


    }
}
