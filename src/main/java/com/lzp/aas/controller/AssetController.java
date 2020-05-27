package com.lzp.aas.controller;

import com.lzp.aas.config.Constants;
import com.lzp.aas.controller.form.AssetAssignUserForm;
import com.lzp.aas.controller.form.AssetChangeStatusForm;
import com.lzp.aas.model.Asset;
import com.lzp.aas.model.enums.AssetStatus;
import com.lzp.aas.service.asset.AssetRequestService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AssetController {

    private final AssetRequestService assetRequestService;

    @ApiOperation(value = "index assets. can be filtered with params",
            authorizations = {@Authorization(Constants.JWT_AUTH)})
    @GetMapping("/assets")
    public ResponseEntity<List<Asset>> indexAssets(
            @RequestHeader("authorization") String authorization,
            @RequestParam("user_id") Long userId,
            @RequestParam("status") AssetStatus assetStatus) {
        return new ResponseEntity<>(assetRequestService.indexAssets(userId, assetStatus), HttpStatus.OK);
    }

    @ApiOperation(value = "index assets. can be filtered with params",
            authorizations = {@Authorization(Constants.JWT_AUTH)})
    @PatchMapping("/assets/{id}/assign_to_user")
    public ResponseEntity<Void> assignAsset(
            @RequestHeader("authorization") String authorization,
            @PathVariable("id") Long assetId,
            @RequestBody AssetAssignUserForm assetAssignUserForm) {
        assetRequestService.assignAssetToUser(assetId, assetAssignUserForm.getAssignAssetToUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "index assets. can be filtered with params",
            authorizations = {@Authorization(Constants.JWT_AUTH)})
    @PatchMapping("/assets/{id}/change_status")
    public ResponseEntity<Void> assignAsset(
            @RequestHeader("authorization") String authorization,
            @PathVariable("id") Long assetId,
            @RequestBody AssetChangeStatusForm changeStatusForm) {
        assetRequestService.changeAssetStatus(assetId, changeStatusForm.getNewStatus());
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
