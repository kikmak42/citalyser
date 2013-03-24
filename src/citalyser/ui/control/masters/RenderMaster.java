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
            if (arrayList.isEmpty()) {
                contentRenderer.displayMessage("<html>Did not find any author search results matching the search query. Try Author Papers Search");
                return;
            }
            for (Author author : arrayList) {
                if (Thread.interrupted()) {
                    break;
                }
                contentRenderer.getGridDisplayPanel().addGridEntityPanel(new GridEntityPanel(author));
            }
            contentRenderer.flipToGridDisplayPanel();
        } else {
            contentRenderer.displayMessage("<html>Did not find any author search results matching the search query. Try Author Papers Search");
        }
    }

    public void renderJournalMetrics(ContentRenderer contentRenderer, ArrayList<Journal> arrayList) {
        if (arrayList != null) {
            //contentRenderer.clearAll();
            if (arrayList.isEmpty()) {
                contentRenderer.displayMessage("Could not fetch journals list");
                return;
            }
            contentRenderer.getTableDisplayPanel().setJournalTable(arrayList);
            contentRenderer.flipToTableDisplayPanel();
        } else {
            contentRenderer.displayMessage("Could not fetch journals list");
        }
    }

    public void render(ContentRenderer contentRenderer, Author author) {
        if (author != null) {
            //contentRenderer.clearAll();
            contentRenderer.getTableDisplayPanel().setTable(author.getPaperCollection());
            contentRenderer.flipToTableDisplayPanel();
        } else {
            contentRenderer.displayMessage("Could not fetch author papers result");
        }
    }

    public void render(ContentRenderer contentRenderer, PaperCollection paperCollection) {
        if (paperCollection != null) {
            //contentRenderer.clearAll();
            if (paperCollection.getPapers() == null) {
                contentRenderer.displayMessage("Could not fetch papers result");
            } else {
                if (paperCollection.getPapers().isEmpty()) {
                    contentRenderer.displayMessage("Could not fetch papers result");
                } else {
                    contentRenderer.getTableDisplayPanel().setTable(paperCollection);
                    contentRenderer.flipToTableDisplayPanel();
                }
            }
        } else {
            //TODO: Need to call api back
            contentRenderer.displayMessage("Could not fetch papers result");
        }
    }

    public void renderCitationsList(ContentRenderer contentRenderer, ArrayList<Paper> papers) {
        if (papers != null) {
            //contentRenderer.clearAll();
            /*
             contentRenderer.getListDisplayPanel().setList(papers,ListModelHandler.getListModel(papers));
             contentRenderer.flipToListDisplayPanel();
             */
            if (papers.isEmpty()) {
                contentRenderer.displayMessage("Citation Count is 0. So No Citations to display.");
            }
            for (Paper p : papers) {
                if (Thread.interrupted()) {
                    break;
                }
                contentRenderer.getCollapsibleListDisplayPanel().addCollapsibleListEntityPanel(new CollapsibleListEntityPanel(p));
            }
            contentRenderer.getCollapsibleListDisplayPanel().addMoreButton();
            contentRenderer.flipToCollapsibleListDisplayPanel();

        } else {
            contentRenderer.displayMessage("Could not fetch citations list");
        }
    }

    public void renderProfile(ContentRenderer contentRenderer, Author author) {
        if (author != null) {
            contentRenderer.clearAll();
            contentRenderer.getProfileDisplayPanel().displayAuthorProfile(author);
            contentRenderer.flipToProfileDisplayPanel();
        } else {
            contentRenderer.displayMessage("Could not fetch author profile");
        }
    }

    public void renderJournal(ContentRenderer contentRenderer, PaperCollection papercollection) {
        if (papercollection != null) {
            contentRenderer.clearAll();
            if (papercollection.getPapers() == null) {
                contentRenderer.displayMessage("Could not fetch journal result");
            } else {
                if (papercollection.getPapers().isEmpty()) {
                    contentRenderer.displayMessage("Could not fetch journal result");
                } else {
                    contentRenderer.getProfileDisplayPanel().displayJournalProfile(papercollection);
                    contentRenderer.flipToProfileDisplayPanel();
                }
            }
        } else {
            contentRenderer.displayMessage("Could not fetch journal result");
        }
    }

    public void renderJournalPaperCollection(ContentRenderer contentRenderer, PaperCollection paperCollection) {
        if (paperCollection != null) {
            //contentRenderer.clearAll();
            contentRenderer.getTableDisplayPanel().setTable(paperCollection, true);
            contentRenderer.flipToTableDisplayPanel();
        } else {
            //TODO: Need to call api back
            contentRenderer.displayMessage("Could not fetch papers result");
        }
    }
}
