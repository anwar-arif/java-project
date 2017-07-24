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
public class Contest{
    String []start ;
    String []end ;
    String []title;
    String judge;
    int indx;
    Contest(String jud){
        start = new String[50];
        end = new String[50];
        title = new String[50];
        judge = jud;
        indx = 0;
    }
}