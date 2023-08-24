package com.example.springboot_devtools_datasourceregister.UNIT_EFFECT_TOOL;

import com.example.springboot_devtools_datasourceregister.UNIT_EFFECT_TOOL.Service.GlobalClass;
import com.example.springboot_devtools_datasourceregister.UNIT_EFFECT_TOOL.Service.UNIT_Query_Verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static com.example.springboot_devtools_datasourceregister.MyHandler.sendLog;


@RestController
@RequestMapping("/unit_effect")
public class UNIT_Query_Verify_New_Controller {

    @Autowired
    private UnitDatabaseService unitDatabaseService;

    @PostMapping("/startUnitVerify")
    public void startVerify(@RequestBody Map<String, Object> jsonBody) throws Exception {
        String taskName = (String) jsonBody.get("taskName");

        //批次号传输
        long currentTimeMillis=System.currentTimeMillis();

        long runBatchNo=GlobalClass.getBatchNo(currentTimeMillis);


        // 通过JPA, JDBC, MyBatis等方式从数据库中查询数据
        List<String> queryData = unitDatabaseService.getQueryData(taskName.trim());
        List<String> answerData = unitDatabaseService.getAnswerData(taskName.trim());

        int total = queryData.size();
        // 调用新方法获取parallel的值
        //int parallel =5;
        int parallel = unitDatabaseService.getParallel(taskName.trim());
        System.out.println("Sending log from startVerify: " + taskName);
        long startTime=System.currentTimeMillis();
        sendLog(taskName, "\n############################################################");
        sendLog(taskName, "【本次多线程批跑测试，开始执行...】");
        sendLog(taskName, "【执行开始时间是:"+GlobalClass.getStandardTime(startTime)+"】");
        sendLog(taskName, "【本次测试的并发数是:"+parallel+"】");
        sendLog(taskName, "##############################################################");

        int length = GlobalClass.getMathCeil(total, parallel);


        CountDownLatch cdl = new CountDownLatch(parallel);
        CountDownLatch latch = new CountDownLatch(parallel); // 创建一个新的CountDownLatch，用于等待所有子线程完成


        for (int i = 1; i <= parallel; i++) {
            int start_i = GlobalClass.getStartPos(total, length, i);
            //System.out.println("start_i is:"+start_i);
            sendLog(taskName, "第"+i+"次并发，数据分片的开始位置是："+start_i);
            int end_i = GlobalClass.getEndPos(total, length, i);
            //System.out.println("end_i is:"+end_i);
            sendLog(taskName, "第"+i+"次并发，数据分片的结束位置是："+end_i);

            // 获取对应片段的数据
            List<String> querySegment = queryData.subList(start_i, end_i);
            List<String> answerSegment = answerData.subList(start_i, end_i);


            Thread t = new Thread(() -> {
                try {
                    cdl.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ":" + System.currentTimeMillis());
                try {
                    sendLog(taskName, "打印线程信息："+Thread.currentThread().getName() + ":" + System.currentTimeMillis());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                try {
                    UNIT_Query_Verify.exec_easy(querySegment, answerSegment,taskName,unitDatabaseService,runBatchNo);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    latch.countDown(); // 确保在每个线程完成时减少计数
                }

            });

            // 加入此部分
            t.setUncaughtExceptionHandler((thread, ex) -> {
                // handle exception here
                try {
                    ex.printStackTrace(); // 将异常信息写入重定向的输出流
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            t.start();
            cdl.countDown();
        }

        try {
            latch.await(); // 等待所有子线程完成
            long endTime=System.currentTimeMillis();
            sendLog(taskName, "\n##############################################################");
            sendLog(taskName, "【本次多线程批跑测试，执行完成！！！】");
            sendLog(taskName, "【执行结束时间是:"+GlobalClass.getStandardTime(endTime)+"】");
            long consumerTime=endTime-startTime;
            sendLog(taskName, "【总耗时为: "+consumerTime/1000+" 秒(s)】");
            sendLog(taskName, "###############################################################");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @GetMapping("/getEffectData")
    public Map<String, Object> getEffectData() {
        return unitDatabaseService.getEffectData();
    }
}
