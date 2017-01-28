package org.atheby.tau.restassured;

import org.atheby.tau.restassured.domain.Post;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
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
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class PostRestTest {

    private static IDatabaseConnection connection ;
    private static IDatabaseTester databaseTester;
    private static Response response;
    private static String jsonAsString;
    private static ArrayList<Map<String, ?>> postsAsArrayList;

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
    public void addPostsTest() throws Exception {
        int blogCount = databaseTester.getDataSet().getTable("BLOG").getRowCount();
        for(int i = 0; i < blogCount; i++) {
            Long blogId = Long.parseLong(databaseTester.getDataSet().getTable("BLOG").getValue(i, "id").toString());
            for(int j = 0; j <= i * 2; j++) {
                Post post = new Post(blogId, "Post " + i + " " + j, "Lorer mere " + j);
                given().contentType("application/json; charset=UTF-16").body(post)
                        .when().post("/posts/").then().assertThat().statusCode(201);
            }
        }
    }

    @Test
    public void getAllPostsTest() {
        response =
                when().get("/posts/").then().assertThat().contentType(ContentType.JSON).extract().response();
        jsonAsString = response.asString();
        postsAsArrayList = from(jsonAsString).get("");
        assertThat(postsAsArrayList.size(), greaterThan(0));
    }

    @AfterClass
    public static void tearDown() throws Exception{
        IDataSet dbDataSet = connection.createDataSet();
        ITable actualTable = dbDataSet.getTable("POST");
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"ID"});
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
                new File("src/test/resources/org/atheby/tau/restassured/postData.xml"));
        ITable expectedTable = expectedDataSet.getTable("POST");
        Assertion.assertEquals(expectedTable, filteredTable);
        databaseTester.onTearDown();
    }
}
