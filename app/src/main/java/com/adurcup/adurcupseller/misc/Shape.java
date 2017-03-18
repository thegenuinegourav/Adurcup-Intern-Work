package com.adurcup.adurcupseller.misc;

import java.io.Serializable;

public class Shape implements Serializable {
    private String overview;
    private String details;

    public Shape(String overview, String details)
    {
        this.details = details;
        this.overview = overview;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
