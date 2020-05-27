package com.lzp.aas.service.request;

import com.lzp.aas.controller.form.RequestCreationForm;
import com.lzp.aas.model.Asset;
import com.lzp.aas.model.Request;
import com.lzp.aas.model.enums.RequestStatus;
import com.lzp.aas.repository.RequestRepository;
import com.lzp.aas.utils.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;

    private final RequestManagementService requestManagementService;

    public List<Request> index() {
        RequestUtil.checkAdminsRules();
        return requestRepository.findAllOpenRequests();
    }

    public Request createRequest(Long authorId, RequestCreationForm requestCreationForm) {
        RequestUtil.checkRightAccess(authorId);
        return requestManagementService.createRequest(authorId, requestCreationForm);
    }

    public void updateRequestStatus(Long requestId, RequestStatus newRequestStatus) {
        RequestUtil.checkAdminsRules();
        requestManagementService.updateRequestStatus(requestId, newRequestStatus);
    }


}
