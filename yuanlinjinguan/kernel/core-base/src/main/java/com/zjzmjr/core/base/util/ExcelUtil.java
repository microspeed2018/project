package com.zjzmjr.core.base.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * excel文件处理的工具类
 * 
 * @author oms
 * @version $Id: ExcelUtil.java, v 0.1 2017-4-25 下午4:45:47 oms Exp $
 */
public class ExcelUtil {

    /**
     * Excel文件到List
     * 
     * @param fileName
     * @param sheetIndex
     *            // 工作表索引
     * @param skipRows
     *            // 跳过的表头
     * @return
     * @throws Exception
     */
    public static List<Object> readToList(String fileName, int sheetIndex, int skipRows) throws Exception {
        List<Object> ls = new ArrayList<Object>();
        Workbook wb = loadWorkbook(fileName);
        if (null != wb) {
            Sheet sh = wb.getSheetAt(sheetIndex);
            int rows = sh.getPhysicalNumberOfRows();
            for (int i = skipRows; i < rows; i++) {
                Row row = sh.getRow(i);
                if (null == row) {
                    break;
                }
                int cells = row.getPhysicalNumberOfCells();
                if (cells == 0) {
                    continue;
                }
                List<String> r = new ArrayList<String>(cells);
                for (int c = 0; c < cells; c++) {
                    // if (c == 0 || c == 4) {
                    // try {
                    // r.add(String.format("%.0f",
                    // row.getCell(c).getNumericCellValue()));
                    // } catch (Exception e) {
                    // throw new
                    // Exception("出现该错误请依次检查：<br>1、【序号】或【端口号】请使用数字<br>2、检查《Webservice信息》是否是第二个sheet页");
                    // }
                    // } else {
                    r.add(getCellValue(row.getCell(c)));
                    // }
                }
                ls.add(r);
            }
        }

        return ls;
    }

    /**
     * Excel文件到List
     * 
     * @param in
     * @param sheetIndex
     * @param skipRows
     * @return
     * @throws Exception
     */
    public static List<Object> readToList(InputStream in, int sheetIndex, int skipRows) throws Exception {
        List<Object> ls = new ArrayList<Object>();
        Workbook wb = new HSSFWorkbook(in);
        if (null != wb) {
            Sheet sh = wb.getSheetAt(sheetIndex);
            int rows = sh.getPhysicalNumberOfRows();
            for (int i = skipRows; i < rows; i++) {
                Row row = sh.getRow(i);
                if (null == row) {
                    break;
                }
                int cells = row.getPhysicalNumberOfCells();
                if (cells == 0) {
                    continue;
                }
                List<String> r = new ArrayList<String>(cells);
                for (int c = 0; c < cells; c++) {
                    // if (c == 0 || c == 4) {
                    // try {
                    // r.add(String.format("%.0f",
                    // row.getCell(c).getNumericCellValue()));
                    // } catch (Exception e) {
                    // throw new
                    // Exception("出现该错误请依次检查：<br>1、【序号】或【端口号】请使用数字<br>2、检查《Webservice信息》是否是第二个sheet页");
                    // }
                    // } else {
                    r.add(getCellValue(row.getCell(c)));
                    // }
                }
                ls.add(r);
            }
        }

        return ls;
    }
    /**
     * 
     * @param in
     * @param fileNamePostFix
     * @param sheetIndex
     * @param skipRows
     * @return
     * @throws Exception
     */
    public static List<Object> readToList(InputStream in, String fileNamePostFix, int sheetIndex, int skipRows) throws Exception {
        List<Object> ls = new ArrayList<Object>();
        Workbook wb=null;
        if(".xlsx".equals(fileNamePostFix)){
            wb = new XSSFWorkbook(in);
        }else{
            wb = new HSSFWorkbook(in);
        }
        if (null != wb) {
            Sheet sh = wb.getSheetAt(sheetIndex);
            int rows = sh.getPhysicalNumberOfRows();
            for (int i = skipRows; i < rows; i++) {
                Row row = sh.getRow(i);
                if (null == row) {
                    break;
                }
                int cells = row.getPhysicalNumberOfCells();
                if (cells == 0) {
                    continue;
                }
                //如果表格里有无数据的，但是带黑框跳过
                String id=getCellValue(row.getCell(0));
                if("".equals(id)){
                    break;
                }
                List<String> r = new ArrayList<String>(cells);
                for (int c = 0; c < cells; c++) {
                    r.add(getCellValue(row.getCell(c)));
                }
                ls.add(r);
            }
        }

        return ls;
    }
    /**
     * list转数组
     * 
     * @param obj
     * @return
     */
    public static Object[][] ObjectToArray(List<Object> obj) {
        Object[][] object = new Object[obj.size()][obj.get(0).toString().split(",").length];
        for (int i = 0; i < obj.size(); i++) {
            String[] data = obj.get(i).toString().split(",");
            for (int j = 0; j < data.length; j++) {
                if (j == 0) {
                    object[i][j] = data[j].substring(1);
                } else if (j == data.length - 1) {
                    object[i][j] = data[j].substring(0, data[j].toString().length() - 1);
                } else {
                    object[i][j] = data[j];
                }
            }
        }
        return object;
    }

