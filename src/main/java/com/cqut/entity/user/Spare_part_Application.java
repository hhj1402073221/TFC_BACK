package com.cqut.entity.user;
import java.util.Date;

import com.cqut.entity.base.Entity;
public class Spare_part_Application extends Entity{
	private String id;
	private String user_id;
	private Date apply_time;
	private String remark;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Date getApply_time() {
		return apply_time;
	}

	public void setApply_time(Date apply_time) {
		this.apply_time = apply_time;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Spare_part_Application [id=" + id + ", user_id=" + user_id
				+ ", apply_time=" + apply_time + ", remark=" + remark + "]";
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "Spare_part_Application";
	}

	@Override
	public String getPrimaryKey() {
		// TODO Auto-generated method stub
		return "id";
	}
	
}
