/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contestscheduletracker;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author anwar_sust
 */
public class DateDifference {

    /**
     * is starting date is later date than cur date
     * @param cur
     * @param start
     * @return
     */
    public static boolean date_difference(String cur, String start) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        boolean  isTrue = false;
        try {
            Date d1 = format.parse(cur);
            Date d2 = format.parse(start);

            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            
            isTrue = (diffDays >= 0 && diffHours >= 0
                    && diffMinutes >= 0&& diffSeconds >= 0);
            
        }
        catch(Exception e){
            e.printStackTrace();
            
        }
        return isTrue;
    }
}
