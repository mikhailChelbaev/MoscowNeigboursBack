package com.moscow.neighbours.backend.db.model.achievements;

import com.moscow.neighbours.backend.db.model.user.DBUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DBCompletedAchievement {
    @Id
    private UUID id;

    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private DBUser user;

    private UUID achievementId;

    @Setter
    @Temporal(TemporalType.DATE)
    private Date date;
}