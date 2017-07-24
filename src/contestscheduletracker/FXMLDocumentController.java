/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contestscheduletracker;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.json.JSONException;

/**
 *
 * @author anwar_sust
 */
public class FXMLDocumentController implements Initializable {

    String contestList;
    boolean hasError = false;
    boolean cf = false;
    boolean tc = false;
    boolean ccf = false;
    boolean hr = false;
    boolean he = false;
    boolean csa = false;
    @FXML
    Label judge_notification, lbl;
    @FXML
    CheckBox codeforces, topcoder, codecheff, hackerrank, hackerearth, csacademy;

    TextArea tx;
    Button contest;
    @FXML
    private ChoiceBox<String> selectTime;
    @FXML
    private TableColumn<TabInfo, String> JudgeCol;
    @FXML
    private TableColumn<TabInfo, String> STCol;
    @FXML
    private TableColumn<TabInfo, String> ETCol;
    @FXML
    private TableColumn<TabInfo, String> TitCol;
    @FXML
    private TableView<TabInfo> table;
    @FXML
    private Button refresh;

    Timer notificationTimer;

    /**
     * updating judges for notification on mouse clicked in checkbox
     */
    @FXML
    public void set_value() {
        if (topcoder.isPressed()) tc = tc == false;

        if (codeforces.isPressed()) cf = cf == false;

        if (hackerrank.isPressed()) hr = hr == false;

        if (codecheff.isPressed()) ccf = ccf == false;

        if (hackerearth.isPressed()) he = he == false;

        if (csacademy.isPressed()) csa = csa == false;
    }

    AllContest[] all_contest = new AllContest[50];

    /**
     * collecting contest data and displaying in table 
     */
    public void work() {
        try {
            ContestData.contestdata((String str) -> {
                contestList = str;
                hasError = "Connection Error!".equals(str);

                Platform.runLater(() -> {
                    if (hasError) {
                        lbl.setText("Connection Error!");
                    } else {
                        lbl.setText("Current/Upcoming Contests");
                        Show.display(str, table);
                    }
                });
                if (!hasError) {
                    all_contest = ParseContestList.parse_contest_list(contestList);
                }
                return null;
            });
        } catch (IOException | JSONException | ParseException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 
     * @return time before notification in milis.
     */
    long getBeforeContestTime() {
        String str = selectTime.getSelectionModel().getSelectedItem();
        
        long ret = 0;
        
        if ("5 min".equals(str)) ret = (long) 5 * 60_000;
            
        if ("10 min".equals(str)) ret = (long) 10 * 60_000;
        
        if ("15 min".equals(str)) ret = (long) 15 * 60_000;
        
        if ("30 min".equals(str)) ret = (long) 30 * 60_000;
        
        if ("45 min".equals(str)) ret = (long) 45 * 60_000;
        
        if ("1 hr".equals(str)) ret = (long) 60 * 60_000;
        
        return ret;
    }

    /**
     * 
     * @param str
     * @return true if judge "str" is selected for notification
     */
    boolean isSelected(String str) {
        boolean t = false;
        
        if (str.equals("codeforces.com")) t = cf;
        
        if (str.equals("codecheff.com")) t = ccf;
            
        if (str.equals("topcoder.com")) t = tc;
        
        if (str.equals("hackerrank.com")) t = hr;
        
        if (str.equals("hackerearth.com")) t = he;
        
        if (str.equals("csacademy.com")) t = csa;
        
        return t;
    }
    
    /**
     * display notification for contest event
     * @param contest
     * @throws ParseException
     */
    public void notifyContest(AllContest contest) throws ParseException {
        if (contest == null || contest.msgShown) {
            return;
        }

//        long agoTime = 28*3600_000;
        long agoTime = getBeforeContestTime();
        
        String startTime = contest.StartTime;//12 h
        startTime = GetDateFormat.get_24_h(startTime);
        
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        
        SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        
        Date start = dateformat.parse(startTime);
        long diff = start.getTime() - System.currentTimeMillis();

        boolean isJudgeSelected = isSelected(contest.JudgeName);
        String title = contest.Title;
        
        String CurrnetTime = dateformat2.format(Calendar.getInstance().getTime());
        CurrnetTime = GetDateFormat.get_24_h(CurrnetTime);
        
        boolean ContestIscomming = DateDifference.date_difference(CurrnetTime , startTime);
        
        if (diff <= agoTime && isJudgeSelected && 
        !contest.msgShown && ContestIscomming) {
            
            String msg = "There is a contest on " + contest.JudgeName + " at " +
                    GetDateFormat.get_12_h(startTime) +"\n"+contest.Title;
            contest.msgShown = true;
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
                
                alert.showAndWait();
            });
        }
    }

    /**
     * initializing java timer
     */
    public void initTimer() {
        notificationTimer = new Timer(true);

        notificationTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // check all contest
                // show a notification for contests
                // which will start 10 / whatever min later.
                //for every contest...
                for (AllContest all_contest1 : all_contest) {
                    try {
                        notifyContest(all_contest1);
                    } catch (ParseException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }, 100, 60_000);//(start calling run after 100ms of compiling,check repeatly 60 sec)
    }

    /**
     * work when refresh button is clicked
     */
    @FXML
    public void refresh() {
        new Thread(() -> {
            work();
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        selectTime.setItems(FXCollections.observableArrayList(
                "5 min", "10 min", "15 min", "30 min", "45 min", "1 hr"
        ));
        selectTime.getSelectionModel().select(1);

        JudgeCol.setCellValueFactory(
                new PropertyValueFactory<>("JC"));
        STCol.setCellValueFactory(
                new PropertyValueFactory<>("SC"));
        ETCol.setCellValueFactory(
                new PropertyValueFactory<>("EC"));
        TitCol.setCellValueFactory(
                new PropertyValueFactory<>("TC"));

        Callback<TableColumn<TabInfo, String>, TableCell<TabInfo, String>> cellFactory
                = new Callback<TableColumn<TabInfo, String>, TableCell<TabInfo, String>>() {
            @Override
            public TableCell call(TableColumn p) {
                return new CustomCell();
            }
        };

        JudgeCol.setCellFactory(cellFactory);
        STCol.setCellFactory(cellFactory);
        ETCol.setCellFactory(cellFactory);
        TitCol.setCellFactory(cellFactory);

        work();
        initTimer();
    }

}