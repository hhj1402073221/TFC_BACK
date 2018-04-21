package com.cqut.controller;

import java.util.HashMap;
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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cqut.service.IRepairService;

@Controller
public class RepairController {
    @Resource
    private IRepairService service;

    /**
     * 
     * 方法简述：
     * 
     * @author tangcailin
     * @date 2017年10月24日下午4:41:46
     */
    @RequestMapping(value = "/repair/getmaterialreplaced", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> getRepairStatis() {
        // 按照时间、材料、站点分别查询记录并统计
        // 最终结果放在map中，对应time、material、terminal
        // time、material、terminal每个都是一个map
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", "this is data");
        return map;
    }

    /**
     * 
     * 方法简述：维修统计下的人员工作量，通过部门ID获取当前部门人员和对应的统计量
     * 
     * @author tangcailin
     * @date 2017年10月24日下午4:44:22
     */
    @RequestMapping(value = "/repair/getworkload", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> getWorkload(
            @RequestParam("department_id") String department_id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", "this " + department_id + " data");
        return map;
    }

    /**
     * 
     * 方法简述：获取维修记录列表
     * 
     * @param row
     * @param page
     * @param terminal_name
     * @param repair_type
     * @return
     * @author huhongjie
     * @date 2017-7-3 上午10:48:07
     */
    @RequestMapping(value = "/repair/getrecords", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getRecords(@RequestParam("row") int row,
            @RequestParam("page") int page,
            @RequestParam("terminal_name") String terminal_name,
            @RequestParam("repair_type") String repair_type) {
        List<Map<String, Object>> result = service.getRecords(row, page,
                terminal_name, repair_type);
        if (result == null) {
            return "-1";
        }
        return JSON.toJSON(result).toString();
    }

    /**
     * 
     * 方法简述：根据记录表的id获取通用维护的详情
     * 
     * @param id
     * @return
     * @author huhongjie
     * @date 2017-7-3 上午10:50:13
     */
    @RequestMapping(value = "/repair/getcommon/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getCommon(@PathVariable(value = "id") String id) {
        Map<String, Object> result = service.getCommon(id);
        if (result == null) {
            return "-1";
        }
        return JSON.toJSON(result).toString();
    }

    /**
     * 
     * 方法简述：根据记录表的id获取危险源报告的详情
     * 
     * @param id
     * @return
     * @author huhongjie
     * @date 2017-7-3 上午11:22:42
     */
    @RequestMapping(value = "/repair/getdanger/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getDanger(@PathVariable(value = "id") String id) {
        Map<String, Object> result = service.getDanger(id);
        if (result == null) {
            return "-1";
        }
        return JSON.toJSON(result).toString();
    }

    /**
     * 
     * 方法简述：新增一个通用维护报告
     * 
     * @param user_id
     * @param repair_common
     * @return
     * @author huhongjie
     * @date 2017-7-2 下午5:06:19
     */
    @RequestMapping(value = "/repair/addcommon/{user_id}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addCommon(@PathVariable(value = "user_id") String user_id,
            @RequestBody Map<String, Object> repair_common) {
        Map<String, Object> repair_report = (Map<String, Object>) repair_common
                .get("repair_report");
        Map<String, Object> common_maintain = (Map<String, Object>) repair_common
                .get("common_maintain");
        Map<String, Object> result = service.addCommon(user_id, repair_report,
                common_maintain);
        return JSONObject.toJSONString(result);
    }

    /**
     * 
     * 方法简述：新增一个危险源报告
     * 
     * @param user_id
     * @param danger
     * @return
     * @author huhongjie
     * @date 2017-7-2 下午5:30:00
     */
    @RequestMapping(value = "/repair/adddanger/{user_id}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addDanger(@PathVariable(value = "user_id") String user_id,
            @RequestBody Map<String, Object> danger) {
        Map<String, Object> repair_report = (Map<String, Object>) danger
                .get("repair_report");
        Map<String, Object> danger_inspection = (Map<String, Object>) danger
                .get("danger_inspection");
        Map<String, Object> danger_maintain = (Map<String, Object>) danger
                .get("danger_maintain");
        Map<String, Object> danger_demarcate = (Map<String, Object>) danger
                .get("danger_demarcate");
        Map<String, Object> result = service.addDanger(user_id, repair_report,
                danger_inspection, danger_maintain, danger_demarcate);
        return JSONObject.toJSONString(result);
    }

    /**
     * 
     * 方法简述：删除一条或者多条维护报告
     * 
     * @param id
     * @return
     * @author huhongjie
     * @date 2017-7-3 上午9:04:06
     */
    @RequestMapping(value = "/repair/deleterecord/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int deleteRecord(@PathVariable(value = "id") String id) {
        int result = service.deleteRecord(id);
        return result;
    }

    /**
     * 
     * 方法简述：修改通用维护报告
     * 
     * @param id
     * @param user_id
     * @param repair_common
     * @return
     * @author huhongjie
     * @date 2017-7-3 上午10:01:41
     */
    @RequestMapping(value = "/repair/updatecommon", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateCommon(@RequestParam("id") String id,
            @RequestParam("user_id") String user_id,
            @RequestBody Map<String, Object> repair_common) {
        Map<String, Object> repair_report = (Map<String, Object>) repair_common
                .get("repair_report");
        Map<String, Object> common_maintain = (Map<String, Object>) repair_common
                .get("common_maintain");
        Map<String, Object> result = service.updateCommon(id, user_id,
                repair_report, common_maintain);
        return JSONObject.toJSONString(result);
    }

    /**
     * 
     * 方法简述：修改危险源报告
     * 
     * @param id
     * @param user_id
     * @param danger
     * @return
     * @author huhongjie
     * @date 2017-7-3 下午4:38:03
     */
    @RequestMapping(value = "/repair/updatedanger", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateDanger(@RequestParam("id") String id,
            @RequestParam("user_id") String user_id,
            @RequestBody Map<String, Object> danger) {
        Map<String, Object> repair_report = (Map<String, Object>) danger
                .get("repair_report");
        Map<String, Object> danger_inspection = (Map<String, Object>) danger
                .get("danger_inspection");
        Map<String, Object> danger_maintain = (Map<String, Object>) danger
                .get("danger_maintain");
        Map<String, Object> danger_demarcate = (Map<String, Object>) danger
                .get("danger_demarcate");
        Map<String, Object> result = service.updateDanger(id, user_id,
                repair_report, danger_inspection, danger_maintain,
                danger_demarcate);
        return JSONObject.toJSONString(result);
    }
}
