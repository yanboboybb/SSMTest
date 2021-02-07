package com.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.common.utils.StringUtils;
import com.test.entity.TestSSM;
import com.test.service.TestSSMService;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * @Description:
 * @ClassName: TestSSMController
 * @Author: yanbobo
 * @CreateDate: 2019/8/1 14:55
 */
@Controller
@RequestMapping("/testssm")
public class TestSSMController extends SupperController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestSSMController.class);


    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        UUID uuid = UUID.randomUUID();
        Cookie cookie = new Cookie("sid", uuid.toString().replaceAll("-", ""));
        cookie.setMaxAge(1 * 24 * 60 * 60);
        cookie.setPath("/");
        cookie.setDomain("ybb.com");
        response.addCookie(cookie);

        Cookie cookie1 = new Cookie("sid", uuid.toString().replaceAll("-", ""));
        cookie1.setMaxAge(1 * 24 * 60 * 60);
        cookie1.setPath("/");
        cookie1.setDomain("ybb.com");
        response.addCookie(cookie);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/welcome");
        return modelAndView;
    }

    @RequestMapping("/left")
    public String left(HttpServletRequest request, HttpServletResponse response) {
        return "views/left";
    }


    @Autowired
    private TestSSMService testSSMService;

    @RequestMapping("/add/testssm")
    @ResponseBody
    public Map<String, Object> addTestSSM(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> json = this.getJsonMap(false, "系统繁忙，请稍后再试！", null);
        try {
            JSONObject jsonObject = readJSON(request);
            String name = jsonObject.getString("name");
            if (StringUtils.isBlank(name))
                return this.getJsonMap(false, "参数错误", null);

            TestSSM testSSM = new TestSSM();
            testSSM.setName(name);
            int rows = testSSMService.insertTestSSM(testSSM);
            if (rows > 0)
                json = this.getJsonMap(true, this.getMsg("success.submit"), null);
        } catch (Exception e) {
            LOGGER.error("TestSSMController.addTestSSM--error", e);
        }
        return json;
    }

    @RequestMapping("/select/page/list")
    @ResponseBody
    public Map<String, Object> selectListPage(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> json = this.getJsonMap(false, "系统繁忙，请稍后再试！", null);
        try {
            JSONObject jsonObject = readJSON(request);
            int pageNum = jsonObject.getIntValue("pageNum");
            int pageSize = jsonObject.getIntValue("pageSize");
            if (pageSize == 0) pageSize = 10;

            TestSSM testSSM = new TestSSM();
            PageHelper.startPage(pageNum, pageSize);
            List<TestSSM> testSSMS = testSSMService.queryTestSSMListPage(testSSM);
            PageInfo<TestSSM> pageInfo = new PageInfo<TestSSM>(testSSMS);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("testSSMS", testSSMS);
            map.put("pageInfo", pageInfo);
            json = this.getJsonMap(true, this.getMsg("success.select"), map);
        } catch (Exception e) {
            LOGGER.error("TestSSMController.selectListPage--error", e);
        }
        return json;
    }


    @RequestMapping("/velocity/hello")
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //配置velocity模板目录
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "webapp");
        properties.setProperty("webapp.resource.loader.class", "org.apache.velocity.tools.view.servlet.WebappLoader");
        properties.setProperty("webapp.resource.loader.path", "/WEB-INF/template");
        properties.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        properties.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        properties.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        VelocityEngine velocityEngine = new VelocityEngine(properties);
        velocityEngine.setApplicationAttribute("javax.servlet.ServletContext", request.getServletContext());

        //配置velocity模板内容
        VelocityContext context = new VelocityContext();
        context.put("name", "user01");

        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", "Tom" + i);
            map.put("age", i);
            map.put("sex", i % 2);
            mapList.add(map);
        }
        context.put("mapList", mapList);
        StringWriter sw = new StringWriter();
        velocityEngine.mergeTemplate("hello.vm", "utf-8", context, sw);
        out.println(sw.toString());

    }

}
