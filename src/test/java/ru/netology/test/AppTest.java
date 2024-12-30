package ru.netology.test;

import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.databaseClean;

public class AppTest {

    @AfterAll
    static void tearDown() {
        databaseClean();
    }

    @Test
    void shouldLoginSuccessful() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = SQLHelper.getCodeVerification();
        var dashboardPage = verificationPage.validVerify(verificationCode.getCode());
        dashboardPage.verifyHeaderText("Личный кабинет"); // Проверка текста хедера
    }

    @Test
    void shouldErrorLoggingRandomUser() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificationVisibility(); // Проверка видимости уведомления об ошибке
        loginPage.verifyErrorNotificationText("Ошибка! Неверно указан логин или пароль"); // Проверка текста ошибки
    }

    @Test
    void shouldErrorIncorrectVerificationCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotificationVisibility(); // Проверка видимости уведомления об ошибке
        verificationPage.verifyErrorNotificationText("Ошибка! Неверно указан код! Попробуйте ещё раз."); // Проверка текста ошибки
    }
}
