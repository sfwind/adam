package com.dianping.ba.es.qyweixin.adapter.biz.domain.qrcode;

/**
 * Created by justin on 16/8/12.
 */
public interface QRCodeService {
    String generateQRCode(String scene);

    String generateQRCode(String scene, int expire_seconds);

    int DEFAULT_EXPIRED_TIME = 60*60*24;
}
