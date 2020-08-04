package inventory.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Supplier implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;
    private String name;
    private String phone;
    private String address;
    private Date createDate;
    private Date updateDate;
    private Integer activeFlag;
    private Set productDetails = new HashSet(0);
    private Set invoices = new HashSet(0);
    private Set vats = new HashSet(0);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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


    public Integer getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Integer activeFlag) {
        this.activeFlag = activeFlag;
    }


    public Set getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(Set productDetails) {
        this.productDetails = productDetails;
    }

    public Set getInvoices() {
        return invoices;
    }

    public void setInvoices(Set invoices) {
        this.invoices = invoices;
    }

    public Set getVats() {
        return vats;
    }

    public void setVats(Set vats) {
        this.vats = vats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Supplier that = (Supplier) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;
        if (activeFlag != null ? !activeFlag.equals(that.activeFlag) : that.activeFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = 31 * result + (activeFlag != null ? activeFlag.hashCode() : 0);
        return result;
    }
}
