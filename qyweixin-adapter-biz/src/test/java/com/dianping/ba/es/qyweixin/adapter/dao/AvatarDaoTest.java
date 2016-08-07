package com.dianping.ba.es.qyweixin.adapter.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dianping.ba.es.qyweixin.adapter.biz.dao.AvatarDao;
import com.dianping.ba.es.qyweixin.adapter.biz.entity.avatar.Avatar;
import com.dianping.ba.es.qyweixin.adapter.biz.domain.TestBase;

/**
 * Created by justin on 14-7-29.
 */
public class AvatarDaoTest extends TestBase {
	@Autowired
	private AvatarDao avatarDao;

//	@Test
//	public void insertTest() {
//		Avatar a = new Avatar();
//		a.setId(1);
//		a.setUserId("2");
//		a.setAvatar("123");
//		avatarDao.insert(a);
//	}

	@Test
	public void queryTest() {
		Avatar a = avatarDao.queryByUserId("2");
		System.out.println(a.getAvatar());
	}

//	@Test
//	public void updateTest() {
//		Avatar a = avatarDao.queryByUserId("2");
//		a.setAvatar("321");
//		avatarDao.update(a);
//	}

}
