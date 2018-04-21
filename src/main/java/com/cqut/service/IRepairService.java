package com.cqut.service;

import java.util.List;
import java.util.Map;

public interface IRepairService {
	public List<Map<String, Object>> getRecords(int row, int page, String terminal_name, String repair_type);
	public Map<String, Object> getCommon(String id);
	public Map<String, Object> getDanger(String id);
	public Map<String, Object> addCommon(String user_id, 
			Map<String, Object> repair_report, Map<String, Object> common_maintain);
	public Map<String, Object> addDanger(String user_id, 
			Map<String, Object> repair_report, Map<String, Object> danger_inspection,
			Map<String, Object> danger_maintain, Map<String, Object> danger_demarcate);
	public int deleteRecord(String id);
	public Map<String, Object> updateCommon(String id, String user_id,
			Map<String, Object> repair_report, Map<String, Object> common_maintain);
    public Map<String, Object> updateDanger(String id, String user_id, 
			Map<String, Object> repair_report, Map<String, Object> danger_inspection,
			Map<String, Object> danger_maintain, Map<String, Object> danger_demarcate);
}
