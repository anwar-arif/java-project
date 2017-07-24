/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contestscheduletracker;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author anwar_sust
 */
public class GetTimeZone {

    /**
     *
     * @param s contest event date with GMT+0 format
     * @return date with GMT+6 format
     * @throws ParseException
     */
    public static String get_time(String s) throws ParseException{
        String date = s ;  //"2016-08-27 13:05:00";
        LocalDateTime gmt = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ZonedDateTime instant = ZonedDateTime.of(gmt, ZoneId.of("GMT"));
        LocalDateTime bst = instant.withZoneSameInstant(ZoneId.of("Asia/Dhaka")).toLocalDateTime();
        String t = bst.toString();
        String ret = "";
        for(int i = 0 ; i < t.length();i++){
            if(t.charAt(i) == 'T')ret += " ";
            else ret += t.charAt(i);
        }
        return ret;
    }
}
