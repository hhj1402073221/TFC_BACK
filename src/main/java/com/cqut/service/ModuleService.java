package com.cqut.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cqut.dao.base.EntityDao;
import com.cqut.dao.base.SearchDao;
import com.cqut.entity.user.Module;
import com.cqut.util.EntityIDFactory;

@Service("moduleService")
public class ModuleService implements IModuleService{
	@Resource(name = "entityDao")
    private EntityDao entityDao;
	@Resource(name = "searchDao")
	private SearchDao searchDao;
	
	@Override
	public Map<String, Object> getModule(int row, int page, String name) {
		String[] properties = {
				"module.id",
				"module.name",
				"module.parent",
				"module.haschild",
				"module.level0",
				"module.is_endofmoduleLevel",
				"module.modulecode",
				"module.remark"
		};
		String tableName = "module";
		String condition = "1 = 1";
		
		if (name != null && name.compareTo("null") != 0){
			condition = "1 = 1 and module.name like '%"+name+"%'";
		}
		
		List<Map<String,Object>> resultList = searchDao.searchWithpagingInMysql
				(properties,tableName,null,null,null,condition,null,"module.id", " DESC ", row
						, page);
		
		int count = searchDao.getForeignCount(Module.getPrimaryKey(Module.class), tableName, null, null, null, condition);		
		
		Map<String,Object> result = new HashMap<String,Object>();
	    result.put("data", resultList);
	    result.put("total", count);
		return result;
    }

	@Override
	public Map<String, Object> addModule(Map<String, Object> moduleinfo) {
		Module module = new Module();
		
		module.setName(moduleinfo.get("name").toString());
		module.setParent(moduleinfo.get("parent").toString());
		module.setHaschild(Integer.parseInt(moduleinfo.get("haschild").toString()));
		module.setLevel0(moduleinfo.get("level0").toString());
		module.setIs_endofmoduleLevel(Integer.parseInt(moduleinfo.get("is_endofmoduleLevel").toString()));
		module.setModulecode(moduleinfo.get("modulecode").toString());
		if(moduleinfo.get("remark")!=null){
			module.setRemark(moduleinfo.get("remark").toString());
		}
		
		String module_id = EntityIDFactory.createId();
		module.setId(module_id);
		
		Map<String, Object> result = new HashMap<String, Object>();;
		String resultString = entityDao.save(module) == 1 ? "true": "false";
		result.put("result", resultString);
		
		return result;
	}

	@Override
	public Map<String, Object> delModuleById(String moduleIds) {
		String[] ModuleIds = moduleIds.split(",");
		
		String resultString = entityDao.deleteEntities(ModuleIds,
				Module.class) >= 1 ? "true" : "false";
				
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", resultString);
		return result;
	}

	@Override
	public Map<String, Object> upModule(String id, Map<String, Object> moduleinfo) {
		Module module = entityDao.getByID(id, Module.class);
		Map<String,Object> result = new HashMap<String,Object>();
		
		if(module==null){
			result.put("result","false");
		}
		module.setName(moduleinfo.get("name").toString());
		module.setParent(moduleinfo.get("parent").toString());
		module.setHaschild(Integer.parseInt(moduleinfo.get("haschild").toString()));
		module.setLevel0(moduleinfo.get("level0").toString());
		module.setIs_endofmoduleLevel(Integer.parseInt(moduleinfo.get("is_endofmoduleLevel").toString()));
		module.setModulecode(moduleinfo.get("modulecode").toString());
		if(moduleinfo.get("remark")!=null){
			module.setRemark(moduleinfo.get("remark").toString());
		}
		
		String resultString = entityDao.updatePropByID(module,
				id) == 1 ? "true" : "false";
		
		result.put("result", resultString);
		return result;
	}

}
