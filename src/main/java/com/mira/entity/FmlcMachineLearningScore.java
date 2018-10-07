/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mira.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author basri baki
 */
@Entity
@Table(name = "fmlc_machine_learning_score")

public class FmlcMachineLearningScore implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fmlc_running_no")
    private Integer fmlcRunningNo;
    @Size(max = 255)
    @Column(name = "fmlc_reference")
    private String fmlcReference;
    @Size(max = 255)
    @Column(name = "fmlc_patient_id")
    private String fmlcPatientId;
    @Column(name = "fmlc_total_session_no")
    private Integer fmlcTotalSessionNo;
    @Size(max = 255)
    @Column(name = "fmlc_game_type")
    private String fmlcGameType;
    @Size(max = 255)
    @Column(name = "fmlc_movement_type")
    private String fmlcMovementType;
    @Size(max = 255)
    @Column(name = "fmlc_side")
    private String fmlcSide;
    @Size(max = 255)
    @Column(name = "fmlc_total_score")
    private String fmlcTotalScore;
    @Size(max = 255)
    @Column(name = "fmlc_difficulty")
    private String fmlcDifficulty;
    @Column(name = "fmlc_session_pass")
    private Integer fmlcSessionPass;
    @Column(name = "fmlc_score_pass")
    private Integer fmlcScorePass;
    @Size(max = 255)
    @Column(name = "fmlc_total_avg_acc")
    private String fmlcTotalAvgAcc;
    @Size(max = 255)
    @Column(name = "fmlc_total_avg_percentage")
    private String fmlcTotalAvgPercentage;
    @Size(max = 255)
    @Column(name = "fmlc_total_avg_speed")
    private String fmlcTotalAvgSpeed;
    @Size(max = 255)
    @Column(name = "fmlc_total_avg_distance")
    private String fmlcTotalAvgDistance;
    @Size(max = 255)
    @Column(name = "fmlc_total_avg_reps")
    private String fmlcTotalAvgReps;
     @Size(max = 255)
    @Column(name = "fmlc_total_avg_points")
    private String fmlcTotalAvgPoints;
     
       @Column(name = "fmlc_score_msg")
    private String fmlcScoreMsg;
    @Size(max = 255)
    @Column(name = "fmlc_session_msg")
    private String fmlcSessionMsg;
     @Size(max = 255)
    @Column(name = "fmlc_suggest_msg")
    private String fmlcSuggestMsg;
    
    @Column(name = "fmlc_taa_pass")
    private Integer fmlcTaaPass;
    @Column(name = "fmlc_tap_pass")
    private Integer fmlcTapPass;
    @Column(name = "fmlc_tas_pass")
    private Integer fmlcTasPass;
    
     @Column(name = "fmlc_tar_pass")
    private Integer fmlcTarPass;
    @Column(name = "fmlc_tapo_pass")
    private Integer fmlcTapoPass;

    public String getFmlcScoreMsg() {
        return fmlcScoreMsg;
    }

    public void setFmlcScoreMsg(String fmlcScoreMsg) {
        this.fmlcScoreMsg = fmlcScoreMsg;
    }

    public String getFmlcSessionMsg() {
        return fmlcSessionMsg;
    }

    public void setFmlcSessionMsg(String fmlcSessionMsg) {
        this.fmlcSessionMsg = fmlcSessionMsg;
    }

    public String getFmlcSuggestMsg() {
        return fmlcSuggestMsg;
    }

    public void setFmlcSuggestMsg(String fmlcSuggestMsg) {
        this.fmlcSuggestMsg = fmlcSuggestMsg;
    }

    
    
    public String getFmlcTotalAvgDistance() {
        return fmlcTotalAvgDistance;
    }

    public void setFmlcTotalAvgDistance(String fmlcTotalAvgDistance) {
        this.fmlcTotalAvgDistance = fmlcTotalAvgDistance;
    }

    public String getFmlcTotalAvgReps() {
        return fmlcTotalAvgReps;
    }

    public void setFmlcTotalAvgReps(String fmlcTotalAvgReps) {
        this.fmlcTotalAvgReps = fmlcTotalAvgReps;
    }

    public String getFmlcTotalAvgPoints() {
        return fmlcTotalAvgPoints;
    }

    public void setFmlcTotalAvgPoints(String fmlcTotalAvgPoints) {
        this.fmlcTotalAvgPoints = fmlcTotalAvgPoints;
    }

    public Integer getFmlcTarPass() {
        return fmlcTarPass;
    }

    public void setFmlcTarPass(Integer fmlcTarPass) {
        this.fmlcTarPass = fmlcTarPass;
    }

    public Integer getFmlcTapoPass() {
        return fmlcTapoPass;
    }

    public void setFmlcTapoPass(Integer fmlcTapoPass) {
        this.fmlcTapoPass = fmlcTapoPass;
    }

    public Integer getFmlcTadPass() {
        return fmlcTadPass;
    }

    public void setFmlcTadPass(Integer fmlcTadPass) {
        this.fmlcTadPass = fmlcTadPass;
    }
    @Column(name = "fmlc_tad_pass")
    private Integer fmlcTadPass;

    public FmlcMachineLearningScore() {
    }

    public FmlcMachineLearningScore(Integer fmlcRunningNo) {
        this.fmlcRunningNo = fmlcRunningNo;
    }

    public Integer getFmlcRunningNo() {
        return fmlcRunningNo;
    }

    public String getFmlcReference() {
        return fmlcReference;
    }

    public void setFmlcReference(String fmlcReference) {
        this.fmlcReference = fmlcReference;
    }
    
    

    public void setFmlcRunningNo(Integer fmlcRunningNo) {
        this.fmlcRunningNo = fmlcRunningNo;
    }

    public String getFmlcPatientId() {
        return fmlcPatientId;
    }

    public void setFmlcPatientId(String fmlcPatientId) {
        this.fmlcPatientId = fmlcPatientId;
    }

    public Integer getFmlcTotalSessionNo() {
        return fmlcTotalSessionNo;
    }

    public void setFmlcTotalSessionNo(Integer fmlcTotalSessionNo) {
        this.fmlcTotalSessionNo = fmlcTotalSessionNo;
    }

    public String getFmlcGameType() {
        return fmlcGameType;
    }

    public void setFmlcGameType(String fmlcGameType) {
        this.fmlcGameType = fmlcGameType;
    }

    public String getFmlcMovementType() {
        return fmlcMovementType;
    }

    public void setFmlcMovementType(String fmlcMovementType) {
        this.fmlcMovementType = fmlcMovementType;
    }

    public String getFmlcSide() {
        return fmlcSide;
    }

    public void setFmlcSide(String fmlcSide) {
        this.fmlcSide = fmlcSide;
    }

    public String getFmlcTotalScore() {
        return fmlcTotalScore;
    }

    public void setFmlcTotalScore(String fmlcTotalScore) {
        this.fmlcTotalScore = fmlcTotalScore;
    }

    public String getFmlcDifficulty() {
        return fmlcDifficulty;
    }

    public void setFmlcDifficulty(String fmlcDifficulty) {
        this.fmlcDifficulty = fmlcDifficulty;
    }

    public Integer getFmlcSessionPass() {
        return fmlcSessionPass;
    }

    public void setFmlcSessionPass(Integer fmlcSessionPass) {
        this.fmlcSessionPass = fmlcSessionPass;
    }

    public Integer getFmlcScorePass() {
        return fmlcScorePass;
    }

    public void setFmlcScorePass(Integer fmlcScorePass) {
        this.fmlcScorePass = fmlcScorePass;
    }

    public String getFmlcTotalAvgAcc() {
        return fmlcTotalAvgAcc;
    }

    public void setFmlcTotalAvgAcc(String fmlcTotalAvgAcc) {
        this.fmlcTotalAvgAcc = fmlcTotalAvgAcc;
    }

    public String getFmlcTotalAvgPercentage() {
        return fmlcTotalAvgPercentage;
    }

    public void setFmlcTotalAvgPercentage(String fmlcTotalAvgPercentage) {
        this.fmlcTotalAvgPercentage = fmlcTotalAvgPercentage;
    }

    public String getFmlcTotalAvgSpeed() {
        return fmlcTotalAvgSpeed;
    }

    public void setFmlcTotalAvgSpeed(String fmlcTotalAvgSpeed) {
        this.fmlcTotalAvgSpeed = fmlcTotalAvgSpeed;
    }

    public Integer getFmlcTaaPass() {
        return fmlcTaaPass;
    }

    public void setFmlcTaaPass(Integer fmlcTaaPass) {
        this.fmlcTaaPass = fmlcTaaPass;
    }

    public Integer getFmlcTapPass() {
        return fmlcTapPass;
    }

    public void setFmlcTapPass(Integer fmlcTapPass) {
        this.fmlcTapPass = fmlcTapPass;
    }

    public Integer getFmlcTasPass() {
        return fmlcTasPass;
    }

    public void setFmlcTasPass(Integer fmlcTasPass) {
        this.fmlcTasPass = fmlcTasPass;
    }
    
    

  
    
}
