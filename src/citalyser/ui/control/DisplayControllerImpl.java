/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.control;

import java.util.Vector;

/**
 *
 * @author Tanmay Patil
 */
public class DisplayControllerImpl implements citalyser.ui.DisplayController {
    
    private DisplayMaster displayMaster;

    @Override
    public void initializeDisplay() {
        this.displayMaster = new DisplayMaster();
    }

    @Override
    public void displayErrorMessage(String message) {
        displayMaster.displayErrorMessage(message);
    }

    @Override
    public void addAutoCompleteSuggestions(Vector<String> suggestions) {
        displayMaster.addAutoCompleteSuggestions(suggestions);
    }

    @Override
    public void addAutoCompleteSuggestion(String suggestion) {
        displayMaster.addAutoCompleteSuggestion(suggestion);
    }

    @Override
    public void emptyAutoCompleteSuggestions() {
        displayMaster.emptyAutoCompleteSuggestions();
    }

    @Override
    public void displayStatusMessage(String status) {
        displayMaster.displayStatusMessage(status);
    }

    @Override
    public void displayInfoMessage(String status) {
        displayMaster.displayInfoMessage(status);
    }
    
}
