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
public class ParseContestList {

    /**
     *
     * @param contestList a string which need to parse
     * @return an array of AllContest class
     */
    public static AllContest[] parse_contest_list(String contestList) {

        AllContest[] all_contest = new AllContest[50];
        
        if ("Connection Error!".equals(contestList)) {
            return all_contest;
        }

        int cnt = 0;
        for (String contest : contestList.split("#")) {
            String items[] = contest.split(">");
            all_contest[cnt] = new AllContest();
            all_contest[cnt].JudgeName = items[0];
            all_contest[cnt].StartTime = items[1];
            all_contest[cnt].EndTime = items[2];
            all_contest[cnt].Title = items[3];
            cnt++;
        }
        
        return all_contest;
    }
}
