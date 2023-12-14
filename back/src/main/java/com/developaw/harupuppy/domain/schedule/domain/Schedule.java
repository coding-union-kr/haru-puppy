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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SCHEDULES")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends DateEntity {
    static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType;

    @NotNull
    @Column(name = "reserved_date")
    private LocalDateTime scheduleDateTime;

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
            LocalDateTime scheduleDateTime,
            RepeatType repeatType,
            AlertType alertType,
            String memo) {
        this.scheduleType = scheduleType;
        this.scheduleDateTime = scheduleDateTime;
        this.repeatType = repeatType;
        this.alertType = alertType;
        this.memo = memo;
    }

    public void done() {
        isActive = false;
    }

    public void delete() {
        isDeleted = true;
    }

    public static LocalDateTime parseDateTime(String date, String time) {
        LocalDate localDate = LocalDate.parse(date, dateFormatter);
        LocalTime localTime = LocalTime.parse(time, timeFormatter);
        return LocalDateTime.of(localDate, localTime);
    }
}
