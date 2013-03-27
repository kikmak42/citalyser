/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui;

import citalyser.ui.visualization.MainFrame;
import java.util.Vector;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author Tanmay Patil
 */
public interface DisplayController {
    
    public void initializeDisplay();
    
    public void displayErrorMessage(String error);
    
    public void displayStatusMessage(String status);
    
    public void displayInfoMessage(String status);
    
    public void addAutoCompleteSuggestions(Vector<String> suggestions);
    
    public void addAutoCompleteSuggestion(String suggestion);
    
    public void emptyAutoCompleteSuggestions();
    
    public MainFrame getMainFrame();

    public ExecutorService getExecutorServeice();

    
}
