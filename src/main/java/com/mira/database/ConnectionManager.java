/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mira.database;

import com.jcore.CoreJdbc;
import com.mira.SysPropUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author basri baki
 */
public class ConnectionManager {

    final private static Logger log = Logger.getLogger(ConnectionManager.class);
    private static Map<String, ConnectionInfo> conEst = new ConcurrentHashMap(new HashMap<String, ConnectionInfo>());
    private static Map<String, ConnectionInfo> conEstTm = new ConcurrentHashMap(new HashMap<String, ConnectionInfo>());
    private static String dbUrl = "";
    private static String dbUsername = "";
    private static String dbPassword = "";
    private static String dbCorp = "";
    private static boolean isLite = false;
    private static boolean isCrt = false;
    private static boolean isDev = false;
    private static boolean isLiteProperties = false;
    private static boolean isSystemProperties = false;
    private static ConnectionInfo conMaster = null;
    public static final int AUTOCLOSE_TIMEOUT = 1000;
    private static ConnectionInfo conCrt = null;

    public static HikariDataSource createDataSource(String dbUrl, String dbUsername, String dbPassword) {
        HikariConfig config = new HikariConfig();
//        config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl(dbUrl + addConfig());
        config.setUsername(dbUsername);
        config.setPassword(dbPassword);
//        if (dbUrl.contains("localhost")) {
            config.setMaximumPoolSize(1);
            config.setMaxLifetime(300000);
            config.setIdleTimeout(120000);
//        } else if (dbUrl.contains("172.16.11")) {
//            config.setMaximumPoolSize(50);
//            config.setMaxLifetime(300000);
//            config.setIdleTimeout(120000);
//        }
        config.setMinimumIdle(1);
        config.setLeakDetectionThreshold(5000);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "500");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new HikariDataSource(config);
    }

    public static HikariDataSource resetDataSource() throws PropertyVetoException {
        return createDataSource(dbUrl, dbUsername, dbPassword);
    }

    private static String addConfig() {
        String ret = "?";
        ret += "jdbcCompliantTruncation=false&";
        ret += "noDatetimeStringSync=true&";
        ret += "zeroDateTimeBehavior=convertToNull&";
        ret += "autoReconnect=true&";
        ret += "loginTimeout=1&";
        ret += "loadBalancePingTimeout=1&";
        ret += "interactiveClient=true&";
        ret += "loadBalanceStrategy=bestResponseTime";
        return ret;
    }

    private static Properties getProperties(String username, String password) {
        Properties prop1 = new Properties();
        prop1.setProperty("jdbcCompliantTruncation", "false");
        prop1.setProperty("noDatetimeStringSync", "true");
        prop1.setProperty("zeroDateTimeBehavior", "convertToNull");
        prop1.setProperty("autoReconnect", "true");
        prop1.setProperty("loginTimeout", "0");
        prop1.setProperty("loadBalancePingTimeout", "1");
        prop1.setProperty("interactiveClient", "true");
        prop1.setProperty("loadBalanceStrategy", "bestResponseTime"); //random or bestResponseTime
        prop1.setProperty("user", username);
        prop1.setProperty("password", password);
        return prop1;
    }

    public static String getHost() {
        return StringUtils.substringBetween(dbUrl, "://", ":");
    }

    public static String getPort() {
        return StringUtils.substringBetween(dbUrl, getHost() + ":", "/");
    }

    public static String getCorpDatabase() {
        return dbCorp + ".";
    }

    public static String corpDatabase() {
        return dbCorp;
    }

    public static boolean isLite() {
        return isLite;
    }

    public static boolean isCrt() {
        return isCrt;
    }

    public static boolean isDev() {
        return isDev;
    }

    public static synchronized Connection getConnection(String url) throws ClassNotFoundException, SQLException {
        return DriverManager.getConnection(url);
    }

    public static synchronized Connection getConnection(String url, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(url, getProperties(username, password));
    }

    /*first connection*/
    public synchronized static Connection getConnection(HttpServletRequest request) {
        HttpSession session = request.getSession();

        return getConnectionMaster().getConnection();
    }

    public static synchronized Connection getConnectionEstate(String estateCode) {
        Connection con = null;
        //check from vcongroup
        ConnectionInfo congp = conEst.get(estateCode);
        if (congp == null) {
            try {
                CoreJdbc cj = new CoreJdbc(conMaster.getConnection());
                if (conMaster.getConnection() == null) {
                    System.out.println("renewConnection corporate");
                    conMaster.renewConnection(System.currentTimeMillis());
                }
                String aodl_database_name = "";
                ResultSet rs = cj.query("select aodl_database_name  from " + getCorpDatabase() + "aodl_database_listing where aodl_estate_code = ?", estateCode);
                boolean found = false;
                if (rs.next()) {
                    aodl_database_name = rs.getString("aodl_database_name");
                    found = true;
                }
                cj.close();
                cj = null;
                if (found) {
                    //create connection estate
                    String urlEstate = "jdbc:mysql://" + getHost() + ":" + getPort() + "/" + aodl_database_name;
                    ConnectionInfo cg = new ConnectionInfo(createDataSource(urlEstate, dbUsername, dbPassword), estateCode);
                    conEst.put(estateCode, cg);
                    con = cg.getConnection();
                    //log.debug("new connect added : " + estateCode);
                } else {
                    //log.debug("estatecode : " + estateCode + " not found!");
                    con = null;
                }
            } catch (SQLException ex) {
                //log.error(ex, ex);
                con = null;
            } catch (ClassNotFoundException ex) {
                //log.error(ex, ex);
                con = null;
            }
        } else {
            con = congp.getConnection();
            //validate connection
            String dbEstate = ConnectionManager.getCurrentDatabase(con);
            //if db is crt.. close and renew connection again
            if (!dbEstate.endsWith(estateCode)) {
                //log.debug("incorrect connection, renew connection again");
                CoreJdbc.closeConnection(con);
                conEst.remove(estateCode);
                con = getConnectionEstate(estateCode);
            }
        }
        return con;
    }

    public static synchronized Connection getConnectionEstateTm(String estateCode) throws Exception {
        Connection con = null;
        //check from vcongroup
        ConnectionInfo congp = conEstTm.get(estateCode);
        if (congp == null) {
            try {
                CoreJdbc cj = new CoreJdbc(conMaster.getConnection());
                String aodl_database_name = "";
                ResultSet rs = cj.query("select aodl_database_name  from " + getCorpDatabase() + "aodl_database_listing where aodl_estate_code = ?", estateCode);
                boolean found = false;
                if (rs.next()) {
                    aodl_database_name = rs.getString("aodl_database_name");
                    found = true;
                }
                cj.close();
                cj = null;
                if (found) {
                    //create connection estate
                    String urlEstate = "jdbc:mysql://" + getHost() + ":" + getPort() + "/" + aodl_database_name;
                    ConnectionInfo cg = new ConnectionInfo(createDataSource(urlEstate, dbUsername, dbPassword), estateCode);
                    conEstTm.put(estateCode, cg);
                    con = cg.getConnection();
                    con.setAutoCommit(false);
                    //log.debug("new connect added : " + estateCode);
                } else {
                    //log.debug("estatecode : " + estateCode + " not found!");
                    con = null;
                }
            } catch (SQLException ex) {
                //log.error(ex, ex);
                con = null;
            } catch (ClassNotFoundException ex) {
                //log.error(ex, ex);
                con = null;
            }
        } else {
            con = congp.getConnection();
            //validate connection
            String dbEstate = ConnectionManager.getCurrentDatabase(con);
            //if db is crt.. close and renew connection again
            if (!dbEstate.endsWith(estateCode)) {
                //log.debug("incorrect connection, renew connection again");
                CoreJdbc.closeConnection(con);
                conEstTm.remove(estateCode);
                con = getConnectionEstateTm(estateCode);
                con.setAutoCommit(false);
            }
        }
        return con;
    }

    /**
     * return current database
     *
     * @param con
     * @return
     */
    public static String getCurrentDatabase(Connection con) {
        if (con == null) {
            return "N/A";
        }
        try {
            CoreJdbc cj = new CoreJdbc(con);
            String db = cj.queryForString("select database()");
            cj.close();
            return db;
        } catch (SQLException e) {
            //log.error(e, e);
            return "N/A";
        }
    }

    /**
     * return version mysql
     *
     * @param con
     * @return
     */
    public static String getVersionMysql(Connection con) {
        if (con == null) {
            return "N/A";
        }
        try {
            CoreJdbc cj = new CoreJdbc(con);
            String db = cj.queryForString("select version()");
            ResultSet rs = cj.query("SHOW VARIABLES LIKE 'version_compile_os'");
            String os = "";
            if (rs.next()) {
                os = rs.getString(2);
            }
            rs.close();
            cj.close();
            rs = null;
            cj = null;
            return db + "," + os;
        } catch (SQLException e) {
            //log.error(e, e);
            return "N/A";
        }
    }

    /**
     * get connection from ConnectionGroup based in session
     *
     * @param request
     * @return
     */
    /**
     * convert object to string
     *
     * @param o
     * @return
     */
    private static String obj2string(String o) {
        try {
            if (o == null) {
                return "";
            }
            return o;
        } catch (Exception e) {
            //log.error(e, e);
            return "";
        }
    }

    public static String getDefaultDatabase() {
        return ConnectionManager.getCurrentDatabase(conMaster.getConnection());
    }

    public static boolean isLiteProperties() {
        return isLiteProperties;
    }

    public static boolean isSystemProperties() {
        return isSystemProperties;
    }

    /**
     * lite.properties exist?
     *
     * @return
     */
    /**
     * system.properties exist?
     *
     * @return
     */
    private static void checkSystemProperties() {
        try {
            Properties prop = new Properties();
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
            isSystemProperties = true;
        } catch (Exception e) {
//            //log.error(e, e);
            isSystemProperties = false;
        }
    }

    /**
     * start up connection pool
     *
     * @param sce
     */
    public static Connection getConnection() {
        Connection con = null;
        //validate file properties
        checkSystemProperties();

        System.out.println("log = " + log);
        System.out.println("isSystemProperties = " + isSystemProperties);
        try {
            if (isSystemProperties) {
                //open connection remote database
                //log.debug("*** ESTATE ONLINE ***");
                isLite = false;
                isLiteProperties = false;
                startRemoteDatabase();
                con = validateConnection();
                
                
            }
        } catch (SQLException e) {
            //log.error(e, e);
        } catch (ClassNotFoundException e) {
            //log.error(e, e);
        }
        return con;
    }

    private static Connection validateConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        System.out.println("con = " + con);
        PreparedStatement ps = con.prepareStatement("select 1");
        CoreJdbc.closePreparedStatement(ps);
        return con;
