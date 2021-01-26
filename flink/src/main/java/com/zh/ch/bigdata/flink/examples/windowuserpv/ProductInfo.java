package com.zh.ch.bigdata.flink.examples.windowuserpv;

import java.sql.Timestamp;

/**
 * @author xzc
 * @description 商品信息
 * @date 2021/01/04
 */
public class ProductInfo {

    private String time;

    private Long productId;

    private Long productTotalSales;

    public ProductInfo(String time, Long productId, Long productTotalSales) {
        this.time = time;
        this.productId = productId;
        this.productTotalSales = productTotalSales;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setProductTotalSales(Long productTotalSales) {
        this.productTotalSales = productTotalSales;
    }

    public String getTime() {
        return time;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getProductTotalSales() {
        return productTotalSales;
    }
}
