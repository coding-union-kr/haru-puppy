package com.developaw.harupuppy.domain.notification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "NOTIFICATIONS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @NotBlank
    private String message;
    // TODO :: 알림 디폴트 메시지

    @Column(name = "send_date")
    @CreatedDate
    private LocalDateTime sendDate;

    //TODO :: 알림 수신자 필드 추가

    @Builder
    public Notification(NotificationType notificationType, String message, LocalDateTime sendDate) {
        this.notificationType = notificationType;
        this.message = message;
        this.sendDate = sendDate;
    }


}
