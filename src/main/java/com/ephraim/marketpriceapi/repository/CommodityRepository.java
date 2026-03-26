package com.ephraim.marketpriceapi.repository;

import com.ephraim.marketpriceapi.entity.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommodityRepository extends JpaRepository<Commodity, Long> {

    List<Commodity> findAllByIsActiveTrue();

    Optional<Commodity> findByIdAndIsActiveTrue(Long id);

    Optional<Commodity> findByName(String name);

    boolean existsByName(String name);
}
