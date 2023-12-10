package com.developaw.harupuppy.domain.schedule;

import com.developaw.harupuppy.global.common.DateEntity;
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

@Entity
@Table(name = "SCHEDULES")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends DateEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "schedule_id")
  private Long id;

  @Enumerated(EnumType.STRING)
  private ScheduleType scheduleType;

  @Column(name = "start_date")
  private LocalDateTime scheduleStartTime;

  @Column(name = "end_date")
  private LocalDateTime scheduleEndTime;

  @Enumerated(EnumType.STRING)
  private RepeatType repeatType = RepeatType.NONE;

  @Enumerated(EnumType.STRING)
  private AlertType alertType = AlertType.NONE;

  @Column(columnDefinition = "TEXT")
  private String memo;

  @Column(name = "is_deleted")
  private boolean isDeleted = false;

  private boolean active = true;

  @Builder
  public Schedule(
      ScheduleType scheduleType,
      LocalDateTime scheduleStartTime,
      LocalDateTime scheduleEndTime,
      RepeatType repeatType,
      AlertType alertType,
      String memo,
      Boolean isDeleted,
      Boolean active) {
    this.scheduleType = scheduleType;
    this.scheduleStartTime = scheduleStartTime;
    this.scheduleEndTime = scheduleEndTime;
    this.repeatType = repeatType;
    this.alertType = alertType;
    this.memo = memo;
    this.isDeleted = isDeleted;
    this.active = active;
  }

  public void done() {
    active = false;
  }

  public void delete() {
    isDeleted = true;
  }
}
