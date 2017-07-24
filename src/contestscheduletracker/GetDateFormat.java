/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contestscheduletracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anwar_sust
 */
public class GetDateFormat {

    /**
     *
     * @param s date format with 12/24 hr format
     * @return String with formated date
     */
    public static String get_12_h(String s){
        String ret = null;
        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            Date date = parseFormat.parse(s);
            ret = displayFormat.format(date);
            
        } catch (ParseException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    /**
     *
     * @param s s is 12 hr date string
     * @return 24 hr date string
     */
    public static String get_24_h(String s){
        String ret = null;
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            Date date = parseFormat.parse(s);
            ret = displayFormat.format(date);
            
        } catch (ParseException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
}
