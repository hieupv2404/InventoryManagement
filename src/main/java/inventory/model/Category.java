package inventory.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category implements java.io.Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Integer id;
	private String name;
	private String code;
	private String description;
	private int activeFlag;
	private Date createDate;
	private Date updateDate;
	private Set productInfos = new HashSet(0);

	public Category() {
	}

	public Category(String name, String code, int activeFlag, Date createDate, Date updateDate) {
		this.name = name;
		this.code = code;
		this.activeFlag = activeFlag;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public Category(String name, String code, String description, int activeFlag, Date createDate, Date updateDate,
			Set productInfos) {
		this.name = name;
		this.code = code;
		this.description = description;
		this.activeFlag = activeFlag;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.productInfos = productInfos;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Set getProductInfos() {
		return this.productInfos;
	}

	public void setProductInfos(Set productInfos) {
		this.productInfos = productInfos;
	}
	@Override
	public String toString() {
		return "Name ="+name+" Code= "+code+" description="+description;
	}

}
