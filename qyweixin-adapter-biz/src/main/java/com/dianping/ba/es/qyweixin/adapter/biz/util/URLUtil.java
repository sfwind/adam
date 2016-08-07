package com.dianping.ba.es.qyweixin.adapter.biz.util;

import org.apache.commons.lang.StringUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by justin on 16/8/7.
 */
public class URLUtil {
    public static String urlReplace(String url, Map<String, String> replacer){
        if(StringUtils.isNotEmpty(url) && replacer!=null) {
            for (Iterator<Map.Entry<String, String>> it =
                 replacer.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, String> entry = it.next();
                url = StringUtils.replace(url, "{"+entry.getKey()+"}", entry.getValue());
            }
        }
        return url;
    }
}
