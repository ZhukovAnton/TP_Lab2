package com.lzp.aas.service.asset;

import com.lzp.aas.controller.form.AssetCreationForm;
import com.lzp.aas.model.Asset;
import com.lzp.aas.model.enums.AssetStatus;
import com.lzp.aas.repository.AssetRepository;
import com.lzp.aas.utils.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetRequestService {

    private final AssetRepository assetRepository;

    private final AssetManagementService assetManagementService;

    public List<Asset> indexAssets(Long userId, AssetStatus assetStatus) {
        RequestUtil.checkAdminsRules();
        return assetRepository.indexFilteredByParams(userId, assetStatus);
    }

    public Asset createAsset(AssetCreationForm assetCreationForm) {
        RequestUtil.checkAdminsRules();
        return assetManagementService.createAsset(assetCreationForm);
    }

    public void assignAssetToUser(Long assetId, Long userId) {
        RequestUtil.checkAdminsRules();
        assetManagementService.assignAssetToUser(assetId, userId);
    }

    public void changeAssetStatus(Long assetId, AssetStatus newStatus) {
        RequestUtil.checkAdminsRules();
        assetManagementService.changeAssetStatus(assetId, newStatus);
    }
}
