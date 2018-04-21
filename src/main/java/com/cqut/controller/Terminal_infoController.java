package com.cqut.controller;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cqut.service.ITerminal_infoService;

@Controller
public class Terminal_infoController {
	@Resource
	private ITerminal_infoService service;
	
    /**
     * 
     * 方法简述：获取所有模块信息 或者相应模块名的信息
     * @param row
     * @param page
     * @param info
     * @return 
     * @author wuzhenwei
     * @date 2017年7月2日 上午10:04:16
     */
	@RequestMapping(value ="/terminal/allterminal",method=RequestMethod.POST)
	@ResponseBody
	public String getTerminal(
			@RequestParam(value="row",required=false) int row,
			@RequestParam(value="page",required=false) int page,
			@RequestBody Map<String,String> info){
		
		String code_name = info.get("code_name");
		String type_id = info.get("type_id");
		Map<String,Object> result = service.getTerminal(row,page,code_name,type_id);
		if(result == null){
			return "-1"; 
		}
		else{
			return JSONObject.toJSONString(result);
		}
	}	
	
	/**
	 * 
	 * 方法简述：获取所有区域名
	 * @return 
	 * @author wuzhenwei
	 * @date 2017年7月2日 上午10:45:38
	 */
	@RequestMapping(value ="/Tarea/all",method=RequestMethod.GET)
	@ResponseBody
	public String getArea(){
		List<Map<String,Object>> result = service.getArea();
		if(result == null){
			return "-1"; 
		}
		else{
			return JSONObject.toJSONString(result);
		}
	}
	
	
	/**
	 * 
	 * 方法简述：获取所有站点类型名称
	 * @return 
	 * @author wuzhenwei
	 * @date 2017年7月2日 上午10:46:40
	 */
	@RequestMapping(value ="/Ttype/all",method=RequestMethod.GET)
	@ResponseBody
	public String getType(){
		List<Map<String,Object>> result = service.getType();
		if(result == null){
			return "-1"; 
		}
		else{
			return JSONObject.toJSONString(result);
		}
	}

	/**
	 * 
	 * 方法简述：获取所有项目ID与name
	 * @return 
	 * @author wuzhenwei
	 * @date 2017年7月2日 上午10:59:17
	 */
	@RequestMapping(value ="/Tproject/all",method=RequestMethod.GET)
	@ResponseBody
	public String getProject(){
		List<Map<String,Object>> result = service.getProject();
		if(result == null){
			return "-1"; 
		}
		else{
			return JSONObject.toJSONString(result);
		}
	}
	
	/**
	 * 
	 * 方法简述：新增一个站点
	 * @param terminal
	 * @return 
	 * @author wuzhenwei
	 * @date 2017年7月2日 上午10:05:02
	 */
	@RequestMapping(value="/terminal/newterminal",method=RequestMethod.POST)
	@ResponseBody
	public String addTerminal(@RequestBody Map<String,Object> terminal){
		Map<String, Object> result = service.addTerminal(terminal);
		
		return JSONObject.toJSONString(result);
	}

	/**
	 * 
	 * 方法简述：删除一个或多个站点
	 * @param terminalIds
	 * @return 
	 * @author wuzhenwei
	 * @date 2017年7月2日 上午10:05:40
	 */
	@RequestMapping(value="/terminal/delterminals/{terminalIds}",method=RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> delTerminalById(@PathVariable String terminalIds){
		Map<String, Object> result = service.delTerminalById(terminalIds);
		return result;
	}
	
	/**
	 * 
	 * 方法简述：修改某条站点信息
	 * @param id
	 * @param terminal
	 * @return 
	 * @author wuzhenwei
	 * @date 2017年7月2日 上午10:05:59
	 */
	@RequestMapping(value="/terminal/upterminal/{id}",method = RequestMethod.PUT)
	@ResponseBody
	public String upTerminal(@PathVariable String id,@RequestBody Map<String,Object> terminal){
		
		Map<String, Object> result = service.upTerminal(id,terminal);
		if(result == null){
			return "-1";
		}else{
			return JSONObject.toJSONString(result);
		}
	}
}
