package com.zyg.controller;

import com.zyg.service.TableLockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.locks.Lock;

/**
 * @author .gang
 * @date 2023/5/10
 */
@Api(value = "基于数据库表的分布式锁", tags = {"基于数据库表的分布式锁"})
@RestController
@RequestMapping(value = "/table")
public class TableLockController {
    @Autowired
    TableLockService tableLockService;

    @Autowired
    LockRegistry lockRegistry;

    @ApiOperation(value = "表记录分布式锁")
    @GetMapping("/info")
    public void tableInfo() {
        tableLockService.tryLock("draw", Thread.currentThread().getName());
    }


    @ApiOperation(value = "悲观锁")
    @GetMapping("/pessimisticLock")
    public void pessimisticLock() throws InterruptedException {
        tableLockService.pessimisticLock("draw", Thread.currentThread().getName());
    }

    @ApiOperation(value = "乐观锁")
    @ApiImplicitParams({@ApiImplicitParam(name = "productId", value = "产品id", required = true)})
    @GetMapping("/optimisticLock")
    public void optimisticLock(@RequestParam("productId")int productId) {
        tableLockService.optimisticLock(productId,Thread.currentThread().getName());
    }

    @ApiOperation(value = "integration-jdbc")
    @ApiImplicitParams({@ApiImplicitParam(name = "productId", value = "产品id", required = true)})
    @GetMapping("/integration/jdbc")
    public String integrationJdbc(@RequestParam("productId")String productId) {
        Lock lock = lockRegistry.obtain(productId);
        //获取锁
        if (lock.tryLock()) {
            try {
                    Thread.sleep(1);
                    System.out.println(Thread.currentThread().toString() + " ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //释放锁
                lock.unlock();
            }
            return System.currentTimeMillis() + "_" + productId;
        } else {
            return System.currentTimeMillis() + "_" + productId + "\t" + Thread.currentThread() + " 获取锁失败";
        }
    }
}
