package com.cqut.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cqut.dao.base.EntityDao;
import com.cqut.dao.base.SearchDao;
import com.cqut.entity.user.Area;
import com.cqut.entity.user.Common_Maintain;
import com.cqut.entity.user.Danger_Demarcate;
import com.cqut.entity.user.Danger_Inspection;
import com.cqut.entity.user.Danger_Maintain;
import com.cqut.entity.user.Participate_Relation;
import com.cqut.entity.user.Repair_Report;
import com.cqut.util.EntityIDFactory;

@Service("repairService")
public class RepairService implements IRepairService{
	@Resource(name = "entityDao")
    private EntityDao entityDao;
	@Resource(name = "searchDao")
	private SearchDao searchDao;
	
	@Override
	public List<Map<String, Object>> getRecords(int row, int page,
			String terminal_name, String repair_type) {
		String condition = " 1=1 ";
		if (terminal_name != null && !terminal_name.isEmpty())
			condition += " AND terminal_info.terminal_name like '%"
					+ terminal_name + "%'";
		if(repair_type != null && !repair_type.isEmpty())
			condition += " AND repair_report.repair_type like '%"
					+ repair_type + "%'";
		String joinEntity = " LEFT JOIN repair_report ON repair_report.id = participate_relation.repair_report_id "
				+ " LEFT JOIN terminal_info ON terminal_info.id = repair_report.terminal_id ";
		List<Map<String, Object>> resultList = searchDao
				.searchWithpagingInMysql(
						new String[] { " participate_relation.id, "
								+ " participate_relation.repair_report_id, "
								+ " participate_relation.user_id, "
								+ " repair_report.terminal_id, "
								+ " terminal_info.terminal_name, "
								+ " repair_report.repair_type, "
								+ " repair_report.repair_time, "
								+ " participate_relation.remark "},
						" participate_relation ", joinEntity, null, null, condition,
						null, "participate_relation.id", "ASC", row, page);
		return resultList;
	}
	
	@Override
	public Map<String, Object> getCommon(String id) {
		String condition = " 1=1 ";
		if (id != null && !id.isEmpty())
			condition += " AND participate_relation.id = " + id + "";
		String joinEntity = " LEFT JOIN repair_report ON repair_report.id = participate_relation.repair_report_id "
				+ " LEFT JOIN terminal_info ON terminal_info.id = repair_report.terminal_id "
				+ " LEFT JOIN user ON user.id = participate_relation.user_id "
				+ " LEFT JOIN common_maintain ON common_maintain.repair_report_id = participate_relation.repair_report_id ";
		List<Map<String, Object>> resultList = searchDao.searchForeign(
				new String[] { " participate_relation.id, "
						+ " participate_relation.repair_report_id, "
						+ " participate_relation.user_id, "
						+ " user.user_account, "
						+ " common_maintain.id, "
						+ " common_maintain.description, "
						+ " common_maintain.repace_facilitys, "
						+ " common_maintain.ishandle, "
						+ " repair_report.terminal_id, "
						+ " terminal_info.terminal_name, "
						+ " repair_report.picture_before, "
						+ " repair_report.picture_now, "
						+ " repair_report.picture_after, "
						+ " repair_report.repair_type, "
						+ " repair_report.repair_time, "
						+ " repair_report.terminal_id, "
						+ " terminal_info.terminal_name, "
						+ " participate_relation.remark "},
						" participate_relation ", joinEntity, null, null, condition);
		return resultList.get(0);
	}
	
