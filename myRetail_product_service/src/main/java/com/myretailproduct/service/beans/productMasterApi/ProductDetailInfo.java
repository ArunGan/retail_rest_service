package com.myretailproduct.service.beans.productMasterApi;

/**
 * Created by agane13 on 05/17/18.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDetailInfo {

    private ItemInfo itemInfo;

    public ItemInfo getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(ItemInfo itemInfo) {
        this.itemInfo = itemInfo;
    }

    @Override
    public String toString() {
        return "ProductDetailInfo{" +
                "itemInfo=" + itemInfo +
                '}';
    }
}
