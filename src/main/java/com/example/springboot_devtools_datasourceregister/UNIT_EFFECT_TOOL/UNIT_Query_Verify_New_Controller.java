package com.example.springboot_devtools_datasourceregister.UNIT_EFFECT_TOOL;

import com.example.springboot_devtools_datasourceregister.UNIT_EFFECT_TOOL.Service.GlobalClass;
import com.example.springboot_devtools_datasourceregister.UNIT_EFFECT_TOOL.Service.UNIT_Query_Verify;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


@RestController
@RequestMapping("/unit_effect")
public class UNIT_Query_Verify_New_Controller {

    @PostMapping("/startUnitVerify")

    public String startVerify(@RequestBody Map<String, Object> jsonBody) throws IOException {
        String taskName = (String) jsonBody.get("taskName");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);


        String module = "FAQ";
        String fileNameOfQuery = "D:\\workspace\\BMLPrivateOpenApi\\src\\main\\resources\\VOLVO_FAQ\\" + module + "_Query.txt";
        String content_query = GlobalClass.readFileContent(fileNameOfQuery);
        int total = GlobalClass.getStrLines(content_query);

        String fileNameOfStandardAnswer = "D:\\workspace\\BMLPrivateOpenApi\\src\\main\\resources\\VOLVO_FAQ\\" + module + "_Standard_Result.txt";

        int parallel = 1;
        int length = GlobalClass.getMathCeil(total, parallel);

        CountDownLatch cdl = new CountDownLatch(parallel);
        CountDownLatch latch = new CountDownLatch(parallel); // 创建一个新的CountDownLatch，用于等待所有子线程完成


        for (int i = 1; i <= parallel; i++) {
            int start_i = GlobalClass.getStartPos(total, length, i);
            int end_i = GlobalClass.getEndPos(total, length, i);
            String fileName = "UNIT_Query_Verify_" + module + i;

            Thread t = new Thread(() -> {
                try {
                    cdl.await();
                } catch (InterruptedException e) {
                    e.printStackTrace(ps);
                }
                System.out.println(Thread.currentThread().getName() + ":" + System.currentTimeMillis());
                try {
                    UNIT_Query_Verify.exec_easy(fileNameOfQuery, fileNameOfStandardAnswer, fileName, start_i, end_i);
                } catch (Exception e) {
                    e.printStackTrace(ps);
                }finally {
                    latch.countDown(); // 确保在每个线程完成时减少计数
                }

            });

            // 加入此部分
            t.setUncaughtExceptionHandler((thread, ex) -> {
                // handle exception here
                try {
                    ex.printStackTrace(ps); // 将异常信息写入重定向的输出流
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            t.start();
            cdl.countDown();
        }

        try {
            latch.await(); // 等待所有子线程完成
        } catch (InterruptedException e) {
            e.printStackTrace(ps);
        }


        // 将系统输出重定向回原始流并关闭我们的流
        ps.flush(); // 将缓冲区内容强制写入输出流
        System.out.flush();
        System.setOut(old);

        // 将日志返回到前端
        return baos.toString();
    }
}