//        CoreJdbc.closeConnection(con);
    }

    public static boolean validateConnectionAccess() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        boolean connectionEstablished = false;
        ////log.debug("dbPassword : "+dbPassword);
        Connection con;
        try {
            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            Statement smt = con.createStatement();
            ResultSet rs = smt.executeQuery("select 1");
            if (rs.next()) {
                connectionEstablished = true;
                //log.info("Connection OK");
                CoreJdbc.closeConnection(con);
            } else {
                connectionEstablished = false;
                //log.info("Connection Not OK");
            }
        } catch (SQLException ex) {
            connectionEstablished = false;
//            java.util.logging.Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connectionEstablished;
    }

    /**
     * get count of tables
     *
     * @return
     */
    public static int getCountTables() {
        try {
            Connection con = ConnectionManager.getConnection(dbUrl, dbUsername, dbPassword);
            CoreJdbc cj = new CoreJdbc(con);
            int total = cj.queryForInt("select count(*) from information_schema.`TABLES` a where a.TABLE_SCHEMA = database()");
            cj.close();
            return total;
        } catch (ClassNotFoundException e) {
            //log.error(e, e);
            return 0;
        } catch (SQLException e) {
            //log.error(e, e);
            return 0;
        }
    }

    /**
     * start local database
     */
    private static void startLocalDatabase() {
        try {
            String estateCode = SysPropUtils.getProperty("estate.code");
            //database connection
            dbUrl = obj2string(SysPropUtils.getProperty("dbUrl"));
            dbUsername = obj2string(SysPropUtils.getProperty("dbUsername"));
            dbPassword = obj2string(SysPropUtils.getProperty("dbPassword"));
            //check database if exist
            createDatabaseIfNotFound(dbUrl, estateCode, dbUsername, dbPassword);
            dbCorp = dbUrl.substring(dbUrl.lastIndexOf("/") + 1);
            //log.debug("dbCorp : " + dbCorp);
            isCrt = false;
            isDev = false;
            //create master connection
            conMaster = createConnectionMaster(createDataSource(dbUrl, dbUsername, dbPassword));
        } catch (ClassNotFoundException e) {
            //log.error(e, e);
        } catch (SQLException e) {
            //log.error(e, e);
        }
    }

    /**
     * start remote database (read from db.propteries)
     */
    private static void startRemoteDatabase() {
        try {
            //read from db.properties
            Properties dbCfg = new Properties();
            dbCfg.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
            dbUrl = obj2string(dbCfg.getProperty("dbUrl"));
            dbUsername = obj2string(dbCfg.getProperty("dbUsername"));
            dbPassword = obj2string(dbCfg.getProperty("dbPassword"));

            //create master connection
            conMaster = createConnectionMaster(createDataSource(dbUrl, dbUsername, dbPassword));

        } catch (IOException e) {
            //log.error(e, e);
        } catch (ClassNotFoundException e) {
            //log.error(e, e);
        } catch (SQLException e) {
            //log.error(e, e);
        }
    }

    public static void createDatabaseIfNotFound(String dbUrl, String estatecode, String dbUsername, String dbPassword) {
        //security
        boolean ok = false;
        if (dbUrl.contains("localhost")) {
            ok = true;
        }
        if (dbUrl.contains("127.0.0.1")) {
            ok = true;
        }
        if (!ok) {
            //log.error("Security Policy eRMLLITE: NOT allow connect to remote database.");
            return;
        }
        //replace dbUrl
        String aa[] = StringUtils.split(dbUrl, "/");
        String dburl = aa[0] + "//" + aa[1] + "/mysql";
        String dbname = aa[2];
        //log.debug(dburl + " @ " + dbname);
        Connection con = null;
        try {
            con = ConnectionManager.getConnection(dburl, dbUsername, dbPassword);
            PreparedStatement ps = con.prepareStatement("show databases like '" + dbname + "'");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            } else {
                //create database
                PreparedStatement ps0 = con.prepareStatement("create database `" + dbname + "`");
                ps0.execute();
                ps0.close();
                ps0 = null;
                //log.debug("database `" + dbname + "` created successfully");
            }
            rs.close();
            ps.close();
            rs = null;
            ps = null;
        } catch (ClassNotFoundException e) {
            //log.error(e, e);
        } catch (SQLException e) {
            //log.error(e, e);
        } finally {
            if (con != null) {
                CoreJdbc.closeConnection(con);
            }
        }
    }

    /**
     * create new connection
     *
     * @param cpds
     * @return
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public static ConnectionInfo createConnectionMaster(HikariDataSource cpds) throws ClassNotFoundException, SQLException {
        return new ConnectionInfo(cpds, "HQ01");
    }

    /**
     * release current connection if connection not stable, close current
     * connection and create new connection
     *
     * @return
     */
    public static synchronized ConnectionInfo getConnectionMaster() {
        if (conMaster == null) {
            return null;
        }
        return conMaster;
    }

}
