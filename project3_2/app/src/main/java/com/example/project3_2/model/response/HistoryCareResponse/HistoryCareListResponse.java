package com.example.project3_2.model.response.HistoryCareResponse;

import com.example.project3_2.model.response.BaseResponse;

import java.util.List;

public class HistoryCareListResponse  extends BaseResponse {
    List<HistoryCareRes> historyCareList;

    public List<HistoryCareRes> getHistoryCareList() {
        return historyCareList;
    }

    public void setHistoryCareList(List<HistoryCareRes> historyCareList) {
        this.historyCareList = historyCareList;
    }
}
