/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mira.bean.machineLearning;

import com.jcore.CoreJdbc;
import com.mira.BaseActionBean;
import com.mira.entity.FgctGraphConsolidationTable;
import com.mira.entity.FmlcMachineLearningScore;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author basri baki
 */
public class FmlcMachineLearningScoreBean extends BaseActionBean {

    private Connection con;
    private String patientId;
    private int taa_pass;
    private int tap_pass;
    private int tas_pass;
    private int tar_pass;
    private int tad_pass;
    private int tapo_pass;
    private String support_msg="";
    private String score_msg = "";
    private String sess_msg = "";

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void clearTableFmlc() {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Query query = session.createQuery("delete FmlcMachineLearningScore ");
        int result = query.executeUpdate();
        t.commit();
        session.close();
    }

    public void insertTableFmlc(String weakness) throws SQLException {
        CoreJdbc cj = new CoreJdbc(this.con);
        System.out.println("weakness = " + weakness);

        String sql = "SELECT fgct_patient_id,fgct_patient_name,COUNT(fgct_session_id) as fgct_session_id,fgct_game,fgct_movement,fgct_side,fgct_difficulty,AVG(fgct_avg_acc) as fgct_avg_acc ,"
                + " AVG(fgct_avg_percentage) as fgct_avg_percentage,AVG(fgct_avg_speed) as fgct_avg_speed,AVG(fgct_distance) as fgct_distance,AVG(fgct_points) as fgct_points,AVG(fgct_repetations) as fgct_repetations"
                + " FROM `fgct_graph_consolidation_table` "
                + "where fgct_patient_id = '" + this.getPatientId() + "' "
                //                + "and fgct_game = 'Izzy the bee' and fgct_movement='Knee Flexion' and fgct_side = 'left' "
                + "and fgct_difficulty <> '' and fgct_side in ('left','right') GROUP BY fgct_game , fgct_movement , fgct_side , fgct_difficulty ";
        ResultSet rs = cj.query(sql);
        while (rs.next()) {
            taa_pass = 1;
            tap_pass = 1;
            tas_pass = 1;

            String reference = rs.getString("fgct_game").replaceAll("\\s", "") + rs.getString("fgct_movement").replaceAll("\\s", "") + rs.getString("fgct_side");;
            String name = rs.getString("fgct_patient_name");
            int total_session = rs.getInt("fgct_session_id");
            String game = rs.getString("fgct_game");
            String movement = rs.getString("fgct_movement");
            String side = rs.getString("fgct_side");
            String difficulty = rs.getString("fgct_difficulty");
            double avg_acc = rs.getDouble("fgct_avg_acc");
            double avg_perct = rs.getDouble("fgct_avg_percentage");
            double avg_speed = rs.getDouble("fgct_avg_speed");
            double avg_dis = rs.getDouble("fgct_distance");
            double avg_rep = rs.getDouble("fgct_repetations");
            double avg_points = rs.getDouble("fgct_points");
            double score = calculateScore(avg_acc, avg_perct, avg_speed,avg_dis,avg_points,avg_rep, side, difficulty,weakness);
            int ses_pass = calculateSessionPass(total_session, difficulty);
            int score_pass = calculateScorePass(score, difficulty);

            String sqlInsFmlc = "insert into fmlc_machine_learning_score set fmlc_reference = '" + reference + "',fmlc_patient_id = '" + this.getPatientId() + "',fmlc_total_session_no = " + total_session + ",fmlc_game_type='" + game + "',"
                    + "fmlc_movement_type='" + movement + "',fmlc_side = '" + side + "',"
                    + "fmlc_total_score = '" + score + "',fmlc_difficulty = '" + difficulty + "',fmlc_session_pass=" + ses_pass + ",fmlc_score_pass=" + score_pass + ",fmlc_total_avg_acc='" + avg_acc + "',"
                    + "fmlc_total_avg_percentage='" + avg_perct + "',fmlc_total_avg_speed='" + avg_speed + "',fmlc_taa_pass=" + taa_pass + ",fmlc_tap_pass=" + tap_pass + ",fmlc_tas_pass=" + tas_pass + ","
                    + "fmlc_total_avg_reps="+avg_rep+",fmlc_total_avg_distance = "+avg_dis+",fmlc_total_avg_points="+avg_points+",fmlc_tar_pass = '"+tar_pass+"',fmlc_tapo_pass = '"+tapo_pass+"',fmlc_tad_pass='"+tad_pass+"',"
                    + "fmlc_score_msg='"+score_msg+"',fmlc_session_msg='"+sess_msg+"',fmlc_suggest_msg='"+support_msg+"'";
            cj.execute(sqlInsFmlc);

        }

        cj.close();
        this.con.close();
    }

