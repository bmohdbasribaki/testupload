/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcore;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author basri baki
 */
public class CoreUtil {

    public static String toString(Object s) {
        if (s == null) {
            return "";
        } else {
            if (s instanceof byte[]) {
                return new String((byte[]) s).trim();
            } else {
                return s.toString().trim();
            }
        }
    }

    public static String formatDateTime(String format, Date date) {
        if (date == null) {
            return "00.00.0000";
        }
        return formatDateTime(format, date, Locale.UK);
    }

    public static String ddMMyyyy(Date date) {
        return formatDateTime("dd.MM.yyyy", date, Locale.UK);
    }

    public static String formatDateTime(String format, Date date, Locale locale) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = null;
        try {
            sdf = new SimpleDateFormat(format, locale);
        } catch (IllegalArgumentException ex) {
            return "ERROR FORMAT";
        }
        return sdf.format(date);
    }

    public static boolean isEmpty(Object s) {
        return StringUtils.isEmpty(CoreUtil.toString(s));
    }

    public static boolean isEmpty(String[] s) {
        for (String v : s) {
            if (StringUtils.isEmpty(v)) {
                return true;
            }
        }
        return false;
    }

    public static String millisec2Time(long baki) {
        DecimalFormat rm = new DecimalFormat("#,###.####");
        String test = rm.format(baki * 0.001);
        return test + " seconds";
    }

    /*
     * Printing hierarcy data - indent code.
     */
    public static String indentCode(String code) {
        String indent = "";

        for (int u = 0; u < (code.length() / 2) - 1; u++) {
            indent += "&nbsp;&nbsp;&nbsp;&nbsp;";
        }
        return indent + code;
    }

    public static String indentHideCode(String code) {
        String indent = "";

        for (int u = 0; u < (code.length() / 2) - 1; u++) {
            indent += "&nbsp;&nbsp;&nbsp;&nbsp;";
        }
        return indent;
    }

    public static int getDOB(Date dateOfBirth) {
        Calendar dob = Calendar.getInstance();
        dob.setTime(dateOfBirth);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }

    public static String toRoundBigDecimal(double unrounded, int round) {
        BigDecimal bd = new BigDecimal(unrounded);
        bd = bd.divide(new BigDecimal("1"), round, BigDecimal.ROUND_HALF_UP);
        return bd.toPlainString();
    }

    /**
     * convert java.net.URL to java.io.File
     */
    public static File file2url(URL url) {
        File f = null;
        try {
            f = new File(url.toURI());
        } catch (Exception e) {
            f = new File(url.getPath());
        }
        return f;
    }

    public static String getRequestURL(HttpServletRequest req) {
        try {
            String url = req.getScheme();
            url += "://";
            url += req.getServerName();
            int port = req.getServerPort();
            if (port != 80) {
                url += ":";
                url += port;
            }
            url += req.getContextPath();
            return url;
        } catch (Exception e) {
            return "";
        }
    }

    public static String encodeSql(String str) {
        if (str == null) {
            return null;
        }
        return StringUtils.replace(str, "'", "''");
    }

    public static String formatMyKad(String mykad) {
        try {
            String a = mykad.substring(0, 6);
            String b = mykad.substring(6, 8);
            String c = mykad.substring(8, mykad.length());
            return a.concat("-").concat(b).concat("-").concat(c);
        } catch (Exception e) {
            return mykad;
        }
    }

    public static String formatCurrency(Object wang) {
        return formatCurrency(CoreUtil.toDouble(wang));
    }

    public static String formatCurrency(double wang) {
        StringBuffer baru = new StringBuffer();
        boolean tolak = false;
        try {
            String ss = NumberFormat.getCurrencyInstance().format(wang);
            //log.info("currency : "+ss);
            StringBuffer s = new StringBuffer(ss);

            for (int x = 0; x < s.length(); x++) {
                if (s.charAt(x) == ',') {
                    baru.append(s.charAt(x));
                }
                if (s.charAt(x) == '.') {
                    baru.append(s.charAt(x));
                }
                if (CharUtils.isAsciiNumeric(s.charAt(x))) {
                    baru.append(s.charAt(x));
                }
                if (s.charAt(x) == '(' || s.charAt(x) == '-') {
                    tolak = true;
                }
            }
        } catch (Exception e) {
            return Double.toString(wang);
        } finally {
            if (tolak) {
                return "-" + baru.toString();
            } else {
                return baru.toString();
            }
        }
    }

    public static double roundCurrency(double val, int roundNo) {
        return round(val, roundNo);
    }

    public static double round(double Rval, int Rpl) {
        double p = Math.pow(10.0D, Rpl);

        Rval *= p;
        double tmp = Math.round(Rval);
        return tmp / p;
    }

    public static String bracketCurrency(double amount) {
        if (amount < 0) {
            return "(" + formatCurrency(Math.abs(amount)) + ")";
        } else {
            return formatCurrency(amount);
        }
    }

    /**
     * request.getQueryString
     *
     * @param name
     * @return
     */
    public static String getQueryString(HttpServletRequest request, String name) {
        String q = request.getQueryString();
        String[] params = q.split("&");
        String param = null;
        String p[] = null;
        for (int x = 0; x < params.length; x++) {
            param = params[x];
            p = param.split("=");
            if (p != null) {
                if (p.length > 0) {
                    if (p[0].trim().equals(name)) {
                        return p[1];
                    }
                }
            }
        }
        return null;
    }

    public static double roundMath(double val, int no) {
        double value = val * 100;
        double round_value = Math.round(value);
        round_value = round_value / (double) 100;
        return round_value;
    }

    public static double roundHalfUp(double x, int scale) {
        return round(x, scale, BigDecimal.ROUND_HALF_UP);
    }

    public static double round(double x, int scale, int roundingMethod) {
        try {
            return (new BigDecimal(Double.toString(x))
                    .setScale(scale, roundingMethod))
                    .doubleValue();
        } catch (NumberFormatException ex) {
            if (Double.isInfinite(x)) {
                return x;
            } else {
                return Double.NaN;
            }
        }
    }

    public static String removeInitial0(String arr) {
        
        System.out.println("arr = " + arr);
       String wer = arr.startsWith("0") ? arr.substring(1) : arr;
        return wer;
    }

    /**
     * * takes a string of time in hh:mm:ss and reformats it to hh:mm a
     *
     * @param timestr
     * @return
     */
    public static String formatTimeFromTimeString(String timestr) {
        return formatTimeFromTimeString(timestr, "hh:mm:ss");
    }

    /**
     * takes a string of time and reformats it.
     *
     * @param timeStr - 24hr format time when timeformat is default. ie...
     * "24:23:54"
     * @param timeformat - what is the format to help the parser extract time
     * @return
     */
    public static String formatTimeFromTimeString(String timeStr, String timeformat) {
        String date_format = "dd-M-yyyy " + timeformat;

        SimpleDateFormat beforeformat = new SimpleDateFormat(date_format);
        String dateInString = "01-01-1970 " + timeStr;
        try {
            Date dummyDateWithTime = beforeformat.parse(dateInString);
            SimpleDateFormat afterFormat = new SimpleDateFormat("hh:mm a");

            return afterFormat.format(dummyDateWithTime);

        } catch (ParseException e) {
            return "00:00:00";
        }

    }

    public static String toCapitalize(String s) {
        return WordUtils.capitalizeFully(s);

    }

    public static void reverseArray(Object a[]) {
        ArrayUtils.reverse(a);
    }

    public static void removeArray(Object a[], int index) {
        ArrayUtils.remove(a, index);
    }

    public static String toFixed(double value, int decimalPoint) {
        String s = "###,###,##0.";
        for (int x = 0; x < decimalPoint; x++) {
            s += "0";
        }

        DecimalFormat f = new DecimalFormat(s);
        return f.format(value);
    }

    public static String toFixedNon(double value, int decimalPoint) {
        String s = "0.";
        for (int x = 0; x < decimalPoint; x++) {
            s += "0";
        }
        DecimalFormat f = new DecimalFormat(s);
        return f.format(value);
    }

    public static String toLeftPad(Object value, String padNo) {
        String s = CoreUtil.toString(value);
        DecimalFormat df = new DecimalFormat(padNo);
        s = df.format(toInteger(value));
        return s;
    }

    public static String toUpperCase(Object ss) {
        return StringUtils.upperCase(toString(ss));
    }

    public static String encodeBase36(long longValue) {
        return Long.toString(longValue, 36);
    }

    public static long decodeBase36(String strValue) {
        return Long.parseLong(strValue, 36);
    }

    public static Date getFirstYear() {
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR), 0, 1);
        return c.getTime();
    }

    public static Date getLastYear() {
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR), 11, 31);
        return c.getTime();
    }

    public static Date getFirstNextYear() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 1);
        c.set(c.get(Calendar.YEAR), 0, 1);
        return c.getTime();
    }

    public static Date getLastNextYear() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 1);
        c.set(c.get(Calendar.YEAR), 11, 31);
        return c.getTime();
    }

    public static int getNextYear() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 1);
        return c.get(Calendar.YEAR);
    }

    public static String n2br(String v) {
        return StringUtils.replace(v, "\n", "<br/>");
    }

    public static String br2n(String v) {
        return StringUtils.replace(v, "<br/>", "\n");
    }

    public static void addToMap(Map<String, Object> map, String nama, String value) {
        if (map == null) {
            map = new HashMap<String, Object>();
        }
        map.put(nama, value);
    }

    /**
     * send redirect
     *
     * @param url
     */
    public static void sendRedirect(javax.servlet.jsp.JspWriter out, String url) throws IOException {
        out.print("<script>location='" + CoreUtil.encodeJavaScript(url) + "';</script>");
    }

    public static void sendRedirectTarget(javax.servlet.jsp.JspWriter out, String url, String target) throws IOException {
        out.print("<script>window.open('" + CoreUtil.encodeJavaScript(url) + "', '" + target + "');</script>");
    }

    public static void sendRedirectPOST(javax.servlet.jsp.JspWriter out, String url) throws IOException {
        Map<String, String> map = getQueryMap(url);
        Iterator iter = map.entrySet().iterator();
        final long id = System.currentTimeMillis();
        if (CoreUtil.isEmpty(url)) {

            out.println("<form id='myform" + id + "' action='index.jsp' method='POST'>");
            out.println("<input type='hidden' name='rand' value='" + id + "'/>");
            out.println("</form>");
        } else {
            out.println("<form id='myform" + id + "' action='' method='POST'>");
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String name = CoreUtil.toString(entry.getKey());
                String value = CoreUtil.toString(entry.getValue());
                out.println("<input type='hidden' name='" + name + "' value='" + value + "'/>");
            }
            out.println("</form>");
        }

        out.print("<script>");
        out.println("$(document).ready(function() { $('#myform" + id + "').submit(); });");
        out.print("</script>");
    }

    public static Map<String, String> getQueryMap(String query) {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params) {
            try {
                String name = param.split("=")[0];
                String value = param.split("=")[1];
                map.put(name, value);
            } catch (Exception e) {
            }
        }
        return map;
    }

    public static String periodFormating(String period) {
        String prdNewFormat = "";
        if (period.length() == 1) {
            prdNewFormat = "0" + period;
        } else {
            prdNewFormat = period;
        }

        return prdNewFormat;
    }

    public static void sendRedirect(PrintWriter out, String url) {
        out.print("<script>location='" + CoreUtil.encodeJavaScript(url) + "';</script>");
    }

    public static String getFileName(String jspPath, boolean ext) {
        java.io.File jspFile = new java.io.File(jspPath);
        if (ext) {
            return jspFile.getName().replace(".jsp", "");
        } else {
            return jspFile.getName();
        }
    }

    /**
     * convert java.sql.date to java.util.date
     *
     * @param d
     * @return
     */
    public static java.util.Date toDate(Object d) {
        if (d instanceof java.sql.Date) {
            return new java.util.Date(((java.sql.Date) d).getTime());
        } else if (d instanceof java.util.Date) {
            return (java.util.Date) d;
        }
        return null;
    }

    public static Date dateParsing(String date) throws SQLException, ParseException {

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date newD = df.parse(date);

        return newD;

    }

    /**
     * parse input string date and convert to date
     *
     * @param format
     * @param d
     * @return
     */
    public static java.util.Date toDate(String format, String d) {
        d = CoreUtil.toString(d);
        if (d.equals("0000-00-00")) {
            return null;
        } else if (d.equals("00.00.0000")) {
            return null;
        }
        if (isEmpty(d)) {
            return null;
        }
        SimpleDateFormat sdf = null;

        if (format.equals("yyyy-MM-dd HH:mm:ss")) {
            sdf = new SimpleDateFormat(format, Locale.UK);
            try {
                return sdf.parse(d);
            } catch (Exception ex) {
            }
        } else if (format.equalsIgnoreCase("dd.MM.yyyy HH:mm a")) {
            sdf = new SimpleDateFormat(format, Locale.UK);
            try {
                System.out.println("sdf.parse(d) = " + sdf.parse(d));
                return sdf.parse(d);
            } catch (Exception ex) {
            }
        }

        //test -
        String m[] = StringUtils.split(d, "-");
        if (m.length > 0) {
            //test yyyy
            if (m[0].length() == 4) {
                //try with format yyyy-MM-dd
                sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
                try {
                    return sdf.parse(d);
                } catch (Exception ex) {
                }

                //try with format YYYY-MMM-dd
                sdf = new SimpleDateFormat("yyyy-MMM-dd", Locale.UK);
                try {
                    return sdf.parse(d);
                } catch (Exception ex) {
                }
            }

            //try with format dd-MM-yyyy
            sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
            try {
                return sdf.parse(d);
            } catch (Exception ex) {
            }
            //try with format d-M-yyyy
            sdf = new SimpleDateFormat("dd-M-yyyy", Locale.UK);
            try {
                return sdf.parse(d);
            } catch (Exception ex) {
            }
            //try with format yyyy-M-d
            sdf = new SimpleDateFormat("yyyy-M-d", Locale.UK);
            try {
                return sdf.parse(d);
            } catch (Exception ex) {
            }
            //try with format yyyy-MM-d
            sdf = new SimpleDateFormat("yyyy-MM-d", Locale.UK);
            try {
                return sdf.parse(d);
            } catch (Exception ex) {
            }
            //try with format input
            sdf = new SimpleDateFormat(format, Locale.UK);
            try {
                return sdf.parse(d);
            } catch (Exception ex) {
            }

            //failure
            return null;
        }
        //test /
        m = StringUtils.split(d, "/");
        if (m.length > 0) {
            //try with format dd/MMM/yyyy
            sdf = new SimpleDateFormat("dd/MMM/yyyy", Locale.UK);
            try {
                return sdf.parse(d);
            } catch (Exception ex) {
            }
            //try with format dd/MM/yyyy
            sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
            try {
                return sdf.parse(d);
            } catch (Exception ex) {
            }
            //try with format d/M/yyyy
            sdf = new SimpleDateFormat("d/M/yyyy", Locale.UK);
            try {
                return sdf.parse(d);
            } catch (Exception ex) {
            }
            //try with format yyyy/M/d
            sdf = new SimpleDateFormat("yyyy/M/d", Locale.UK);
            try {
                return sdf.parse(d);
            } catch (Exception ex) {
            }
            //try with format yyyy/MM/d
            sdf = new SimpleDateFormat("yyyy/MM/d", Locale.UK);
            try {
                return sdf.parse(d);
            } catch (Exception ex) {
            }
            //try with format yyyy/MMM/dd
            sdf = new SimpleDateFormat("yyyy/MMM/dd", Locale.UK);
            try {
                return sdf.parse(d);
            } catch (Exception ex) {
            }
            //try with format input
            sdf = new SimpleDateFormat(format, Locale.UK);
            try {
                return sdf.parse(d);
            } catch (Exception ex) {
            }

            //failure
            return null;
        }

        //test /
        m = StringUtils.split(d, " ");
        if (m.length > 0) {
            //try with format dd MMM yyyy
            sdf = new SimpleDateFormat("dd MMM yyyy", Locale.UK);
            try {
                return sdf.parse(d);
            } catch (Exception ex) {
            }
            //try with format dd MM yyyy
            sdf = new SimpleDateFormat("dd MM yyyy", Locale.UK);
            try {
                return sdf.parse(d);
            } catch (Exception ex) {
            }
            //try with format d M yyyy
            sdf = new SimpleDateFormat("d M yyyy", Locale.UK);
            try {
                return sdf.parse(d);
            } catch (Exception ex) {
            }
            //try with format yyyy M d
            sdf = new SimpleDateFormat("yyyy M d", Locale.UK);
            try {
                return sdf.parse(d);
            } catch (Exception ex) {
            }
            //try with format yyyy MM d
            sdf = new SimpleDateFormat("yyyy MM d", Locale.UK);
            try {
                return sdf.parse(d);
            } catch (Exception ex) {
            }
            //try with format yyyy MMM dd
            sdf = new SimpleDateFormat("yyyy MMM dd", Locale.UK);
            try {
                return sdf.parse(d);
            } catch (Exception ex) {
            }
            //failure
            return null;
        } else {
            return null;
        }

    }

    public static boolean toBoolean(Object s) {
        try {
            return Boolean.parseBoolean(s.toString());
        } catch (Exception e) {
            return false;
        }
    }

    public static double toDouble(Object s) {
        try {
            String ss = s.toString();
            ss = ss.replaceAll(",", "");
            if (ss.startsWith("(") && ss.endsWith(")")) {
                ss = ss.replace("(", "").replace(")", "");
                return Double.parseDouble(ss) * -1;
            } else {
                return Double.parseDouble(ss);
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public static long toLong(Object s) {
        try {
            return Long.parseLong(s.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public static int dayOfMonth(String dateS) {
        int days = 0;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(toDate("d.MM.yyyy", dateS));
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            cal.set(year, month, day);
            days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            System.out.println("dayOfMonth->" + e.toString());
        }
        return days;
    }

    /**
     * to lower case
     *
     * @param s
     * @return
     */
    public static String toLowerCase(String s) {
        return StringUtils.lowerCase(s);
    }

    /**
     * convert object to integer
     *
     * @param o
     * @return
     */
    public static int toInteger(Object o) {
        try {
            return Integer.parseInt(o.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * convert object to integer
     *
     * @param o
     * @return
     */
    public static byte toByte(Object o) {
        try {
            return Byte.valueOf(o.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * encode html
     *
     * @param e
     * @return
     */
    public static String encodeHtml(String e) {
        return StringEscapeUtils.escapeHtml4(e);
    }

    public static String encodeXMLSpecial(String e) {
        e = toString(e);
        e = e.replaceAll("&", "&amp;");
        e = e.replaceAll(">", "&gt;");
        e = e.replaceAll("<", "&lt;");
        e = e.replaceAll("\'", "&apos;");
        e = e.replaceAll("\"", "&quot;");
        return e;
    }

    public static String encodeJavaScript(Object s) {
        return StringEscapeUtils.escapeEcmaScript(toString(s));
    }

    /**
     * join array into string
     *
     * @param array
     * @param s
     * @return
     */
    public static String join(Object[] array, String s) {
        return StringUtils.join(array, s);
    }

    /**
     * get current date time system
     *
     * @return
     */
    public static Date now() {
        return Calendar.getInstance().getTime();
    }

    /**
     * convert null or empty value to desired value
     *
     * @param pckgname
     * @return String
     */
    public static String handleValue(String variable, String expected) {
        String newVal = expected;
        variable = CoreUtil.toString(variable).trim();
        if (variable.equals("") || variable.equalsIgnoreCase("null")) {
            newVal = expected;
        } else {
            newVal = variable;
        }
        return newVal;
    }

    /**
     * convert null or empty value to desired value for certain value only
     *
     * @param pckgname
     * @return String
     */
    public static String handleValue(String variable, String expected, String contain) {
        String newVal = expected;
        variable = CoreUtil.toString(variable).trim();
        if (variable.equals("") || variable.equalsIgnoreCase("null") || variable.equalsIgnoreCase(contain)) {
            newVal = expected;
        } else {
            newVal = variable;
        }
        return newVal;
    }

    public static String formatCurrencyRound(double wang) {
        StringBuffer baru = new StringBuffer();
        boolean tolak = false;
        try {
            wang = round(wang, 2);
            String ss = NumberFormat.getCurrencyInstance().format(wang);
            //log.info("currency : "+ss);
            StringBuffer s = new StringBuffer(ss);

            for (int x = 0; x < s.length(); x++) {
                if (s.charAt(x) == ',') {
                    baru.append(s.charAt(x));
                }
                if (s.charAt(x) == '.') {
                    baru.append(s.charAt(x));
                }
                if (CharUtils.isAsciiNumeric(s.charAt(x))) {
                    baru.append(s.charAt(x));
                }
                if (s.charAt(x) == '(' || s.charAt(x) == '-') {
                    tolak = true;
                }
            }
        } catch (Exception e) {
            return Double.toString(wang);
        } finally {
            if (tolak) {
                return "-" + baru.toString();
            } else {
                return baru.toString();
            }
        }
    }

}
