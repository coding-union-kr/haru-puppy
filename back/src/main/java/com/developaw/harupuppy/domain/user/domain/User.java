package com.developaw.harupuppy.domain.user.domain;

import com.developaw.harupuppy.domain.dog.domain.Dog;
import com.developaw.harupuppy.domain.home.domain.Home;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Where(clause = "is_deleted = false")
//@NotNull - 더 생각 해봐야
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long userId;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "user_img", nullable = false)
    private String userImg;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING) //미완
    @Column(name = "user_role")
    private String userRole;

    //@Convert
    @Column(name = "is_deleted", columnDefinition = "TINYINT(1)")
    private boolean isDeleted;

    @Column(name = "allow_otification", columnDefinition = "TINYINT(1)")
    private boolean allowNotification;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "home_id")
    private Long homeId;

    @ManyToOne
    @JoinColumn(name = "dog_id")
    private Dog dog;

    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;


    @Builder
    public User(String email, String userImg, String nickname, String userRole, Boolean isDeleted,
                Boolean allowNotification, LocalDateTime createdDate, LocalDateTime modifiedDate, Home home, Dog dog) {
        this.email = email;
        this.userImg = userImg;
        this.nickname = nickname;
        this.userRole = userRole;
        this.isDeleted = isDeleted;
        this.allowNotification = allowNotification;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.home = home;
        this.dog = dog;
    }
}
