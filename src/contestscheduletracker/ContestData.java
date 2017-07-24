/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contestscheduletracker;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Scanner;
import javafx.util.Callback;
import org.json.JSONException;

/**
 *
 * @author anwar_sust
 */
public class ContestData {
    /**
     * 
     * @param s format 20160819T1230
     * @return date with format 2016-08-19 12:30:00
     * @throws ParseException
     */
    static public String dc(String s) throws ParseException {
        String ret = "";
        for (int i = 0; i <= 3; i++) {
            ret += (s.charAt(i));
        }
        ret += "-";
        ret += s.charAt(4);
        ret += s.charAt(5);
        ret += "-";
        ret += s.charAt(6);
        ret += s.charAt(7);
        ret += " ";
        ret += s.charAt(9);
        ret += s.charAt(10);
        ret += ":";
        ret += s.charAt(11);
        ret += s.charAt(12);
        ret += ":00";
        String tim = GetTimeZone.get_time(ret);
        return tim;
    }

    static boolean oka(String s) {
        return s.equals("hackerearth.com") || s.equals("topcoder.com")
                || s.equals("codechef.com") || s.equals("hackerrank.com")
                || s.equals("codeforces.com") || s.equals("csacademy.com");
    }

    /**
     *
     * @param callback
     * @throws MalformedURLException
     * @throws IOException
     * @throws JSONException
     * @throws ParseException
     */
    public static void contestdata(Callback<String, Void> callback)
            throws MalformedURLException, IOException, JSONException, ParseException {

        URL url = new URL("http://clist.by/");

        String str = "";
        String s, now = "";
        int len, i;
        String[] result = new String[7000];
        boolean t;
        int cnt = 0;
        Scanner scan;
        try {
            scan = new Scanner(url.openStream());
        } catch (IOException e) {
            callback.call("Connection Error!");
            return;
        }

        while (scan.hasNext()) {
            s = scan.nextLine();

            len = s.length();
            if (len > 0) {
                i = 0;
                t = true;
                while (s.charAt(i) == ' ' && i < len) {
                    ++i;
                    if (i == len) {
                        t = false;
                        break;
                    }
                }
                String cur = "";
                for (int j = i; j < len; j++) {
                    cur += s.charAt(j);
                }
                if (t) {
                    result[++cnt] = cur;
                }
            }
        }
        int ans = 0;
        String p;
        String cur;
        String jud;
        String start;
        String end;
        String title;
        String judge;

        HashMap hm = new HashMap();
        HashMap rhm = new HashMap();
        hm.clear();
        rhm.clear();
        int judges = 0;
        Contest[] c = new Contest[100];

        for (i = 1; i <= cnt; i++) {
            p = "<var class=\"atc_event\">";
            if (result[i].equals(p)) {
                cur = result[i + 5];
                jud = "";
                start = "";
                end = "";
                title = "";
                judge = "";
                //location
                for (int j = 26; j < cur.length(); j++) {
                    if (cur.charAt(j) == '<') {
                        break;
                    }
                    jud += cur.charAt(j);
                }

                if (hm.get(jud) == null) {
                    ++judges;
                    c[judges] = new Contest(jud);
                    hm.put(jud, judges);
                    rhm.put(judges, jud);
                }
                //start time
                cur = result[i + 1];
                for (int j = 28; j < cur.length(); j++) {
                    if (cur.charAt(j) == '<') {
                        break;
                    }
                    start += cur.charAt(j);
                }
                //end time
                cur = result[i + 2];
                for (int j = 26; j < cur.length(); j++) {
                    if (cur.charAt(j) == '<') {
                        break;
                    }
                    end += cur.charAt(j);
                }
                //title
                cur = result[i + 4];
                for (int j = 23; j < cur.length(); j++) {
                    if (cur.charAt(j) == ' ' && cur.charAt(j + 1) == '@') {
                        break;
                    }
                    if (cur.charAt(j) != '@') {
                        title += cur.charAt(j);
                    }
                }
                //judge
                judge = jud;

                int judge_id = (int) hm.get(jud);

                c[judge_id].indx++;
                int indx = c[judge_id].indx;

                c[judge_id].start[indx] = start;
                c[judge_id].end[indx] = end;
                c[judge_id].title[indx] = title;
                c[judge_id].judge = jud;

                i += 6;
                ++ans;
            }
        }
        
        String ret = "";
        boolean contests = false;
        for (i = 1; i <= judges; i++) {
            if (!oka(c[i].judge)) {
                continue;
            }
            contests = true;
            len = c[i].indx;

            for (int j = 1; j <= Math.min(len, 3); j++) {
                ret += c[i].judge + ">";
                ret += GetDateFormat.get_12_h(dc(c[i].start[j])) + ">" + 
                        GetDateFormat.get_12_h(dc(c[i].end[j])) + ">" + c[i].title[j] + "#";
            }
        }

        if (!contests) {
            ret = "no contests!!";
        }
        callback.call(ret);
    }
}
