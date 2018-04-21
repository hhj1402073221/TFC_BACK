package com.cqut.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cqut.dao.base.EntityDao;
import com.cqut.dao.base.SearchDao;
import com.cqut.entity.user.Department;
import com.cqut.entity.user.User;
import com.cqut.util.EntityIDFactory;

@Service("departmentService")
public class DepartmentService implements IDepartmentService{
	@Resource(name = "entityDao")
    private EntityDao entityDao;
	@Resource(name = "searchDao")
	private SearchDao searchDao;
	
	@Override
	public Map<String, Object> getDepart(int page, int row, String name) {
		String condition = " 1=1 ";
		if (name != null && !name.isEmpty())
			condition += " AND department.name like '%"
					+ name + "%'";
		List<Map<String, Object>> resultList = searchDao
				.searchWithpagingInMysql(
						new String[] { " department.id, "
								+ " department.name, "
								+ " department.phone_number, "
								+ " department.remark"},
						" department ", null, null, null, condition,
						null, "department.id", "ASC", row, page);
		int count = searchDao.getForeignCount(" department.id ", " department ", null, null, null, condition);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", resultList);
		map.put("total", count);
		return map;
	}

	@Override
	public Map<String, Object> addDepart(Map<String, Object> department) {
		Department departments = new Department();
		String id = EntityIDFactory.createId();
		departments.setId(id);
		departments.setProperties(department);
		
		Map<String, Object> result = new HashMap<String, Object>();;
		String resultString = entityDao.save(departments) == 1 ? "true": "false";
		result.put("result", resultString);
		
		return result;
	}

	@Override
	public Map<String, Object> updateDepart(String id,
			Map<String, Object> department) {
		Department departments = entityDao.getByID(id, Department.class);
		departments.setProperties(department);
		String resultString = entityDao.updatePropByID(departments,
				id) == 1 ? "true" : "false";
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("result", resultString);
		return result;
	}

	@Override
	public int deleteDepart(String department_id) {
		String[] ids = department_id.split(",");
		int BackValue = 0;
		for(String id:ids){
			BackValue += entityDao.deleteByID(id, Department.class);
		}
		if(BackValue != ids.length){
			BackValue = BackValue - ids.length;
		}
		//返回删除成功的个数
	    return BackValue;
	}

}
