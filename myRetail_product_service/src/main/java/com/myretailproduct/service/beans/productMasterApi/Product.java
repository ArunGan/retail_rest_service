package com.myretailproduct.service.beans.productMasterApi;

/**
 * Created by agane13 on 05/17/18.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Product{" +
                "item=" + item +
                '}';
    }
}
