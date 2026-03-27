package com.ephraim.marketpriceapi.repository;

import com.ephraim.marketpriceapi.entity.PriceEntry;
import com.ephraim.marketpriceapi.entity.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PriceEntryRepository extends JpaRepository<PriceEntry, Long> {

    // Latest verified price for a commodity in a market
    @Query("""
        SELECT pe FROM PriceEntry pe
        JOIN FETCH pe.commodity
        JOIN FETCH pe.market
        JOIN FETCH pe.agent
        WHERE pe.commodity.id = :commodityId
          AND pe.market.id = :marketId
          AND pe.verificationStatus = :status
        ORDER BY pe.recordedAt DESC
        LIMIT 1
        """)
    Optional<PriceEntry> findTopByCommodityIdAndMarketIdAndVerificationStatusOrderByRecordedAtDesc(
        @Param("commodityId") Long commodityId,
        @Param("marketId") Long marketId,
        @Param("status") VerificationStatus status
    );

    // Verified price history for a commodity in a market within a date range
    @Query("""
        SELECT pe FROM PriceEntry pe
        JOIN FETCH pe.commodity
        JOIN FETCH pe.market
        JOIN FETCH pe.agent
        WHERE pe.commodity.id = :commodityId
          AND pe.market.id = :marketId
          AND pe.verificationStatus = :status
          AND pe.recordedAt BETWEEN :from AND :to
        ORDER BY pe.recordedAt ASC
        """)
    List<PriceEntry> findAllByCommodityIdAndMarketIdAndVerificationStatusAndRecordedAtBetweenOrderByRecordedAtAsc(
        @Param("commodityId") Long commodityId,
        @Param("marketId") Long marketId,
        @Param("status") VerificationStatus status,
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to
    );

    // Latest verified price per market for a commodity in a city
    @Query("""
        SELECT pe FROM PriceEntry pe
        JOIN FETCH pe.commodity
        JOIN FETCH pe.market
        JOIN FETCH pe.agent
        WHERE pe.commodity.id = :commodityId
          AND LOWER(pe.market.city) = LOWER(:city)
          AND pe.verificationStatus = :status
          AND pe.recordedAt = (
              SELECT MAX(pe2.recordedAt)
              FROM PriceEntry pe2
              WHERE pe2.commodity.id = :commodityId
                AND pe2.market.id = pe.market.id
                AND pe2.verificationStatus = :status
          )
        ORDER BY pe.price ASC
        """)
    List<PriceEntry> findLatestVerifiedPricesPerMarketInCity(
        @Param("commodityId") Long commodityId,
        @Param("city") String city,
        @Param("status") VerificationStatus status
    );
}
