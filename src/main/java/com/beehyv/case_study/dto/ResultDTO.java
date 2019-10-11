package com.beehyv.case_study.dto;

public class ResultDTO {
    private String result;

    public ResultDTO(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResultDTO{" +
                "result='" + result + '\'' +
                '}';
    }
}
