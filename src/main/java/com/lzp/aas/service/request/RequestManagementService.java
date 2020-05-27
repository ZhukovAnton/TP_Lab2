package com.lzp.aas.service.request;

import com.lzp.aas.controller.form.RequestCreationForm;
import com.lzp.aas.exception.AppException;
import com.lzp.aas.exception.HttpAppError;
import com.lzp.aas.model.Asset;
import com.lzp.aas.model.Request;
import com.lzp.aas.model.User;
import com.lzp.aas.model.enums.RequestStatus;
import com.lzp.aas.repository.AssetRepository;
import com.lzp.aas.repository.RequestRepository;
import com.lzp.aas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RequestManagementService {

    private final RequestRepository requestRepository;

    private final AssetRepository assetRepository;

    private final UserRepository userRepository;

    public Request createRequest(Long authorId, RequestCreationForm requestCreationForm) {
        Asset asset = assetRepository.findById(requestCreationForm.getAssetId()).orElseThrow(() -> new AppException(HttpAppError.NOT_FOUND));
        User author = userRepository.findById(authorId).orElseThrow(() -> new AppException(HttpAppError.NOT_FOUND));

        Request request = new Request();
        request.setAsset(asset);
        request.setAssetType(asset.getAssetType());
        request.setAuthor(author);
        request.setDescription(requestCreationForm.getDescription());
        request.setTitle(requestCreationForm.getTitle());
        request.setIsRequestOpen(true);
        request.setRequestStatus(RequestStatus.NEW);
        request.setCreatedAt(LocalDateTime.now());

        return requestRepository.save(request);
    }

    public void updateRequestStatus(Long requestId, RequestStatus newRequestStatus) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new AppException(HttpAppError.NOT_FOUND));
        request.setRequestStatus(newRequestStatus);
        requestRepository.save(request);
    }

}
