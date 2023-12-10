package com.developaw.harupuppy.domain.schedule;

import com.developaw.harupuppy.global.common.DateEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

  @NotNull private ScheduleType scheduleType;

  @NotNull
  @Column(name = "start_date")
  private LocalDateTime scheduleStartTime;

  @NotNull
  @Column(name = "end_date")
  private LocalDateTime scheduleEndTime;

  private RepeatType repeatType = RepeatType.NONE;

  private AlertType notificationType = AlertType.NONE;

  @Column(columnDefinition = "TEXT")
  private String memo;

  @Column(name = "is_deleted")
  private Boolean isDeleted = Boolean.FALSE;

  private Boolean active = Boolean.TRUE;

  @Builder
  public Schedule(
      ScheduleType scheduleType,
      LocalDateTime scheduleStartTime,
      LocalDateTime scheduleEndTime,
      RepeatType repeatType,
      AlertType notificationType,
      String memo,
      Boolean isDeleted,
      Boolean active) {
    this.scheduleType = scheduleType;
    this.scheduleStartTime = scheduleStartTime;
    this.scheduleEndTime = scheduleEndTime;
    this.repeatType = repeatType;
    this.notificationType = notificationType;
    this.memo = memo;
    this.isDeleted = isDeleted;
    this.active = active;
  }
}
