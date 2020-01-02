package cn.cloud.shop.tickets_api.tickets.entity;

import cn.cloud.shop.tickets_api.tickets.util.BaseModel;
import java.util.Date;
import javax.persistence.*;

@Table(name = "ticket_info")
public class TicketInfo extends BaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

    private String creator;

    @Column(name = "createTime")
    private Date createtime;

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * @param creator
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * @return createTime
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}