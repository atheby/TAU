package org.atheby.tau.restassured;

import org.atheby.tau.restassured.domain.Post;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.atheby.tau.restassured.helper.DatabaseTester;
import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.junit.*;
import java.util.*;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class PostRestTest {

    private static DatabaseTester dbTester;
    private static final String TABLE_NAME = "POST";

    @BeforeClass
    public static void setUp() throws Exception{
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/restassured/posts";

        dbTester = new DatabaseTester();
    }

    @Before
    public void cleanInsert() throws Exception {
        dbTester.cleanInsert();
    }

    @Test
    public void addPostsTest() throws Exception {
        dbTester.setActualTable("BLOG");
        int blogCount = dbTester.getActualTable().getRowCount();

        for(int i = 0; i < blogCount; i++) {
            Long blogId = Long.parseLong(dbTester.getActualTable().getValue(i, "id").toString());
            for(int j = 0; j <= i * 2; j++) {
                Post post = new Post(blogId, "Post " + i + " " + j, "Lorer mere " + j);
                given().contentType("application/json; charset=UTF-16").body(post)
                        .when().post("/").then().assertThat().statusCode(201);
            }
        }

        dbTester.setActualTable(TABLE_NAME);
        ITable actualTable = dbTester.getActualTable();
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"ID", "BLOGID"});

        dbTester.setExpectedTable("post/addPostData.xml", TABLE_NAME);

        Assertion.assertEquals(dbTester.getExpectedTable(), filteredTable);
    }

    @Test
    public void getPostTest() throws Exception {
        when().get("/0").then().assertThat().contentType(ContentType.JSON).statusCode(200);
    }

    @Test
    public void getAllPostsByBlog() throws Exception {
        Response response =
                when().get("/blog/2").then().assertThat().contentType(ContentType.JSON).extract().response();
        String jsonAsString = response.asString();
        ArrayList<Map<String, ?>> postsAsArrayList = from(jsonAsString).get("");
        assertThat(postsAsArrayList.size(), equalTo(3));
    }

    @Test
    public void getAllPostsTest() {
        Response response =
                when().get("/").then().assertThat().contentType(ContentType.JSON).extract().response();
        String jsonAsString = response.asString();
        ArrayList<Map<String, ?>> postsAsArrayList = from(jsonAsString).get("");
        assertThat(postsAsArrayList.size(), equalTo(6));
    }

    @Test
    public void updatePost() throws Exception {
        Post post = new Post(0, "Title update", "Text update");

        given().contentType("application/json; charset=UTF-16").body(post)
                .when().put("/0").then().assertThat().statusCode(201);

        dbTester.setActualTable(TABLE_NAME);
        ITable actualTable = dbTester.getActualTable();
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"ID", "BLOGID"});

        dbTester.setExpectedTable("post/updatePostData.xml", TABLE_NAME);

        Assertion.assertEquals(dbTester.getExpectedTable(), filteredTable);
    }

    @Test
    public void deletePostTest() throws Exception {
        when().delete("/4").then().assertThat().statusCode(204);

        dbTester.setActualTable(TABLE_NAME);
        ITable actualTable = dbTester.getActualTable();
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"ID", "BLOGID"});

        dbTester.setExpectedTable("post/deletePostData.xml", TABLE_NAME);

        Assertion.assertEquals(dbTester.getExpectedTable(), filteredTable);
    }

    @Test
    public void deletePostsByBlogTest() throws Exception {
        when().delete("/blog/1").then().assertThat().statusCode(204);

        dbTester.setActualTable(TABLE_NAME);
        ITable actualTable = dbTester.getActualTable();
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"ID", "BLOGID"});

        dbTester.setExpectedTable("post/deletePostsByBlogData.xml", TABLE_NAME);

        Assertion.assertEquals(dbTester.getExpectedTable(), filteredTable);
    }

    @Test
    public void deleteAllPostsTest() throws Exception {
        when().delete("/").then().assertThat().statusCode(204);

        dbTester.setActualTable(TABLE_NAME);

        assertThat(dbTester.getActualTable().getRowCount(), equalTo(0));
    }

    @AfterClass
    public static void tearDown() throws Exception{
        dbTester.getIDatabaseTester().onTearDown();
    }
}
