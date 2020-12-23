package com.example.project3.domain.response;

import com.example.project3.model.Report;

public class ReportResponse extends BaseResponse{
    private Report report;

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
