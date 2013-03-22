/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.listdisplaypanel;

import citalyser.model.Paper;
import citalyser.ui.control.DisplayMaster;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JEditorPane;
import org.jdesktop.swingx.JXTaskPane;

/**
 *
 * @author kaushik
 */
public class CollapsibleListEntityPanel extends JXTaskPane {

    public CollapsibleListEntityPanel(Paper paper) {
        this.paper = paper;
        this.setTitle(paper.getTitle());
        this.setCollapsed(true);
        final Paper p = paper;
        JEditorPane pane = new JEditorPane();
        pane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayMaster.citationListClicked(p);
            }
        });
        pane.setText("<html>Hello</html>");
        this.add(pane);

    }
  
    public void setDisplayMaster(DisplayMaster displayMaster) {
        this.displayMaster = displayMaster;
    }

    public DisplayMaster getDisplayMaster() {
        return displayMaster;
    }
    
    private DisplayMaster displayMaster;
    private Paper paper;
}
