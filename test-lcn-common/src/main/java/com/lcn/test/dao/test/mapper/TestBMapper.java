package com.lcn.test.dao.test.mapper;

import com.lcn.test.dao.test.entity.TestB;
import com.lcn.test.dao.test.entity.TestBExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestBMapper {
    int countByExample(TestBExample example);

    int deleteByExample(TestBExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TestB record);

    int insertSelective(TestB record);

    List<TestB> selectByExample(TestBExample example);

    TestB selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TestB record, @Param("example") TestBExample example);

    int updateByExample(@Param("record") TestB record, @Param("example") TestBExample example);

    int updateByPrimaryKeySelective(TestB record);

    int updateByPrimaryKey(TestB record);
}