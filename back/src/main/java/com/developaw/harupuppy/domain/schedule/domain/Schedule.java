package com.developaw.harupuppy.domain.schedule.domain;

import com.developaw.harupuppy.domain.schedule.dto.ScheduleUpdateRequest;
import com.developaw.harupuppy.global.common.DateEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
    @Column(name = "schedule_datetime")
    private LocalDateTime scheduleDateTime;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSchedule> mates = new ArrayList<>();

    private String repeatId;

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
            List<UserSchedule> mates,
            String repeatId,
            RepeatType repeatType,
            AlertType alertType,
            String memo) {
        this.scheduleType = scheduleType;
        this.scheduleDateTime = scheduleDateTime;
        this.mates = mates;
        this.repeatId = repeatId;
        this.repeatType = repeatType;
        this.alertType = alertType;
        this.memo = memo;
    }

    public static Schedule of(Schedule schedule, String repeatId, LocalDateTime repeatDateTime) {
        return Schedule.builder()
                .scheduleType(schedule.scheduleType)
                .scheduleDateTime(repeatDateTime)
                .mates(schedule.mates)
                .repeatId(repeatId)
                .repeatType(schedule.repeatType)
                .alertType(schedule.alertType)
                .memo(schedule.memo)
                .build();
    }

    public static List<Schedule> of(List<LocalDateTime> dateTimesUntilNextYear, Schedule schedule, String repeatId) {
        return dateTimesUntilNextYear.stream()
                .map(datetime -> {
                    return Schedule.of(schedule, repeatId, datetime);
                }).collect(Collectors.toList());
    }

    public void update(ScheduleUpdateRequest dto, List<UserSchedule> mates) {
        this.scheduleType = dto.scheduleType();
        this.scheduleDateTime = parseDateTime(dto.scheduleDate(), dto.scheduleTime());
        this.mates = mates;
        this.repeatType = dto.repeatType();
        this.alertType = dto.alertType();
        this.memo = dto.memo();
    }

    public void done() {
        isActive = true;
    }

    public void planned() {
        isActive = false;
    }

    public void delete() {
        isDeleted = true;
    }

    public void setScheduleDateTime(LocalDateTime dateTime) {
        this.scheduleDateTime = dateTime;
    }

    public void setRepeatId(String repeatId) {
        this.repeatId = repeatId;
    }

    public void addMate(UserSchedule mate) {
        mates.add(mate);
        mate.getUserSchedulePK().setSchedule(this);
    }

    public static LocalDateTime parseDateTime(String date, String time) {
        LocalDate localDate = LocalDate.parse(date, dateFormatter);
        LocalTime localTime = LocalTime.parse(time, timeFormatter);
        return LocalDateTime.of(localDate, localTime);
    }
}
