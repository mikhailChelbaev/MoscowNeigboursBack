package com.moscow.neighbours.backend.db.model.user;

import com.moscow.neighbours.backend.db.model.route.DBRoute;
import com.moscow.neighbours.backend.db.model.achievements.DBCompletedAchievement;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "db_user")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DBUser {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    @Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // email or unique id
    @Column(unique = true, nullable = false)
    private String userId;

    @Column(unique = true, nullable = false)
    private String email;

    @Setter
    private String password;

    @Setter
    @Builder.Default
    private String name = "";

    @Setter
    private String avatarPath = "";

    @Setter
    @Column(name = "is_verified", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean isVerified = false;

    @Setter
    @Column(name = "verification_code")
    private String verificationCode;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "db_user_purchases",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="purchase_id", referencedColumnName = "id")
    )
    @Builder.Default
    private Set<DBRoute> purchasedRoutes = new HashSet<>();

    @Setter
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id"))
    @Builder.Default
    private Collection<DBRole> roles = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DBCompletedAchievement> completedAchievements = new HashSet<>();
}
