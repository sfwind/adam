package com.dianping.ba.es.qyweixin.adapter.web.controller;

import com.dianping.ba.es.qyweixin.adapter.biz.domain.qrcode.QRCodeService;
import com.dianping.ba.es.qyweixin.adapter.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by justin on 16/8/13.
 */
@Controller
public class QRCodeController {
    @Autowired
    private QRCodeService qrCodeService;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @RequestMapping("/qrcode/{scene}")
    public ResponseEntity<Map<String, Object>> generate(@PathVariable String scene) {
        try {
            String url = qrCodeService.generateQRCode(scene);
            return WebUtils.result(url);
        }catch (Exception e){
            LOGGER.error("qrcode failed", e);
        }
        return WebUtils.error("qrcode failed");
    }
}
