package org.atheby.tau.restassured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.atheby.tau.restassured.domain.Blog;
import org.atheby.tau.restassured.helper.DatabaseTester;
import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.junit.*;
import java.util.*;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class BlogRestTest {

    private static DatabaseTester dbTester;
    private static final String TABLE_NAME = "BLOG";

    @BeforeClass
    public static void setUp() throws Exception{
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/restassured/blogs";

        dbTester = new DatabaseTester();
    }

    @Before
    public void cleanInsert() throws Exception {
        dbTester.cleanInsert();
    }

    @Test
    public void addBlogTest() throws Exception {
        Blog blog1 = new Blog("Blog 4");
        Blog blog2 = new Blog("Blog 5");

        given().contentType("application/json; charset=UTF-16").body(blog1)
                .when().post("/").then().assertThat().statusCode(201);

        given().contentType("application/json; charset=UTF-16").body(blog2)
                .when().post("/").then().assertThat().statusCode(201);

        dbTester.setActualTable(TABLE_NAME);
        ITable actualTable = dbTester.getActualTable();
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"ID"});

        dbTester.setExpectedTable("blog/addBlogData.xml", TABLE_NAME);

        Assertion.assertEquals(dbTester.getExpectedTable(), filteredTable);
    }

    @Test
    public void getAllBlogsTest() throws Exception {
        Response response =
                when().get("/").then().assertThat().contentType(ContentType.JSON).extract().response();
        String jsonAsString = response.asString();
        ArrayList<Map<String, ?>> blogsAsArrayList = from(jsonAsString).get("");
        assertThat(blogsAsArrayList.size(), equalTo(3));
    }

    @Test
    public void getBlogTest() throws Exception {
        when().get("/0").then().assertThat().contentType(ContentType.JSON).statusCode(200);
    }

    @Test
    public void updateBlog() throws Exception {
        Blog blog = new Blog("Blog update");

        given().contentType("application/json; charset=UTF-16").body(blog)
                .when().put("/0").then().assertThat().statusCode(201);

        dbTester.setActualTable(TABLE_NAME);
        ITable actualTable = dbTester.getActualTable();
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"ID"});

        dbTester.setExpectedTable("blog/updateBlogData.xml", TABLE_NAME);

        Assertion.assertEquals(dbTester.getExpectedTable(), filteredTable);
    }

    @Test
    public void deleteBlogTest() throws Exception {
        when().delete("/0").then().assertThat().statusCode(204);

        dbTester.setActualTable(TABLE_NAME);
        ITable actualTable = dbTester.getActualTable();
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"ID"});

        dbTester.setExpectedTable("blog/deleteBlogData.xml", TABLE_NAME);

        Assertion.assertEquals(dbTester.getExpectedTable(), filteredTable);
    }

    @Test
    public void deleteCascadeBlogTest() throws Exception {
        ITable actualTable;

        when().delete("/0").then().assertThat().statusCode(204);

        dbTester.setActualTable("BLOG");
        actualTable = dbTester.getActualTable();
        dbTester.setExpectedTable("blog/deleteCascadeBlogData.xml", "BLOG");
        Assertion.assertEquals(dbTester.getExpectedTable(), actualTable);

        dbTester.setActualTable("POST");
        actualTable = dbTester.getActualTable();
        dbTester.setExpectedTable("blog/deleteCascadeBlogData.xml", "POST");
        Assertion.assertEquals(dbTester.getExpectedTable(), actualTable);

        dbTester.setActualTable("COMMENT");
        actualTable = dbTester.getActualTable();
        dbTester.setExpectedTable("blog/deleteCascadeBlogData.xml", "COMMENT");
        Assertion.assertEquals(dbTester.getExpectedTable(), actualTable);
    }

    @Test
    public void deleteAllBlogsTest() throws Exception {
        when().delete("/").then().assertThat().statusCode(204);

        dbTester.setActualTable(TABLE_NAME);

        assertThat(dbTester.getActualTable().getRowCount(), equalTo(0));
    }

    @AfterClass
    public static void tearDown() throws Exception {
        dbTester.getIDatabaseTester().onTearDown();
    }
}
