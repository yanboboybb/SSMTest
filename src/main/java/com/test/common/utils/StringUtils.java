package com.test.common.utils;

import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang.StringUtils {

    public static boolean isBlank(String str) {
        if (org.apache.commons.lang.StringUtils.isBlank(str)) {
            return true;
        }
        if (org.apache.commons.lang.StringUtils.isBlank(str.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 方法用途: 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串
     *
     * @param paraMap     要排序的Map对象
     * @param isUrlEncode 是否需要URLENCODE
     * @param isKeyLower  是否需要将Key转换为全小写 true:key转化成小写，false:不转化
     * @return
     */
    public static String sortAscii(Map<String, String> paraMap, boolean isUrlEncode, boolean isKeyLower) {
        String buff = "";
        Map<String, String> tmpMap = paraMap;
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造URL 键值对的格式
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (StringUtils.isNotBlank(item.getKey())) {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (isUrlEncode) {
                        val = URLEncoder.encode(val, "utf-8");
                    }
                    if (isKeyLower) {
                        buf.append(key.toLowerCase() + "=" + val);
                    } else {
                        buf.append(key + "=" + val);
                    }
                    buf.append("&");
                }
            }
            buff = buf.toString();
            if (buff.isEmpty() == false) {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e) {
            return null;
        }
        return buff;
    }

    public static String delHTMLTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); //过滤

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); //过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签

        return delSpace(htmlStr); //返回文本字符串 
    }

    public static String delSpace(String htmlStr) {
        htmlStr = htmlStr.replaceAll("\r", "");
        htmlStr = htmlStr.replaceAll("\n", "");
        htmlStr = htmlStr.replaceAll("\t", "");
        return htmlStr.trim(); //返回文本字符串 
    }

}
