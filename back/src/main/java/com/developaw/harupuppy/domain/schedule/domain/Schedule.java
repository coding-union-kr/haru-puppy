package com.developaw.harupuppy.domain.schedule.domain;

import com.developaw.harupuppy.global.common.DateEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SCHEDULES")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends DateEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "schedule_id")
  private Long id;

  @NotNull
  @Enumerated(EnumType.STRING)
  private ScheduleType scheduleType;

  @NotNull
  @Column(name = "reserved_date")
  private LocalDateTime reservedDate;

  @Enumerated(EnumType.STRING)
  private RepeatType repeatType = RepeatType.NONE;

  @Enumerated(EnumType.STRING)
  private AlertType alertType = AlertType.NONE;

  @Column(columnDefinition = "TEXT")
  private String memo;

  @Column(name = "is_deleted")
  private boolean isDeleted = false;

  private boolean isActive = true;

  @Builder
  public Schedule(
      ScheduleType scheduleType,
      LocalDateTime reservedDate,
      RepeatType repeatType,
      AlertType alertType,
      String memo,
      boolean isDeleted,
      boolean isActive) {
    this.scheduleType = scheduleType;
    this.reservedDate = reservedDate;
    this.repeatType = repeatType;
    this.alertType = alertType;
    this.memo = memo;
    this.isDeleted = isDeleted;
    this.isActive = isActive;
  }

  public void done() {
    isActive = false;
  }

  public void delete() {
    isDeleted = true;
  }
}
