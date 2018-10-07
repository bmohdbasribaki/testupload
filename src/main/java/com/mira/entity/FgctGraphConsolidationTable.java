/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mira.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author basri baki
 */

@Entity
@Table(name="fgct_graph_consolidation_table")
public class FgctGraphConsolidationTable {
    
    
    
    @Id
    @Column(name = "fgct_reference_no")
    private String fgct_reference_no;
    @Column(name = "fgct_patient_id")
    private String fgct_patient_id;
    @Column(name = "fgct_patient_name")
    private String fgct_patient_name;
    @Column(name = "fgct_session_id")
    private String fgct_session_id;
    @Column(name = "fgct_date")
    private String fgct_date;
    @Column(name = "fgct_game")
    private String fgct_game;
    @Column(name = "fgct_movement")
    private String fgct_movement;
    @Column(name = "fgct_side")
    private String fgct_side;
    @Column(name = "fgct_avg_acc")
    private String fgct_avg_acc;
     @Column(name = "fgct_avg_dec")
    private String fgct_avg_dec;
    @Column(name = "fgct_avg_percentage")
    private String fgct_avg_percentage;
    @Column(name = "fgct_avg_speed")
    private String fgct_avg_speed;
    @Column(name = "fgct_points")
    private String fgct_points;
    @Column(name = "fgct_repetations")
    private String fgct_repetations;
    @Column(name = "fgct_difficulty")
    private String fgct_difficulty;

    public FgctGraphConsolidationTable(String fgct_reference_no, String fgct_patient_id, String fgct_patient_name, String fgct_session_id, 
            String fgct_date, String fgct_game, String fgct_movement, String fgct_side, String fgct_avg_acc,
            String fgct_avg_percentage, String fgct_avg_speed, String fgct_points, String fgct_repetations, String fgct_difficulty,String fgct_avg_dec) {
       
        super();
        this.fgct_reference_no = fgct_reference_no;
        this.fgct_patient_id = fgct_patient_id;
        this.fgct_patient_name = fgct_patient_name;
        this.fgct_session_id = fgct_session_id;
        this.fgct_date = fgct_date;
        this.fgct_game = fgct_game;
        this.fgct_movement = fgct_movement;
        this.fgct_side = fgct_side;
        this.fgct_avg_acc = fgct_avg_acc;
        this.fgct_avg_percentage = fgct_avg_percentage;
        this.fgct_avg_speed = fgct_avg_speed;
        this.fgct_points = fgct_points;
        this.fgct_repetations = fgct_repetations;
        this.fgct_difficulty = fgct_difficulty;
        this.fgct_avg_dec = fgct_avg_dec;
    }

    public FgctGraphConsolidationTable() {
    }

    public String getFgct_reference_no() {
        return fgct_reference_no;
    }

    public void setFgct_reference_no(String fgct_reference_no) {
        this.fgct_reference_no = fgct_reference_no;
    }

    public String getFgct_patient_id() {
        return fgct_patient_id;
    }

    public void setFgct_patient_id(String fgct_patient_id) {
        this.fgct_patient_id = fgct_patient_id;
    }

    public String getFgct_patient_name() {
        return fgct_patient_name;
    }

    public void setFgct_patient_name(String fgct_patient_name) {
        this.fgct_patient_name = fgct_patient_name;
    }

    public String getFgct_session_id() {
        return fgct_session_id;
    }

    public void setFgct_session_id(String fgct_session_id) {
        this.fgct_session_id = fgct_session_id;
    }

    public String getFgct_date() {
        return fgct_date;
    }

    public void setFgct_date(String fgct_date) {
        this.fgct_date = fgct_date;
    }

    public String getFgct_game() {
        return fgct_game;
    }

    public void setFgct_game(String fgct_game) {
        this.fgct_game = fgct_game;
    }

    public String getFgct_movement() {
        return fgct_movement;
    }

    public void setFgct_movement(String fgct_movement) {
        this.fgct_movement = fgct_movement;
    }

    public String getFgct_side() {
        return fgct_side;
    }

    public void setFgct_side(String fgct_side) {
        this.fgct_side = fgct_side;
    }

    public String getFgct_avg_acc() {
        return fgct_avg_acc;
    }

    public void setFgct_avg_acc(String fgct_avg_acc) {
        this.fgct_avg_acc = fgct_avg_acc;
    }

    public String getFgct_avg_percentage() {
        return fgct_avg_percentage;
    }

    public void setFgct_avg_percentage(String fgct_avg_percentage) {
        this.fgct_avg_percentage = fgct_avg_percentage;
    }

    public String getFgct_avg_speed() {
        return fgct_avg_speed;
    }

    public void setFgct_avg_speed(String fgct_avg_speed) {
        this.fgct_avg_speed = fgct_avg_speed;
    }

    public String getFgct_points() {
        return fgct_points;
    }

    public void setFgct_points(String fgct_points) {
        this.fgct_points = fgct_points;
    }

    public String getFgct_repetations() {
        return fgct_repetations;
    }

    public void setFgct_repetations(String fgct_repetations) {
        this.fgct_repetations = fgct_repetations;
    }

    public String getFgct_difficulty() {
        return fgct_difficulty;
    }

    public void setFgct_difficulty(String fgct_difficulty) {
        this.fgct_difficulty = fgct_difficulty;
    }

    public String getFgct_avg_dec() {
        return fgct_avg_dec;
    }

    public void setFgct_avg_dec(String fgct_avg_dec) {
        this.fgct_avg_dec = fgct_avg_dec;
    }
    
    
    
    

    
    
    
    
    
    
    
}
