/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.control;

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
    
}
