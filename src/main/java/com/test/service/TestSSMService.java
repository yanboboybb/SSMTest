package com.test.service;

import com.test.entity.TestSSM;

import java.util.List;

/**
 * @Description:
 * @ClassName: TestSSMService
 * @Author: yanbobo
 * @CreateDate: 2019/8/1 13:54
 */
public interface TestSSMService {

    int insertTestSSM(TestSSM testSSM) throws Exception;

    List<TestSSM> queryTestSSMListPage(TestSSM testSSM) throws Exception;

}
