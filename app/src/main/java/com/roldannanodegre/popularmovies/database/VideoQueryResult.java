package com.roldannanodegre.popularmovies.database;

import java.util.List;

public class VideoQueryResult {
    private long id;
    private List<VideoEntity> results;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<VideoEntity> getResults() {
        return results;
    }

    public void setResults(List<VideoEntity> results) {
        this.results = results;
    }
}
