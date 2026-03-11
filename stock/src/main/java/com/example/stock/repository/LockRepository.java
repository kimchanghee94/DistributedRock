package com.example.stock.repository;

import com.example.stock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LockRepository extends JpaRepository<Stock, Long> {
    //get_lock, release_lock은 mysql 순수 SQL이니 nativeQuery true로 설정해줘야된다(JPQL로는 불가)
    @Query(value = "select get_lock(:key, 3000)", nativeQuery = true)
    void getLock(String key);

    @Query(value="select release_lock(:key)", nativeQuery = true)
     void releaseLock(String key);
}
