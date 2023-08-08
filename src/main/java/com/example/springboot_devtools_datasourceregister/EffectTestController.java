package com.example.springboot_devtools_datasourceregister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EffectTestController {

    @Autowired
    private EffectTestRepository effectTestRepository;

    @PostMapping("/effect_test_params_set")
    public EffectTestEntity addEffectTest(@RequestBody EffectTestEntity effectTest) {
        return effectTestRepository.save(effectTest);
    }
}
