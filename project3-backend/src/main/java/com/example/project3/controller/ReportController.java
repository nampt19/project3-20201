package com.example.project3.controller;

import com.example.project3.domain.response.ReportResponse;
import com.example.project3.domain.response.TransactionListResponse;
import com.example.project3.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    ReportService reportService;

    @CrossOrigin(origins = "*")
    @GetMapping("/getAll")
    public ResponseEntity<ReportResponse> getAll(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(reportService.getReportByAllTime(token));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getBytime/{seconds}")
    public ResponseEntity<ReportResponse> getByTime(@RequestHeader("Authorization") String token,@PathVariable int seconds) {
        return ResponseEntity.ok(reportService.getReportByTime(token,seconds));
    }
}
