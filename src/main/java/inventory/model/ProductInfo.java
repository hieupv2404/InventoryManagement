package inventory.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
public class ProductInfo implements java.io.Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Integer id;
	private Category category;
	private String name;
	private String description;
	private String imgUrl;
	private int activeFlag;
	private Date createDate;
	private Date updateDate;
	private Set histories = new HashSet(0);
	private Set productInStocks = new HashSet(0);
	private Set invoices = new HashSet(0);
	private MultipartFile multipartFile;
	private Integer cateId;
	private Set productDetails = new HashSet(0);
	private Set vatDetails = new HashSet(0);
	private Set productStatusDetails = new HashSet(0);



	public ProductInfo() {
	}

	public ProductInfo(Category category, String name, String imgUrl, int activeFlag, Date createDate,
			Date updateDate) {
		this.category = category;
		this.name = name;
		this.imgUrl = imgUrl;
		this.activeFlag = activeFlag;
		this.createDate = createDate;
		this.updateDate = updateDate;
		
	}

	public ProductInfo(Category category, String name, String description, String imgUrl, int activeFlag,
			Date createDate, Date updateDate, Set histories, Set productInStocks, Set invoices) {
		this.category = category;

		this.name = name;
		this.description = description;
		this.imgUrl = imgUrl;
		this.activeFlag = activeFlag;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.histories = histories;
		this.productInStocks = productInStocks;
		this.invoices = invoices;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getActiveFlag() {
		return this.activeFlag;
	}

	public void setActiveFlag(int activeFlag) {
		this.activeFlag = activeFlag;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Set getHistories() {
		return this.histories;
	}

	public void setHistories(Set histories) {
		this.histories = histories;
	}

	public Set getProductInStocks() {
		return this.productInStocks;
	}

	public void setProductInStocks(Set productInStocks) {
		this.productInStocks = productInStocks;
	}

	public Set getInvoices() {
		return this.invoices;
	}

	public void setInvoices(Set invoices) {
		this.invoices = invoices;
	}

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	public Integer getCateId() {
		return cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

	public Set getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(Set productDetails) {
		this.productDetails = productDetails;
	}

	public Set getVatDetails() {
		return vatDetails;
	}

	public void setVatDetails(Set vatDetails) {
		this.vatDetails = vatDetails;
	}

	public Set getProductStatusDetails() {
		return productStatusDetails;
	}

	public void setProductStatusDetails(Set productStatusDetails) {
		this.productStatusDetails = productStatusDetails;
	}
}
