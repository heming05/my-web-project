package com.example.springboot_devtools_datasourceregister.UNIT_EFFECT_TOOL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UnitDatabaseService {

    @Autowired
    private UnitUploadInfoRepository unitUploadInfoRepository;

    @Autowired
    private TianyinWebEffectTestRepository tianyinWebEffectTestRepository;

    public List<String> getQueryData(String taskName) {
        List<UnitUploadInfoEntity> entities = unitUploadInfoRepository.findByJobname(taskName);
        List<String> result = new ArrayList<>();
        for (UnitUploadInfoEntity entity : entities) {
            result.add(entity.getStandard_query());
        }
        return result;
    }

    public List<String> getAnswerData(String taskName) {
        List<UnitUploadInfoEntity> entities = unitUploadInfoRepository.findByJobname(taskName);
        List<String> result = new ArrayList<>();
        for (UnitUploadInfoEntity entity : entities) {
            result.add(entity.getStandard_answer());
        }
        return result;
    }


    public int getParallel(String jobname) {
        List<TianyinWebEffectTestEntity> entities = tianyinWebEffectTestRepository.findByJobname(jobname);
        if (entities != null && !entities.isEmpty()) {
            return entities.get(0).getParallel();
        }
        return 1; // 返回默认值，如果没有查询到任何数据
    }


    public String getEndpoint(String jobname) {
        List<TianyinWebEffectTestEntity> entities = tianyinWebEffectTestRepository.findByJobname(jobname);
        if (entities != null && !entities.isEmpty()) {
            return entities.get(0).getEndpoint();
        }
        return "http://127.0.0.1"; // 返回默认值，如果没有查询到任何数据
    }


    public String getApi(String jobname) {
        List<TianyinWebEffectTestEntity> entities = tianyinWebEffectTestRepository.findByJobname(jobname);
        if (entities != null && !entities.isEmpty()) {
            return entities.get(0).getApi();
        }
        return "/ngd/core/v3/query?debug=false&nlu=false"; // 返回默认值，如果没有查询到任何数据
    }


    @Autowired
    private TianyinUnitResultDataRepository resultDataRepository;  // 假设你有一个JPA Repository
    public void saveResultData(TianyinUnitResultDataEntity data) {
        resultDataRepository.save(data);
    }





    //效果查看
    public Map<String, Object> getEffectData() {
        Long maxRunBatchNo = resultDataRepository.findMaxRunBatchNo();

        Long totalCount = resultDataRepository.countByRunBatchNo(maxRunBatchNo);
        Long successCount = resultDataRepository.countByRunBatchNoAndCompareResult(maxRunBatchNo, "true");
        Long failureCount = resultDataRepository.countByRunBatchNoAndCompareResult(maxRunBatchNo, "false");
        Double percentage = ((double) successCount / totalCount) * 100;

        Map<String, Object> result = new HashMap<>();
        result.put("maxRunBatchNo", maxRunBatchNo);
        result.put("totalCount", totalCount);
        result.put("successCount", successCount);
        result.put("failureCount", failureCount);
        result.put("percentage", percentage);

        return result;
    }


    // ...Other methods as needed
}
