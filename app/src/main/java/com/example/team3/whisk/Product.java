package com.example.team3.whisk;

/**     File name: Product.java
 *
 *      This class contains a POJO (Plain old java object) written for the SuperMarket API response.
 *      The POJO is used for obtaining information after parsing the XML.
 *
 *      @author Team 3
 *      @version 1.00
 */
public class Product {
    private String ItemName = null;
    private String ItemDescription = null;
    private String ItemCategory = null;
    private String ItemID = null;
    private String ItemImage = null;
    private String AisleNumber = null;
    private String Pricing = null;

    public void setItemName(String ItemName) {
        this.ItemName = ItemName;
    }

    public void setItemDescription(String ItemDescription) {
        this.ItemDescription = ItemDescription;

    }

    public void  setItemCategory(String ItemCategory) {
        this.ItemCategory = ItemCategory;
    }

    public void setItemID(String ItemID) {
        this.ItemID = ItemID;
    }

    public void setItemImage(String ItemImage) {
        this.ItemImage = ItemImage;
    }

    public void setAisleNumber(String AisleNumber) {
        this.AisleNumber = AisleNumber;
    }

    public  void setPricing(String Pricing) {
        this.Pricing = Pricing;
    }
    public String getItemName() {
        return ItemName;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public String getItemCategory() {
        return ItemCategory;
    }

    public String getItemID() {
        return ItemID;
    }

    public String getItemImage() {
        return ItemImage;
    }

    public String getAisleNumber() {
        return AisleNumber;
    }

    public String getPricing() {
        return Pricing;
    }
}
