package com.dianping.ba.es.qyweixin.adapter.biz.dao;

import com.dianping.ba.es.qyweixin.adapter.biz.domain.secret.Secret;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SecretDao {

    List<Secret> queryByAgentId(@Param("agentId") String agentId);

}
