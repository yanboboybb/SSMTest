package com.test.service.impl;

import com.test.entity.TestSSM;
import com.test.mapper.TestSSMMapper;
import com.test.service.TestSSMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @ClassName: TestSSMServiceImpl
 * @Author: yanbobo
 * @CreateDate: 2019/8/1 13:55
 */
@Service("testSSMService")
public class TestSSMServiceImpl implements TestSSMService {

    @Autowired
    private TestSSMMapper testSSMMapper;

    public int insertTestSSM(TestSSM testSSM) throws Exception {
        int rows = testSSMMapper.insertTestSSM(testSSM);
        int a = 1 / 0;
        return rows;
    }

    public List<TestSSM> queryTestSSMListPage(TestSSM testSSM) throws Exception {
        return testSSMMapper.queryTestSSMListPage(testSSM);
    }
}
