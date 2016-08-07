package com.dianping.ba.es.qyweixin.adapter.biz.dao;

import com.dianping.ba.es.qyweixin.adapter.biz.domain.oauth.Callback;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by justin on 14-7-29.
 */
public interface CallbackDao {
    List<Callback> queryByUUID(@Param("uuid") String uuid);

    int insert(@Param("callback") Callback callback);

    void delete(@Param("uuid") String uuid);

    void update(@Param("callback") Callback callback);

}
