/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.listdisplaypanel;

import citalyser.model.Paper;
import citalyser.ui.control.DisplayMaster;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import org.jdesktop.swingx.JXTaskPane;

/**
 *
 * @author kaushik
 */
public class CollapsibleListEntityPanel extends JXTaskPane {
    private PaperDetailsPanel paperPanel;

    public CollapsibleListEntityPanel(Paper paper) {
        this.paper = paper;
        this.setTitle(paper.getTitle());
        this.setCollapsed(true);
        final Paper p = paper;
        paperPanel = new PaperDetailsPanel();
        paperPanel.setPaperTitle(paper.getTitle());
        paperPanel.setPaperAbstract(paper.getAbstract());
        paperPanel.setPaperCitations(paper.getNumCites());
        if (paper.getNumCites() > 0) {
        }
        //paperPanel.setPaperInfo("");
        //paperPanel.setPaperInfo(paper.getInfo);
        this.add(paperPanel);
//        JLabel contentLabel = new JLabel();
//        JLabel titleLabel = new JLabel();
//        String link = "http://www.google.com";
//        titleLabel.setText("<html><a href = ' " + link+" '>"+paper.getTitle()+"</a></html>");
//        /*contentLabel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                displayMaster.citationListClicked(p);
//            }
//        });*/
//        contentLabel.add(titleLabel);
//        //contentLabel.setText(paper.getPrettyText());
//        this.add(contentLabel);

    }

    public void setDisplayMaster(DisplayMaster displayMaster) {
        this.displayMaster = displayMaster;
        paperPanel.setDisplayMaster(displayMaster);
    }

    public DisplayMaster getDisplayMaster() {
        return displayMaster;
    }
    private DisplayMaster displayMaster;
    private Paper paper;
}
