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
    Optional<PriceEntry> findTopByCommodityIdAndMarketIdAndVerificationStatusOrderByRecordedAtDesc(
        Long commodityId,
        Long marketId,
        VerificationStatus status
    );

    // Verified price history for a commodity in a market within a date range
    List<PriceEntry> findAllByCommodityIdAndMarketIdAndVerificationStatusAndRecordedAtBetweenOrderByRecordedAtAsc(
        Long commodityId,
        Long marketId,
        VerificationStatus status,
        LocalDateTime from,
        LocalDateTime to
    );

    // Latest verified price per market for a commodity in a city
    @Query("""
        SELECT pe FROM PriceEntry pe
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
