package com.example.springboot_devtools_datasourceregister.UNIT_EFFECT_TOOL.Service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GlobalClass {

    //this method is read file content
    public static String readFileContent(String fileName) throws IOException {

        String encoding="utf-8";
        String lineTxt;
        StringBuilder AlartTxt=new StringBuilder();
        File file=new File(fileName);
        if(file.isFile() && file.exists()){ //判断文件是否存在
            InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);

            while((lineTxt = bufferedReader.readLine()) != null){
                lineTxt+='\n';
                AlartTxt.append(lineTxt);
            }
            read.close();
        }else{
            System.out.println("找不到指定的文件");
        }

        return AlartTxt.toString();

    }



    //this method is read file content of set row
    public static String readFileContent(String fileName,int row) throws IOException {

        String encoding = "utf-8";
        String lineTxt;
        File file = new File(fileName);

        String lineContent = "Initialed";
        if (file.isFile() && file.exists()) { //判断文件是否存在
            InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file), encoding);//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);

            int line = 1;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                if (line == row) {
                    //lineTxt += '\n';
                    lineContent = lineTxt;
                    System.out.println("第"+row+"行的文本内容是:\n"+lineTxt);
                }

                line += 1;
            }
            read.close();
        } else {
            System.out.println("找不到指定的文件");
        }

        return lineContent.trim();

    }


    //this method is read file content of set row
    public static String readFileContent(String fileName,int start,int end) throws IOException {

        String encoding = "utf-8";
        String lineTxt;
        File file = new File(fileName);

        String lineContent = "Initialed";
        if (file.isFile() && file.exists()) { //判断文件是否存在
            InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file), encoding);//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);

            int line = 1;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                if (line >= start && line <=end) {
                    //lineTxt += '\n';
                    lineContent = lineTxt;
                    //System.out.println("第"+line+"行的文本内容是:\n"+lineTxt);
                }

                line += 1;
            }
            read.close();
        } else {
            System.out.println("找不到指定的文件");
        }

        return lineContent.trim();

    }

    public static int getStrLines(String str){

        String[] lines = str.split("\r\n|\r|\n");

        return lines.length;

    }

    public static String replaceBlank(String content) {
        String dest = "";
        if (content!=null) {
            //  过滤请求报文中的制表符及换行符等无关元素
            //Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Pattern p = Pattern.compile("[\t\r\n]");
            Matcher m = p.matcher(content);
            dest = m.replaceAll("");
        }
        return dest;
    }


    public static String getURLStandard(String endpoint,String api){ return endpoint+api;}

    public static String getCurrentTimeOfStandard(){

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date(System.currentTimeMillis());

        return formatter.format(date);
    }




    //随机生成UUID
    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }


    //拿到文本分割的第loop轮的开始位置
    public static int getStartPos(int total,int parallel,int row){
        int loop=total/parallel+1;
        int start=0;
        if(row<=loop && row>=0){
            start=(row-1)*parallel+1;
        }else
        {
            System.out.println("超出循环的范围,取值无效");
        }
        return start;
    }


    //拿到文本分割的第loop轮的结束位置
    public static int getEndPos(int total,int parallel,int row){
        int loop=total/parallel+1;
        int mod=total%parallel;
        int end=0;
        if(row<loop && row>=0){
            end=row*parallel;
        }else if(row==loop && row>=0){
            end=(row-1)*parallel+mod;
        }else{
            System.out.println("超出循环的范围,取值无效");
        }
        return end;

    }


    //向上取整,比如101/20=5.05,向上取整为6

    public static int getMathCeil(int divisor ,int dividend) {

        DecimalFormat df=new DecimalFormat("0.0000");//设置保留位数

        String str=df.format((double) divisor/dividend);

        // System.out.println(str);

        double d=Double.parseDouble(str);

        // System.out.println(d);

        return (int) Math.ceil(d);


    }










}



