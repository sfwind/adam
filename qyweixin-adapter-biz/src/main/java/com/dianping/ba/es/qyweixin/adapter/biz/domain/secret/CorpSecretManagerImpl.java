package com.dianping.ba.es.qyweixin.adapter.biz.domain.secret;

import com.dianping.ba.es.qyweixin.adapter.biz.dao.SecretDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorpSecretManagerImpl implements CorpSecretManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(CorpSecretManagerImpl.class);

    @Autowired
    private SecretDao secretDao;

    public String getCorpSecretFromDB(String agentId) {
        List<Secret> secrets = secretDao.queryByAgentId(agentId);
        if (secrets.size() > 1) {
            LOGGER.warn("AgentId: " + agentId + ", 有多条记录！");
        } else if (secrets.size() == 1 && secrets.get(0) != null) {
            return secrets.get(0).getSecret();
        } else {
            LOGGER.warn("AgentId: " + agentId + ", 没有记录！");
            return "";
        }
        LOGGER.warn("AgentId: " + agentId + ", 没有记录！");
        return "";
    }

    /**
     * 改成从数据库读取，读取Lion不好维护（数据之间不独立）
     */
//    @Override
//    public String getCorpSecret(String agentId) {
//        String json = LionUtils.getAgentSecretMap();
//        Map<String, String> map = new HashMap<String, String>();
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            map = mapper.readValue(json,
//                    new TypeReference<HashMap<String, String>>() {
//                    });
//        } catch (IOException e) {
//            return null;
//        }
//        return map.get(agentId);
//    }
}
