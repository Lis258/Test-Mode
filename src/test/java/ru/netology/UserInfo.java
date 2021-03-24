package ru.netology;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class UserInfo {
    private final String login;
    private final String password;
    private final String status;
}
