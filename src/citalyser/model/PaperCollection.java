/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.model;

/**
 *
 * @author rohan
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class PaperCollection implements Serializable {

    private int h_index;
    private int i_index;
    private ArrayList<Paper> papers;
    private ArrayList<String> uniqueAuthorList;
    public PaperCollection() {
        this.h_index = 0;
        this.i_index = 0;
        this.papers = null;
    }

    public int getHIndex() {
        this.calcHIndex();
        return this.h_index;
    }

    public void setHIndex(int idx) {
        this.h_index = idx;
    }

    public int getIIndex() {
        this.calcIIndex();
        return this.i_index;
    }

    public void setIIndex(int idx) {
        this.i_index = idx;
    }

    public ArrayList<Paper> getPapers() {
        return this.papers;
    }

    public void setPapers(ArrayList<Paper> p) {
        this.papers = new ArrayList<>(p);
    }

    public void addPaper(Paper p) {
        this.papers.add(p);
    }

    public void removePaper() {
        // TODO
    }

    public ArrayList<String> extractAuthors() {
        ArrayList<Paper> p = this.papers;
        ArrayList<String> author = new ArrayList<>();
        for (Paper paper : p) {
            for (Author auth : paper.getAuthors()) {
                if (!author.contains(auth.getName())) {
                    author.add(auth.getName());
                }
            }
        }
        this.uniqueAuthorList = new ArrayList<>(new HashSet<>(author));
        return uniqueAuthorList;
    }

    public ArrayList<Integer> extractYears() {
        ArrayList<Paper> p = this.papers;
        ArrayList<Integer> year = new ArrayList<>();
        for (Paper paper : p) {
                year.add(paper.getYear());
            }
        ArrayList<Integer> uniqueList = new ArrayList<Integer>(new HashSet<>(year));
        return uniqueList;
    }
    
    public ArrayList<Integer> getAuthorPos(ArrayList<String> auth) {
        ArrayList<Integer> retPos = new ArrayList<>();
        for (String eachAuthor : auth) {
            int i=0;
            for (String au : this.uniqueAuthorList) {
                if(auth.equals(au)) {
                    retPos.add(i+1);
                }
                i++;
            }
        }
        return retPos;
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
            if (list.get(h) >= h + 1) {
                this.h_index = h + 1;
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
            } else {
                break;
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

    public Map<Integer, Integer> getCitationForYear() {
        //TODO:
        int year, cit;
        ArrayList<Paper> p = this.papers;
        HashMap<Integer, Integer> map1 = new HashMap<Integer, Integer>();

        for (Paper paper : p) {
            year = paper.getYear();
            cit = paper.getNumCites();
            map1.get(year);
            if (map1.get(year) == null) {
                map1.put(year, cit);
            } else {
                cit += map1.get(year);
                map1.put(year, cit);
            }
        }

        return map1;

    }

    public float getCitationPerPaper() {
        int size = 0;
        int i = 0;
        size = papers.size();
        float citations_per_paper = 0;
        int total_citations = 0;
        for (i = 0; i < size; i++) {
            total_citations += papers.get(i).getNumCites();
        }
        //TODO:
        try {
            citations_per_paper = total_citations / size;

        } catch (Exception e) {
            citations_per_paper = 0;
        }
        return citations_per_paper;
    }

    public int getTotalNumberOfCitations() {

        int size = 0;
        int i = 0;
        size = papers.size();
        int total_citations = 0;
        for (i = 0; i < size; i++) {
            total_citations += papers.get(i).getNumCites();
        }
        //TODO:
        return total_citations;
    }

    public double getAvgCitationPerYear() {
        int citations = getTotalNumberOfCitations();
        Map<Integer, Integer> map1 = getCitationForYear();
        int yearmin = 99999999, yearmax = 0;
        for (Map.Entry<Integer, Integer> entry : map1.entrySet()) {
            if (yearmin > entry.getKey()) {
                yearmin = entry.getKey();
            }
            if (yearmax < entry.getKey()) {
                yearmax = entry.getKey();
            }
        }
        return citations / ((yearmax - yearmin) + 1);
    }
}
