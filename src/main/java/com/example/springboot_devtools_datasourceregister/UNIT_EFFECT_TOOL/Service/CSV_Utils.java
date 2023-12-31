package com.example.springboot_devtools_datasourceregister.UNIT_EFFECT_TOOL.Service;


import com.alibaba.druid.util.StringUtils;

import java.io.*;
import java.util.*;

import org.springframework.stereotype.Service;

/**

 * 文件操作

 */

@Service
public class CSV_Utils {

    /**

     * 功能说明：获取UTF-8编码文本文件开头的BOM签名。

     * BOM(Byte Order Mark)，是UTF编码方案里用于标识编码的标准标记。例：接收者收到以EF BB BF开头的字节流，就知道是UTF-8编码。

     * @return UTF-8编码文本文件开头的BOM签名

     */

    public static String getBOM() {

        byte b[] = {(byte)0xEF, (byte)0xBB, (byte)0xBF};

        return new String(b);

    }


    public static File createCSVFile(String outPutPath, String fileName) throws IOException {

        File csvFile = new File(outPutPath+fileName+".csv");
        csvFile.createNewFile();
        return csvFile;

    }




    public static void addRecordsToCSVFile(List exportData, Map<String,Object> header, String outPutPath, String fileName) throws IOException {

        File csvFile = new File(outPutPath+fileName+".csv");
        BufferedWriter csvFileOutputStream = null;


        //UTF-8使正确读取分隔符",",如果生产文件乱码，windows下用gbk，linux用UTF-8

        csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GBK"), 1024);

        //写入前段字节流，防止乱码
        csvFileOutputStream.write(getBOM());

        //csvFileOutputStream.newLine();
        // 写入文件内容
        for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {

            Object row = (Object) iterator.next();

            for (Iterator propertyIterator = header.entrySet().iterator(); propertyIterator

                    .hasNext();) {

                java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator

                        .next();

                String str=row!=null?((String)((Map)row).get( propertyEntry.getKey())):"";

                if(StringUtils.isEmpty(str)){

                    str="";

                }else{

                    str=str.replaceAll("\"","\"\"");

                    if(str.indexOf(",")>=0){

                        str="\""+str+"\"";

                    }

                }

                csvFileOutputStream.write(str);

                if (propertyIterator.hasNext()) {

                    csvFileOutputStream.write(",");

                }

            }

            if (iterator.hasNext()) {

                csvFileOutputStream.newLine();

            }

        }

        csvFileOutputStream.flush();
    }




    /**

     * 删除该目录filePath下的所有文件

     * @param filePath

     * 文件目录路径

     */

    public static void deleteFiles(String filePath) {

        File file = new File(filePath);

        if (file.exists()) {
            file.delete();
        }

    }

    /**

     * 删除单个文件

     * @param filePath

     * 文件目录路径

     * @param fileName

     * 文件名称

     */

    public static void deleteFile(String filePath, String fileName) {

        File file = new File(filePath);

        if (file.exists()) {

            File[] files = file.listFiles();

            for (int i = 0; i < files.length; i++) {

                if (files[i].isFile()) {

                    if (files[i].getName().equals(fileName)) {

                        files[i].delete();

                        return;

                    }

                }

            }

        }

    }

    /**

     * 测试数据

     * @param args

     */

    @SuppressWarnings({ "rawtypes", "unchecked" })

    public static void main(String[] args) {

        List exportData = new ArrayList();

        Map row1 = new LinkedHashMap();

        row1.put("1", "11");

        row1.put("2", "12");

        row1.put("3", "13");

        row1.put("4", "14");

        exportData.add(row1);

        row1 = new LinkedHashMap();

        row1.put("1", "21");

        row1.put("2", "22");

        row1.put("3", "23");

        row1.put("4", "24");

        exportData.add(row1);

        LinkedHashMap map = new LinkedHashMap();

//设置列名

        map.put("1", "第一列名称");

        map.put("2", "第二列名称");

        map.put("3", "第三列名称");

        map.put("4", "第四列名称");

//这个文件上传到路径，可以配置在数据库从数据库读取，这样方便一些！

        String path = "C:\\Users\\heming05\\Desktop\\data\\";

//文件名=生产的文件名称+时间戳

        String fileName = "文件导出";

        File file = CSVUtils.createCSVFile(exportData, map, path, fileName);

        String fileName2 = file.getName();

        System.out.println("文件名称：" + fileName2);

    }

}