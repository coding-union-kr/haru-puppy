package com.developaw.harupuppy.domain.activity.domain;

import com.developaw.harupuppy.domain.home.domain.Home;
import com.developaw.harupuppy.domain.schedule.domain.ScheduleType;
import com.developaw.harupuppy.domain.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "ACTIVITY_LOGS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Activity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Enumerated(EnumType.STRING)
  private ScheduleType scheduleType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "home_id")
  private Home home;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "created_date")
  @CreatedDate
  private LocalDateTime createdDate;

  @Builder
  public Activity(ScheduleType scheduleType, Home home, User user) {
    this.scheduleType = scheduleType;
    this.home = home;
    this.user = user;
  }
}
