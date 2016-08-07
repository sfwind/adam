package com.dianping.ba.es.qyweixin.adapter.biz.domain;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:/config/spring/local/appcontext-*.xml",
        "classpath*:/config/spring/local/dbcon/appcontext-*.xml" })
@Ignore
public class TestBase {
}
