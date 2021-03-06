package com.moscow.neighbours.backend.controllers.routes.dto;

import com.moscow.neighbours.backend.controllers.routes.interfaces.IEntityPresentable;
import com.moscow.neighbours.backend.db.model.route.DBShortInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortInfo implements IEntityPresentable<DBShortInfo>, Serializable {

    String title;
    String subtitle;

    public ShortInfo(DBShortInfo dbModel) {
        title = dbModel.getTitle();
        subtitle = dbModel.getSubtitle();
    }

    @Override
    public DBShortInfo toDBModel() {
        return new DBShortInfo(UUID.randomUUID(), title, subtitle);
    }

}
