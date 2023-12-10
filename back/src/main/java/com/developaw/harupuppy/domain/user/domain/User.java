package com.developaw.harupuppy.domain.user.domain;

import com.developaw.harupuppy.domain.dog.domain.Dog;
import com.developaw.harupuppy.domain.home.domain.Home;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@Table(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", updatable = false, nullable = false)
  private Long userId;

  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Column(name = "user_img", nullable = false)
  private String userImg;

  @Column(name = "nickname", nullable = false)
  private String nickname;

  @Enumerated(EnumType.STRING)
  @Column(name = "user_role", nullable = false)
  private UserRole userRole;

  @Column(name = "is_deleted", nullable = false, columnDefinition = "TINYINT(1)")
  private boolean isDeleted;

  @Column(name = "allow_notification", nullable = false, columnDefinition = "TINYINT(1)")
  private boolean allowNotification;

  @CreatedDate
  @Column(name = "created_date", updatable = false)
  private LocalDateTime createdDate;

  @LastModifiedDate
  @Column(name = "modified_date")
  private LocalDateTime modifiedDate;

  @ManyToOne
  @JoinColumn(name = "dog_id")
  private Dog dog;

  @ManyToOne
  @JoinColumn(name = "home_id")
  private Home home;

  @Builder
  public User(String userImg, String nickname, UserRole userRole) {
    this.userImg = userImg;
    this.nickname = nickname;
    this.userRole = userRole;
  }
}
