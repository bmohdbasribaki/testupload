/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mira.bean.graph;

import com.mira.BaseActionBean;
import com.mira.database.ConnectionManager;
import java.sql.SQLException;
import java.text.ParseException;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.RestActionBean;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author basri baki
 */
@UrlBinding("/processFgct")
public class FgctGraphConsolidationProcessBean extends BaseActionBean {

//    @Validate(required = true)
    private String patientId;
    @Validate(required = true)
    private String gameType;
    @Validate(required = true)
    private String side;
    @Validate(required = true)
    private String movement;
    @Validate(required = true)
    private String fromDate;
    @Validate(required = true)
    private String toDate;
    JSONObject job;
    JSONObject jarr;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    
    
    
    public JSONObject getJarr() {
        return jarr;
    }

    public void setJarr(JSONObject jarr) {
        this.jarr = jarr;
    }

    public JSONObject getJob() {
        return job;
    }

    public void setJob(JSONObject job) {
        this.job = job;
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

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getMovement() {
        return movement;
    }

    public void setMovement(String movement) {
        this.movement = movement;
    }

    @DefaultHandler
    public Resolution getForm() throws JSONException, SQLException, ParseException {

        FgctGraphConsolidationTableBean fgct = new FgctGraphConsolidationTableBean();
        fgct.setCon(ConnectionManager.getConnection());
        
        fgct.setPatientId(getPatientId());
        fgct.setGame(getGameType());
        fgct.setMove(getMovement());
        fgct.setSide(getSide());
        fgct.setFromDate(getFromDate());
        fgct.setToDate(getToDate());

        job = new JSONObject();
        jarr = new JSONObject();
        jarr = fgct.getData();
        job = fgct.getDataDifficulty();

        RedirectResolution r = new RedirectResolution("/aGraph/graph.jsp");
        r.addParameter("job",job);
        r.addParameter("jarr",jarr);

        return r;
    }

}
