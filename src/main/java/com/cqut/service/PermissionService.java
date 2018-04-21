package com.cqut.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cqut.dao.base.EntityDao;
import com.cqut.dao.base.SearchDao;
import com.cqut.entity.user.Module_Assignment;
import com.cqut.entity.user.Permission;
import com.cqut.entity.user.Permission_Assignment;
import com.cqut.util.EntityIDFactory;


@Service("permissionService")
public class PermissionService implements IPermissionService {
	@Resource(name = "entityDao")
    private EntityDao entityDao;
	@Resource(name = "searchDao")
	private SearchDao searchDao;

	@Override
	public Map<String, Object> getPermLev(int row,int page,String perm_name) {
		String[] properties = {
				"permission.id",
				"permission.permission_name",
				"permission.remark"
		};
		String tableName = "permission";
		String condition = "1 = 1";
		
		if (perm_name != null && perm_name.compareTo("null") != 0){
			condition = " and permission.permission_name like '%"+perm_name+"%'";
		}
		List<Map<String,Object>> resultList = searchDao.searchWithpagingInMysql
				(properties,tableName,null,null,null,condition,null,"permission.id", " DESC ", row, page);
		
		int count = searchDao.getForeignCount(Permission.getPrimaryKey(Permission.class), tableName, null, null, null, condition);
		
		Map<String,Object> result = new HashMap<String,Object>();
	    result.put("data", resultList);
	    result.put("total", count);
		return result;
    }

	/**
	 * 添加权限
	 * @param Module_ids
	 * @param PermLevinfo
	 * @return
	 */
	@Override
	public Map<String, Object> addPermLev(String Module_ids,Map<String, Object> PermLevinfo) {
		Permission pl = new Permission();
		
		if(PermLevinfo.get("permission_name")!=null){
			pl.setPermission_name(PermLevinfo.get("permission_name").toString());
		}

		String permission_id = EntityIDFactory.createId();
		pl.setId(permission_id);
		
		if(PermLevinfo.get("remark")!=null){
			pl.setRemark(PermLevinfo.get("remark").toString());
		}
		
		Map<String, Object> result = new HashMap<String, Object>();;
		String resultString = entityDao.save(pl) == 1 ? "true": "false";
		result.put("result", resultString);

        String[] module_ids = Module_ids.split(",");
		if(Module_ids!=null && !module_ids[0].equals("empty")){
			for(String module_id:module_ids){
				Module_Assignment ma = new Module_Assignment();
				String ma_id = EntityIDFactory.createId();
				ma.setId(ma_id);
				ma.setModule_id(module_id);
				ma.setPermission_id(permission_id);
				
				String resultString2 = entityDao.save(ma) == 1 ? "true": "false";
				result.put("perm_add", resultString2);
			}
		}
		return result;
	}

	/**
	 * 通过ID删除权限
	 * @param Ids
	 * @return
	 */
	@Override
	public Map<String, Object> delPermLevById(String Ids) {
		String[] ids = Ids.split(",");
		String[] properties = {"module_assignment.id"};

		//删除权限
		String resultString = entityDao.deleteEntities(ids,
				Permission.class) >= 1 ? "true" : "false";

		for(int i = 0; i < ids.length;i++){
			String condition = " module_assignment.permission_id like '"+ids[i]+"'";
			
			List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
			result = searchDao.searchWithpagingInMysql(properties, "module_assignment", null,
					null, null, condition, null, 
					null, null, 100, 1);
			//内循环删除具体的权限
			for(Map<String,Object> res:result){
				String deleteResult = entityDao.deleteByID(res.get("id").toString(),
						Module_Assignment.class) >= 1 ? "true" : "false";
			}

			//将该权限从拥有该权限的角色中删除
			entityDao.deleteByCondition("permission_id like " +  ids[i], Permission_Assignment.class);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", resultString);
		return result;
	}

	@Override
	public Map<String, Object> upPermLev(String id, Map<String, Object> PermLevinfo) {
		Permission PL = entityDao.getByID(id, Permission.class);
		Map<String,Object> result = new HashMap<String,Object>();

		if(PL==null){
			result.put("result","false");
		}
		PL.setPermission_name(PermLevinfo.get("permission_name").toString());
		
		if(PermLevinfo.get("remark")!=null){
			PL.setRemark(PermLevinfo.get("remark").toString());
		}
		
		String resultString = entityDao.updatePropByID(PL,
				id) == 1 ? "true" : "false";
		
		result.put("result", resultString);
		return result;
	}

	
	@Override
	public List<Map<String, Object>> getMoudle() {
		String[] properties = {
				"module.id",
				"module.name"
		};
		String tableName = "module";
		String condition = "1 = 1";
		
		condition = " module.parent is NOT NULL and module.parent  NOT like '"+""+"'";
		
		List<Map<String,Object>> resultList = searchDao.searchWithpagingInMysql
				(properties,tableName,null,null,null,condition,null,"module.id", " DESC ", 100
						, 1);
		return resultList;
	}

	@SuppressWarnings("unused")
	@Override
	public List<Map<String, Object>> upPermission(String permission_id,String module_ids) {
		Module_Assignment MA = new Module_Assignment();
		String[] ids = module_ids.split(",");
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		
		if(module_ids!=null){
			for(String module_id:ids){
				MA.setPermission_id(permission_id);
				String id = EntityIDFactory.createId();
				MA.setId(id);
				MA.setModule_id(module_id);
				
				String resultString = entityDao.save(MA) == 1 ? "true": "false";
				
				result.put("result", resultString);
				resultList.add(result);
				}
		}
		else{
			result.put("status", "false");
			resultList.add(result);
			return resultList;
		}
		
		return resultList;
	}

	@Override
	public List<Map<String, Object>> getOldPerm(String permission_id) {
		String[] properties = {
				"module.id",
				"module.name"
		};
		String tableName = "module_assignment";
		String condition = "1 = 1";
		if (permission_id != null && permission_id.compareTo("null") != 0){
			condition = "1 = 1 and module_assignment.permission_id like '"+permission_id+"'";
		}
		
		String joinEntity = " LEFT JOIN module on module_assignment.module_id = module.id ";
		
		List<Map<String,Object>> resultList = searchDao.searchWithpagingInMysql
				(properties,tableName,joinEntity,null,null,condition,null,"module_assignment.id", " DESC ", 1000
						, 1);

		return resultList;
	}

	@Override
	public Map<String, Object> delPerm(String permission_id,String ids) {
		 String[] Ids = ids.split(",");
			String[] properties = {
					"module_assignment.id"
			};
			String tableName = "module_assignment";
			
			Map<String,Object> moudle_ass = new HashMap<String, Object>();
			Map<String, Object> result = new HashMap<String, Object>();
			
			String condition = "1 = 1";
			for(String moudle_id:Ids){
				if (permission_id != null && permission_id.compareTo("null") != 0 && ids != null){
					condition = "1 = 1 and module_assignment.permission_id like '"+permission_id+"' and "
							+ "module_assignment.module_id LIKE '"+moudle_id+"'";
				}
				
				List<Map<String,Object>> resultList = searchDao.searchWithpagingInMysql
						(properties,tableName,null,null,null,condition,null,"module_assignment.id", " DESC ", 100
								, 1);
				
				moudle_ass = resultList.get(0);
				int resultInt = entityDao.deleteByID(moudle_ass.get("id").toString(), Module_Assignment.class);
				
				result.put("result", resultInt);
			}	
			    return result; 
		}

}
