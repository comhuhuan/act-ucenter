package com.act.ucenter.common.configration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JpaConfigration
 * Description:
 * author: Administrator
 * 2017/10/26 0026
 */
@Configuration
@EnableJpaRepositories("com.act.ucenter.*.**")
public class JpaConfigration {


}
