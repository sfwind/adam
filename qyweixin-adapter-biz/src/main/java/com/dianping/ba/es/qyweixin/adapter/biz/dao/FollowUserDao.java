package com.dianping.ba.es.qyweixin.adapter.biz.dao;

import com.dianping.ba.es.qyweixin.adapter.biz.dao.po.Account;
import org.apache.ibatis.annotations.Param;

/**
 * Created by justin on 16/8/12.
 */
public interface FollowUserDao {
    void insert(@Param("account") Account account);
}
