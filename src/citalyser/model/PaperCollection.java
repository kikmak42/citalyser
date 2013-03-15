/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.model;

/**
 *
 * @author rohan
 */
import java.util.ArrayList;
import java.util.Collections;

public class PaperCollection {

    private int h_index;
    private int i_index;
    private ArrayList<Paper> papers;

    public PaperCollection() {
        this.h_index = 0;
        this.i_index = 0;
        this.papers = null;
    }

    public int getHIndex() {
        return this.h_index;
    }

    public void setHIndex(int idx){
        this.h_index = idx;
    }
    
    public int getIIndex() {
        return this.i_index;
    }

    public void setIIndex(int idx){
        this.i_index = idx;
    }
    
    public ArrayList<Paper> getPapers(){
        return this.papers;
    }
    
    public void setPapers(ArrayList<Paper> p) {
        this.papers = new ArrayList<>(p);
    }
    
    public void addPaper(Paper p){
        this.papers.add(p);
    }
    
    public void removePaper(){
        // TODO
    }
    
    public ArrayList<Paper> extractPaperByYear(int low, int high) {
        ArrayList<Paper> retval = new ArrayList<>();
        int i, size;
        size = papers.size();
        for (i = 0; i < size; i++) {
            if (papers.get(i).getYear() >= low && papers.get(i).getYear() <= high) {
                retval.add(papers.get(i));
            }
        }
        return retval;
    }

    public ArrayList<Paper> extractPaperByCitation(int low, int high) {
        ArrayList<Paper> retval = new ArrayList<>();
        int i, size;
        size = papers.size();
        for (i = 0; i < size; i++) {
            if (papers.get(i).getNumCites() >= low && papers.get(i).getNumCites() <= high) {
                retval.add(papers.get(i));
            }
        }
        return retval;
    }

    /**
     * Returns The Maximum cited paper from the collection
     *
     * @param none
     *
     * @return A Paper Object
     */
    public Paper maximumCitedPaper() {
        int i, size;
        int max = papers.get(0).getNumCites();
        Paper paper = papers.get(0);
        size = papers.size();
        for (i = 0; i < size; i++) {
            if (papers.get(i).getNumCites() > max) {
                max = papers.get(i).getNumCites();
                paper = papers.get(i);
            }
        }
        return paper;
    }

    /**
     * Finds top ten cited papers of a year
     *
     * @param Required Year
     *
     *
     * @return A Paper ArrayList
     */
    public ArrayList<Paper> topTenForAYear(int year) {
        ArrayList<Paper> retval = new ArrayList<>();
        ArrayList<Paper> paperOfYear = extractPaperByCitation(year, year);
        int i = 0, size, max1, max2, curmax, j;
        size = paperOfYear.size();
        max1 = 0;
        max2 = 9999999;
        curmax = -1;
        if (size > 10) {
            ArrayList<Integer> index = new ArrayList<>();
            j = 0;
            while (j < 10) {
                i = 0;
                while (i < size) {
                    if (max1 < paperOfYear.get(i).getNumCites() && max2 > paperOfYear.get(i).getNumCites()) {
                        if (!isThere(i, index)) {
                            max1 = paperOfYear.get(i).getNumCites();
                            curmax = i;
                        }
                    }
                    i++;
                }
                index.add(curmax);
                retval.add(paperOfYear.get(curmax));
                max1 = 0;
                max2 = paperOfYear.get(curmax).getNumCites();
                j++;
            }
        } else {
            i = 0;
            while (i < size) {
                retval.add(paperOfYear.get(i));
                i++;
            }
        }
        return retval;
    }

    public void calcHIndex() {
        ArrayList<Integer> list = new ArrayList<>();
        int size = this.papers.size();

        int i;
        for (i = 0; i < size; i++) {
            list.add(this.papers.get(i).getNumCites());
        }

        Collections.sort(list);
        Collections.reverse(list);

        int h;
        for (h = 0; h < size; h++) {
            if (list.get(h) < h + 1) {
                this.h_index = h;
            } else {
                break;
            }
        }
    }

    public void calcIIndex() {
        // This is the code for calculating i10 index
        int size = papers.size();

        this.i_index = 0;
        int i;

        for (i = 0; i < size; i++) {
            if (papers.get(i).getNumCites() >= 10) {
                this.i_index++;
            }
        }
    }
    
    private boolean isThere(int k, ArrayList<Integer> index) {
        int size = index.size();
        int i = 0;
        while (i < size) {
            if (index.get(i) == k) {
                return true;
            }
            i++;
        }
        return false;
    }
    
}
