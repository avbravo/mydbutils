package com.avbravo.mavenproject2.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 *
 * @author avbravo
 */
public class DBUnitCrud {

    private Connection connection = null;

    public DBUnitCrud() {
    }

    public void run() throws Exception {
        setupDB();
        findAdllMap();
        findAll();
        querySingle();
        findByNombre("Pedro");
        closeBD();
    }

    public void setupDB() throws Exception {
//        Class.forName("org.h2.Driver");
//        String db
//                = "jdbc:h2:mem:;INIT=runscript from 'classpath:/Empleados.sql'";

        if (connection == null) {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = connection = DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433/DATABASE;instance=SQLEXPRESS;", "sa", "MIPASSWORD");

        }
    }

    public void closeBD() {
        DbUtils.closeQuietly(connection);
    }

    //Let's start with a quick example of obtaining all the records in the database as a list of maps using a MapListHandler:
    public void findAdllMap()
            throws SQLException {
        MapListHandler beanListHandler = new MapListHandler();

        QueryRunner runner = new QueryRunner();
        List<Map<String, Object>> list
                = runner.query(connection, "SELECT * FROM Empleado", beanListHandler);

        System.out.println("--------------------findAdllMap---------------------------------");
        System.out.println("registros " + list.size());
        System.out.println("primer persona " + list.get(0).get("nombre"));
        System.out.println("5 persona " + list.get(4).get("nombre"));

    }

    //BeanListHandler to transform the results into Employee instances:
    public void findAll()
            throws SQLException {
        BeanListHandler<Empleado> beanListHandler
                = new BeanListHandler<>(Empleado.class);

        QueryRunner runner = new QueryRunner();
        List<Empleado> EmpleadoList
                = runner.query(connection, "SELECT * FROM Empleado", beanListHandler);
        System.out.println("-------------------findAll----------------------------------");
        System.out.println("total " + EmpleadoList.size());
        System.out.println("6 persona " + EmpleadoList.get(5).getNombre());

//    assertEquals(EmpleadoList.get(4).getFirstName(), "Christian");
    }

    /**
     * For queries that return a single value, we can use a ScalarHandler:
     *
     * @throws SQLException
     */
    public void querySingle()
            throws SQLException {
        ScalarHandler<Integer> scalarHandler = new ScalarHandler<>();

        QueryRunner runner = new QueryRunner();

        System.out.println("---------------querySingle()--------------------------------------");
        String query = "SELECT COUNT(*) FROM Empleado";
        Integer count
                = runner.query(connection, query, scalarHandler);
        System.out.println("----> total de empleados " + count);

    }

    public void insert() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String insertSQL
                = "INSERT INTO Empleado (nombre,id,fecha) "
                + "VALUES (?, ?, ?)";

        int numRowsInserted
                = runner.update(
                        connection, insertSQL, "Ana", "4",  new Date());

        if (numRowsInserted == 1) {
            System.out.println("insertado");
        } else {
            System.out.println("no insertado");
        }

    }

    public void insertAndupdate() throws SQLException {
        QueryRunner runner = new QueryRunner();
        int inserts = runner.update(connection, "INSERT INTO Empleado (nombre,id,fecha) VALUES (?,?,?)",
                "Juan", "3",new Date());
        // The line before uses varargs and autoboxing to simplify the code

        // Now it's time to rise to the occation...
        int updates = runner.update(connection, "UPDATE Empleado SET nombre=? WHERE id=?",
                "Maria", "3");

        if (inserts == 1) {
            System.out.println("insertado");
        } else {
            System.out.println("no insertado");
        }
        if (updates == 1) {
            System.out.println("actualizado");
        } else {
            System.out.println("no updates");
        }

    }

    public void findByNombre(String nombre) throws SQLException {

        QueryRunner runner = new QueryRunner();

        ResultSetHandler<Empleado> t = new BeanHandler<Empleado>(Empleado.class);
        System.out.println("----------------- findByNombre()----------------------------");
// Execute the SQL statement with one replacement parameter and
// return the results in a new Empleado object generated by the BeanHandler.
        Empleado p = runner.query(connection,
                "SELECT * FROM Empleado WHERE nombre=?", t, nombre);

        if (p == null) {
            System.out.println("no encontro un empleado  con ese nombre ");
        } else {
            System.out.println("nombre " + p.getNombre());
            System.out.println("id " + p.getId());
            System.out.println("fecha " + p.getFecha());
        }

    }
}
