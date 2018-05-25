package com.myretailproduct.service.beans.productMasterApi;

/**
 * Created by agane13 on 05/17/18.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDetailInfo {

    @JsonProperty("item")
    private ItemInfo item;

    public ItemInfo getItemInfo() {
        return item;
    }

    public void setItemInfo(ItemInfo itemInfo) {
        this.item = itemInfo;
    }

    @Override
    public String toString() {
        return "ProductDetailInfo{" +
                "itemInfo=" + item +
                '}';
    }
}
