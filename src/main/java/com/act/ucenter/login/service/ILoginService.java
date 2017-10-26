package com.act.ucenter.login.service;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ILoginService
 * Description:
 * author: Administrator
 * 2017/10/26 0026
 */
public interface ILoginService extends JpaRepository{

    Object findByUserId(String trim);
}
