package com.example.project3.service;

import com.example.project3.domain.response.ReportResponse;
import com.example.project3.repository.JDBC.ReportDAO;
import org.springframework.stereotype.Service;

@Service
public class ReportService  extends CommonService{

    ReportDAO reportDAO = new ReportDAO();
    ReportResponse response = new ReportResponse();

    public ReportResponse getReportByAllTime(String token){
        if (checkToken(token)){
            response.setReport(reportDAO.getReportByAllTime());
                response.setCode("100");
                response.setMessage("success");
        }else {
            response.setCode("111");
            response.setMessage("no token");
        }
        return response;
    }

    public ReportResponse getReportByTime(String token,int seconds){
        if (checkToken(token)){
            response.setReport(reportDAO.getReportByTime(seconds));
            response.setCode("100");
            response.setMessage("success");
        }else {
            response.setCode("111");
            response.setMessage("no token");
        }
        return response;
    }

}
