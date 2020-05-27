package com.lzp.aas.controller.form;

import com.lzp.aas.model.enums.AssetStatus;
import lombok.Data;

@Data
public class AssetChangeStatusForm {

    private AssetStatus newStatus;
}