    public double calculateScore(double acc, double perc, double speed,double dist,double points,double reps, String side, String difficulty,String weakness) {
        double acce = 0;
        double per = 0;
        double spd = 0;
        double dis = 0;
        double rep = 0;
        double pts = 0;
        double total = 0;
        taa_pass = 1;
        tap_pass = 1;
        tad_pass = 1;
        tapo_pass =1;
        tar_pass = 1;
        
        if (weakness.equalsIgnoreCase("1")) {
            if (difficulty.equalsIgnoreCase("easy")) {
                if (acc > 0.6616) {acce = 1;} else {acce = 0;}
                if (perc > 33.0823) {per = 1;} else {per = 0;}
                if (dist > 11.3345) {dis = 1;} else {dis = 0;}
                if (points > 107.1576) {pts = 1;} else {pts = 0;}
                if (reps >8.3473) {rep = 1;} else {rep = 0;}

            }
            if (difficulty.equalsIgnoreCase("medium")) {
                if (acc > 0.7027) {acce = 1;} else {acce = 0;}
                if (perc > 39.063) {per = 1;} else {per = 0;}
                if (dist > 11.927) {dis = 1;} else {dis = 0;}
                if (points > 170.7372) {pts = 1;} else {pts = 0;}
                if (reps >11.4872) {rep = 1;} else {rep = 0;}
            }
            if (difficulty.equalsIgnoreCase("hard")) {
                if (acc > 0.7382) {acce = 1;} else {acce = 0;}
                if (perc > 38.2898) {per = 1;} else {per = 0;}
                if (dist > 10.9091) {dis = 1;} else {dis = 0;}
                if (points >143.5705) {pts = 1;} else {pts = 0;}
                if (reps >8.8971) {rep = 1;} else {rep = 0;}
            }
        } else if (weakness.equalsIgnoreCase("0")) {
            if (difficulty.equalsIgnoreCase("easy")) {
                if (acc > 0.6945) {acce = 1;} else {acce = 0;}
                if (perc >28.0264) {per = 1;} else {per = 0;}
                if (dist > 14.0419) {dis = 1;} else {dis = 0;}
                if (points > 113.5419) {pts = 1;} else {pts = 0;}
                if (reps >8.2867) {rep = 1;} else {rep = 0;}
            }
            if (difficulty.equalsIgnoreCase("medium")) {
                    if (acc > 0.7559) {acce = 1;} else {acce = 0;}
                    if (perc >31.3088) {per = 1;} else {per = 0;}
                    if (dist > 16.2632) {dis = 1;} else {dis = 0;}
                    if (points > 175.3889) {pts = 1;} else {pts = 0;}
                    if (reps >9.1667) {rep = 1;} else {rep = 0;}
            }
            if (difficulty.equalsIgnoreCase("hard")) {
                if (acc > 0.784) {acce = 1;} else {acce = 0;}
                if (perc >37.0447) {per = 1;} else {per = 0;}
                if (dist > 12.0165) {dis = 1;} else {dis = 0;}
                if (points > 139.0995) {pts = 1;} else {pts = 0;}
                if (reps >8.6138) {rep = 1;} else {rep = 0;}
            }
        }

        total = (0.2 * acce) + (0.2 * per) + (0.2 * dis)+(0.2 * pts)+(0.2 * rep);
//        System.out.println("total = " + total);
support_msg = "Focus on these attributes:-";
String str = "";
        if (difficulty.equalsIgnoreCase("easy")) {
            if(total < 0.6){
                //repeat
                
                if(acce == 0){taa_pass = 0;str+="Acceleration,";}
                if(per == 0){tap_pass = 0;str+="Percentage,";}
                if(dis == 0){tad_pass = 0;str+="Distance,";}
                if(pts == 0){tapo_pass =0;str+="Point,";}
                if(rep == 0){tar_pass = 0;str+="Repetation,";}
                
            }
        } else if (difficulty.equalsIgnoreCase("medium")) {
            if (total < 0.6) {
                  if(acce == 0){taa_pass = 0;str+="Acceleration,";}
                if(per == 0){tap_pass = 0;str+="Percentage,";}
                if(dis == 0){tad_pass = 0;str+="Distance,";}
                if(pts == 0){tapo_pass =0;str+="Point,";}
                if(rep == 0){tar_pass = 0;str+="Repetation,";}
            } 
        } else if (difficulty.equalsIgnoreCase("hard")) {
            if (total < 0.4) {
                  if(acce == 0){taa_pass = 0;str+="Acceleration,";}
                if(per == 0){tap_pass = 0;str+="Percentage,";}
                if(dis == 0){tad_pass = 0;str+="Distance,";}
                if(pts == 0){tapo_pass =0;str+="Point,";}
                if(rep == 0){tar_pass = 0;str+="Repetation,";}
            }
        }
        
        if(total == 0.0){
             if(acce == 0){taa_pass = 0;}
                 if(acce == 0){taa_pass = 0;str+="Acceleration,";}
                if(per == 0){tap_pass = 0;str+="Percentage,";}
                if(dis == 0){tad_pass = 0;str+="Distance,";}
                if(pts == 0){tapo_pass =0;str+="Point,";}
                if(rep == 0){tar_pass = 0;str+="Repetation,";}
        }
        
        support_msg = support_msg+str;
        return total;

    }

