package ru.netology.data;

import lombok.Value;
import lombok.With;

@Value
public class UserData {
    @With
    String login;
    @With
    String password;
    @With
    String status;
}