package ru.netology.util;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;
import ru.netology.data.UserData;

import java.util.Locale;

import static io.restassured.RestAssured.given;

@UtilityClass
public class DataGenerator {
    Faker faker = new Faker(new Locale("en"));

    public String loginGenerator() {
        return faker.name().username();
    }

    public String changeLogin(String unsuitedLogin) {
        String result;
        do {
            result = loginGenerator();
        } while (result.equals(unsuitedLogin));
        return result;
    }

    public String passwordGenerator() {
        return faker.internet().password();
    }

    public String changePassword(String unsuitedPassword) {
        String result;
        do {
            result = passwordGenerator();
        } while (result.equals(unsuitedPassword));
        return result;
    }

    public UserData getUser(String status) {
        return UserData.builder()
                .login(loginGenerator())
                .password(passwordGenerator())
                .status(status)
                .build();
    }

    private RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public void sendRequest(UserData getUser) {

        given()
                .spec(requestSpec)
                .body(getUser)
        .when().log().all()
                .post("/api/system/users")
        .then().log().all()
                .statusCode(200);
    }

    public UserData getRegisteredUser(String status) {
        UserData user = getUser(status);
        sendRequest(user);
        return user;
    }
}