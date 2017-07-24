/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contestscheduletracker;

import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

/**
 *
 * @author anwar_sust
 */
public class CustomCell extends TableCell<TabInfo, String> {

    private TextField textField;

    /**
     * updating custom cell
     */
    public CustomCell() {
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else if (isEditing()) {
            if (textField != null) {
                textField.setText(getString());
            }
            setText(null);
            setGraphic(textField);
        } else {
            setText(getString());
            setGraphic(null);
            Tooltip tip = new Tooltip(getString());
            Tooltip.install(this, tip);

        }
    }

    private String getString() {
        return getItem() == null ? "" : getItem();
    }
}
