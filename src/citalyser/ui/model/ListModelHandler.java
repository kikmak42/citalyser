/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.model;

import citalyser.model.Paper;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 *
 * @author Tanmay Patil
 */
public class ListModelHandler {
    
    public static ListModel<Paper> getListModel(ArrayList<Paper> papers) {
        DefaultListModel<Paper> listModel = new DefaultListModel<>();
        for (Paper paper : papers) {
            listModel.addElement(paper);
        }
        return listModel;
    }

}
