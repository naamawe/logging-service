package com.xhx.loggingservice.listener;

import com.xhx.loggingservice.entity.pojo.OperationLog;
import com.xhx.loggingservice.mapper.OperationLogMapper;
import entity.dto.OperationLogDTO;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

import static com.xhx.loggingservice.constant.constant.*;
import static constant.mqConstant.*;

/**
 * @author master
 */
@Component
public class LogListener {

    @Resource
    private OperationLogMapper  operationLogMapper;
    /**
     * 监听指定队列的消息
     */
    @RabbitListener(queues = {USER_QUEUE, PERMISSION_QUEUE})
    public void onMessage(OperationLogDTO dto) {
        if (dto == null) {
            System.err.println(RECEIVE_NULL_MESSAGE);
            return;
        }

        try {
            // 构造实体对象
            OperationLog log = new OperationLog();
            log.setUserId(dto.getUserId());
            log.setAction(dto.getAction());
            log.setIp(dto.getIp());
            log.setDetail(dto.getDetail() != null ? dto.getDetail().toString() : null);
            // 注意这里改为 setGmtCreate
            log.setGmtCreate(dto.getGmtCreate() != null ? dto.getGmtCreate() : new Timestamp(System.currentTimeMillis()));

            // 调用数据库插入
            int rows = operationLogMapper.insertLog(log);
            if (rows != 1) {
                System.err.println(INSERT_OPERATION_LOG_FAILED + log);
            } else {
                System.out.println(INSERT_OPERATION_LOG_SUCCESS + dto.getUserId());
            }

        } catch (Exception e) {
            throw new RuntimeException(FAILED_TO_PROCESS_OPERATION_LOG, e);
        }
    }

}