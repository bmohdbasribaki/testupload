/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mira.helper;

import com.jcore.CoreJdbc;
import com.jcore.CoreUtil;
import com.mira.BaseActionBean;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.time.DateUtils;

/**
 *
 * @author basri baki
 */
public class DataIndexRawTracking extends BaseActionBean {

    CoreJdbc cjBasri = null;

    public DataIndexRawTracking() {
    }

    public DataIndexRawTracking(Connection con) {
        this.cjBasri = new CoreJdbc(con);
    }

    public List<Integer> getTrackingNo() throws SQLException {
//        CoreJdbc cj = new CoreJdbc(getConnection());
        List<Integer> list = new ArrayList<Integer>();

        String Sql = "Select * from firt_index_raw_tracking order by firt_running_no desc limit 1";
        ResultSet rs = this.cjBasri.query(Sql);
        if (rs.next()) {
            list.add(rs.getInt("firt_start"));
            list.add(rs.getInt("firt_finish"));

        }

        return list;
    }

    public void InsertTableFgct() throws SQLException {


        List<Integer> list = getTrackingNo();

        int[] j = new int[2];
        for (int i = 0; i < list.size(); i++) {
            j[i] = list.get(i);
        }

        Map<String, Object[]> map = new LinkedHashMap<String, Object[]>();

        String str = "";
        String running_no = "", patient_id = "", session_id = "", game = "", type = "", value = "", patient_name = "", date = "", diagnosis = "",m="";
        String sqlPatient = "Select * from fdtr_data_table_raw  where RunningNo between " + j[0] + " and " + j[1] + " group by PatientID";
//        String sqlPatient = "Select * from fdtr_data_table_raw  where RunningNo between " + j[0] + " and " + j[1] + " and PatientId = " + patient_id + "";
        System.out.println("sqlPatient = " + sqlPatient);
        ResultSet rs = this.cjBasri.query(sqlPatient);
        while (rs.next()) {
            patient_id = rs.getString("PatientId");
            patient_name = rs.getString("FirstName");
            diagnosis = rs.getString("Diagnosis");
            str = patient_id;
            String sqlSession = "Select * from fdtr_data_table_raw  where RunningNo between " + j[0] + " and " + j[1] + " and PatientId = " + patient_id + " group by SessionId  ";
            ResultSet rsSes = this.cjBasri.query(sqlSession);
            while (rsSes.next()) {

                session_id = rsSes.getString("SessionId");
                date = rsSes.getString("SessionStartDate").substring(0, 9);
                str = str + "," + session_id;
                String sqlExercise = "Select * from fdtr_data_table_raw where RunningNo between " + j[0] + " and " + j[1] + " and SessionId = " + session_id + " group by `ExerciseGame`,`Movement`";
                ResultSet rsGame = this.cjBasri.query(sqlExercise);
                while (rsGame.next()) {
                    String avgAcc = "", avgDec = "", avgPer = "", avgSpd = "", pts = "", rep = "", dif = "", move = "", side = "", dis = "";
                    game = rsGame.getString("ExerciseGame");
                    m = rsGame.getString("Movement");
                    
                    String sqlType = "Select * from fdtr_data_table_raw where RunningNo between " + j[0] + " and " + j[1] + " and SessionId = " + session_id + " and ExerciseGame = '" + game + "' and Movement='"+m+"'";
                    ResultSet rsType = this.cjBasri.query(sqlType);
                    while (rsType.next()) {
                        running_no = rsType.getString("RunningNo");
                        type = rsType.getString("Name");
                        value = rsType.getString("Value");
                        move = rsType.getString("Movement");
                        side = rsType.getString("Side");
                        dif = rsType.getString("Difficulty");

                        if (type.equalsIgnoreCase("Average Acceleration")) {
                            avgAcc = value;
                        } else if (type.equalsIgnoreCase("Average Deceleration")) {
                            avgDec = value;
                        } else if (type.equalsIgnoreCase("Average Percentage")) {
                            avgPer = value;
                        } else if (type.equalsIgnoreCase("Average Speed")) {
                            avgSpd = value;
                        } else if (type.equalsIgnoreCase("Points")) {
                            pts = value;
                        } else if (type.equalsIgnoreCase("Repetitions")) {
                            rep = value;
                        } else if (type.equalsIgnoreCase("Distance")) {
                            dis = value;
                        }

                    }

                    System.out.println("inserting........." + running_no);
                    
                    this.cjBasri.execute("insert into fgct_graph_consolidation_table "
                            + "("
                            + "fgct_patient_id,"
                            + "fgct_patient_name,"
                            + "fgct_session_id,"
                            + "fgct_diagnosis,"
                            + "fgct_date,"
                            + "fgct_game,"
                            + "fgct_movement,"
                            + "fgct_side,"
                            + "fgct_avg_acc,"
                            + "fgct_avg_dec,"
                            + "fgct_distance,"
                            + "fgct_avg_percentage,"
                            + "fgct_avg_speed,"
                            + "fgct_points,"
                            + "fgct_repetations,"
                            + "fgct_difficulty) "
                            + "values("
                            + "'" + patient_id + "',"
                            + "'" + patient_name + "',"
                            + "'" + session_id + "',"
                            + "'" + diagnosis + "',"
                            + "'" + date + "',"
                            + "'" + game + "',"
                            + "'" + move + "',"
                            + "'" + side + "',"
                            + "'" + avgAcc + "',"
                            + "'" + avgDec + "',"
                            + "'" + dis + "',"
                            + "'" + avgPer + "',"
                            + "'" + avgSpd + "',"
                            + "'" + pts + "',"
                            + "'" + rep + "',"
                            + "'" + dif + "') ");
                    

                }

            }

        }
        
        this.cjBasri.close();
        
    }

    public static void main(String args[]) throws SQLException, Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mira1.0.0?noDatetimeStringSync=true&&user=root&password=root");
            DataIndexRawTracking ob = new DataIndexRawTracking(con);
            ob.InsertTableFgct();

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(DataIndexRawTracking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
