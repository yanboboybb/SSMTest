package com.test.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description:
 * @ClassName: TestSSM
 * @Author: yanbobo
 * @CreateDate: 2019/8/1 11:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestSSM implements Serializable {

    private static final long serialVersionUID = -5666453518359918354L;

    private Integer id;
    private String name;
}
