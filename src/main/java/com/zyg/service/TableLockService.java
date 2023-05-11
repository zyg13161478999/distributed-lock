package com.zyg.service;

import com.zyg.entity.OptimisticLock;
import com.zyg.mapper.InsertLockMapper;
import com.zyg.mapper.OptimisticLockMapper;
import com.zyg.mapper.PessimisticLockMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author .gang
 * @date 2023/5/10
 */
@Service
@Slf4j
public class TableLockService {

    @Autowired
    InsertLockMapper insertLockMapper;

    @Autowired
    PessimisticLockMapper pessimisticLockMapper;

    @Autowired
    PlatformTransactionManager platformTransactionManager;

    @Autowired
    OptimisticLockMapper optimisticLockMapper;

    Map<String, TransactionStatus> transactionStatusMap = new HashMap<String, TransactionStatus>();

    /**
     * 表记录获取锁
     *
     * @param key
     * @param clientId
     * @return
     */
    public boolean tryLock(String key, String clientId) {
        log.info(clientId + "进入tryLock");
        try {
            if (insertLockMapper.insertLock(key, clientId) > 0) {
                log.info(clientId + "获取资源成功");
                Thread.sleep(10);
                unLock(key, clientId);
                return true;
            }
        } catch (Exception e) {
        }
        log.info(clientId + "获取资源失败");
        return false;
    }

    /**
     * 表记录释放锁
     *
     * @param key
     * @param clientId
     */
    @Async
    public void unLock(String key, String clientId) {
        log.info(clientId + "进入unLock");
        try {
            if (insertLockMapper.delLock(key, clientId) > 0) {
                log.info(clientId + "释放资源成功");
                return;
            }
        } catch (Exception e) {
        }
        log.info(clientId + "释放资源失败");
    }

    /**
     * 悲观锁获取锁
     *
     * @param key
     * @param clientId
     */
    public void pessimisticLock(String key, String clientId) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
        transactionStatusMap.put(clientId, transactionStatus);
        String query = pessimisticLockMapper.query(key);
        log.info("pessimisticLock" + clientId + "获取锁" + query);
        pessimisticUnLock(clientId);
    }


    /**
     * 悲观锁获解锁
     *
     * @param clientId
     */
    public void pessimisticUnLock(String clientId) {
        TransactionStatus transactionStatus = transactionStatusMap.get(clientId);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("pessimisticLock" + clientId + "释放锁");
        platformTransactionManager.commit(transactionStatus);
        transactionStatusMap.remove(clientId);
    }

    /**
     * 乐观锁
     * @param productId
     * @param clientId
     */
    public void optimisticLock(int productId, String clientId) {
        OptimisticLock optimisticLock = optimisticLockMapper.selectById(productId);
        if (null != optimisticLock && optimisticLock.getProductNum() > 0) {
            String nuwUpdateVersion = UUID.randomUUID().toString();
            if (optimisticLockMapper.updateProductNum(optimisticLock.getProductId(),
                    optimisticLock.getUpdateVersion(), nuwUpdateVersion) > 0) {
                log.info("optimisticLock" + clientId + "购买成功");
                return;
            }
        }
        log.info("optimisticLock" + clientId + "购买失败");
    }
}
