package com.lcn.test.dao.test.mapper;

import com.lcn.test.dao.test.entity.TestA;
import com.lcn.test.dao.test.entity.TestAExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestAMapper {
    int countByExample(TestAExample example);

    int deleteByExample(TestAExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TestA record);

    int insertSelective(TestA record);

    List<TestA> selectByExample(TestAExample example);

    TestA selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TestA record, @Param("example") TestAExample example);

    int updateByExample(@Param("record") TestA record, @Param("example") TestAExample example);

    int updateByPrimaryKeySelective(TestA record);

    int updateByPrimaryKey(TestA record);
}