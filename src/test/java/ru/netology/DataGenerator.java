package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private DataGenerator() {
    }

    private static Faker faker = new Faker(new Locale("en"));

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void setUpAll(UserInfo registration) {
        given()
                .spec(requestSpec)
                .body(registration)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static UserInfo generateActiveUser() {
        UserInfo activeUser = new UserInfo(faker.name().firstName(), faker.internet().password(), "active");
        setUpAll(activeUser);
        return activeUser;
    }

    public static UserInfo generateBlockedUser() {
        UserInfo activeUser = new UserInfo(faker.name().firstName(), faker.internet().password(), "blocked");
        setUpAll(activeUser);
        return activeUser;
    }

    public static UserInfo generateNotRegisteredUser() {
        UserInfo activeUser = new UserInfo(faker.name().firstName(), faker.internet().password(), "blocked");
        return activeUser;
    }

    public static UserInfo generateActiveUserInvalidPassword() {
        String login = faker.name().firstName();
        UserInfo activeUser = new UserInfo(login, faker.internet().password(), "active");
        setUpAll(activeUser);
        return new UserInfo(login, faker.internet().password(), "active");
    }
}
