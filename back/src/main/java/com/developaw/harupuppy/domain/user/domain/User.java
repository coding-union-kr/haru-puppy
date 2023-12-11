package com.developaw.harupuppy.domain.user.domain;

import com.developaw.harupuppy.domain.dog.domain.Dog;
import com.developaw.harupuppy.domain.home.domain.Home;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;


@Entity
@Getter
@Table(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NonNull
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long userId;

  @Email
  @Column(unique = true)
  private String email;

  @Column(name = "user_img")
  private String userImg;

  private String nickname;

  @Enumerated(EnumType.STRING)
  @Column(name = "user_role")
  private UserRole userRole;

  @Column(name = "is_deleted", columnDefinition = "TINYINT(1)")
  private boolean isDeleted;

  @Column(name = "allow_notification", columnDefinition = "TINYINT(1)")
  private boolean allowNotification;

  @ManyToOne
  @JoinColumn(name = "dog_id")
  private Dog dog;

  @ManyToOne
  @JoinColumn(name = "home_id")
  private Home home;

  @Builder
  public User(String email, String userImg, String nickname, UserRole userRole) {
    this.email = email;
    this.userImg = userImg;
    this.nickname = nickname;
    this.userRole = userRole;
    this.isDeleted = false;
    this.allowNotification = true;
  }

  public void delete () {
    this.isDeleted = true;
  }

}
