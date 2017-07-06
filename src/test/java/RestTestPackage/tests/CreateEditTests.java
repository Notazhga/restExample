package RestTestPackage.tests;

import RestTestPackage.objects.Issue;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;


public class CreateEditTests {
        private RequestSpecification requestSpec;

        @BeforeClass
        public void setUp() {
            RestAssured.port = 3000;
            RestAssured.baseURI = "http://redmine-train.dev.thumbtack.net";
            RequestSpecBuilder builder = new RequestSpecBuilder();
            builder.addHeader("Content-Type", "application/json");
            builder.addHeader("X-Redmine-API-Key","ef56b46ab35101865a50bcf1b28974bd77eff10d");
            requestSpec = builder.build();
        }

        @Test
        public void create() {
            Issue issue = new Issue();
            issue.setProjectId("2071");
            issue.setSubject("nkamarskaya_bug");
            issue.setDescription("1 2 3");
            issue.setTrackerId("3");            //Bug
            issue.setWatcherId("104");          //Kamarskaya
            issue.setAssignedId("104");         //Kamarskaya

            given().spec(requestSpec)
                    .body(issue)
                    .when().post("/issues.json").then()
                    .statusCode(201);
        }

        @Test
        public void edit() {

            Issue issue = new Issue();
            issue.setProjectId("2071");
            issue.setSubject("nkamarskaya_bug");
            issue.setDescription("1 2 3");
            issue.setTrackerId("3");            //Bug
            issue.setWatcherId("104");          //Kamarskaya
            issue.setAssignedId("104");         //Kamarskaya

            int issueId = given()
                    .spec(requestSpec)
                    .body(issue)
                    .when().post("/issues.json").then()
                    .statusCode(201)
                    .extract().path("issue.id");


            issue.setPriorityId("6");               //Urgent
            issue.setDoneRatio("50");               //50%


            given().header("X-Redmine-API-Key","ef56b46ab35101865a50bcf1b28974bd77eff10d")
                    .contentType("application/json")
                    .pathParam("issueId", issueId)
                    .body(issue)
                    .when().put("/issues/{issueId}.json").then()
                    .statusCode(200);
        }
    }

