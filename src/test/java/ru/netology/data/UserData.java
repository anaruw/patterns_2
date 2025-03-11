package ru.netology.data;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
public class UserData {
    @With
    String login;
    @With
    String password;
    @With
    String status;
}