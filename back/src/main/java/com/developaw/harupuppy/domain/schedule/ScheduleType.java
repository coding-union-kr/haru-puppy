package com.developaw.harupuppy.domain.schedule;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ScheduleType {
  WALK("산책"),
  WASH("목욕"),
  POO("응가"),
  BRUSH("빗질"),
  FOOD("밥"),
  GROOM("미용"),
  HOSPITAL("병원"),
  WATER("물갈이"),
  ANNIVERSARY("기념일");

  private final String desc;
}
