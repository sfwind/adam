package com.dianping.ba.es.qyweixin.adapter.biz.dao;


import com.dianping.ba.es.qyweixin.adapter.biz.entity.avatar.Avatar;
import org.apache.ibatis.annotations.Param;

public interface AvatarDao {

	Avatar queryByUserId(@Param("userId") String userId);

	void insert(@Param("avatar") Avatar avatar);

	void update(@Param("avatar") Avatar avatar);

}
