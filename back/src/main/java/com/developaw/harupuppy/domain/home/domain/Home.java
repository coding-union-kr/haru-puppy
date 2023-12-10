package com.developaw.harupuppy.domain.home.domain;

import com.developaw.harupuppy.domain.dog.domain.Dog;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "HOME")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Home {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "home_id", updatable = false, nullable = false)
  private Long homeId;

  @Column(name = "home_name", nullable = false)
  private String homeName;

  @OneToOne(mappedBy = "home")
  private Dog dog;
}
