package inventory.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Users extends NewObj implements java.io.Serializable {


	@GeneratedValue
//	private Integer id;
	private String userName;
	private String password;
	private String email;
	private String name;
//	private int activeFlag;
//	private Date createDate;
//	private Date updateDate;
	private Set userRoles = new HashSet(0);
	private Integer roleID;
	private int status;

	public Users() {
		super();
	}

	public Users(String userName, String password, String name, int activeFlag, Date createDate, Date updateDate) {
		super();
		this.userName = userName;
		this.password = password;
		this.name = name;
//		super(activeFlag) = activeFlag;
//		this.createDate = createDate;
//		this.updateDate = updateDate;
	}

	public Users(String userName, String password, String email, String name, int activeFlag, Date createDate,
			Date updateDate, Set userRoles) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.name = name;
//		this.activeFlag = activeFlag;
//		this.createDate = createDate;
//		this.updateDate = updateDate;
		this.userRoles = userRoles;
	}

//	public Integer getId() {
//		return this.id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public int getActiveFlag() {
//		return this.activeFlag;
//	}
//
//	public void setActiveFlag(int activeFlag) {
//		this.activeFlag = activeFlag;
//	}
//
//	public Date getCreateDate() {
//		return this.createDate;
//	}
//
//	public void setCreateDate(Date createDate) {
//		this.createDate = createDate;
//	}
//
//	public Date getUpdateDate() {
//		return this.updateDate;
//	}
//
//	public void setUpdateDate(Date updateDate) {
//		this.updateDate = updateDate;
//	}

	public Set getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set userRoles) {
		this.userRoles = userRoles;
	}

	public Integer getRoleID() {
		return roleID;
	}

	public void setRoleID(Integer roleID) {
		this.roleID = roleID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
