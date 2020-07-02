package inventory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class ProductStatusDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private ProductStatusList productStatusList;
    private ProductInfo productInfo;
    private int qty;
    private BigDecimal priceOne;
    private BigDecimal priceTotal;
    private int activeFlag;
    private Integer productStatusListId;
    private Integer productInfoId;
    private BigDecimal fromPriceOne;
    private BigDecimal toPriceOne;
    private BigDecimal fromPriceTotal;
    private BigDecimal toPriceTotal;

    public ProductStatusDetail() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductStatusList getProductStatusList() {
        return productStatusList;
    }

    public void setProductStatusList(ProductStatusList productStatusList) {
        this.productStatusList = productStatusList;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getPriceOne() {
        return priceOne;
    }

    public void setPriceOne(BigDecimal priceOne) {
        this.priceOne = priceOne;
    }

    public BigDecimal getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(BigDecimal priceTotal) {
        this.priceTotal = priceTotal;
    }

    public int getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(int activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Integer getProductStatusListId() {
        return productStatusListId;
    }

    public void setProductStatusListId(Integer productStatusListId) {
        this.productStatusListId = productStatusListId;
    }

    public Integer getProductInfoId() {
        return productInfoId;
    }

    public void setProductInfoId(Integer productInfoId) {
        this.productInfoId = productInfoId;
    }

    public BigDecimal getFromPriceOne() {
        return fromPriceOne;
    }

    public void setFromPriceOne(BigDecimal fromPriceOne) {
        this.fromPriceOne = fromPriceOne;
    }

    public BigDecimal getToPriceOne() {
        return toPriceOne;
    }

    public void setToPriceOne(BigDecimal toPriceOne) {
        this.toPriceOne = toPriceOne;
    }

    public BigDecimal getFromPriceTotal() {
        return fromPriceTotal;
    }

    public void setFromPriceTotal(BigDecimal fromPriceTotal) {
        this.fromPriceTotal = fromPriceTotal;
    }

    public BigDecimal getToPriceTotal() {
        return toPriceTotal;
    }

    public void setToPriceTotal(BigDecimal toPriceTotal) {
        this.toPriceTotal = toPriceTotal;
    }
}
