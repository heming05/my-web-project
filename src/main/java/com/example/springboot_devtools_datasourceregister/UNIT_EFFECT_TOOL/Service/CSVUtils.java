package com.example.springboot_devtools_datasourceregister.UNIT_EFFECT_TOOL.Service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**

 * 文件操作

 */

@Service
public class CSVUtils {

    /**

     * 功能说明：获取UTF-8编码文本文件开头的BOM签名。

     * BOM(Byte Order Mark)，是UTF编码方案里用于标识编码的标准标记。例：接收者收到以EF BB BF开头的字节流，就知道是UTF-8编码。

     * @return UTF-8编码文本文件开头的BOM签名

     */

    public static String getBOM() {

        byte b[] = {(byte)0xEF, (byte)0xBB, (byte)0xBF};

        return new String(b);

    }

    public static File createCSVFile(List exportData, LinkedHashMap header, String outPutPath, String fileName) {

        File csvFile = null;

        BufferedWriter csvFileOutputStream = null;

        try {

            File file = new File(outPutPath);

            if (!file.exists()) {

                file.mkdirs();

            }

            //定义文件名格式并创建
            csvFile =new File(outPutPath+fileName+".csv");

            file.createNewFile();

            //UTF-8使正确读取分隔符","

            //如果生产文件乱码，windows下用gbk，linux用UTF-8

            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GBK"), 1024);

            //写入前段字节流，防止乱码
            csvFileOutputStream.write(getBOM());

            // 写入文件头部

            for (Iterator propertyIterator = header.entrySet().iterator(); propertyIterator.hasNext();) {

                java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();

                csvFileOutputStream.write((String) propertyEntry.getValue() != null ? (String) propertyEntry.getValue() : "" );

                if (propertyIterator.hasNext()) {

                    csvFileOutputStream.write(",");

                }

            }

            csvFileOutputStream.newLine();

            csvFileOutputStream.flush();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            try {

                csvFileOutputStream.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

        return csvFile;

    }



}