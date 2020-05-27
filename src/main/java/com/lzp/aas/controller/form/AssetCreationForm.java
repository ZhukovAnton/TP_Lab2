package com.lzp.aas.controller.form;

import com.lzp.aas.model.enums.AssetStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AssetCreationForm {
    private Long assetTypeId;

    private BigDecimal price;

    private String vendor;

    private String description;

    private AssetStatus assetStatus;
}
