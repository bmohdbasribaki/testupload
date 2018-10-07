/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mira.helper;

import com.jcore.CoreJdbc;
import com.mira.database.ConnectionManager;
import com.mira.entity.FdtrDataTableRaw;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.servlet.http.Part;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 *
 * @author basri baki
 */
@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig
public class FileUploadHelper extends HttpServlet {

    private final static Logger LOGGER
            = Logger.getLogger(FileUploadHelper.class.getCanonicalName());

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        // Create path components to save the file
        final String path = request.getServletContext().getRealPath("");
//        System.out.println("path = " + path);
        final Part filePart = request.getPart("file");
//        System.out.println("filePart = " + filePart);
        final String fileName = getFileName(filePart);
        final String filePath = path + File.separator + fileName;
//        System.out.println("filePath = " + filePath);

        OutputStream out = null;
        InputStream filecontent = null;
        final PrintWriter writer = response.getWriter();

        try {
            out = new FileOutputStream(new File(filePath));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            /*read excel file*/
            SessionFactory sf = new AnnotationConfiguration().configure("hibernate.cfg.xml").buildSessionFactory();
            Session session = sf.openSession();
            FileInputStream file = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();

            Row row;
            for (int i = 0; i < sheet.getLastRowNum(); i++) {

                System.out.println("sheet last row = " + sheet.getPhysicalNumberOfRows());
                row = (Row) sheet.getRow(i);

                String id = "";
                String session_id = "";
                String session_start_date = "";
                String patient_id = "";
                String first_name = "";
                String last_name = "";
                String gender = "";
                String diagnosis = "";
                String value = "";
                String name = "";
                String unit = "";
                String exercise_game = "";
                String movement = "";
                String side = "";
                String difficulty = "";
                String tolerance = "";
                String min_range = "";
                String max_range = "";
                System.out.println("start inserting = ");

                Cell c = row.getCell(0);
                if (c == null || c.getCellType() == Cell.CELL_TYPE_BLANK) {
                    break;
                }

                if (row.getCell(0) == null) {
                    id = null;
                } else {
                    id = dataFormatter.formatCellValue(row.getCell(0));
                }
                if (row.getCell(1) == null) {
                    session_id = null;
                } else {
                    session_id = dataFormatter.formatCellValue(row.getCell(1));
                }  //else copies cell data to name variable}
                if (row.getCell(2) == null) {
                    session_start_date = null;
                } else {
                    session_start_date = dataFormatter.formatCellValue(row.getCell(2));
                }
                if (row.getCell(3) == null) {
                    patient_id = null;
                } else {
                    patient_id = dataFormatter.formatCellValue(row.getCell(3));
                }
                if (row.getCell(4) == null) {
                    first_name = null;
                } else {
                    first_name = row.getCell(4).toString();
                }
                if (row.getCell(5) == null) {
                    last_name = null;
                } else {
                    last_name = row.getCell(5).toString();
                }
                if (row.getCell(6) == null) {
                    gender = null;
                } else {
                    gender = row.getCell(6).toString();
                }
                if (row.getCell(11) == null) {
                    diagnosis = null;
                } else {
                    diagnosis = row.getCell(11).toString();
                }
                if (row.getCell(15) == null) {
                    value = null;
                } else {
                    value = row.getCell(15).toString();
                }
                if (row.getCell(16) == null) {
                    name = null;
                } else {
                    name = row.getCell(16).toString();
                }
                if (row.getCell(17) == null) {
                    unit = null;
                } else {
                    unit = row.getCell(17).toString();
                }
                if (row.getCell(21) == null) {
                    exercise_game = null;
                } else {
                    exercise_game = row.getCell(21).toString();
                }
                if (row.getCell(23) == null) {
                    movement = null;
                } else {
                    movement = row.getCell(23).toString();
                }
                if (row.getCell(24) == null) {
                    side = null;
                } else {
                    side = row.getCell(24).toString();
                }
                if (row.getCell(28) == null) {
                    difficulty = null;
                } else {
                    difficulty = row.getCell(28).toString();
                }
                if (row.getCell(29) == null) {
                    tolerance = null;
                } else {
                    tolerance = row.getCell(29).toString();
                }
                if (row.getCell(30) == null) {
                    min_range = null;
                } else {
                    min_range = dataFormatter.formatCellValue(row.getCell(30));
                }
                if (row.getCell(31) == null) {
                    max_range = null;
                } else {
                    max_range = dataFormatter.formatCellValue(row.getCell(31));
                }

                System.out.println("end inserting = ");
                System.out.println("id= " + id);

                Transaction t = session.beginTransaction();
                FdtrDataTableRaw fdtr = new FdtrDataTableRaw();
                fdtr.setId(id);
                fdtr.setSession_id(session_id);
                fdtr.setSession_start_date(session_start_date);
                fdtr.setPatient_id(patient_id);
                fdtr.setFirst_name(first_name);
                fdtr.setLast_name(last_name);
                fdtr.setGender(gender);
                fdtr.setDiagnosis(diagnosis);
                fdtr.setValue(value);
                fdtr.setName(name);
                fdtr.setUnit(unit);
                fdtr.setExercise_game(exercise_game);
                fdtr.setMovement(movement);
                fdtr.setSide(side);
                fdtr.setDifficulty(difficulty);
                fdtr.setTolerance(tolerance);
                fdtr.setMin_range(min_range);
                fdtr.setMax_range(max_range);
                session.saveOrUpdate(fdtr);
                t.commit();

            }
            file.close();
            
            Connection con = ConnectionManager.getConnection();
            CoreJdbc cj = new CoreJdbc(con);
            String sql = "select firt_finish from firt_index_raw_tracking order by firt_running_no desc limit 1";
            ResultSet rs = cj.query(sql);
            int min = 0;
            if(rs.next()){
                min = rs.getInt("firt_finish") + 1;
            }
//            
           
            
            
            cj.execute("insert into firt_index_raw_tracking set firt_start = '"+min+"' , firt_finish = (select max(RunningNo) from "
                    + "fdtr_data_table_raw)");
            
            
            
            DataIndexRawTracking ob = new DataIndexRawTracking(con);
            ob.InsertTableFgct();
            
            request.setAttribute("result", "SUCCESS");
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
            con.close();
          
        } catch (FileNotFoundException fne) {
            writer.println("You either did not specify a file to upload or are "
                    + "trying to upload a file to a protected or nonexistent "
                    + "location.");
            writer.println("<br/> ERROR: " + fne.getMessage());

//            LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}",
//                    new Object[]{fne.getMessage()});
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
            if (writer != null) {
                writer.close();
            }

        }

    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
//    LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
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
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(FileUploadHelper.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(FileUploadHelper.class.getName()).log(Level.SEVERE, null, ex);
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
