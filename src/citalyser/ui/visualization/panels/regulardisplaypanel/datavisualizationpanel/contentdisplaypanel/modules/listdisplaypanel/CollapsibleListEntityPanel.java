/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.listdisplaypanel;

import citalyser.model.Paper;
import citalyser.ui.control.DisplayMaster;
import java.awt.Dimension;
import javax.swing.JEditorPane;
import org.jdesktop.swingx.JXTaskPane;

/**
 *
 * @author kaushik
 */
public class CollapsibleListEntityPanel extends JXTaskPane {

    public CollapsibleListEntityPanel(Paper paper) {

        this.setTitle(paper.getTitle());
        this.setCollapsed(true);
      //  this.setPreferredSize(new Dimension(20,0));
        this.add(new JEditorPane("text/html", "<html>Hello</html>"));

    }

    public void setDisplayMaster(DisplayMaster displayMaster) {
        this.displayMaster = displayMaster;
    }

    public DisplayMaster getDisplayMaster() {
        return displayMaster;
    }
    private DisplayMaster displayMaster;
}
