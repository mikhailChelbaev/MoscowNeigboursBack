package com.moscow.neighbours.backend.controllers.routes.dto;

import com.moscow.neighbours.backend.controllers.routes.interfaces.IEntityPresentable;
import com.moscow.neighbours.backend.db.model.entities.DBPlace;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PlaceDto implements IEntityPresentable<DBPlace>, Serializable {

    UUID id;

    String name;

    String description;

    String address;

    public PlaceDto (DBPlace dbModel) {
        id = dbModel.getId();
        name = dbModel.getName();
        description = dbModel.getDescription();
        address = dbModel.getAddress();
    }

    @Override
    public DBPlace toDBModel() {
        return new DBPlace(
                Objects.nonNull(id) ? id : UUID.randomUUID(),
                name,
                description,
                address
        );
    }

}
