/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.control.masters;

import citalyser.Main;
import citalyser.model.Author;
import citalyser.model.Journal;
import citalyser.model.Paper;
import citalyser.model.PaperCollection;
import citalyser.ui.model.ContentRenderer;
import citalyser.ui.model.TableModelHandler;
import citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.griddisplaypanel.GridEntityPanel;
import citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.listdisplaypanel.CollapsibleListEntityPanel;
import java.util.ArrayList;

/**
 *
 * @author Tanmay Patil
 */
public class RenderMaster {
    
    public void render(ContentRenderer contentRenderer, ArrayList<Author> arrayList) {
        if (arrayList != null) {
            //contentRenderer.clearAll();
            for (Author author : arrayList) {
                contentRenderer.getGridDisplayPanel().addGridEntityPanel(new GridEntityPanel(author));
            }
            contentRenderer.flipToGridDisplayPanel();
        } else {
            Main.getDisplayController().displayErrorMessage("Null Author List");
        }
    }

    public void renderJournalMetrics(ContentRenderer contentRenderer, ArrayList<Journal> arrayList) {
        if (arrayList != null) {
            contentRenderer.clearAll();
            contentRenderer.getTableDisplayPanel().setJournalMetricsTable(arrayList, TableModelHandler.getTableModel(arrayList));
            contentRenderer.flipToTableDisplayPanel();
        } else {
            Main.getDisplayController().displayErrorMessage("Null Journal List");
        }
    }

    public void render(ContentRenderer contentRenderer, Author author) {
        if (author != null) {
            contentRenderer.clearAll();
            contentRenderer.getTableDisplayPanel().setTable(author.getPaperCollection(), TableModelHandler.getTableModel(author.getPaperCollection()));
            contentRenderer.flipToTableDisplayPanel();
        } else {
            Main.getDisplayController().displayErrorMessage("Null Author");
        }
    }

    public void render(ContentRenderer contentRenderer, PaperCollection paperCollection) {
        if (paperCollection != null) {
            contentRenderer.clearAll();
            contentRenderer.getTableDisplayPanel().setTable(paperCollection, TableModelHandler.getTableModel(paperCollection));
            contentRenderer.flipToTableDisplayPanel();
        } else {
            //TODO: Need to call api back
            Main.getDisplayController().displayErrorMessage("Null Paper Collection");
        }
    }

    public void renderCitationsList(ContentRenderer contentRenderer, ArrayList<Paper> papers) {
        if (papers != null) {
            contentRenderer.clearAll();
            /*
             contentRenderer.getListDisplayPanel().setList(papers,ListModelHandler.getListModel(papers));
             contentRenderer.flipToListDisplayPanel();
             */
            for (Paper p : papers) {
                contentRenderer.getCollapsibleListDisplayPanel().addCollapsibleListEntityPanel(new CollapsibleListEntityPanel(p));
            }
            contentRenderer.flipToCollapsibleListDisplayPanel();

        } else {
            Main.getDisplayController().displayErrorMessage("Null Citations List");
        }
    }

    public void renderProfile(ContentRenderer contentRenderer, Author author) {
        if (author != null) {
            contentRenderer.clearAll();
            contentRenderer.getProfileDisplayPanel().displayAuthorProfile(author);
            contentRenderer.flipToProfileDisplayPanel();
        } else {
            Main.getDisplayController().displayErrorMessage("Null Author");
        }
    }
    
    public void renderJournal(ContentRenderer contentRenderer, PaperCollection papercollection) {
        if (papercollection != null) {
            contentRenderer.getProfileDisplayPanel().displayJournalProfile(papercollection);
            contentRenderer.flipToProfileDisplayPanel();
        } else {
            Main.getDisplayController().displayErrorMessage("Null Journal");
        }
    }
}
