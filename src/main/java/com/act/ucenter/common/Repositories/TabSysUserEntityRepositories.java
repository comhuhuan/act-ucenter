package com.act.ucenter.common.Repositories;

import com.act.ucenter.common.entity.TabSysUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * TabSysUserEntityRepositories
 * Description:
 * author: Administrator
 * 2017/10/26 0026
 */
public interface TabSysUserEntityRepositories extends JpaRepository<TabSysUserEntityRepositories,Long> {
   List<TabSysUserEntity> findFirst10ByUsername(String username);
}
