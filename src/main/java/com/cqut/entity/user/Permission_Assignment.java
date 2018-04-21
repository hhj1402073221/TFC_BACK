package com.cqut.entity.user;
import com.cqut.entity.base.Entity;
public class Permission_Assignment extends Entity{
	private String id;
	private String role_type_id;
	private String permission_id;
	private String remark;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRole_type_id() {
		return role_type_id;
	}

	public void setRole_type_id(String role_type_id) {
		this.role_type_id = role_type_id;
	}

	public String getPermission_id() {
		return permission_id;
	}

	public void setPermission_id(String permission_id) {
		this.permission_id = permission_id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Permission_Assignment [id=" + id + ", role_type_id="
				+ role_type_id + ", permission_id=" + permission_id
				+ ", remark=" + remark + "]";
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "Permission_Assignment";
	}

	@Override
	public String getPrimaryKey() {
		// TODO Auto-generated method stub
		return "id";
	}
	
}
