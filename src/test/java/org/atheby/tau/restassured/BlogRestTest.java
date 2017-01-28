package org.atheby.tau.restassured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.atheby.tau.restassured.domain.Blog;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.*;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class BlogRestTest {

    private static IDatabaseConnection connection ;
    private static IDatabaseTester databaseTester;
    private static Response response;
    private static String jsonAsString;
    private static ArrayList<Map<String, ?>> blogsAsArrayList;

    @BeforeClass
    public static void setUp() throws Exception{
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/restassured";

        Connection jdbcConnection;
        jdbcConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb", "sa", "");
        connection = new DatabaseConnection(jdbcConnection);

        DatabaseConfig dbConfig = connection.getConfig();
        dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());

        databaseTester = new JdbcDatabaseTester(
                "org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/workdb", "sa", "");
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(
                new FileInputStream(new File("src/test/resources/org/atheby/tau/restassured/fullData.xml")));
        databaseTester.setDataSet(dataSet);
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);

        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
    }

    @Test
    public void addBlogsTest() throws Exception {
        Blog blog1 = new Blog("Blog 4");
        Blog blog2 = new Blog("Blog 5");
        Blog blog3 = new Blog("Blog 6");

        given().contentType("application/json; charset=UTF-16").body(blog1)
                .when().post("/blogs/").then().assertThat().statusCode(201);

        given().contentType("application/json; charset=UTF-16").body(blog2)
                .when().post("/blogs/").then().assertThat().statusCode(201);

        given().contentType("application/json; charset=UTF-16").body(blog3)
                .when().post("/blogs/").then().assertThat().statusCode(201);
    }

    @Test
    public void getAllBlogsTest() throws Exception {
        response =
                when().get("/blogs/").then().assertThat().contentType(ContentType.JSON).extract().response();
        jsonAsString = response.asString();
        blogsAsArrayList = from(jsonAsString).get("");
        assertThat(blogsAsArrayList.size(), greaterThan(0));
    }

    @Test
    public void getBlogTest() throws Exception {
        when().get("/blogs/" + blogsAsArrayList.get(0).get("id")).then().assertThat().contentType(ContentType.JSON);
    }

    @Test
    public void deleteBlogTest() throws Exception {
        when().delete("/blogs/0").then().assertThat().statusCode(204);
        blogsAsArrayList.remove(0);
    }

    @AfterClass
    public static void tearDown() throws Exception{
        IDataSet dbDataSet = connection.createDataSet();
        ITable actualTable = dbDataSet.getTable("BLOG");
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"ID"});
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
                new File("src/test/resources/org/atheby/tau/restassured/blogData.xml"));
        ITable expectedTable = expectedDataSet.getTable("BLOG");
        Assertion.assertEquals(expectedTable, filteredTable);
        databaseTester.onTearDown();
    }
}
