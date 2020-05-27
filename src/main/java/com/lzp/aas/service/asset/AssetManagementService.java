package com.lzp.aas.service.asset;

import com.lzp.aas.exception.AppException;
import com.lzp.aas.exception.HttpAppError;
import com.lzp.aas.model.Asset;
import com.lzp.aas.model.User;
import com.lzp.aas.model.enums.AssetStatus;
import com.lzp.aas.repository.AssetRepository;
import com.lzp.aas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssetManagementService {

    private final AssetRepository assetRepository;

    private final UserRepository userRepository;

    public void assignAssetToUser(Long assetId, Long userId) {
        Optional<Asset> assetOptional = assetRepository.findById(assetId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (assetOptional.isPresent() && assetOptional.get().getAssignedUser() != null)
            throw new AppException(HttpAppError.ALREADY_ASSIGNED);
        if (assetOptional.isPresent() && userOptional.isPresent()) {
            assetOptional.get().setAssignedUser(userOptional.get());
            assetRepository.save(assetOptional.get());
        } else throw new AppException(HttpAppError.NOT_FOUND);
    }

    public void changeAssetStatus(Long assetId, AssetStatus newStatus) {
        Optional<Asset> assetOptional = assetRepository.findById(assetId);
        if (assetOptional.isPresent()) {
            assetOptional.get().setAssetStatus(newStatus);
            assetRepository.save(assetOptional.get());
        } else throw new AppException(HttpAppError.NOT_FOUND);
    }

}
