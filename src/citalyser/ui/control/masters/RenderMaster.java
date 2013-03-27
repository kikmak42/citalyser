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
import citalyser.model.query.Query;
import citalyser.model.query.QueryType;
import citalyser.ui.model.ContentRenderer;
import citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.ContentDisplayPanel;
import citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.DetailsDisplayPanel;
import citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.griddisplaypanel.GridEntityPanel;
import citalyser.ui.visualization.panels.regulardisplaypanel.datavisualizationpanel.contentdisplaypanel.modules.listdisplaypanel.CollapsibleListEntityPanel;
import java.util.ArrayList;

/**
 *
 * @author Tanmay Patil
 */
public class RenderMaster {
    
    private ContentDisplayPanel contentDisplayPanel;

    public RenderMaster(ContentDisplayPanel contentDisplayPanel) {
        this.contentDisplayPanel = contentDisplayPanel;
    }

    public void render(ContentRenderer contentRenderer, Query query, ArrayList<Author> arrayList) {
        if (arrayList != null) {
            //contentRenderer.clearAll();
            for (Author author : arrayList) {
                if (Thread.interrupted()) {
                    break;
                }
                contentRenderer.getGridDisplayPanel().addGridEntityPanel(new GridEntityPanel(author));
            }
            contentRenderer.flipToGridDisplayPanel();
        } else {
            //contentRenderer.displayMessage("<html>Did not find any author search results matching the search query. Try Author Papers Search");
        }
    }

    public void renderJournalMetrics(ContentRenderer contentRenderer, Query query, ArrayList<Journal> arrayList) {
        //contentRenderer.clearAll();
        int numResults = arrayList.size();
        if (numResults > 0) {
            contentRenderer.getTableDisplayPanel().setJournalTable(arrayList);
            contentRenderer.flipToTableDisplayPanel();
        }
    }

    public void render(ContentRenderer contentRenderer, Query query, Author author) {
        if (author != null) {
            //contentRenderer.clearAll();
            contentRenderer.getTableDisplayPanel().setTable(query,author.getPaperCollection());
            contentRenderer.flipToTableDisplayPanel();
        } else {
            Main.getDisplayController().displayErrorMessage("Unknown Error while fetching Author Profile.");
            //contentRenderer.displayMessage("Could not fetch author papers result");
        }
    }

    public void render(ContentRenderer contentRenderer, Query query, PaperCollection paperCollection) {
        contentRenderer.getTableDisplayPanel().setTable(query,paperCollection);
        contentRenderer.flipToTableDisplayPanel();
        
    }

    public void renderCitationsList(ContentRenderer contentRenderer, Query query, ArrayList<Paper> papers) {
        if (papers == null) {
            return;
        }
        for (Paper p : papers) 
        {
            if (Thread.interrupted()) {
                break;
            }
            contentRenderer.getCollapsibleListDisplayPanel().addCollapsibleListEntityPanel(new CollapsibleListEntityPanel(p));
            contentRenderer.getCollapsibleListDisplayPanel().addMoreButton();
            contentRenderer.flipToCollapsibleListDisplayPanel();
        }

    }

    public void renderAuthorProfile(ContentRenderer contentRenderer, Query query, Author author) {
        contentDisplayPanel.displayDetailsDisplayPanel(true, 0.5);
        if (author != null) {
            contentRenderer.clearAll();
            contentRenderer.getProfileDisplayPanel().displayAuthorProfile(author);
            contentRenderer.flipToProfileDisplayPanel();
        } else {
            Main.getDisplayController().displayErrorMessage("Unknown Error while displaying Author Profile.");
        }
    }

    public void renderJournal(ContentRenderer contentRenderer, Query query, Journal journal) {
        if (journal.getPaperCollection() != null) {
            //contentRenderer.clearAll();
            contentRenderer.getTableDisplayPanel().setTable(query,journal.getPaperCollection(), true);
            contentRenderer.flipToTableDisplayPanel();
        }
    }

    public void renderGeneralProfile(ContentRenderer contentRenderer, Query query, PaperCollection papercollection) {
        contentDisplayPanel.displayDetailsDisplayPanel(true, 0.5);
        contentDisplayPanel.getDetailsDisplayPanel().flipToUpperDetailsDisplayPanel();
        if(query.flag == QueryType.GEN_JOURN){
            contentDisplayPanel.getDetailsDisplayPanel().setNameJounal(true);
        }
        else{
            contentDisplayPanel.getDetailsDisplayPanel().setNameJounal(false);
        }
        if (papercollection != null) {
            contentRenderer.clearAll();
            if (papercollection.getPapers() == null) {
                contentRenderer.displayMessage("Could not fetch journal result");
            } else {
                if (papercollection.getPapers().isEmpty()) {
                    contentRenderer.displayMessage("Could not fetch journal result");
                } else {
                    contentRenderer.getProfileDisplayPanel().displayGeneralProfile(query,papercollection);
                    contentRenderer.flipToProfileDisplayPanel();
                }
            }
        } else {
            contentRenderer.displayMessage("Could not fetch journal result");
        }
    }

    public void renderJournalProfile(ContentRenderer contentRenderer, Query query, Journal journal) {
        if (journal != null) {
            contentRenderer.clearAll();

            contentRenderer.getProfileDisplayPanel().displayJournalProfile(journal);
            contentRenderer.flipToProfileDisplayPanel();
        } else {
            contentRenderer.displayMessage("Could not fetch Journal Details");
        }
    }
}
