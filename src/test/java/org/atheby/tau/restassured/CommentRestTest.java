package org.atheby.tau.restassured;

import org.atheby.tau.restassured.domain.Comment;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.atheby.tau.restassured.helper.DatabaseTester;
import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.junit.*;
import java.util.*;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class CommentRestTest {

    private static DatabaseTester dbTester;
    private static final String TABLE_NAME = "COMMENT";

    @BeforeClass
    public static void setUp() throws Exception{
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/restassured/comments";

        dbTester = new DatabaseTester();
    }

    @Before
    public void cleanInsert() throws Exception {
        dbTester.cleanInsert();
    }

    @Test
    public void addComments() throws Exception {
        Comment comm1 = new Comment(4, "Author 5", "Lorem ipsum");
        Comment comm2 = new Comment(4, "Author 6", "Lorem ipsum");

        given().contentType("application/json; charset=UTF-16").body(comm1)
                .when().post("/").then().assertThat().statusCode(201);

        given().contentType("application/json; charset=UTF-16").body(comm2)
                .when().post("/").then().assertThat().statusCode(201);

        dbTester.setActualTable(TABLE_NAME);
        ITable actualTable = dbTester.getActualTable();
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"ID", "POSTID"});

        dbTester.setExpectedTable("comment/addCommentData.xml", TABLE_NAME);

        Assertion.assertEquals(dbTester.getExpectedTable(), filteredTable);
    }

    @Test
    public void getComment() throws Exception {
        when().get("/3").then().assertThat().contentType(ContentType.JSON).statusCode(200);
    }

    @Test
    public void getAllCommentsByPost() throws Exception {
        Response response =
                when().get("/post/0").then().assertThat().contentType(ContentType.JSON).extract().response();
        String jsonAsString = response.asString();
        ArrayList<Map<String, ?>> commentsAsArrayList = from(jsonAsString).get("");
        assertThat(commentsAsArrayList.size(), equalTo(2));
    }

    @Test
    public void getAllComments() throws Exception {
        Response response =
                when().get("/").then().assertThat().contentType(ContentType.JSON).extract().response();
        String jsonAsString = response.asString();
        ArrayList<Map<String, ?>> commentsAsArrayList = from(jsonAsString).get("");
        assertThat(commentsAsArrayList.size(), equalTo(5));
    }

    @Test
    public void updateComment() throws Exception {
        Comment comment = new Comment(0, "Author update", "Comment update");

        given().contentType("application/json; charset=UTF-16").body(comment)
                .when().put("/0").then().assertThat().statusCode(201);

        dbTester.setActualTable(TABLE_NAME);
        ITable actualTable = dbTester.getActualTable();
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"ID", "POSTID"});

        dbTester.setExpectedTable("comment/updateCommentData.xml", TABLE_NAME);

        Assertion.assertEquals(dbTester.getExpectedTable(), filteredTable);
    }

    @Test
    public void deleteCommentTest() throws Exception {
        when().delete("/0").then().assertThat().statusCode(204);
        when().delete("/1").then().assertThat().statusCode(204);

        dbTester.setActualTable(TABLE_NAME);
        ITable actualTable = dbTester.getActualTable();
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"ID", "POSTID"});

        dbTester.setExpectedTable("comment/deleteCommentData.xml", TABLE_NAME);

        Assertion.assertEquals(dbTester.getExpectedTable(), filteredTable);
    }

    @Test
    public void deleteCommentsByPostTest() throws Exception {
        when().delete("/post/0").then().assertThat().statusCode(204);

        dbTester.setActualTable(TABLE_NAME);
        ITable actualTable = dbTester.getActualTable();
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"ID", "POSTID"});

        dbTester.setExpectedTable("comment/deleteCommentsByPostData.xml", TABLE_NAME);

        Assertion.assertEquals(dbTester.getExpectedTable(), filteredTable);
    }

    @Test
    public void deleteAllCommentsTest() throws Exception {
        when().delete("/").then().assertThat().statusCode(204);

        dbTester.setActualTable(TABLE_NAME);

        assertThat(dbTester.getActualTable().getRowCount(), equalTo(0));
    }

    @AfterClass
    public static void tearDown() throws Exception {
        dbTester.getIDatabaseTester().onTearDown();
    }
}
