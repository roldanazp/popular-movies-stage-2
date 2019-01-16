package com.roldannanodegre.popularmovies.database;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewQueryResult {
    private long id;
    private int page;
    private List<ReviewEntity> results;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<ReviewEntity> getResults() {
        return results;
    }

    public void setResults(List<ReviewEntity> results) {
        this.results = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
