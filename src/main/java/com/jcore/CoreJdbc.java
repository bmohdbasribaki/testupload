/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcore;

import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author basri baki
 */
public class CoreJdbc {
    
    private ResultSet rs = null;
    private PreparedStatement ps = null;
    private Connection con = null;
    private String sql = null;
    private Object[] bind = null;
    
    public CoreJdbc(Connection con){
            this.con = con;
    }
    
    public Connection getConnection(){
        return this.con;
    }
    
    public ResultSet query(String sql, Object... params) throws SQLException {
        synchronized(con) {
            ps = con.prepareStatement(sql);
            this.sql = sql;
            this.bind = params;
            for(int x=0; x<params.length; x++) {
                ps.setObject((x+1), params[x]);
            }
            return ps.executeQuery();
        }
    }
    
    public void executeScript(File fileSQL) throws SQLException,IOException {
        synchronized(con) {
            PreparedStatement pss = con.prepareStatement(FileUtils.readFileToString(fileSQL));
            pss.execute();
            pss.close();
        }
    }
    
    public String queryForString(String sql, Object... params) throws SQLException {
        synchronized(con) {
            ps = con.prepareStatement(sql);
            for(int x=0; x<params.length; x++) {
                ps.setObject((x+1), params[x]);
            }
            ResultSet rs = ps.executeQuery();
            String data = null;
            if(rs.next()) {
                data = CoreUtil.toString(rs.getString(1));
            }
            return data;
        }
    }
    
    public double queryForDouble(String sql, Object... params) throws SQLException {
        Statement stm = null;  ResultSet rs = null;  double data = 0;
        try {
            if (params != null) {
                PreparedStatement ps = con.prepareStatement(sql);  stm = ps;
                for (int x=0; x<params.length; x++) {
                    ps.setObject((x+1), params[x]);
                }
                rs = ps.executeQuery();                
            } else {
                stm = con.createStatement();
                rs = stm.executeQuery(sql);
            }
            if (rs.next()) {
                data = rs.getDouble(1);
            }
        } 
        finally {
            if (rs != null) { rs.close(); }
            if (stm != null) { stm.close(); }    
        }
        return data;
    }
    
    public int queryForInt(String sql, Object... params) throws SQLException {
        synchronized(con) {
            ps = con.prepareStatement(sql);
            for(int x=0; x<params.length; x++) {
                ps.setObject((x+1), params[x]);
            }
            ResultSet rs = ps.executeQuery();
            int data = 0;
            if(rs.next()) {
                data = rs.getInt(1);
            }
            return data;
        }
    }
    
        public int execute(String sql, Object... params) throws SQLException {
        synchronized(con) {
            ps = con.prepareStatement(sql);
            this.sql = sql;
            this.bind = params;
            for(int x=0; x<params.length; x++) {
                ps.setObject((x+1), params[x]);
            }
            return ps.executeUpdate();
        }
    }
    
     public int executeNotNull(String sql, Object... params) throws SQLException {
         synchronized(con) {
            ps = con.prepareStatement(sql);
            int y = 1;
            for (int x = 0; x < params.length; x++) {
                if(params[x] != null){
                   ps.setObject((y), params[x]);
                   y++;
                }   
            }
            return ps.executeUpdate();
         }
    }
     
     public void close() {
        closePreparedStatement(ps);
        closeResultSet(rs);
    }
     
       public static void closeResultSet(ResultSet rs) {
        try {
            rs.close();
            rs = null;
        }catch(Exception e) {}
    }
    
    
    /**
     * close PreparedStatement
     * 
     * @param ps 
     */
    public static void closePreparedStatement(PreparedStatement ps) {
        try {
            ps.close();
            ps = null;
        }catch(Exception e) {}
    }
    
    /**
     * close CallableStatement
     * 
     * @param cs 
     */
    public static void closeCallableStatement(CallableStatement cs) {
        try {
            cs.close();
            cs = null;
        }catch(Exception e) {}
    }
    
    /**
     * close Connection
     * 
     * @param con
     */
    public static void closeConnection(Connection con) {
        try {
            con.close();
            con = null;
        }catch(Exception e) {}
    }
    
        /**
     * generate sql with bind parameter
     * @return
     */
    public String getQueryString() {
        if (CoreUtil.isEmpty(sql)) {
            return "";
        }
        if(bind == null) {
            return sql;
        }
        StringBuilder buf = new StringBuilder();
        StringTokenizer tok = new StringTokenizer(sql + " ", "?");
        int x = 0;
        while (tok.hasMoreTokens()) {
            String oneChunk = tok.nextToken();
            buf.append(oneChunk);
            Object value = null;
            if(x < bind.length) {
                value = bind[x];
                if(value instanceof java.util.Date) {
                    value = "'"+CoreUtil.formatDateTime("yyyy.MM.dd", (java.util.Date)value)+"'";
                }else if(value instanceof java.sql.Date) {
                    value = "'"+CoreUtil.formatDateTime("yyyy.MM.dd", (java.sql.Date)value)+"'";
                }else if(value instanceof String) {
                    value = "'"+value+"'";
                }
                buf.append(value);
            }
            x++;
        }
        return buf.toString().trim();
    }

    public ResultSet logQuery(String log, String sql, Object... params) throws SQLException {
        synchronized(con) {
            //System.out.println(log);
            //System.out.println(sql);
            //System.out.println("----------------------------------------------------------------------------------------------------------------------------");
            ps = con.prepareStatement(sql);
            for(int x=0; x<params.length; x++) {
                ps.setObject((x+1), params[x]);
            }
            return ps.executeQuery();
        }
    }
    
    public void commit(){
        try {
            con.commit();
        }catch(SQLException e) {}
    }
    
    public void rollback(){
        try {
            con.rollback();
        }catch(SQLException e) {}
    }
    
}
