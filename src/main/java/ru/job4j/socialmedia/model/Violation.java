package ru.job4j.socialmedia.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@RequiredArgsConstructor
@Getter
public class Violation {
    private final String fieldName;
    private final String message;
}
