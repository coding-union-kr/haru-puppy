package com.developaw.harupuppy.domain.notification.domain;

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

  @NotBlank private String message;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User receiver;

  @Column(name = "send_date")
  @CreatedDate
  private LocalDateTime sendDate;

  @Builder
  public Notification(NotificationType notificationType, String message, User receiver) {
    this.notificationType = notificationType;
    this.message = message;
    this.receiver = receiver;
  }
}
