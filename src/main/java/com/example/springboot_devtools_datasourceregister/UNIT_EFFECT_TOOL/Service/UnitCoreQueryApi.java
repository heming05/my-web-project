package com.example.springboot_devtools_datasourceregister.UNIT_EFFECT_TOOL.Service;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UnitCoreQueryApi {

    public static String[] getResponse(String queryText) {

        String endpoint="http://10.147.89.91:8826";
        String api="/ngd/core/v3/query?channel=&debug=false&nlu=false";

        //为了便于批量执行，采用Map格式的请求负载
        Map<String,Object> jsonBody =new HashMap<>();
        String queryTime=GlobalClass.getCurrentTimeOfStandard();
        jsonBody.put("queryTime",queryTime);
        jsonBody.put("queryText",queryText);
        String uuid=GlobalClass.randomUUID();
        jsonBody.put("sessionId",uuid);
        jsonBody.put("collect",true);
        jsonBody.put("vad",null);
        jsonBody.put("queryMediaType","text");
        jsonBody.put("uniqueId","10");



        String url = GlobalClass.getURLStandard(endpoint, api);
        System.out.println("url是:\n"+url);
        long start_time=System.currentTimeMillis();

        String  Authorization="a5430299-4331-4d26-9cde-42d65c326f84";

        // 发送 POST 请求
        String resultData = sendPostRequest(url, Authorization, jsonBody);
        System.out.println("Result data: " + resultData);

        // 检查是否返回了 JSON 数据
        if (JSONUtil.isJson(resultData)) {
            // 将 JSON 数据解析成 String
            String jsonString = JSONUtil.parseObj(resultData).toString();
            System.out.println("JSON string: " + jsonString);
        } else {
            System.out.println("Invalid response.");
        }



        long end_time=System.currentTimeMillis();
        long consume_time=end_time-start_time;

        System.out.println("resultData is: " + resultData);
        JSONObject jo = JSON.parseObject(resultData); //将json字符串转换成jsonObject对象
        String code=jo.getString("code");
        String msg=jo.getString("msg");

        JSONObject data=jo.getJSONObject("data");
        JSONObject answer=data.getJSONObject("answer");
        String source=data.getString("source");
        String answerText;
        if(answer.containsKey("answerText")) {
            answerText = answer.getString("answerText").trim();
        }else if(Objects.equals(source,"clarify")){
            JSONObject clarify=answer.getJSONObject("clarify");
            JSONObject text=clarify.getJSONObject("text");
            String title=text.getString("title");
            JSONArray list_arr=text.getJSONArray("list");
            StringBuilder list_content= new StringBuilder();
            int loop = Math.min(list_arr.size(), 6);
            for(int i=0;i<loop;i++){
                list_content.append(list_arr.getString(i));
            }
            answerText=title+list_content;
        }else{
            answerText="批跑程序返回:没有对应的答案";
        }



        String[] result =new String[5];
        result[0]= String.valueOf(consume_time);
        result[1]=code.trim();
        result[2]=msg.trim();
        result[3]=GlobalClass.replaceBlank(answerText);
        result[4]=source;
        return result;


    }

    public static String sendPostRequest(String url, String authHeader, Map<String, Object> jsonBody) {
        // 创建一个 POST 请求
        HttpRequest request = HttpRequest.post(url);
        // 添加自定义请求头
        request.header("Authorization", "NGD " + authHeader.trim());
        // 设置请求体为 JSON 格式
        request.body(JSONUtil.toJsonStr(jsonBody));
        // 设置 Content-Type 为 application/json
        request.header("Content-Type", "application/json");
        // 发送请求并获取响应
        return request.execute().body();
    }

}
