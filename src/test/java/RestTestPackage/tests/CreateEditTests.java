package RestTestPackage.tests;

import RestTestPackage.objects.Issue;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.mapper.ObjectMapper;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;


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
        public void create() {                              //создаем новый ишью
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
                    .when().post("/issues.json")
                    .then()
                    .statusCode(201)
                    .extract().path("issue.id");   //запоминаем айди созданного бага


            given().spec(requestSpec)                       //проверяем созданный баг
                    .when()
                    .pathParam("issueId", issueId)
                    .get("/issues/{issueId}.json")
                    .then()
                    .statusCode(200);

        }

            @Test
            public void edit() {                            //создаем новый ишью
                Issue issue = new Issue();
                issue.setProjectId("2071");
                issue.setSubject("nkamarskaya_bug");
                issue.setDescription("1 2 3");
                issue.setTrackerId("3");            //Bug
                issue.setWatcherId("104");          //Kamarskaya
                issue.setAssignedId("104");         //Kamarskaya

                int issueId = given()                       //постим его
                        .spec(requestSpec)
                        .body(issue)
                        .when().post("/issues.json")
                        .then()
                        .statusCode(201)
                        .extract().path("issue.id");        //запоминаем айди


                given().spec(requestSpec)                           //проверяем созданный ишью по запомненному айди
                        .when()
                        .pathParam("issueId", issueId)
                        .get("/issues/{issueId}.json")
                        .then()
                        .body("issue.subject",equalTo("nkamarskaya_bug"))
                        .body("issue.description",equalTo("1 2 3"))
                        .body("issue.tracker.id",equalTo(3))
                        .body("issue.author.id",equalTo(104))
                        .body("issue.assigned_to.id",equalTo(104))
                        .statusCode(200);


                issue.setPriorityId("6");               //Urgent                    //редактируем созданный баг
                issue.setDoneRatio("50");               //50%


                given().spec(requestSpec)
                        .contentType("application/json")
                        .pathParam("issueId", issueId)
                        .body(issue)
                        .when()
                        .put("/issues/{issueId}.json").then()
                        .statusCode(200);

                given().spec(requestSpec)                                           //проверяем созданный баг по запомненному ишьюайди
                        .when()
                        .pathParam("issueId", issueId)
                        .get("/issues/{issueId}.json")
                        .then()
                        .body("issue.subject",equalTo("nkamarskaya_bug"))
                        .body("issue.description",equalTo("1 2 3"))
                        .body("issue.tracker.id",equalTo(3))
                        .body("issue.author.id",equalTo(104))
                        .body("issue.assigned_to.id",equalTo(104))
                        .body("issue.priority.id", equalTo(6))
                        .body("issue.done_ratio", equalTo(50))
                        .statusCode(200);
        }
        }