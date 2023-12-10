package com.developaw.harupuppy.domain.dog.domain;

import com.developaw.harupuppy.domain.home.domain.Home;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "dog")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Where(clause = "is_deleted = false")
//@NotNull - 더 생각 해봐야
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dog_id")
    private Long dogId;

    @Column(name = "name")
    private String name;

    @Column(name = "img")
    private String profilePicture;

    @Column(name = "gender")//미완 (enum적용?)
    private String gender;

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

    @OneToOne(mappedBy = "dog_id")
    private Home home;

}
