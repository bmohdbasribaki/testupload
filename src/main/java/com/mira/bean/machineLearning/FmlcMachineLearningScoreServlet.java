/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mira.bean.machineLearning;

import com.google.gson.Gson;
import com.jcore.CoreUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletResponse;
import static jdk.nashorn.internal.objects.NativeArray.map;

/**
 *
 * @author basri baki
 */
@WebServlet(name = "FmlcMachineLearningScoreServlet", urlPatterns = {"/FmlcMachineLearningScoreServlet"})
public class FmlcMachineLearningScoreServlet extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            Map<String, Object> map = new HashMap<String, Object>();
            boolean isValid = false;
            double vrom = CoreUtil.toDouble(request.getParameter("rom"));
            double vmaxpoint = CoreUtil.toDouble(request.getParameter("maxpoint"));
            double vminpoint = CoreUtil.toDouble(request.getParameter("minpoint"));
            int vmaxdif = CoreUtil.toInteger(request.getParameter("maxdif"));
//            System.out.println("vmaxdif = " + vmaxdif);
            int vmaxrep = CoreUtil.toInteger(request.getParameter("maxrep"));
            int vminrep = CoreUtil.toInteger(request.getParameter("minrep"));
            int visweak = CoreUtil.toInteger(request.getParameter("isweak"));
//            System.out.println("visweak = " + visweak);

            double points = 0,  rom = 0,from = 0;
            int diff = 0, reps = 0, weak = 0;
            double fpoints = 0;
            int fdiff = 0,freps = 0;
           
            Random rand = new Random();
            
            long selection = 0;
            double[] s = new double[100];
            double minS = 1000000;
//            System.out.println("a = " + a + " b = " + b + " c = " + c);

            for (int i = 0; i < s.length; i++) {
                points = vminpoint + ThreadLocalRandom.current().nextDouble(0, vmaxpoint-vminpoint);
//                System.out.println("points = " + points);
                diff = rand.nextInt(vmaxdif + 1);
//                System.out.println("diff = " + diff);
                reps = vminrep + ThreadLocalRandom.current().nextInt(0, vmaxrep - vminrep);
//                System.out.println("reps = " + reps);
                rom = 15.2487 + (0.06327481 * points) + (0.143241804 * reps) + (0.562633624 * visweak) + (4.593988899 * diff);
//                System.out.println("rom = " + rom);
                    
                s[i] = Math.abs(vrom - rom);
                if(s[i] < minS){
                    minS = s[i];
                    selection = i;
                    fpoints = points;
                    fdiff = diff;
                    freps = reps;
                    from = rom;
                    
                }else{
                    minS = minS;
                }
//                System.out.println("");
                
            }
            String strdiff = "";
            
//            System.out.println("fdiff = " + fdiff);
            if(fdiff==1){
                strdiff = "EASY";
            }else if(fdiff==2){
                strdiff = "MEDIUM";
            }else if(fdiff==3){
                strdiff = "HARD";
            }
//            System.out.println("strdiff = " + strdiff);
            
            map.put("rom", CoreUtil.round(from, 2));
            map.put("points", CoreUtil.round(fpoints,2));
            map.put("diff", strdiff);
            map.put("rep", freps);

            out.write(new Gson().toJson(map));
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
        processRequest(request, response);
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
        processRequest(request, response);
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
