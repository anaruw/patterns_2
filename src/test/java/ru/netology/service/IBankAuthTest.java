package ru.netology.service;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.UserData;
import ru.netology.util.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class IBankAuthTest {

    @BeforeEach
    public void setUp() {
        Selenide.open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    public void shouldSuccessfullyLoginWithActiveRegisteredUser() {

        UserData getRegisteredUser = DataGenerator.getRegisteredUser("active");

        $("[data-test-id='login'] input").setValue(getRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(getRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();

        $("h2").shouldHave(Condition.allOf(
                Condition.visible,
                Condition.exactText("  Личный кабинет")
        ), Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should get error message with unregistered user")
    public void shouldGetErrorMessageWithUnregisteredUser() {

        UserData getNotRegisteredUser = DataGenerator.getUser("active");

        $("[data-test-id='login'] input").setValue(getNotRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(getNotRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification'] .notification__content").shouldBe(Condition.allOf(
                Condition.visible,
                Condition.exactText("Ошибка! Неверно указан логин или пароль")
        ), Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should get error message with active registered user if wrong login")
    public void shouldGetErrorMessageWithActiveRegisteredUserIfWrongLogin() {

        UserData getRegisteredUser = DataGenerator.getRegisteredUser("active");
        String wrongLogin = DataGenerator.changeLogin(getRegisteredUser.getLogin());

        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(getRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification'] .notification__content").shouldBe(Condition.allOf(
                Condition.visible,
                Condition.exactText("Ошибка! Неверно указан логин или пароль")
        ), Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should get error message with blocked registered user if wrong password")
    public void shouldGetErrorMessageWithBlockedRegisteredUserIfWrongPassword() {

        UserData getRegisteredUser = DataGenerator.getRegisteredUser("blocked");
        String wrongPassword = DataGenerator.changePassword(getRegisteredUser.getLogin());

        $("[data-test-id='login'] input").setValue(getRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification'] .notification__content").shouldBe(Condition.allOf(
                Condition.visible,
                Condition.exactText("Ошибка! Неверно указан логин или пароль")
        ), Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Should get error message with blocked registered user")
    public void shouldGetErrorMessageWithBlockedRegisteredUser() {

        UserData getRegisteredUser = DataGenerator.getRegisteredUser("blocked");

        $("[data-test-id='login'] input").setValue(getRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(getRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();

        $("[data-test-id='error-notification'] .notification__content").shouldBe(Condition.allOf(
                Condition.visible,
                Condition.exactText("Ошибка! Пользователь заблокирован")
        ), Duration.ofSeconds(15));
    }
}