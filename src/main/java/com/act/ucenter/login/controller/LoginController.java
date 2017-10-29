package com.act.ucenter.login.controller;

import com.act.ucenter.common.BaseController;
import com.act.ucenter.login.vo.DataVo;
import com.act.ucenter.login.vo.LoginVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huhuan
 * @date
 * The type Login controller.
 */
@RestController()
@RequestMapping(value = "/user" )

public class LoginController extends BaseController{
    @RequestMapping("/login")
    public Object login(LoginVo vo, HttpServletRequest request, HttpServletResponse response){
        String username = vo.getUsername();
        String password = vo.getPassword();
        System.out.println(username+"haha"+password);
        vo.setData(new DataVo("haha "));



        return ajax(Status.success,vo);
    }


}



