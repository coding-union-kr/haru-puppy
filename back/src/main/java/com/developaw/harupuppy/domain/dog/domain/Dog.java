package com.developaw.harupuppy.domain.dog.domain;

import com.developaw.harupuppy.domain.home.domain.Home;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Table(name = "DOG")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NonNull
public class Dog {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long dogId;

  @Column
  @Size(min = 1, max = 5)
  private String name;

  @Column(name = "img")
  private String profilePicture;

  @Enumerated(EnumType.STRING)
  private DogGender gender;

  private LocalDate birthday;

  private double weight;


  @OneToOne
  @JoinColumn(name = "home_id")
  private Home home;


  @Builder
  public Dog (String name, String profilePicture, DogGender gender, LocalDate birthday, double weight){
    this.name = name;
    this.profilePicture = profilePicture;
    this.gender = gender;
    this.birthday = birthday;
    this.weight = weight;
  }
}
