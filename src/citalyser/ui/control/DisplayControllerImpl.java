/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.control;

import citalyser.ui.visualization.MainFrame;
import java.util.Vector;
import java.util.concurrent.ExecutorService;

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
    public void displayStatusMessage(String status) {
        displayMaster.displayStatusMessage(status);
    }

    @Override
    public void displayInfoMessage(String status) {
        displayMaster.displayInfoMessage(status);
    }

    @Override
    public void addAutoCompleteSuggestions(Vector<String> suggestions) {
        displayMaster.getSearchMaster().addAutoCompleteSuggestions(suggestions);
    }

    @Override
    public void addAutoCompleteSuggestion(String suggestion) {
        displayMaster.getSearchMaster().addAutoCompleteSuggestion(suggestion);
    }

    @Override
    public void emptyAutoCompleteSuggestions() {
        displayMaster.getSearchMaster().emptyAutoCompleteSuggestions();
    }

    @Override
    public MainFrame getMainFrame() {
        return displayMaster.getMainFrame();
    }

    @Override
    public ExecutorService getExecutorServeice() {
        return displayMaster.getExecutorService();
    }
    
}
