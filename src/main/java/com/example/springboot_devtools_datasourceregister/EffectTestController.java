package com.example.springboot_devtools_datasourceregister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RestController
public class EffectTestController {

    @Autowired
    private EffectTestRepository effectTestRepository;

    @PostMapping("/effect_test_params_set")
    public EffectTestEntity addEffectTest(@RequestBody EffectTestEntity effectTest) {
        return effectTestRepository.save(effectTest);
    }


    //点击效果测试按钮，将左侧区域的内容显示出来
    @Autowired
    private EffectTestService effectTestService;


    @GetMapping("/effect-test-get-data")
    public Page<EffectTestEntity> getData(@PageableDefault(size = 20) Pageable pageable) {
        return effectTestService.findAll(pageable);
    }

}
