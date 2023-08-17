package com.example.springboot_devtools_datasourceregister;

import jakarta.persistence.*;

@Entity
@Table(name = "tianyin_unit_uploadinfo") // 注意更新了表名
public class StandardQuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobname;
    private String standard_query; // 注意更新了字段名
    private String standard_answer; // 新增的字段

    // Getter and Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for jobname
    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    // Getter and Setter for standard_query
    public String getStandard_query() {
        return standard_query;
    }

    public void setStandard_query(String standard_query) {
        this.standard_query = standard_query;
    }

    // Getter and Setter for standard_answer
    public String getStandard_answer() {
        return standard_answer;
    }

    public void setStandard_answer(String standard_answer) {
        this.standard_answer = standard_answer;
    }
}
