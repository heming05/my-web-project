package com.example.springboot_devtools_datasourceregister.UNIT_EFFECT_TOOL.Service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UNIT_Query_Verify {

    public static void exec_easy(String fileNameOfQuery,String fileNameOfStandardAnswer,String fileName,int start,int end) throws IOException {

        //首先创建一个CSV文件，并将首行写入
        Map<String,Object> header =new HashMap<>();

        //设置列名

        header.put("1", "标准问query");

        header.put("2", "标准答案");

        header.put("3", "返回答案");

        header.put("4","返回答案来源");

        header.put("5", "返回状态码");


        //CSV文件输出的路径
        String path = "C:\\Users\\heming05\\Desktop\\data\\";
        //文件名=生产的文件名称+时间戳

        String fileNamePath="C:\\Users\\heming05\\Desktop\\data\\"+fileName;
        CSV_Utils.deleteFiles(fileNamePath);
        CSV_Utils.createCSVFile(path, fileName);

        List exportData = new ArrayList();
        exportData.add(header);


        for(int i=start;i<=end;i++){
            //读取第i行faq提问

            String queryText_i=GlobalClass.readFileContent(fileNameOfQuery,i);
            String[] logContent = UnitCoreQueryApi.getResponse(queryText_i);

            String code_i = logContent[1];
            String msg = logContent[2];
            String answerText_i=logContent[3];
            String source_i=logContent[4];


            String StandardAnswerText_i=GlobalClass.readFileContent(fileNameOfStandardAnswer,i);

            Map<String,Object> rows = new HashMap<>();

            rows.put("1", queryText_i);

            rows.put("2", StandardAnswerText_i);

            rows.put("3", answerText_i);

            rows.put("4", source_i);

            rows.put("5", code_i);

            exportData.add(rows);

        }

        //将内容写入到csv文件中
        CSV_Utils.addRecordsToCSVFile(exportData,header, path, fileName);

    }
}
