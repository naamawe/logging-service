package com.xhx.loggingservice.listener;

import com.xhx.loggingservice.entity.pojo.OperationLog;
import com.xhx.loggingservice.mapper.OperationLogMapper;
import entity.dto.OperationLogDTO;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

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
            System.err.println("Received null message");
            return;
        }

        try {
            // 校验必填字段
            if (dto.getUserId() == null || dto.getAction() == null || dto.getAction().isBlank()) {
                System.err.println("Invalid operation log data: missing userId or action");
                return;
            }

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
                System.err.println("Insert operation log failed for: " + log);
            } else {
                System.out.println("Operation log inserted successfully for userId=" + dto.getUserId());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to process operation log", e);
        }
    }

}