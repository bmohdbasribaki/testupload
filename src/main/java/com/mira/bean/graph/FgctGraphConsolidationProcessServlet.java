/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mira.bean.graph;

import com.jcore.CoreJdbc;
import com.mira.database.ConnectionManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sourceforge.stripes.action.RedirectResolution;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author basri baki
 */
@WebServlet(name = "FgctGraphConsolidationProcessServlet", urlPatterns = {"/FgctGraphConsolidationProcessServlet"})
public class FgctGraphConsolidationProcessServlet extends HttpServlet {

    JSONObject job;
    JSONObject jarr;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException, SQLException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
//        response.getWriter().write(new Gson())
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String inPath = request.getContextPath();
            String inBasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + inPath + "/";
            String game = request.getParameter("gameType");
            String move = request.getParameter("movement");
            String side = request.getParameter("side");
            String patientId = request.getParameter("patientId");
            String dateFrom = request.getParameter("fromDate");
            String dateTo = request.getParameter("toDate");
            String weakness = request.getParameter("weakness");

            System.out.println("dateFrom = " + dateFrom);
            if (dateFrom.equalsIgnoreCase("") || dateTo.equalsIgnoreCase("")) {

            } else {

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");

                date = df.parse(dateFrom);
                dateFrom = df1.format(date).toString();
                date = df.parse(dateTo);
                dateTo = df1.format(date).toString();
            }
//            System.out.println("game = " + game + "\n move = " + move + "\n side = " + side);
            String fmlcid = game.replaceAll("\\s", "") + move.replaceAll("\\s", "") + side.replaceAll("\\s", "");
            Connection con = ConnectionManager.getConnection();
            FgctGraphConsolidationTableBean fgct = new FgctGraphConsolidationTableBean();
            fgct.setCon(con);

            fgct.setPatientId(patientId);
            fgct.setGame(game);
            fgct.setMove(move);
            fgct.setSide(side);
            fgct.setFromDate(dateFrom);
            fgct.setToDate(dateTo);

            job = new JSONObject();
            jarr = new JSONObject();
            jarr = fgct.getData();
            job = fgct.getDataDifficulty();
            
            String diagnosis = fgct.getDiagnosis();
//            System.out.println("jarr = " + jarr + " job = " + job);

//            RequestDispatcher rd = request.getRequestDispatcher("/aGraph/graph.jsp");
//            request.setAttribute("job","basri");
//            request.setAttribute("jarr","baki");
//            rd.forward(request, response);
//            
            HttpSession session = request.getSession();
            session.setAttribute("weakness", weakness);
            session.setAttribute("game", game);
            session.setAttribute("movement", move);
            session.setAttribute("diagnosis", diagnosis);
            session.setAttribute("sidet", side);
            session.setAttribute("fmlcid", fmlcid);
            session.setAttribute("job", job.toString());
            session.setAttribute("jarr", jarr.toString());
            response.sendRedirect(inBasePath + "aGraph/graph.jsp");
//            RequestDispatcher rd = request.getRequestDispatcher("/aGraph/graph.jsp");
//            rd.forward(request, response);
//            RedirectResolution r = new RedirectResolution("/aGraph/graph.jsp");
//            r.addParameter("job", job);
//            r.addParameter("jarr", jarr);

//            CoreJdbc.closeConnection(con);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(FgctGraphConsolidationProcessServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FgctGraphConsolidationProcessServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FgctGraphConsolidationProcessServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(FgctGraphConsolidationProcessServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FgctGraphConsolidationProcessServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FgctGraphConsolidationProcessServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