    private int calculateSessionPass(int total_session, String difficulty) {
        int score = 0;
        if (difficulty.equalsIgnoreCase("easy")) {
            if (total_session < 3) {
                score = 10;
                sess_msg = "This patient needs more sessions for EASY level.";
            } else if (total_session >= 3) {
                score = 11;
                sess_msg = "Minimim sessions achieved.";
            }
        }
        if (difficulty.equalsIgnoreCase("medium")) {
            if (total_session < 3) {
                score = 20;
                 sess_msg = "This patient needs more sessions for MEDIUM level.";
            } else if (total_session >= 3) {
                score = 21;
                sess_msg = "Minimim sessions achieved.";
            }
        }
        if (difficulty.equalsIgnoreCase("hard")) {
            if (total_session < 3) {
                score = 30;
                sess_msg = "This patient needs more sessions for HARD level.";
            } else if (total_session >= 3) {
                score = 31;
                 sess_msg = "Minimim sessions achieved.";
            }
        }
        
        if(difficulty.equalsIgnoreCase("null") || (difficulty == null) || (difficulty == "null")){
            score_msg = "Data error.Difficulty can not be determined.";
            sess_msg = "Data error.Difficulty can not be determined.";
        }
    

        return score;
    }

    private int calculateScorePass(double score, String difficulty) {
        int scorePass = 0;

        if (difficulty.equalsIgnoreCase("easy")) {
            if ((score >= 0.4)&&(score < 0.6)) {
                scorePass = 11;
                score_msg = "This patient scored "+score+".Minimum score required to move to MEDIUM stage is 0.6 ";
            } else if (score >= 0.6){
                scorePass = 14;
                score_msg = "This patient scored "+score+".Congratulation , this patient can now plays on MEDIUM stage ";
            }else if(score < 0.4){
                 scorePass = 10;
                 score_msg = "This patient scored "+score+".The score is too low .";
            }
        }

        if (difficulty.equalsIgnoreCase("medium")) {
           if ((score >= 0.4)&&(score < 0.6)) {
                scorePass = 21;
                score_msg = "This patient scored "+score+".Minimum score required to move to MEDIUM stage is 0.6 ";
            } else if (score >= 0.6){
                scorePass = 24;
                score_msg = "This patient scored "+score+".Congratulation , this patient can now plays on HARD stage";
            }else if(score < 0.4){
                 scorePass = 20;
                 score_msg = "This patient scored "+score+".The score is too low .";
            }
        }

        if (difficulty.equalsIgnoreCase("hard")) {
            if (score >= 0.4) {
                scorePass = 34;
                score_msg = "This patient scored "+score+".Congratulation , this patient pass the HARD stage ";
            } else if(score < 0.4){
                 scorePass = 30;
                 score_msg = "This patient scored "+score+".The score is too low .";
            }
        }

        return scorePass;
    }

    public  Map<String, Object[]>  getFmlcGroupRef() throws SQLException {
        CoreJdbc cj = new CoreJdbc(this.con);
        Map<String, Object[]> map = new LinkedHashMap<String, Object[]>();
        String sql = "SELECT * FROM `fmlc_machine_learning_score` where fmlc_patient_id = '" + this.getPatientId() + "' group by fmlc_reference";
        ResultSet rs = cj.query(sql);
        while (rs.next()) {
            Object[] o = new Object[3];

            o[0] = rs.getString("fmlc_game_type");
            o[1] = rs.getString("fmlc_movement_type");
            o[2] = rs.getString("fmlc_side");

            map.put(rs.getString("fmlc_reference"), o);
        }
        cj.close();

        return map;
    }

    public List<FmlcMachineLearningScore> getDataList(String pid) {
        List<FmlcMachineLearningScore> fmlcEntity = new ArrayList<FmlcMachineLearningScore>();
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Query query = session.createQuery("from FmlcMachineLearningScore where fmlc_patient_id = '" + pid + "' ORDER BY fmlc_reference asc, FIELD(fmlc_difficulty,'easy','medium','hard') ");
        fmlcEntity = query.list();
        t.commit();
        session.close();

        return fmlcEntity;
    }
    
    public List<FmlcMachineLearningScore> getDataListReference(String referenceNo) {
        List<FmlcMachineLearningScore> fmlcEntity = new ArrayList<FmlcMachineLearningScore>();
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Query query = session.createQuery("from FmlcMachineLearningScore where fmlc_reference = '" + referenceNo + "' ORDER BY fmlc_reference asc, FIELD(fmlc_difficulty,'easy','medium','hard') ");
        fmlcEntity = query.list();
        t.commit();
        session.close();

        return fmlcEntity;
    }

}
