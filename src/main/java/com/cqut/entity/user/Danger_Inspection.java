package com.cqut.entity.user;
import com.cqut.entity.base.Entity;
public class Danger_Inspection extends Entity{
	private String id;
	private String repair_report_id;
	private int facility_run_status;
	private int facility_ppm;
	private int handle_ppm;
	private String remark;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRepair_report_id() {
		return repair_report_id;
	}

	public void setRepair_report_id(String repair_report_id) {
		this.repair_report_id = repair_report_id;
	}

	public int getFacility_run_status() {
		return facility_run_status;
	}

	public void setFacility_run_status(int facility_run_status) {
		this.facility_run_status = facility_run_status;
	}

	public int getFacility_ppm() {
		return facility_ppm;
	}

	public void setFacility_ppm(int facility_ppm) {
		this.facility_ppm = facility_ppm;
	}

	public int getHandle_ppm() {
		return handle_ppm;
	}

	public void setHandle_ppm(int handle_ppm) {
		this.handle_ppm = handle_ppm;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Danger_Inspection [id=" + id + ", repair_report_id="
				+ repair_report_id + ", facility_run_status="
				+ facility_run_status + ", facility_ppm=" + facility_ppm
				+ ", handle_ppm=" + handle_ppm + ", remark=" + remark + "]";
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "Danger_Inspection";
	}

	@Override
	public String getPrimaryKey() {
		// TODO Auto-generated method stub
		return "id";
	}

}
