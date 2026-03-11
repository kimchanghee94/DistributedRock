package com.example.stock.facade;

import com.example.stock.repository.LockRepository;
import com.example.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NamedLockStockFacade {
    private final LockRepository lockRepository;

    private final StockService stockService;

    //namedLock에서 먼저 tranctional을 걸어준 이유는 같은 커넥션을 유지할 수 있게 해야된다.
    //주의사항이 락을 얻은 커넥션이 A이면 오로지 A만 해제가 가능해진다
    //그리고 stockService.decrease를 REQUIRED로 하면 같은 커넥션 A로 처리할 수 있게 되고 stockService.decrease메서드에서 롤백시 얘도 롤백된다.
    //REQUIRES_NEW로 하게되면 다른 커넥션으로 stockService.decrease가 발행되고 롤백 분기가 서로 독립적으로 동작하게 된다.
    @Transactional
    public void decrease(Long id, Long quantity){
        try{
            lockRepository.getLock(id.toString());
            stockService.decrease(id, quantity);
        }finally {
            lockRepository.releaseLock(id.toString());
        }
    }
}
