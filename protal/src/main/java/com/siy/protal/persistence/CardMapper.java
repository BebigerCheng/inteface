package com.siy.protal.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CardMapper {

    void insertNumber(String s);

    void insertName(@Param("name") String name,@Param("id") int i);
}
