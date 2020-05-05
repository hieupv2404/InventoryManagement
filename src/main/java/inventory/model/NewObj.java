package inventory.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public abstract class NewObj {
    @Id
    private Integer id;
    private int activeFlag;
    private Date createDate;
    private Date updateDate;

    public NewObj(int activeFlag, Date createDate, Date updateDate) {

        this.activeFlag = activeFlag;
        this.createDate = createDate;
		this.updateDate = updateDate;
    }

    public NewObj() {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(int activeFlag) {
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
}
