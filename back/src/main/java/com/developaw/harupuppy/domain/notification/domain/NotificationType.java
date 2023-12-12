package com.developaw.harupuppy.domain.notification.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
  SCHEDULE("일정 알림"),
  SYSTEM("시스템 알림");
  private final String desc;
}
