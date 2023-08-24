package com.example.springboot_devtools_datasourceregister.UNIT_EFFECT_TOOL.Service;

import com.example.springboot_devtools_datasourceregister.UNIT_EFFECT_TOOL.TianyinUnitResultDataEntity;
import com.example.springboot_devtools_datasourceregister.UNIT_EFFECT_TOOL.UnitDatabaseService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.springboot_devtools_datasourceregister.MyHandler.sendLog;

@Service
public class UNIT_Query_Verify {

    public static void exec_easy(List<String> querySegment, List<String> answerSegment, String taskName, UnitDatabaseService unitDatabaseService,long runBatchNo) throws Exception {

        for (int i = 0; i < querySegment.size(); i++) {
            String queryText_i = querySegment.get(i);
            sendLog(taskName, "第<" + (i + 1) + ">条标准问是:\n" + queryText_i);

            String[] logContent = UnitCoreQueryApi.getResponse(queryText_i, taskName, i + 1, unitDatabaseService);

            String code_i = logContent[1];
            String msg = logContent[2];
            String answerText_i = logContent[3];
            String source_i = logContent[4];
            String standardAnswerText_i = answerSegment.get(i);

            boolean compareFlag=standardAnswerText_i.equals(answerText_i);
            String compareResult;
            if(compareFlag){
                 compareResult="TRUE";
            }else{
                 compareResult="FALSE";
            }

            sendLog(taskName, "第<" + (i + 1) + ">条标准答是:\n" + standardAnswerText_i);

            // 创建数据对象并存储到数据库中
            TianyinUnitResultDataEntity resultData = new TianyinUnitResultDataEntity();
            resultData.setJobname(taskName);
            resultData.setStandardQuery(queryText_i);
            resultData.setStandardAnswer(standardAnswerText_i);
            resultData.setReturnedAnswer(answerText_i);
            resultData.setAnswerSource(source_i);
            resultData.setStatusCode(code_i);
            resultData.setCompareResult(compareResult);
            resultData.setRunBatchNo(runBatchNo);

            unitDatabaseService.saveResultData(resultData);  // 假设你在UnitDatabaseService里有一个saveResultData方法用于保存数据
        }
    }
}
