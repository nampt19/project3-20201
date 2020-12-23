package com.example.project3.domain.response;

import java.util.List;

public class HistoryCareListResponse  extends BaseResponse{
    List<HistoryCareRes> historyCareList;

    public List<HistoryCareRes> getHistoryCareList() {
        return historyCareList;
    }

    public void setHistoryCareList(List<HistoryCareRes> historyCareList) {
        this.historyCareList = historyCareList;
    }
}
