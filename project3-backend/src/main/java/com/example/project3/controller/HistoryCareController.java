package com.example.project3.controller;


import com.example.project3.domain.response.BaseResponse;
import com.example.project3.domain.response.HistoryCareListResponse;
import com.example.project3.model.HistoryCare;
import com.example.project3.service.HistoryCareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/historyCare")
public class HistoryCareController {
    @Autowired
    HistoryCareService historyCareService;

    @CrossOrigin(origins = "*")
    @PostMapping("/updateStatusUser/{id}")
    public ResponseEntity<BaseResponse> updateStatusUser(@RequestHeader("Authorization") String token, @PathVariable int id) {
        return ResponseEntity.ok(historyCareService.updateStatusUser(token,id));
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/refreshStatusUser/{id}")
    public ResponseEntity<BaseResponse> refreshStatusUser(@RequestHeader("Authorization") String token, @PathVariable int id) {
        return ResponseEntity.ok(historyCareService.refreshStatusUser(token,id));
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/getHistoryCaresByIdCustomer/{id}")
    public ResponseEntity<HistoryCareListResponse> getHistoryCaresByIdCustomer(@RequestHeader("Authorization") String token, @PathVariable int id) {
        return ResponseEntity.ok(historyCareService.getHistoryCaresByIdCustomer(token,id));
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/create")
    public ResponseEntity<BaseResponse> createHistoryCare(@RequestHeader("Authorization") String token, @RequestBody HistoryCare historyCare) {
        return ResponseEntity.ok(historyCareService.createHistoryCare(token,historyCare));
    }
}
