package com.developaw.harupuppy.domain.activity;

import com.developaw.harupuppy.domain.schedule.ScheduleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

  @Enumerated(EnumType.STRING)
  private ScheduleType scheduleType;

  @Column(name = "created_date")
  @CreatedDate
  private LocalDateTime createdDate;

  // TODO :: 스케줄 담당 유저 필드 추가
  // TODO :: 홈 필드 추가

  @Builder
  public Activity(ScheduleType scheduleType, LocalDateTime createdDate) {
    this.scheduleType = scheduleType;
    this.createdDate = createdDate;
  }
}
