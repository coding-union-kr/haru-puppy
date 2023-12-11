package com.developaw.harupuppy.domain.home.domain;

import com.developaw.harupuppy.domain.dog.domain.Dog;
import com.developaw.harupuppy.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

  @OneToMany(mappedBy = "home")
  private List<User> users = new ArrayList<User>();
}
