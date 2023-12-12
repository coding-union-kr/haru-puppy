package com.developaw.harupuppy.domain.dog.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DogGender {
  MALE("남아"),
  FEMALE("여아");

  private final String desc;
}
