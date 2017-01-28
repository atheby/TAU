package org.atheby.tau.restassured;

import org.atheby.tau.restassured.domain.Comment;
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

import static com.jayway.restassured.RestAssured.*;

public class CommentRestTest {

    private static IDatabaseConnection connection ;
    private static IDatabaseTester databaseTester;

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
    public void addComments() throws Exception{
        Comment comm1 = new Comment(4, "Author 5", "Lorem ipsum");
        Comment comm2 = new Comment(4, "Author 6", "Lorem ipsum");

        given().contentType("application/json; charset=UTF-16").body(comm1)
                .when().post("/comments/").then().assertThat().statusCode(201);

        given().contentType("application/json; charset=UTF-16").body(comm2)
                .when().post("/comments/").then().assertThat().statusCode(201);
    }

    @Test
    public void getComment() throws Exception{
        when().get("/comments/3").then().assertThat().contentType(ContentType.JSON);
    }

    @AfterClass
    public static void tearDown() throws Exception{
        IDataSet dbDataSet = connection.createDataSet();
        ITable actualTable = dbDataSet.getTable("COMMENT");
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"ID", "POSTID"});
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
                new File("src/test/resources/org/atheby/tau/restassured/commentData.xml"));
        ITable expectedTable = expectedDataSet.getTable("COMMENT");
        Assertion.assertEquals(expectedTable, filteredTable);
        databaseTester.onTearDown();
    }
}
