package com.example.springboot_devtools_datasourceregister;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class EffectTestController {

    private final EffectTestRepository effectTestRepository;
    private final EffectTestService effectTestService;

    public EffectTestController(EffectTestRepository effectTestRepository, EffectTestService effectTestService) {
        this.effectTestRepository = effectTestRepository;
        this.effectTestService = effectTestService;
    }

    @PostMapping("/effect_test_params_set")
    public EffectTestEntity addEffectTest(@RequestBody EffectTestEntity effectTest) {
        return effectTestRepository.save(effectTest);
    }


    //点击效果测试按钮，将左侧区域的内容显示出来
    @GetMapping("/effect-test-get-data")
    public Page<EffectTestEntity> getData(@PageableDefault(size = 20) Pageable pageable) {
        return effectTestService.findAll(pageable);
    }

    @GetMapping("/effect-test-get-data-with-jobName")
    public EffectTestEntity getDataWithJobName(@RequestParam String jobName) {
        return effectTestService.findByJobName(jobName)
                .orElseThrow(() -> new IllegalArgumentException("No data found with the provided job name"));
    }


    @PutMapping("/effect_test_update")
    public ResponseEntity<EffectTestEntity> updateEffectTest(@RequestBody EffectTestEntity updatedEffectTest) {
        // 根据jobname找到要更新的记录
        Optional<EffectTestEntity> existingEffectTest = effectTestRepository.findByJobname(updatedEffectTest.getJobname());

        // 如果找不到，返回404
        if (existingEffectTest.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // 更新找到的记录并保存
        EffectTestEntity effectTest = existingEffectTest.get();
        effectTest.setEndpoint(updatedEffectTest.getEndpoint());
        effectTest.setApi(updatedEffectTest.getApi());
        effectTest.setAuthorization(updatedEffectTest.getAuthorization());
        effectTest.setParallel(updatedEffectTest.getParallel());

        effectTestRepository.save(effectTest);

        // 返回更新后的记录
        return new ResponseEntity<>(effectTest, HttpStatus.OK);
    }








}
