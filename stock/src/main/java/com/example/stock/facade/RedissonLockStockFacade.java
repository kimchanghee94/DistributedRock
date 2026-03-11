package com.example.stock.facade;

import com.example.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class RedissonLockStockFacade {
    /*
    레디슨을 활요하면 pub/sub기반으로 레디스 부하를 줄여주고 Luascript 처리로 단일 원자성 코드로 처리할 수 있게 된다.
     */
    private final RedissonClient redissonClient;
    private final StockService stockService;

    public void decrease(Long id, Long quantity){
        RLock lock = redissonClient.getLock(id.toString());

        try{
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);
            if(!available){
                System.out.println("lock 획득 실패");
                return;
            }

            stockService.decrease(id, quantity);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }finally{
            lock.unlock();
        }
    }
}
