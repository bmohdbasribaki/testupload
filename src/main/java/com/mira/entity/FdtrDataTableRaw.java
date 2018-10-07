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
@Table(name = "fdtr_data_table_raw")
public class FdtrDataTableRaw {

    public FdtrDataTableRaw() {
    }
    
    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "SessionId")
    private String session_id;
    @Column(name = "SessionStartDate")
    private String session_start_date;
    @Column(name = "PatientID")
    private String patient_id;
    @Column(name = "FirstName")
    private String first_name;
    @Column(name = "LastName")
    private String last_name;
    @Column(name = "Gender")
    private String gender;
    @Column(name = "Birthday")
    private String birthday;
    @Column(name = "Description")
    private String description;
    @Column(name = "DateIntroduced")
    private String date_introduced;
    @Column(name = "DateRecovered")
    private String date_recovered;
    @Column(name = "Diagnosis")
    private String diagnosis;
    @Column(name = "Email")
    private String email;
    @Column(name = "PatientSystemUid")
    private String patient_system_id;
    @Column(name = "IsDeleted")
    private String is_deleted;
    @Column(name = "Value")
    private String value;
    @Column(name = "Name")
    private String name;
    @Column(name = "Unit")
    private String unit;
    @Column(name = "Timestamp")
    private String timestamp;
    @Column(name = "JointId")
    private String joint_id;
    @Column(name = "JointName")
    private String joint_name;
    @Column(name = "ExerciseGame")
    private String exercise_game;
    @Column(name = "Unknown")
    private String unknown;
    @Column(name = "Movement")
    private String movement;
    @Column(name = "Side")
    private String side;
    @Column(name = "SensorName")
    private String sensor_name;
    @Column(name = "ScheduleItemDuringSec")
    private String schedule_item_during_sec;
    @Column(name = "ScheduleItemPauseAfterSec")
    private String schedule_item_pause_after_sec;
    @Column(name = "Difficulty")
    private String difficulty;
    @Column(name = "Tolerance")
    private String tolerance;
    @Column(name = "MinRange")
    private String min_range;
    @Column(name = "MaxRange")
    private String max_range;
    @Column(name = "Address")
    private String address;
    @Column(name = "Demoraphic")
    private String demographic;
    
    

    
    /*getter setter*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getSession_start_date() {
        return session_start_date;
    }

    public void setSession_start_date(String session_start_date) {
        this.session_start_date = session_start_date;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_introduced() {
        return date_introduced;
    }

    public void setDate_introduced(String date_introduced) {
        this.date_introduced = date_introduced;
    }

    public String getDate_recovered() {
        return date_recovered;
    }

    public void setDate_recovered(String date_recovered) {
        this.date_recovered = date_recovered;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPatient_system_id() {
        return patient_system_id;
    }

    public void setPatient_system_id(String patient_system_id) {
        this.patient_system_id = patient_system_id;
    }

    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getJoint_id() {
        return joint_id;
    }

    public void setJoint_id(String joint_id) {
        this.joint_id = joint_id;
    }

    public String getJoint_name() {
        return joint_name;
    }

    public void setJoint_name(String joint_name) {
        this.joint_name = joint_name;
    }

    public String getExercise_game() {
        return exercise_game;
    }

    public void setExercise_game(String exercise_game) {
        this.exercise_game = exercise_game;
    }

    public String getUnknown() {
        return unknown;
    }

    public void setUnknown(String unknown) {
        this.unknown = unknown;
    }

    public String getMovement() {
        return movement;
    }

    public void setMovement(String movement) {
        this.movement = movement;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getSensor_name() {
        return sensor_name;
    }

    public void setSensor_name(String sensor_name) {
        this.sensor_name = sensor_name;
    }

    public String getSchedule_item_during_sec() {
        return schedule_item_during_sec;
    }

    public void setSchedule_item_during_sec(String schedule_item_during_sec) {
        this.schedule_item_during_sec = schedule_item_during_sec;
    }

    public String getSchedule_item_pause_after_sec() {
        return schedule_item_pause_after_sec;
    }

    public void setSchedule_item_pause_after_sec(String schedule_item_pause_after_sec) {
        this.schedule_item_pause_after_sec = schedule_item_pause_after_sec;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getTolerance() {
        return tolerance;
    }

    public void setTolerance(String tolerance) {
        this.tolerance = tolerance;
    }

    public String getMin_range() {
        return min_range;
    }

    public void setMin_range(String min_range) {
        this.min_range = min_range;
    }

    public String getMax_range() {
        return max_range;
    }

    public void setMax_range(String max_range) {
        this.max_range = max_range;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDemographic() {
        return demographic;
    }

    public void setDemographic(String demographic) {
        this.demographic = demographic;
    }
    
    
    
}
