package com.test.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @Description:
 * @ClassName: SupperController
 * @Author: yanbobo
 * @CreateDate: 2019/8/1 14:52
 */
public class SupperController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SupperController.class);

    @Autowired
    private MessageSource messageSource;

    public Map getJsonMap(Boolean success, String message, Object result) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("success", success);
        data.put("message", message);
        data.put("result", result);
        return data;
    }

    public String getMsg(String key) {
        return messageSource.getMessage(key, null, Locale.CHINA);
    }

    public JSONObject readJSON(HttpServletRequest request) throws Exception {
        return readJSON(request, "UTF-8");
    }

    public JSONObject readJSON(HttpServletRequest request, String encoding) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(readString(request, encoding));
        return jsonObject;
    }

    public String readString(HttpServletRequest request, String encoding) throws Exception {
        InputStream input = null;
        BufferedReader reader = null;
        String res = "";
        try {
            input = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input, encoding));
            String content = null;
            StringBuffer strbuf = new StringBuffer();
            while ((content = reader.readLine()) != null) {
                strbuf.append(content);
            }
            res = strbuf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (input != null) {
                input.close();
            }
        }
        LOGGER.info("read string:" + res);
        return res;
    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
