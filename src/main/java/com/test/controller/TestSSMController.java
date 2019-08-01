package com.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.common.utils.StringUtils;
import com.test.entity.TestSSM;
import com.test.service.TestSSMService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

}
