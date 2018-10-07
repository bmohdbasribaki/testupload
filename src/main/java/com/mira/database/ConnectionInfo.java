/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mira.database;

import com.jcore.CoreUtil;
import com.zaxxer.hikari.HikariDataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;


/**
 *
 * @author basri baki
 */
public class ConnectionInfo {
    private Logger log = Logger.getLogger(ConnectionInfo.class);
    private Connection con = null;
    private HikariDataSource cpds;
    private String dbUrl = "";
    private String dbUsername = "";
    private String dbPassword = "";
    private int tryerror_max = 3;
    private int tryerror = 0;

    public HikariDataSource getDataSource() {
        return cpds;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }
    
        public ConnectionInfo(HikariDataSource cpds, String estateCode) throws ClassNotFoundException, SQLException {
        this.dbUrl = cpds.getJdbcUrl();
        this.dbUsername = cpds.getUsername();
        this.dbPassword = cpds.getPassword();
        this.cpds = cpds;

    }
    
     public Connection getConnection() {
        long s = System.currentTimeMillis();
        try {
            if (con == null) {
                try {
                    con = cpds.getConnection();
                } catch (SQLException e) {
                    log.error(e, e);
                    //failure - reset connection
                    con = renewConnection(s);
                    if (con == null) {
                        if (tryerror <= tryerror_max) {
                            tryerror++;
                            log.debug("try reconnect again @ " + tryerror + "/" + (tryerror_max + 1));
                            con = getConnection();
                        }
                    } else {
                        //reset tryerror
                        tryerror = 0;
                    }
                }
            }
        } catch (SQLException e) {
            //failure again
            log.error(e, e);
        } finally {
        }
        return con;
    }
     
      public Connection renewConnection(long s) throws SQLException {
        cpds.close();
        Connection renewCon = null;
        try {
            cpds = ConnectionManager.resetDataSource();
            renewCon = cpds.getConnection();
        } catch (PropertyVetoException e) {
            log.error(e, e);
            renewCon = null;
        } finally {
        }
        return renewCon;
    }
}
