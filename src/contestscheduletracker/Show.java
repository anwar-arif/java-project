/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contestscheduletracker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 *
 * @author anwar_sust
 */
public class Show {
    
    private static ObservableList<TabInfo> data = FXCollections.observableArrayList();
    
    /**
     * display contests items in table
     * @param str
     * @param table
     */
    public static void display(String str, TableView<TabInfo> table){
        data.removeAll(data);
        for(int i=0; i<str.length(); i++){
            String s1 = "";
            while(str.charAt(i) != '>'){
                s1 += str.charAt(i);
                i++;
            }
            i++;
            String s2 = "";
            while(str.charAt(i) != '>'){
                s2 += str.charAt(i);
                i++;
            }
            i++;
            String s3 = "";
            while(str.charAt(i) != '>'){
                s3 += str.charAt(i);
                i++;
            }
            i++;
            String s4 = "";
            while(str.charAt(i) != '#'){
                s4 += str.charAt(i);
                i++;
            }
            
            data.add(new TabInfo(s1, s2, s3, s4));
        }
        table.setItems(data);
    }
}
