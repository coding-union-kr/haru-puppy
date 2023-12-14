package com.developaw.harupuppy.domain.dog.domain;

import com.developaw.harupuppy.domain.home.domain.Home;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
    public Dog(
            String name, String profilePicture, DogGender gender, LocalDate birthday, double weight) {
        this.name = name;
        this.profilePicture = profilePicture;
        this.gender = gender;
        this.birthday = birthday;
        this.weight = weight;
    }
}