	@Override
	public Map<String, Object> getDanger(String id) {
		String condition = " 1=1 ";
		if (id != null && !id.isEmpty())
			condition += " AND participate_relation.id = " + id + "";
		String joinEntity = " LEFT JOIN repair_report ON repair_report.id = participate_relation.repair_report_id "
				+ " LEFT JOIN terminal_info ON terminal_info.id = repair_report.terminal_id "
				+ " LEFT JOIN user ON user.id = participate_relation.user_id ";
		List<Map<String, Object>> resultList = searchDao.searchForeign(
				new String[] { " participate_relation.id, "
						+ " participate_relation.repair_report_id, "
						+ " participate_relation.user_id, "
						+ " user.user_account, "
						+ " repair_report.terminal_id, "
						+ " terminal_info.terminal_name, "
						+ " repair_report.picture_before, "
						+ " repair_report.picture_now, "
						+ " repair_report.picture_after, "
						+ " repair_report.repair_type, "
						+ " repair_report.repair_time, "
						+ " repair_report.terminal_id, "
						+ " terminal_info.terminal_name, "
						+ " participate_relation.remark "},
						" participate_relation ", joinEntity, null, null, condition);
		Map<String, Object> resultMap = resultList.get(0);
		String repair_report_id = resultMap.get("repair_report_id").toString();
		String condition1 = " 1=1 ";
		String condition2 = " 1=1 ";
		String condition3 = " 1=1 ";
		if (id != null && !id.isEmpty()){
			condition1 += " AND danger_inspection.repair_report_id = " + repair_report_id + "";
			condition2 += " AND danger_maintain.repair_report_id = " + repair_report_id + "";
			condition3 += " AND danger_demarcate.repair_report_id = " + repair_report_id + "";
		}
		List<Map<String, Object>> list1 = searchDao.searchForeign(
				new String[] { " danger_inspection.id, "
						+ " danger_inspection.repair_report_id, "
						+ " danger_inspection.facility_run_status, "
						+ " danger_inspection.facility_ppm, "
						+ " danger_inspection.handle_ppm, "
						+ " danger_inspection.remark "},
				" danger_inspection ", null, null, null, condition1);
		List<Map<String, Object>> list2 = searchDao.searchForeign(
				new String[] { " danger_maintain.id, "
						+ " danger_maintain.repair_report_id, "
						+ " danger_maintain.fault_description, "
						+ " danger_maintain.repace_facilitys, "
						+ " danger_maintain.isrecover, "
						+ " danger_maintain.remark "},
				" danger_maintain ", null, null, null, condition2);
		List<Map<String, Object>> list3 = searchDao.searchForeign(
				new String[] { " danger_demarcate.id, "
						+ " danger_demarcate.repair_report_id, "
						+ " danger_demarcate.before_demarcate, "
						+ " danger_demarcate.after_demarcate, "
						+ " danger_demarcate.ishandle, "
						+ " danger_demarcate.remark "},
				" danger_demarcate ", null, null, null, condition3);
		resultMap.put("danger_inspection", list1.get(0));
		resultMap.put("danger_maintain", list2.get(0));
		resultMap.put("danger_demarcate", list3.get(0));
		
		return resultMap;
	}
	
