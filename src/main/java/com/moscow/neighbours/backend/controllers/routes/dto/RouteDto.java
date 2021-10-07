package com.moscow.neighbours.backend.controllers.routes.dto;

import com.moscow.neighbours.backend.controllers.routes.interfaces.IEntityPresentable;
import com.moscow.neighbours.backend.db.model.DBRoute;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteDto implements IEntityPresentable<DBRoute>, Serializable {

    UUID id;

    String name;

    String description;

    String duration;

    String distance;

    String coverUrl;

    List<PersonInfoDto> personsInfo;

    public RouteDto(DBRoute dbModel) {
        id = dbModel.getId();
        name = dbModel.getName();
        description = dbModel.getDescription();
        duration = dbModel.getDuration();
        distance = dbModel.getDistance();
        coverUrl = dbModel.getCoverUrl();
        personsInfo = dbModel.getPersonInfo().stream()
                .map(PersonInfoDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public DBRoute toDBModel() {
        return new DBRoute(
                id,
                name,
                description,
                duration,
                distance,
                coverUrl,
                personsInfo.stream()
                        .map(PersonInfoDto::toDBModel)
                        .collect(Collectors.toList())
        );
    }

}
