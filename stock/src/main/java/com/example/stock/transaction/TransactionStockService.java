package com.example.stock.transaction;

import com.example.stock.service.StockService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransactionStockService {
    private StockService stockService;

    /*decrease service메서드에 synchronized를 붙여도 동일락 처리가 안되는 이유
    @Transactional 어노테이션 문제로 해당 어노테이션 사용이 현재 자바 파일처럼 Proxy객체를 갖게된다
    그러면 endTransaction호출과정에서 다른 쓰레드가 decrease메서드로 접근이 가능해지면서
    완전히 종료되기 이전의 값을 읽어와버리게 된다.

    따라서 서비스절에 Transactional어노테이션 지워서 처리해보면 정상처리되는 것도 확인 가능하다
    */
    public void decrease(Long id, Long quantity){
        startTransaction(); //트랜잭션 시작
        stockService.decrease(id, quantity);
        endTransaction();   //트랜잭션 종료
    }

    private void startTransaction(){
        System.out.println("Transaction Start");
    }

    private void endTransaction(){
        System.out.println("Commit");
    }
}
