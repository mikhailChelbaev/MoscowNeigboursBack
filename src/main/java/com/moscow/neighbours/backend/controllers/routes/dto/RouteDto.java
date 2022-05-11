package com.moscow.neighbours.backend.controllers.routes.dto;

import com.moscow.neighbours.backend.controllers.routes.interfaces.IEntityPresentable;
import com.moscow.neighbours.backend.db.model.route.DBRoute;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteDto implements IEntityPresentable<DBRoute>, Serializable {

    public UUID id;
    public String name;
    public String description;
    public String duration;
    public String distance;
    public String coverUrl;
    public RoutePurchaseDto purchase;
    public List<PersonInfoDto> personsInfo;
    public Integer position;

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
        purchase = new RoutePurchaseDto(dbModel.getPurchase());
        position = dbModel.getPresentationPosition();
    }

    @Override
    public DBRoute toDBModel() {
        return new DBRoute(
                Objects.nonNull(id) ? id : UUID.randomUUID(),
                name,
                description,
                duration,
                distance,
                position,
                coverUrl,
                purchase.toDBModel(),
                personsInfo.stream()
                        .map(PersonInfoDto::toDBModel)
                        .collect(Collectors.toList()),
                null
        );
    }

}
