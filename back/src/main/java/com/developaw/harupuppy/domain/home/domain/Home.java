package com.developaw.harupuppy.domain.home.domain;

import com.developaw.harupuppy.domain.dog.domain.Dog;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "HOME")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Home {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "home_id", updatable = false)
  private Long homeId;

  @Column(name = "home_name")
  private String homeName;

  @OneToOne(mappedBy = "home")
  private Dog dog;


  @Builder
  public Home (String homeName){
    this.homeName = homeName;
  }
}
