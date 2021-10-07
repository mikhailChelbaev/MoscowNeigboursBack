package com.moscow.neighbours.backend.controllers.routes.dto;

import com.moscow.neighbours.backend.controllers.routes.interfaces.IEntityPresentable;
import com.moscow.neighbours.backend.db.model.entities.DBPerson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto implements IEntityPresentable<DBPerson>, Serializable {

    UUID id;

    String name;

    String description;

    String shortDescription;

    String avatarUrl;

    List<ShortInfo> info;

    public PersonDto (DBPerson dbModel) {
        id = dbModel.getId();
        name = dbModel.getName();
        description = dbModel.getDescription();
        shortDescription = dbModel.getShortDescription();
        avatarUrl = dbModel.getAvatarUrl();
        info = dbModel.getInfo().stream()
                .map(ShortInfo::new)
                .collect(Collectors.toList());
    }

    @Override
    public DBPerson toDBModel() {
        return new DBPerson(
                id,
                name,
                description,
                shortDescription,
                avatarUrl,
                info.stream()
                        .map(ShortInfo::toDBModel)
                        .collect(Collectors.toList())
        );
    }

}
