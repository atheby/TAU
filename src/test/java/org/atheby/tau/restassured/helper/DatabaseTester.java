package org.atheby.tau.restassured.helper;

import org.atheby.tau.restassured.service.*;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.*;
import org.dbunit.dataset.*;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import java.io.*;
import java.sql.*;

public class DatabaseTester {

    private static IDatabaseConnection connection ;
    private static IDatabaseTester databaseTester;
    private IDataSet actualDataSet;
    private ITable actualTable, expectedTable;

    private static final String RESOURCE_URL = "src/test/resources/org/atheby/tau/restassured/";
    private static final String DB_DRIVER = "org.hsqldb.jdbcDriver";
    private static final String DB_URL = "jdbc:hsqldb:hsql://localhost/workdb";
    private static final String DB_USER = "sa";
    private static final String DB_PASS = "";

    public DatabaseTester() throws Exception {
        new BlogManager();
        new PostManager();
        new CommentManager();
        Connection jdbcConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        connection = new DatabaseConnection(jdbcConnection);

        DatabaseConfig dbConfig = connection.getConfig();
        dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());

        databaseTester = new JdbcDatabaseTester(DB_DRIVER, DB_URL, DB_USER, DB_PASS);

        actualDataSet = new FlatXmlDataSetBuilder().build(new FileInputStream(new File(RESOURCE_URL + "fullData.xml")));

        databaseTester.setDataSet(actualDataSet);
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);

        cleanInsert();
    }

    public IDatabaseTester getIDatabaseTester() {
        return databaseTester;
    }

    public void setActualTable(String tableName) throws Exception {
        IDataSet dataSet = connection.createDataSet();
        actualTable = dataSet.getTable(tableName);
    }

    public ITable getActualTable() {
        return actualTable;
    }

    public void setExpectedTable(String dataSet, String tableName) throws Exception{
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File(RESOURCE_URL + dataSet));
        expectedTable = expectedDataSet.getTable(tableName);
    }

    public ITable getExpectedTable() {
        return expectedTable;
    }

    public void cleanInsert() throws Exception {
        DatabaseOperation.CLEAN_INSERT.execute(connection, actualDataSet);
    }
}
