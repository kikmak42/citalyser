/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.visualization.panels.regulardisplaypanel.sidebarpanel.authorlistpanel;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author kaushik
 */
public class AuthorListCellRenderer implements ListCellRenderer<JCheckBox> {

    @Override
    public Component getListCellRendererComponent(JList<? extends JCheckBox> list, JCheckBox value, int index, boolean isSelected, boolean cellHasFocus) {
        return value;
    }
    
}
