package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

/*    public synchronized void decrease(Long id, Long quantity){
    Synchronized를 사용하게 될 경우 단일 서버에서 처리된다면 상관없지만
    수평적확대로 처리된 서버 2개 이상에 처리되면 데이터 접근을 여러대에서 접근가능해진다
 */
    @Transactional
    public synchronized void decrease(Long id, Long quantity){
        //Stock 조회
        //재고를 감소
        //갱신된 값을 저장
        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decreate(quantity);
        stockRepository.saveAndFlush(stock);
    }
}
