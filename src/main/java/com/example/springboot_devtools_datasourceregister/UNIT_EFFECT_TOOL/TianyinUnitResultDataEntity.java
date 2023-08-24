package com.example.springboot_devtools_datasourceregister.UNIT_EFFECT_TOOL;

import jakarta.persistence.*;

@Entity
@Table(name = "tianyin_unit_result_data")
public class TianyinUnitResultDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobname;

    private long runBatchNo;
    @Column(columnDefinition="TEXT")
    private String standardQuery;
    @Column(columnDefinition="TEXT")
    private String standardAnswer;
    @Column(columnDefinition="TEXT")
    private String returnedAnswer;
    private String answerSource;
    private String statusCode;
    private String compareResult;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStandardQuery() {
        return standardQuery;
    }

    public void setStandardQuery(String standardQuery) {
        this.standardQuery = standardQuery;
    }

    public String getStandardAnswer() {
        return standardAnswer;
    }

    public void setStandardAnswer(String standardAnswer) {
        this.standardAnswer = standardAnswer;
    }

    public String getReturnedAnswer() {
        return returnedAnswer;
    }

    public void setReturnedAnswer(String returnedAnswer) {
        this.returnedAnswer = returnedAnswer;
    }

    public String getAnswerSource() {
        return answerSource;
    }

    public void setAnswerSource(String answerSource) {
        this.answerSource = answerSource;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getCompareResult() {
        return compareResult;
    }

    public void setCompareResult(String compareResult) {
        this.compareResult = compareResult;
    }

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public long getRunBatchNo() {
        return runBatchNo;
    }

    public void setRunBatchNo(long runBatchNo) {
        this.runBatchNo = runBatchNo;
    }

    // getters, setters, constructors
}