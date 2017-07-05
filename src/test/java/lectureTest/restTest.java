package lectureTest;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import lectureTest.objects.TimeEntry;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;


public class restTest {

    RequestSpecification requestSpec;

    @BeforeClass
    public void setUp() {
        RestAssured.port = 3000;
        RestAssured.baseURI = "http://redmine-train.dev.thumbtack.net";
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addHeader("Content-Type", "application/json");
        builder.addHeader("X-Redmine-API-Key", "e1af45dccd3a615925031ddf3b379b910e4137d9");
        requestSpec = builder.build();
    }

    @Test
    public void firstTest() {
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setIssueId(7919);
        timeEntry.setHours(2);
        timeEntry.setActivityId(10);

        int entryId = given()
                .spec(requestSpec)
                .body(timeEntry)
                .when().post("/time_entries.json").then()
                .statusCode(201)
                .extract().path("time_entry.id");

        given().spec(requestSpec)
                .pathParam("entryID", entryId)
                .when().get("/time_entries/{entryID}.json").then()
                .statusCode(200);

        System.out.println(entryId);

    }
}
