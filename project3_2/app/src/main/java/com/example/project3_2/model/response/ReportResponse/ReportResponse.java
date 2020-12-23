package com.example.project3_2.model.response.ReportResponse;

import com.example.project3_2.model.response.BaseResponse;

public class ReportResponse extends BaseResponse {
    Report report;

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
