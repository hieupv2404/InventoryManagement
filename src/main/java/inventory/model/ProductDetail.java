package inventory.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class ProductDetail implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;
    private BigDecimal priceIn;
    private Integer activeFlag;
    private Date createDate;
    private Date updateDate;
    private BigDecimal priceOut;
    private String code;
    private ProductInfo productInfo;
    private Invoice invoice;
    private History history;
    private Supplier supplier;
    private Integer productInfoId;
    private Integer supplierId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public BigDecimal getPriceIn() {
        return priceIn;
    }

    public void setPriceIn(BigDecimal priceIn) {
        this.priceIn = priceIn;
    }


    public Integer getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Integer activeFlag) {
        this.activeFlag = activeFlag;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }


    public BigDecimal getPriceOut() {
        return priceOut;
    }

    public void setPriceOut(BigDecimal priceOut) {
        this.priceOut = priceOut;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        ProductDetail that = (ProductDetail) o;
//
//        if (id != that.id) return false;
//        if (priceIn != null ? !priceIn.equals(that.priceIn) : that.priceIn != null) return false;
//        if (activeFlag != null ? !activeFlag.equals(that.activeFlag) : that.activeFlag != null) return false;
//        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
//        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;
//        if (priceOut != null ? !priceOut.equals(that.priceOut) : that.priceOut != null) return false;
//        if (code != null ? !code.equals(that.code) : that.code != null) return false;
//
//        return true;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = id;
//        result = 31 * result + (priceIn != null ? priceIn.hashCode() : 0);
//        result = 31 * result + (activeFlag != null ? activeFlag.hashCode() : 0);
//        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
//        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
//        result = 31 * result + (priceOut != null ? priceOut.hashCode() : 0);
//        result = 31 * result + (code != null ? code.hashCode() : 0);
//        return result;
//    }


    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }


    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }


    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }


    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Integer getProductInfoId() {
        return productInfoId;
    }

    public void setProductInfoId(Integer productInfoId) {
        this.productInfoId = productInfoId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }
}
