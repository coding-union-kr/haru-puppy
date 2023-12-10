package com.developaw.harupuppy.domain.notification;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    SCHEDULE("일정알림"),
    SYSTEM("시스템알림")
    ;
    private final String desc;
}
