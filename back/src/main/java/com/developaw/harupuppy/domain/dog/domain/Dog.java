package com.developaw.harupuppy.domain.dog.domain;

import com.developaw.harupuppy.domain.home.domain.Home;
import com.developaw.harupuppy.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "DOG")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dog {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "dog_id", nullable = false, updatable = false)
  private Long dogId;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "img")
  private String profilePicture;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender")
  private DogGender gender;

  @Column(name = "birthday")
  private LocalDateTime birthday;

  @Column(name = "weight")
  private double weight;

  @CreatedDate
  @Column(name = "created_date", updatable = false)
  private LocalDateTime createdDate;

  @LastModifiedDate
  @Column(name = "modified_date")
  private LocalDateTime modifiedDate;

  @OneToOne
  @JoinColumn(name = "home_id")
  private Home home;

//  @OneToMany(mappedBy = "dog")
//  private List<User> users = new ArrayList<User>();

  @Builder
  public Dog (String name, String profilePicture, DogGender gender, LocalDateTime birthday, double weight){
    this.name = name;
    this.profilePicture = profilePicture;
    this.gender = gender;
    this.birthday = birthday;
    this.weight = weight;
  }
}
