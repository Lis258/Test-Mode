package ru.netology;

import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldLogInActiveUser() {
        val UserInfo = DataGenerator.generateActiveUser();
        $("[data-test-id='login'] [name='login']").setValue(UserInfo.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(UserInfo.getPassword());
        $("[data-test-id='action-login'] [class='button__text']").click();
        $("h2").shouldHave(text("Личный кабинет"));
    }

    @Test
    void shouldNotLogInBlockedUser() {
        val UserInfo = DataGenerator.generateBlockedUser();
        $("[data-test-id='login'] [name='login']").setValue(UserInfo.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(UserInfo.getPassword());
        $("[data-test-id='action-login'] [class='button__text']").click();
        $(withText("Пользователь заблокирован")).shouldBe(Condition.visible);
    }

    @Test
    void shouldNotLogInNotRegisterUser() {
        val UserInfo = DataGenerator.generateNotRegisteredUser();
        $("[data-test-id='login'] [name='login']").setValue(UserInfo.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(UserInfo.getPassword());
        $("[data-test-id='action-login'] [class='button__text']").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    void shouldNotLogInActiveUserInvalidPassword() {
        val UserInfo = DataGenerator.generateActiveUserInvalidPassword();
        $("[data-test-id='login'] [name='login']").setValue(UserInfo.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(UserInfo.getPassword());
        $("[data-test-id='action-login'] [class='button__text']").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

}
