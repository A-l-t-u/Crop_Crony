package com.example.crop_crony;

public class ProductRequestClass
{
    String RequestId, RequestedBy, RequestedProduct, RequestDescription;

    public ProductRequestClass(String requestId, String requestedBy, String requestedProduct, String requestDescription)
    {
        RequestId = requestId;
        RequestedBy = requestedBy;
        RequestedProduct = requestedProduct;
        RequestDescription = requestDescription;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public String getRequestedBy() {
        return RequestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        RequestedBy = requestedBy;
    }

    public String getRequestedProduct() {
        return RequestedProduct;
    }

    public void setRequestedProduct(String requestedProduct) {
        RequestedProduct = requestedProduct;
    }

    public String getRequestDescription() {
        return RequestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        RequestDescription = requestDescription;
    }
}
