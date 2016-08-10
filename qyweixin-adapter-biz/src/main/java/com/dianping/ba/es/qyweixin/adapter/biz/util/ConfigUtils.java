package com.dianping.ba.es.qyweixin.adapter.biz.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ConfigUtils {

	public static String getToken() {
		return "dianpingqywx";
	}

	public static String getEncodingAesKey() {
		return "NdljBZaXGGkx8c9R70fpZ54M6s1OHlxxpKMG7bIoadd";
	}

	public static String getAppid() {
		return "wx063e8dd5f3d84210";
	}

	public static String getSecret() {
		return "548fd59826f6213f116f7325d1765108";
	}

	public static String getAccessTokenUrl() {
		return "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={secret}";
	}

	public static String getOAuthCodeUrl() {
		return "https://open.weixin.qq.com/connect/oauth2/authorize?appid={appid}&redirect_uri={redirect_url}&response_type=code&scope=snsapi_base&state={state}#wechat_redirect";
	}

	public static String getUserInfoUrl() {
		return "https://api.weixin.qq.com/cgi-bin/user/info?access_token={access_token}&openid={openid}&lang=zh_CN";
	}

	public static String getTemplateIdUrl(){
		return "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token={access_token}";
	}

	public static String sendTemplateMessageUrl(){
		return "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={access_token}";
	}

	public static String encode(String url) {
		try {
			String value = URLEncoder.encode(url, "utf-8");
			return value;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public static String getAuthenticationCode() {
		return null;
	}

	public static int getJsSignatureInterval() {
		return 1000;
	}

	public static String getJsTicketUrl() {
		return null;
	}

	public static boolean getBatchSendSwitch() {
		return false;
	}

	public static String getCorpId() {
		return null;
	}

	public static boolean getWhiteListSwitch() {
		return false;
	}

	public static String getWhiteListAgents() {
		return null;
	}

	public static int getBatchSendLimit() {
		return 1000;
	}
}
