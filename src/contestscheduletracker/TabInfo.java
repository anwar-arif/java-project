/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contestscheduletracker;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author anwar_sust
 */
public class TabInfo {

    /**
     * JC = contest judge column
     */
    public SimpleStringProperty JC;

    /**
     * SC = contest starting time column
     */
    public SimpleStringProperty SC;

    /**
     * EC = contest ending time column
     */
    public SimpleStringProperty EC;

    /**
     * TC = contest title column
     */
    public SimpleStringProperty TC;

    /**
     * set JC,SC,EC,TC
     * @param a judge
     * @param b start time
     * @param c end time
     * @param d title
     */
    public TabInfo(String a, String b, String c, String d) {
        this.JC = new SimpleStringProperty(a);
        this.SC = new SimpleStringProperty(b);
        this.EC = new SimpleStringProperty(c);
        this.TC = new SimpleStringProperty(d);
    }

    /**
     *
     * @return judge name
     */
    public String getJC() {
        return JC.get();
    }

    /**
     * set JC = a
     * @param a judge name
     */
    public void setJC(String a) {
        JC.set(a);
    }

    /**
     *
     * @return start time
     */
    public String getSC() {
        return SC.get();
    }

    /**
     * set JC = b
     * @param b start time
     */
    public void setSC(String b) {
        SC.set(b);
    }
    
    /**
     * 
     * @return ending time
     */
    public String getEC() {
        return EC.get();
    }

    /**
     * set EC = c
     * @param c ending time
     */
    public void setEC(String c) {
        EC.set(c);
    }
    
    /**
     * 
     * @return contest title
     */
    public String getTC() {
        return TC.get();
    }

    /**
     * set TC = r
     * @param r contest title
     */
    public void setTC(String r) {
        TC.set(r);
    }
}
