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
        if(currentPaperPosition==papers.size()){
        currentPaperPosition++;
        papers.add(paper);
        }
        else
        {
            for(int i = currentPaperPosition ;i<=papers.size(); i++)
            {
                papers.remove(papers.size()-1);
            }
         papers.add(paper);
         currentPaperPosition++;
        }
    }

    public Paper getCurrentPaper() {
        return this.papers.get(currentPaperPosition);
    }

    public Paper gotoNextPaper() {
        if(currentPaperPosition < this.papers.size())
        {
            currentPaperPosition++;
            return this.papers.get(currentPaperPosition-1);
        }
        return this.papers.get(currentPaperPosition-1);
    }

    public Paper gotoPreviousPaper() 
    {
        if(currentPaperPosition > 1)
        {
            currentPaperPosition--;
            return this.papers.get(currentPaperPosition-1);
        }
        return this.papers.get(currentPaperPosition-1);
    }

    public Paper getPaperAtPosition(int position) {
        return this.papers.get(position-1);
    }

    public void clear() {
        this.papers.clear();
    }

    public void printPapers() {
        for (int i = 0; i < papers.size(); i++) {
            System.out.println(papers.get(i).getTitle());
            System.out.println(currentPaperPosition);
        }

    }

    public int getCurrentPosition() {
        return currentPaperPosition;
    }
}