    /**
     * 判断单元格类型及转换
     * 
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        if(Util.isNull(cell)){
            return "";
        }
        String cellValue = "";
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_STRING:
            cellValue = cell.getRichStringCellValue().getString().trim();
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
            short formatV = cell.getCellStyle().getDataFormat();
            if(HSSFDateUtil.isCellDateFormatted(cell)){
                SimpleDateFormat sdf = null;
                if(cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")){
                    sdf = new SimpleDateFormat("HH:mm");
                }else{
                    sdf = new SimpleDateFormat("yyyy-MM-dd"); 
                }
                Date date = cell.getDateCellValue();  
                cellValue = sdf.format(date); 
            }else if(formatV == 14 || formatV == 31 || formatV == 57 || formatV == 58  
                    || (176<=formatV && formatV<=178) || (182<=formatV && formatV<=196) 
                    || (210<=formatV && formatV<=213) || (208==formatV )){
                // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
                double value = cell.getNumericCellValue();  
                Date date = org.apache.poi.ss.usermodel.DateUtil  
                        .getJavaDate(value);  
                cellValue = sdf.format(date); 
            }else if (formatV == 20 || formatV == 32 || formatV==183 || (200<=formatV && formatV<=209) ) { // 时间
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                double value = cell.getNumericCellValue();  
                Date date = org.apache.poi.ss.usermodel.DateUtil  
                        .getJavaDate(value);  
                cellValue = sdf.format(date); 
            }else{
                double value = cell.getNumericCellValue();  
                CellStyle style = cell.getCellStyle();  
                DecimalFormat format = new DecimalFormat("##.####");  
                String temp = style.getDataFormatString();  
                // 单元格设置成常规  
                if (temp.equals("General")) {  
                    format.applyPattern("#");  
                }  
                cellValue = format.format(value);  
            }
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
            break;
        case HSSFCell.CELL_TYPE_FORMULA:
            cellValue = cell.getCellFormula();
            break;
        default:
            cellValue = "";
        }
        return cellValue;
    }

    /**
     * 读取Excel文件，支持2000与2007格式
     * 
     * @param fileName
     * @return
     * @throws Exception
     */
    public static Workbook loadWorkbook(String fileName) throws Exception {
        if (null == fileName)
            return null;

        Workbook wb = null;
        if (fileName.toLowerCase().endsWith(".xls")) {
            try {
                InputStream in = new FileInputStream(fileName);
                POIFSFileSystem fs = new POIFSFileSystem(in);
                wb = new HSSFWorkbook(fs);
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (fileName.toLowerCase().endsWith(".xlsx")) {
            try {
                InputStream in = new FileInputStream(fileName);
                wb = new XSSFWorkbook(in);
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new Exception("不是一个有效的Excel文件");
        }
        return wb;
    }

    public static void writeToExcel(Workbook wb, OutputStream out) {
        try {
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static enum ExcelType {
        xls, xlsx;
    }

    public static Workbook listToWorkbook(List<?> rows, ExcelType type) {
        Workbook wb = null;
        if (ExcelType.xls.equals(type)) {
            wb = new HSSFWorkbook();
        } else if (ExcelType.xlsx.equals(type)) {
            wb = new XSSFWorkbook();
        } else {
            return null;
        }

        Sheet sh = wb.createSheet();
        if (null != rows) {
            for (int i = 0; i < rows.size(); i++) {
                Object obj = rows.get(i);
                Row row = sh.createRow(i);

                if (obj instanceof Collection) {
                    Collection<?> r = (Collection<?>) obj;
                    Iterator<?> it = r.iterator();
                    int j = 0;
                    while (it.hasNext()) {
                        Cell cell = row.createCell(j++);
                        cell.setCellValue(String.valueOf(it.next()));
                    }
                } else if (obj instanceof Object[]) {
                    Object[] r = (Object[]) obj;
                    for (int j = 0; j < r.length; j++) {
                        Cell cell = row.createCell(j);
                        cell.setCellValue(String.valueOf(r[j]));
                    }
                } else {
                    Cell cell = row.createCell(0);
                    cell.setCellValue(String.valueOf(obj));
                }
            }
        }

        return wb;
    }

    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     * 
     * @param title
     *            表格标题名
     * @param headers
     *            表格属性列名数组
     * @param dataset
     *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out
     *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern
     *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     * @throws Exception
     */
    @SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
    public static <T> HSSFWorkbook exportExcel(String title, String[] headers, String[] rows, List<T> list, String pattern) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为20个字节
        sheet.setDefaultColumnWidth((short) 25);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();

        //设置背景色
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
        style.setFillBackgroundColor(HSSFColor.PALE_BLUE.index); 
        
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();

        font.setFontHeightInPoints((short) 12);

        style.setFont(font);

        HSSFCellStyle style2 = workbook.createCellStyle();

        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        style2.setFont(font);
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        // 遍历集合数据，产生数据行
        Iterator<T> it = list.iterator();
        int index = 0;
        HSSFFont font3 = workbook.createFont();
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            // Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 0; i < rows.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style2);
                String fieldName = rows[i];
                String[] fieldNameArray = {};
                String getMethodName ="";
                String getMethodNameS = "";
                if(fieldName.contains(".")){
                    fieldNameArray = fieldName.split("\\.");
                    getMethodName = "get" + fieldNameArray[0].substring(0, 1).toUpperCase() + fieldNameArray[0].substring(1);
                    getMethodNameS = "get" + fieldNameArray[1].substring(0, 1).toUpperCase() + fieldNameArray[1].substring(1);
                }else{
                    getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                }
                try {
                    Class tCls = t.getClass();
                    Method getMethod;
                    Object value;
                    if(fieldNameArray.length > 0){
                        Method getMethodF = tCls.getMethod(getMethodName, new Class[] {});
                        T valueF = (T) getMethodF.invoke(t, new Object[] {});
                        if(Util.isNull(valueF)){
                            value = null; 
                        }else{
                            Class sCls = valueF.getClass();
                            getMethod = sCls.getMethod(getMethodNameS, new Class[] {});
                            value = getMethod.invoke(valueF, new Object[] {});
                        }
                    }else{
                        getMethod = tCls.getMethod(getMethodName, new Class[] {});
                        value = getMethod.invoke(t, new Object[] {});
                    }
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    // if (value instanceof Integer) {
                    // int intValue = (Integer) value;
                    // cell.setCellValue(intValue);
                    // } else if (value instanceof Float) {
                    // float fValue = (Float) value;
                    // textValue = new HSSFRichTextString(
                    // String.valueOf(fValue));
                    // cell.setCellValue(textValue);
                    // } else if (value instanceof Double) {
                    // double dValue = (Double) value;
                    // textValue = new HSSFRichTextString(
                    // String.valueOf(dValue));
                    // cell.setCellValue(textValue);
                    // } else if (value instanceof Long) {
                    // long longValue = (Long) value;
                    // cell.setCellValue(longValue);
                    // }
                    if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                        HSSFCellStyle cellStyle = workbook.createCellStyle();
                        HSSFDataFormat format = workbook.createDataFormat();
                        cellStyle.setDataFormat(format.getFormat(pattern));
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue(textValue);
                        continue;
                    } else if (value instanceof BigDecimal){
                        textValue = NumberFormat.getNumberInstance().format(value); 
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        if (!Util.isNull(value)) {
                            textValue = value.toString();
                        }
                    }
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
            }
        }

        return workbook;

    }

    public static void main(String[] args) {
        try {
            String filepath = "F:test.xls";
            // 对读取Excel表格内容测试
            List<Object> map = readToList(filepath, 0, 0);
            System.out.println("获得Excel表格的内容:");
            for (int i = 0; i < map.size(); i++) {
                String[] data = map.get(i).toString().split(",");
                for (int j = 0; j < data.length; j++) {
                    System.out.println(data[j]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("未找到指定路径的文件!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
