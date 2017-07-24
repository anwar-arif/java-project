/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contestscheduletracker;

/**
 *
 * @author anwar_sust
 */
public class AllContest {
    boolean msgShown;
    String JudgeName , StartTime , EndTime , Title;
    AllContest(String a,String b,String c,String d){
        this();
        JudgeName = a;
        StartTime = b;
        EndTime = c;
        Title = d;
    }

    AllContest() {
        msgShown = false;
    }
}
