package com.myretailproduct.service.beans.productMasterApi;

/**
 * Created by agane13 on 11/12/17.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDescription {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ProductDescription{" +
                "title='" + title + '\'' +
                '}';
    }
}
