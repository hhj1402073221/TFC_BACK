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
import com.cqut.entity.user.Terminal_info;
import com.cqut.util.EntityIDFactory;

@Service("terminal_infoService")
public class Terminal_infoService implements ITerminal_infoService{
	@Resource(name = "entityDao")
    private EntityDao entityDao;
	@Resource(name = "searchDao")
	private SearchDao searchDao;
	
	@Override
	public Map<String, Object> getTerminal(int row, int page, String code_name,String type_id) {
		String[] properties = {
				"terminal_info.id",
				"terminal_info.station_code",
				"terminal_info.data_card",
				"terminal_info.terminal_name",
				"terminal_info.area_id",
				"area.area_name",
				"terminal_info.type_id",
				"type.type_name",
				"terminal_info.project_id",
				"project.name",
				"terminal_info.personliable",
				"terminal_info.personliable_phone",
				"DATE_FORMAT(terminal_info.install_time, '%Y-%c-%d %H:%i:%s') as install_time",
				"terminal_info.is_guarantee",
				"terminal_info.longitude",
				"terminal_info.latitude",
				"terminal_info.picture1",
				"terminal_info.picture2",
				"terminal_info.picture3",
				"terminal_info.remark"
		};
		String tableName = "terminal_info";
		String condition = "1 = 1";
		
		if (code_name != null && code_name.compareTo("null") != 0){
			condition = "1 = 1 and (terminal_info.terminal_name like '%"+code_name+"%' or "
					+ "terminal_info.station_code like '%"+code_name+"%')";
		}
		if (type_id != null && type_id.compareTo("null") != 0){
			condition += " and terminal_info.type_id like '%"+type_id+"%'";
		}
		String joinEntity = " LEFT JOIN area on terminal_info.area_id = area.id "
				+ " LEFT JOIN type on terminal_info.type_id = type.id "
				+ " LEFT JOIN project on terminal_info.project_id = project.id ";
		
		List<Map<String,Object>> resultList = searchDao.searchWithpagingInMysql
				(properties,tableName,joinEntity,null,null,condition,null,"terminal_info.id", " DESC ", row
						, page);
		
		int count = searchDao.getForeignCount(Terminal_info.getPrimaryKey(Terminal_info.class), tableName, null, null, null, condition);		
		
		Map<String,Object> result = new HashMap<String,Object>();
	    result.put("data", resultList);
	    result.put("total", count);
		return result;
    }

	@Override
	public Map<String, Object> addTerminal(Map<String, Object> Terminal) {
		Terminal_info terminal = new Terminal_info();
		
		terminal.setStation_code(Terminal.get("station_code").toString());
		terminal.setData_card(Terminal.get("data_card").toString());
		terminal.setTerminal_name(Terminal.get("terminal_name").toString());
		terminal.setArea_id(Terminal.get("area_id").toString());
		
		
		terminal.setType_id(Terminal.get("type_id").toString());
		terminal.setProject_id(Terminal.get("project_id").toString());
		terminal.setPersonliable(Terminal.get("personliable").toString());
		terminal.setPersonliable_phone(Terminal.get("personliable_phone").toString());
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try{
			date = df.parse(Terminal.get("install_time").toString());
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		
		terminal.setInstall_time(date);
		terminal.setLongitude(Float.parseFloat(Terminal.get("longitude").toString()));
		terminal.setLatitude(Float.parseFloat(Terminal.get("latitude").toString()));
		terminal.setIs_guarantee(Integer.parseInt(Terminal.get("is_guarantee").toString()));
		
		if(Terminal.get("picture1")!=null){
			terminal.setPicture1(Terminal.get("picture1").toString());
		}
		
		if(Terminal.get("picture2")!=null){
			terminal.setPicture1(Terminal.get("picture2").toString());
		}
		
		if(Terminal.get("picture3")!=null){
			terminal.setPicture1(Terminal.get("picture3").toString());
		}
		
		if(Terminal.get("remark")!=null){
			terminal.setRemark(Terminal.get("remark").toString());
		}
		
		String id = EntityIDFactory.createId();
		terminal.setId(id);
		
		Map<String, Object> result = new HashMap<String, Object>();;
		String resultString = entityDao.save(terminal) == 1 ? "true": "false";
		result.put("result", resultString);
		
		return result;
	}

	@Override
	public Map<String, Object> delTerminalById(String terminalIds) {
		String[] TerminalIds = terminalIds.split(",");
		
		String resultString = entityDao.deleteEntities(TerminalIds,
				Terminal_info.class) >= 1 ? "true" : "false";
				
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", resultString);
		return result;
	}

	@Override
	public Map<String, Object> upTerminal(String id, Map<String, Object> Terminal) {
		Terminal_info terminal = entityDao.getByID(id, Terminal_info.class);
		if(terminal == null){
			// 数据库没有该id 对应的数据
		    return null;
		}
		terminal.setStation_code(Terminal.get("station_code").toString());
		terminal.setData_card(Terminal.get("data_card").toString());
		terminal.setTerminal_name(Terminal.get("terminal_name").toString());
		terminal.setArea_id(Terminal.get("area_id").toString());
		terminal.setType_id(Terminal.get("type_id").toString());
		terminal.setProject_id(Terminal.get("project_id").toString());
		terminal.setPersonliable(Terminal.get("personliable").toString());
		terminal.setPersonliable_phone(Terminal.get("personliable_phone").toString());
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try{
			date = df.parse(Terminal.get("install_time").toString());
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		
		terminal.setInstall_time(date);
		terminal.setLongitude(Float.parseFloat(Terminal.get("longitude").toString()));
		terminal.setLatitude(Float.parseFloat(Terminal.get("latitude").toString()));
		terminal.setIs_guarantee(Integer.parseInt(Terminal.get("is_guarantee").toString()));
		if(Terminal.get("picture1")!=null){
			terminal.setPicture1(Terminal.get("picture1").toString());
		}
		
		if(Terminal.get("picture2")!=null){
			terminal.setPicture1(Terminal.get("picture2").toString());
		}
		
		if(Terminal.get("picture3")!=null){
			terminal.setPicture1(Terminal.get("picture3").toString());
		}
		
		if(Terminal.get("remark").toString() !=null){
			terminal.setRemark(Terminal.get("remark").toString());
		}
		String resultString = entityDao.updatePropByID(terminal,
				id) == 1 ? "true" : "false";
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("result", resultString);
		return result;
	}

	@Override
	public List<Map<String, Object>> getType() {
		String[] properties = {
				"type.id",
				"type.type_name"
		};
		String tableName = "type";
		String condition = "1 = 1";
		
		List<Map<String,Object>> resultList = searchDao.searchWithpagingInMysql
				(properties,tableName,null,null,null,condition,null,"type.id", " DESC ", 10
						, 1);
		return resultList;
	}

	@Override
	public List<Map<String, Object>> getArea() {
		String[] properties = {
				"area.id",
				"area.area_name"
		};
		String tableName = "area";
		String condition = "1 = 1";
		
		List<Map<String,Object>> resultList = searchDao.searchWithpagingInMysql
				(properties,tableName,null,null,null,condition,null,"area.id", " DESC ", 10
						, 1);
		return resultList;
	}

	@Override
	public List<Map<String, Object>> getProject() {
		String[] properties = {
				"project.id",
				"project.name"
		};
		String tableName = "project";
		String condition = "1 = 1";
		
		List<Map<String,Object>> resultList = searchDao.searchWithpagingInMysql
				(properties,tableName,null,null,null,condition,null,"project.id", " DESC ", 10
						, 1);
		return resultList;
	}

}
