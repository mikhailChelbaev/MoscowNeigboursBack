package com.moscow.neighbours.backend.controllers.routes.dto;

import com.moscow.neighbours.backend.controllers.routes.interfaces.IEntityPresentable;
import com.moscow.neighbours.backend.db.model.entities.DBRoutePurchase;
import com.moscow.neighbours.backend.models.RoutePurchaseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutePurchaseDto implements IEntityPresentable<DBRoutePurchase>, Serializable {

    public String status;
    public String productId;

    public RoutePurchaseDto(DBRoutePurchase dbModel) {
        status = dbModel.getStatus().name().toLowerCase();
        productId = dbModel.getProductId();
    }

    @Override
    public DBRoutePurchase toDBModel() {
        return new DBRoutePurchase(
                UUID.randomUUID(),
                RoutePurchaseStatus.valueOf(status.toUpperCase()),
                productId
        );
    }
}
