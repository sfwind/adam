package com.dianping.ba.es.qyweixin.adapter.biz.domain.qrcode;

import com.dianping.ba.es.qyweixin.adapter.biz.util.CommonUtils;
import com.dianping.ba.es.qyweixin.adapter.biz.util.ConfigUtils;
import com.dianping.ba.es.qyweixin.adapter.biz.util.RestfulHandler;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by justin on 16/8/12.
 */
@Service
public class QRCodeServiceImpl implements QRCodeService {
    @Autowired
    private RestfulHandler restfulHandler;

    public String generateQRCode(String scene, int expire_seconds) {
        QRRequest qrRequest = new QRRequest(scene, expire_seconds);
        String json = new Gson().toJson(qrRequest);
        String body = restfulHandler.post(GEN_QRCODE_URL, json);

        Map<String, Object> result = CommonUtils.jsonToMap(body);
        return (String)result.get("url");
    }

    public String generateQRCode(String scene) {
        return generateQRCode(scene, DEFAULT_EXPIRED_TIME);
    }
}