	@Override
	public Map<String, Object> addCommon(String user_id,
			Map<String, Object> repair_report,
			Map<String, Object> common_maintain) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		String repair_time = repair_report.get("repair_time").toString();
		repair_report.remove("repair_time");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
			try {
				date = df.parse(repair_time);
				
				Repair_Report repair_reports = new Repair_Report();
				String id = EntityIDFactory.createId();
				repair_reports.setId(id);
				repair_reports.setRepair_time(date);
				repair_reports.setProperties(repair_report);
				
				Participate_Relation participate_relation = new Participate_Relation();
				participate_relation.setId(EntityIDFactory.createId());
				participate_relation.setRepair_report_id(id);
				participate_relation.setUser_id(user_id);
				participate_relation.setIs_writer(1);
				
				Common_Maintain common_maintains = new Common_Maintain();
				common_maintains.setId(EntityIDFactory.createId());
				common_maintains.setRepair_report_id(id);
				common_maintains.setProperties(common_maintain);
				
				String resultString = entityDao.save(repair_reports) + 
						entityDao.save(participate_relation) + 
						entityDao.save(common_maintains) == 3 ? "ture":"false";
				result.put("result", resultString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return result;
	}

	@Override
	public Map<String, Object> addDanger(String user_id,
			Map<String, Object> repair_report,
			Map<String, Object> danger_inspection,
			Map<String, Object> danger_maintain,
			Map<String, Object> danger_demarcate) {
		Map<String, Object> result = new HashMap<String, Object>();
		String repair_time = repair_report.get("repair_time").toString();
		repair_report.remove("repair_time");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
			try {
				date = df.parse(repair_time);
				
				Repair_Report repair_reports = new Repair_Report();
				String id = EntityIDFactory.createId();
				repair_reports.setId(id);
				repair_reports.setRepair_time(date);
				repair_reports.setProperties(repair_report);
				
				Participate_Relation participate_relation = new Participate_Relation();
				participate_relation.setId(EntityIDFactory.createId());
				participate_relation.setRepair_report_id(id);
				participate_relation.setUser_id(user_id);
				participate_relation.setIs_writer(1);		
				
				int num = 0;
				Danger_Inspection danger_inspections = new Danger_Inspection();
				if(danger_inspection.containsKey("facility_run_status")){
					danger_inspections.setId(EntityIDFactory.createId());
					danger_inspections.setRepair_report_id(id);
					danger_inspections.setProperties(danger_inspection);
					entityDao.save(danger_inspections);
					num++;
				}
				
				Danger_Maintain danger_maintains = new Danger_Maintain();
				if(danger_maintain.containsKey("fault_description")){
					danger_maintains.setId(EntityIDFactory.createId());
					danger_maintains.setRepair_report_id(id);
					danger_maintains.setProperties(danger_maintain);
					entityDao.save(danger_maintains);
					num++;
				}
				
				Danger_Demarcate danger_demarcates = new Danger_Demarcate();
				if(danger_demarcate.containsKey("before_demarcate")){
					danger_demarcates.setId(EntityIDFactory.createId());
					danger_demarcates.setRepair_report_id(id);
					danger_demarcates.setProperties(danger_demarcate);
					entityDao.save(danger_demarcates);
					num++;
				}
				
				String resultString = entityDao.save(repair_reports) + 
						entityDao.save(participate_relation) + num == 5 ? "ture":"false";
				result.put("result", resultString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return result;
	}

	@Override
	public int deleteRecord(String Record_id) {
		String[] ids = Record_id.split(",");
		int BackValue = 0;
		for(String id:ids){
			Participate_Relation participate_relation = entityDao.getByID(id, Participate_Relation.class);
			//entityDao.deleteByID(participate_relation.getRepair_report_id(), Repair_Report.class);
			entityDao.deleteRecord(participate_relation.getRepair_report_id());
			BackValue += entityDao.deleteByID(id, Participate_Relation.class);
		}
		if(BackValue != ids.length){
			BackValue = BackValue - ids.length;
		}
		//返回删除成功的个数
	    return BackValue;
	}

	@Override
	public Map<String, Object> updateCommon(String id, String user_id,
			Map<String, Object> repair_report,
			Map<String, Object> common_maintain) {
		Map<String, Object> result = new HashMap<String, Object>();
		String repair_time = repair_report.get("repair_time").toString();
		repair_report.remove("repair_time");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
			try {
				Participate_Relation participate_relation = entityDao.getByID(id, Participate_Relation.class);
				participate_relation.setUser_id(user_id);
				participate_relation.setIs_writer(1);
				
				date = df.parse(repair_time);
				Repair_Report repair_reports = new Repair_Report();
				repair_reports.setRepair_time(date);
				repair_reports.setProperties(repair_report);
				
				Common_Maintain common_maintains = new Common_Maintain();
				common_maintains.setProperties(common_maintain);
				
				String resultString = entityDao.updatePropByID(participate_relation, id) + 
						entityDao.updatePropByID(repair_reports, repair_reports.getId()) + 
						entityDao.updatePropByID(common_maintains, common_maintains.getId()) == 3 ? "ture":"false";
				
				result.put("result", resultString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return result;
	}

	@Override
	public Map<String, Object> updateDanger(String id, String user_id,
			Map<String, Object> repair_report,
			Map<String, Object> danger_inspection,
			Map<String, Object> danger_maintain,
			Map<String, Object> danger_demarcate) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		String repair_time = repair_report.get("repair_time").toString();
		repair_report.remove("repair_time");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
			try {
				Participate_Relation participate_relation = entityDao.getByID(id, Participate_Relation.class);
				participate_relation.setUser_id(user_id);
				participate_relation.setIs_writer(1);
				
				date = df.parse(repair_time);
				Repair_Report repair_reports = new Repair_Report();
				repair_reports.setRepair_time(date);
				repair_reports.setProperties(repair_report);
				
				int num = 0;
				Danger_Inspection danger_inspections = new Danger_Inspection();
				if(danger_inspection.containsKey("facility_run_status")){
					danger_inspections.setProperties(danger_inspection);
					entityDao.updatePropByID(danger_inspections, danger_inspections.getId());
					num++;
				}
				
				Danger_Maintain danger_maintains = new Danger_Maintain();
				if(danger_maintain.containsKey("fault_description")){
					danger_maintains.setProperties(danger_inspection);
					entityDao.updatePropByID(danger_maintains, danger_maintains.getId());
					num++;
				}
				
				Danger_Demarcate danger_demarcates = new Danger_Demarcate();
				if(danger_demarcate.containsKey("before_demarcate")){
					danger_demarcates.setProperties(danger_inspection);
					entityDao.updatePropByID(danger_demarcates, danger_demarcates.getId());
					num++;
				}
				
				String resultString = entityDao.updatePropByID(participate_relation, id) + 
						entityDao.updatePropByID(repair_reports, repair_reports.getId()) + num == 5 ? "ture":"false";
				result.put("result", resultString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return result;
	}
}
