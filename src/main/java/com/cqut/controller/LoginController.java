package com.cqut.controller;

import com.cqut.dao.base.EntityDao;
import com.cqut.entity.user.Login;
import com.cqut.util.MD5;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    @Resource
    EntityDao entityDao;

    @RequestMapping(value = "access/token", method = RequestMethod.POST)
    @ResponseBody
    public Object getLogin(@RequestBody Map<String, String> login){

        String loginName =  login.get("userName");
        String passWord = login.get("password");

        String condition = "Login.user_account = '"+loginName+"'";

        List<Login> list = entityDao.getByCondition(condition,Login.class);

        if( list == null || list.size()==0){
            System.out.println("账户不存在");
            return 0;
        }
        else{
            Login DBlogin = list.get(0);

            if (DBlogin.getPassword().equals(MD5.MD5(passWord))) {
                    if(DBlogin.getState() == 0) {
                        System.out.println("账户被禁用");
                        return  -2;
                    }
                    else {
                        System.out.println("登陆成功");
                        return 1;
                    }
               }


           else  {
                System.out.println("密码错误");
                return -1;
                }

        }
    }
}