package com.developaw.harupuppy.domain.dog.domain;

import com.developaw.harupuppy.domain.user.domain.Home;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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

    @Column(name = "img_url")
    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private DogGender gender;

    private LocalDate birthday;

    private Double weight;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_id")
    private Home home;

    @Builder
    public Dog(
            String name, String imgUrl, DogGender gender, LocalDate birthday, Double weight, Home home) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.gender = gender;
        this.birthday = birthday;
        this.weight = weight;
        this.home = home;
    }
}
