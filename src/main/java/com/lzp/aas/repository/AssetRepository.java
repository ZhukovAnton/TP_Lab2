package com.lzp.aas.repository;

import com.lzp.aas.model.Asset;
import com.lzp.aas.model.enums.AssetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    @Query("from Asset " +
            "where (:userId is null or assignedUser.id = :userId) " +
            "and (:assetStatus is null or assetStatus = :assetStatus)")
    List<Asset> indexFilteredByParams(Long userId, AssetStatus assetStatus);
}
