/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.ui.model;

import citalyser.model.Paper;
import java.util.ArrayList;

/**
 *
 * @author kaushik
 */
public class CitationListHistory {

    private ArrayList<Paper> papers;
    private int currentPaperPosition;

    public CitationListHistory() {
        this.currentPaperPosition = 0;
        papers = new ArrayList<>();
    }

    public void addPaper(Paper paper) {
        papers.add(paper);
    }

    public Paper getCurrentPaper() {
        return this.papers.get(currentPaperPosition);
    }

    public Paper gotoNextPaper() {
        currentPaperPosition++;
        return this.papers.get(currentPaperPosition);
    }

    public Paper gotoPreviousPaper() {
        currentPaperPosition--;
        return this.papers.get(currentPaperPosition);
    }

    public Paper getPaperAtPosition(int position) {
        return this.papers.get(position);
    }

    public void clear() {
        this.papers.clear();
    }
}
