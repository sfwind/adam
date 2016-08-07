package com.dianping.ba.es.qyweixin.adapter.biz.domain;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by justin on 14-5-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/local/appcontext-*.xml",
        "classpath*:/config/spring/local/dbcon/appcontext-*.xml" })
@Ignore
@Transactional
public class TestRollbackBase extends AbstractTransactionalJUnit4SpringContextTests {

}
