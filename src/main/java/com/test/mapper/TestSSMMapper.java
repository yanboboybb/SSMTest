package com.test.mapper;

import com.test.entity.TestSSM;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @ClassName: TestSSMMapper
 * @Author: yanbobo
 * @CreateDate: 2019/8/1 13:57
 */
@Repository
public interface TestSSMMapper {

    int insertTestSSM(TestSSM testSSM);

    List<TestSSM> queryTestSSMListPage(TestSSM testSSM);
}
