package com.im.domain;

import java.util.ArrayList;
import java.util.List;

public class SearchHotelsResponse {

    protected List<Hotel> searchHotelsResult;

    public List<Hotel> getSearchHotelsResult() {
        if (searchHotelsResult == null) {
            searchHotelsResult = new ArrayList<Hotel>();
        }
        return searchHotelsResult;
    }

    public void setSearchHotelsResult(List<Hotel> searchHotelsResult) {
        this.searchHotelsResult = searchHotelsResult;
    }
}
