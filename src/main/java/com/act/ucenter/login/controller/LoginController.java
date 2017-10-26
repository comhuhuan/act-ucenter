package com.act.ucenter.login.controller;

import com.act.ucenter.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * LoginController
 * Description:
 * author: Administrator
 * 2017/10/26 0026
 */
@Controller
@RequestMapping("/user")
public class LoginController extends BaseController {

    @RequestMapping("/login")
    @ResponseBody
    public String  login(){
        return "1";

    }


}

