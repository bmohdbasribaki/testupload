/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mira.bean.graph;

import com.jcore.CoreJdbc;
import com.jcore.CoreUtil;
import com.mira.BaseActionBean;
import com.mira.entity.FgctGraphConsolidationTable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author basri baki
 */
public class FgctGraphConsolidationTableBean extends BaseActionBean {

    private Connection con;
    private String patientId;
    private String game;
    private String move;
    private String side;
    private String fromDate;
    private String toDate;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public FgctGraphConsolidationTableBean() {

//        System.out.println("con = " + con);
    }

    public List getGameType() throws SQLException {
        CoreJdbc cj = new CoreJdbc(this.con);
        List<String> ls = new ArrayList<String>();
        String sql = "SELECT DISTINCT(fgct_game) as game FROM `fgct_graph_consolidation_table` where fgct_patient_id = '" + this.getPatientId() + "'";
        System.out.println("sql = " + sql);
        ResultSet rs = cj.query(sql);
        while (rs.next()) {
            ls.add(rs.getString("game"));

        }
        cj.close();
//        CoreJdbc.closeConnection(this.con);

        return ls;
    }
    
    public String getDiagnosis() throws SQLException{
        CoreJdbc cj = new CoreJdbc(this.con);
        String diag = "";
        String sql="select fgct_diagnosis from fgct_graph_consolidation_table where fgct_patient_id = '" + this.getPatientId() + "' ";
        ResultSet rs = cj.query(sql);
        if(rs.next()){
            diag = rs.getString("fgct_diagnosis");
        }
        cj.close();
        return diag;
    }

    public List getMovement(String gameType) throws SQLException {
        CoreJdbc cj = new CoreJdbc(this.con);
        List<String> ls = new ArrayList<String>();
        String sql = "SELECT DISTINCT(fgct_movement) as move FROM `fgct_graph_consolidation_table` where fgct_patient_id = '" + this.getPatientId() + "' and fgct_game='"+gameType+"'";
        System.out.println("sql = " + sql);
        ResultSet rs = cj.query(sql);
        while (rs.next()) {
            ls.add(rs.getString("move"));

        }
        cj.close();
//        CoreJdbc.closeConnection(this.con);

        return ls;
    }

    public List getSideAction(String gameType,String move) throws SQLException {
        CoreJdbc cj = new CoreJdbc(this.con);
        List<String> ls = new ArrayList<String>();
        String sql = "SELECT DISTINCT(fgct_side) as side FROM `fgct_graph_consolidation_table` where fgct_patient_id = '" + this.getPatientId() + "' and fgct_game='"+gameType+"' and fgct_movement ='"+move+"'";
        ResultSet rs = cj.query(sql);
        while (rs.next()) {
            ls.add(rs.getString("side"));

        }
        cj.close();
//        CoreJdbc.closeConnection(this.con);

        return ls;
    }

    public JSONObject getData() throws SQLException, JSONException, ParseException {
        JSONObject json = new JSONObject();

        JSONArray jarr = new JSONArray();

        CoreJdbc cj = new CoreJdbc(this.con);
        String filter = "";
        if(this.getFromDate().equalsIgnoreCase("") && this.getToDate().equalsIgnoreCase("")){
           
        }else{
             filter = "where fgct_date between '" + CoreUtil.removeInitial0(this.getFromDate()) + "' and '" + CoreUtil.removeInitial0(this.getToDate()) + "'";
        }
        String sql = "select * from ("
                + "select * from fgct_graph_consolidation_table where fgct_patient_id = '" + this.getPatientId() + "' and fgct_game = '" + this.getGame() + "' and fgct_movement = '" + this.getMove() + "' and "
                + "fgct_side = '" + this.getSide() + "' ORDER BY fgct_session_id) as t "+filter;
        System.out.println("sql = " + sql);
        ResultSet rs = cj.query(sql);
        while (rs.next()) {
//            System.out.println("date" + rs.getString("fgct_date"));
            JSONObject dataDetails = new JSONObject();
            dataDetails.put("date", rs.getString("fgct_date"));
            dataDetails.put("avgAcc", CoreUtil.round(rs.getDouble("fgct_avg_acc"), 2));
            dataDetails.put("avgPoints", CoreUtil.round(rs.getDouble("fgct_points"), 2));
            dataDetails.put("avgDis", CoreUtil.round(rs.getDouble("fgct_distance"), 2));
            dataDetails.put("avgPrct", CoreUtil.round(rs.getDouble("fgct_avg_percentage"), 2));
            dataDetails.put("rep", CoreUtil.round(rs.getDouble("fgct_repetations"), 2));
            dataDetails.put("points", CoreUtil.round(rs.getDouble("fgct_points"), 2));
            jarr.put(dataDetails);
        }
        cj.close();
//        CoreJdbc.closeConnection(this.con);

        json.put("data", jarr);
        return json;
    }

    public JSONObject getDataDifficulty() throws JSONException, SQLException {

        JSONObject jarr = new JSONObject();

        CoreJdbc cj = new CoreJdbc(this.con);

        Map<String, String> map = new HashMap<String, String>();
        map.put("MINEASY", "EASY");
        map.put("MAXEASY", "EASY");
        map.put("MINMEDIUM", "MEDIUM");
        map.put("MAXMEDIUM", "MEDIUM");
        map.put("MINHARD", "HARD");
        map.put("MAXHARD", "HARD");

        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {

            String key = it.next();
            String sel = key.substring(0, 3);

            String value = map.get(key);

            String sql = "select " + sel + "(fgct_date) as fgct_date from fgct_graph_consolidation_table where fgct_patient_id = '" + this.getPatientId() + "' and fgct_game = '" + this.getGame()
                    + "' and fgct_movement = '" + this.getMove() + "' and "
                    + "fgct_side = '" + this.getSide() + "' and fgct_difficulty = '" + value + "' ORDER BY fgct_session_id";
            System.out.println("sql = " + sql);

            ResultSet rs = cj.query(sql);
            if (rs.next()) {

                jarr.put(key, rs.getString("fgct_date"));
            }

        }

        cj.close();
//        CoreJdbc.closeConnection(this.con);
        return jarr;
    }

    public List<FgctGraphConsolidationTable> getDataList(String pid) {
        List<FgctGraphConsolidationTable> fgctEntity = new ArrayList<FgctGraphConsolidationTable>();
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Query query = session.createQuery("from FgctGraphConsolidationTable where fgct_patient_id = '"+pid+"' ");
        fgctEntity = query.list();
        t.commit();
        session.close();
        
        return fgctEntity;
    }

}
