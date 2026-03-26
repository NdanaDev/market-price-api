package com.ephraim.marketpriceapi.repository;

import com.ephraim.marketpriceapi.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {

    List<Market> findAllByIsActiveTrue();

    Optional<Market> findByIdAndIsActiveTrue(Long id);

    List<Market> findAllByCityIgnoreCaseAndIsActiveTrue(String city);

    boolean existsByNameAndCity(String name, String city);
}
