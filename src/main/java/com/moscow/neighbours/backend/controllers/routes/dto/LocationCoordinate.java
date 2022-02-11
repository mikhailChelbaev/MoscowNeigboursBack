package com.moscow.neighbours.backend.controllers.routes.dto;

import com.moscow.neighbours.backend.controllers.routes.interfaces.IEntityPresentable;
import com.moscow.neighbours.backend.db.model.entities.DBLocationCoordinate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationCoordinate implements IEntityPresentable<DBLocationCoordinate>, Serializable {

    Double latitude;
    Double longitude;

    public LocationCoordinate (DBLocationCoordinate dbModel) {
        latitude = dbModel.getLat();
        longitude = dbModel.getLng();
    }

    @Override
    public DBLocationCoordinate toDBModel() {
        return new DBLocationCoordinate(UUID.randomUUID(), latitude, longitude);
    }

}
