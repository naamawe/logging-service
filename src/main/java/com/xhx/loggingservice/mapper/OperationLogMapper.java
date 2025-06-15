package com.xhx.loggingservice.mapper;


import com.xhx.loggingservice.entity.pojo.OperationLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author master
 */
@Mapper
public interface OperationLogMapper {

    int insertLog(OperationLog log);
}
