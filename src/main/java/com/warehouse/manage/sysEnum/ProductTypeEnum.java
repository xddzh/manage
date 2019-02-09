package com.warehouse.manage.sysEnum;

public enum ProductTypeEnum {
    SFProduct("半成品",1),
    Product("成品",2),
    Consumables("耗材",3)
    ;

    private int productType;
    private String productTypeName;

    private ProductTypeEnum(String productTypeName, int productType){
        this.productType = productType;
        this.productTypeName = productTypeName;
    }

    public static String getProductTypeName(int productType){
        for (ProductTypeEnum typeEnum: ProductTypeEnum.values()
        ) {
            if (typeEnum.getProductType() == productType){
                return  typeEnum.productTypeName;
            }
        }
        return null;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }
}
