package com.moscow.neighbours.backend.db.model.achievements;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
public class DBCompletedAchievement {
    @Id
    private UUID achievementId;

    @Temporal(TemporalType.DATE)
    private Date date;
}