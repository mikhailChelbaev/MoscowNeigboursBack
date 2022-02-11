package com.moscow.neighbours.backend.controllers.routes.dto;

import com.moscow.neighbours.backend.controllers.routes.interfaces.IEntityPresentable;
import com.moscow.neighbours.backend.db.model.DBPersonInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonInfoDto implements IEntityPresentable<DBPersonInfo>, Serializable {

    UUID id;
    PersonDto person;
    PlaceDto place;
    LocationCoordinate coordinates;

    public PersonInfoDto(DBPersonInfo dbModel) {
        id = dbModel.getId();
        person = new PersonDto(dbModel.getPerson());
        place = new PlaceDto(dbModel.getPlace());
        coordinates = new LocationCoordinate(dbModel.getCoordinates());
    }

    @Override
    public DBPersonInfo toDBModel() {
        return new DBPersonInfo(
                Objects.nonNull(id) ? id : UUID.randomUUID(),
                coordinates.toDBModel(),
                person.toDBModel(),
                place.toDBModel()
        );
    }

}
