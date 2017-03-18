package com.adurcup.adurcupseller.misc;

import java.io.Serializable;
import java.util.List;

public class ProductModel implements Serializable {
    private String _id;
    private String updatedAt;
    private String createdAt;
    private String user;
    private String productID;
    private String groupID;
    private Boolean isActive;
    private String nameTag;
    private String quality;
    private Integer rank;
    private String category;
    private String subCategory;
    private String productTitle;
    private String material;
    private String colour;
    private String gsm;
    private String compartment;
    private String surfaceTexture;
    private String surfaceDesign;
    private String additionalFeatures;
    private String sealable;
    private Boolean freezeSafe;
    private Boolean microwaveSafe;
    private Boolean foodGrade;
    private Boolean recyclable;
    private Boolean biodegradable;
    private String bestUsedFor;
    private String bestUsedIn;
    private String manufacturer;
    private String seller;
    private Double vendorPrice;
    private String vendorPriceUnit;
    private Double tax;
    private Integer __v;
    private String parentId;
    private String slug;
    private String gramage;
    private Size sizeSide;
    private Size sizeBottom;
    private Size sizeTop;
    private List<String> lid;
    private ValueUnit volume;
    private ValueUnit weight;
    private Shape shape;
    private List<CPrice> customerPrice;
    private List<NewPrice> newPrice;
    private UnitDescription unitDescription;
    private List<String> productOptions;
    private List<String> lidOptions;
    private List<Size> size;
    private List<String> imagesURLs;
    private String lidAvailability;

    private Integer order_quantity;
    private Double order_price;
    private Boolean isChecked;


    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public Double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(Double order_price) {
        this.order_price = order_price;
    }

    public Integer getOrder_quantity() {
        return order_quantity;
    }

    public void setOrder_quantity(Integer order_quantity) {
        this.order_quantity = order_quantity;
    }

    public List<NewPrice> getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(List<NewPrice> newPrice) {
        this.newPrice = newPrice;
    }

    public Double getVendorPrice() {
        return vendorPrice;
    }

    public void setVendorPrice(Double vendorPrice) {
        this.vendorPrice = vendorPrice;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Integer get__v() {
        return __v;
    }

    public void set__v(Integer __v) {
        this.__v = __v;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }


    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getSurfaceDesign() {
        return surfaceDesign;
    }

    public void setSurfaceDesign(String surfaceDesign) {
        this.surfaceDesign = surfaceDesign;
    }

    public String getCompartment() {
        return compartment;
    }

    public void setCompartment(String compartment) {
        this.compartment = compartment;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

//    public Integer getRank() {
//        return rank;
//    }
//
//    public void setRank(Integer rank) {
//        this.rank = rank;
//    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public String getSurfaceTexture() {
        return surfaceTexture;
    }

    public void setSurfaceTexture(String surfaceTexture) {
        this.surfaceTexture = surfaceTexture;
    }

    public String getAdditionalFeatures() {
        return additionalFeatures;
    }

    public void setAdditionalFeatures(String additionalFeatures) {
        this.additionalFeatures = additionalFeatures;
    }

    public String getSealable() {
        return sealable;
    }

    public void setSealable(String sealable) {
        this.sealable = sealable;
    }

    public Boolean getFreezeSafe() {
        return freezeSafe;
    }

    public void setFreezeSafe(Boolean freezeSafe) {
        this.freezeSafe = freezeSafe;
    }

    public Boolean getMicrowaveSafe() {
        return microwaveSafe;
    }

    public void setMicrowaveSafe(Boolean microwaveSafe) {
        this.microwaveSafe = microwaveSafe;
    }

    public Boolean getFoodGrade() {
        return foodGrade;
    }

    public void setFoodGrade(Boolean foodGrade) {
        this.foodGrade = foodGrade;
    }

    public Boolean getRecyclable() {
        return recyclable;
    }

    public void setRecyclable(Boolean recyclable) {
        this.recyclable = recyclable;
    }

    public Boolean getBiodegradable() {
        return biodegradable;
    }

    public void setBiodegradable(Boolean biodegradable) {
        this.biodegradable = biodegradable;
    }

    public String getBestUsedFor() {
        return bestUsedFor;
    }

    public void setBestUsedFor(String bestUsedFor) {
        this.bestUsedFor = bestUsedFor;
    }

    public String getBestUsedIn() {
        return bestUsedIn;
    }

    public void setBestUsedIn(String bestUsedIn) {
        this.bestUsedIn = bestUsedIn;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

//    public Double getVendorPrice() {
//        return vendorPrice;
//    }
//
//    public void setVendorPrice(Double vendorPrice) {
//        this.vendorPrice = vendorPrice;
//    }

    public String getVendorPriceUnit() {
        return vendorPriceUnit;
    }

    public void setVendorPriceUnit(String vendorPriceUnit) {
        this.vendorPriceUnit = vendorPriceUnit;
    }

//    public Double getTax() {
//        return tax;
//    }

//    public void setTax(Double tax) {
//        this.tax = tax;
//    }
//
//    public Integer get__v() {
//        return __v;
//    }
//
//    public void set__v(Integer __v) {
//        this.__v = __v;
//    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getGramage() {
        return gramage;
    }

    public void setGramage(String gramage) {
        this.gramage = gramage;
    }

    public Size getSizeSide() {
        return sizeSide;
    }

    public void setSizeSide(Size sizeSide) {
        this.sizeSide = sizeSide;
    }

    public Size getSizeBottom() {
        return sizeBottom;
    }

    public void setSizeBottom(Size sizeBottom) {
        this.sizeBottom = sizeBottom;
    }

    public Size getSizeTop() {
        return sizeTop;
    }

    public void setSizeTop(Size sizeTop) {
        this.sizeTop = sizeTop;
    }

    public List<String> getLid() {
        return lid;
    }

    public void setLid(List<String> lid) {
        this.lid = lid;
    }

    public ValueUnit getVolume() {
        return volume;
    }

    public void setVolume(ValueUnit volume) {
        this.volume = volume;
    }

    public ValueUnit getWeight() {
        return weight;
    }

    public void setWeight(ValueUnit weight) {
        this.weight = weight;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public List<CPrice> getCustomerPrice() {
        return customerPrice;
    }

    public void setCustomerPrice(List<CPrice> customerPrice) {
        this.customerPrice = customerPrice;
    }

    public UnitDescription getUnitDescription() {
        return unitDescription;
    }

    public void setUnitDescription(UnitDescription unitDescription) {
        this.unitDescription = unitDescription;
    }

    public List<String> getProductOptions() {
        return productOptions;
    }

    public void setProductOptions(List<String> productOptions) {
        this.productOptions = productOptions;
    }

    public List<String> getLidOptions() {
        return lidOptions;
    }

    public void setLidOptions(List<String> lidOptions) {
        this.lidOptions = lidOptions;
    }

    public List<Size> getSize() {
        return size;
    }

    public void setSize(List<Size> size) {
        this.size = size;
    }

    public List<String> getImagesURLs() {
        return imagesURLs;
    }

    public void setImagesURLs(List<String> imagesURLs) {
        this.imagesURLs = imagesURLs;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getNameTag() {
        return nameTag;
    }

    public void setNameTag(String nameTag) {
        this.nameTag = nameTag;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getLidAvailability() {
        return lidAvailability;
    }

    public void setLidAvailability(String lidAvailability) {
        this.lidAvailability = lidAvailability;
    }

}

