package inventory.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class VatDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private Vat vat;
    private ProductInfo productInfo;
    private int qty;
    private BigDecimal priceOne;
    private BigDecimal priceTotal;
    private Integer vatId;
    private Integer productInfoId;
    private BigDecimal fromPriceOne;
    private BigDecimal toPriceOne;
    private BigDecimal fromPriceTotal;
    private BigDecimal toPriceTotal;
    private int activeFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Vat getVat() {
        return vat;
    }

    public void setVat(Vat vat) {
        this.vat = vat;
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

    public Integer getVatId() {
        return vatId;
    }

    public void setVatId(Integer vatId) {
        this.vatId = vatId;
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

    public int getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(int activeFlag) {
        this.activeFlag = activeFlag;
    }
}
