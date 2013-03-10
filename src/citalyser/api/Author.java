/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citalyser.api;

/**
 *
 * @author rohan
 */
import java.util.*;
import java.util.Iterator;

public class Author extends PaperCollection{
	private String name;
        private double no_cites_per_paper;
        private double no_cites_per_year;
	public Author(String name){
            super();
            this.name = name;
	}

	public String getName(){
            return this.name;
	}
	public void setName(String s){
            name = s;
	}
        public void showstats(){
            ArrayList<Paper> paperList;
            Set<Integer> years = new TreeSet<>();
            int total_cites = 0;
            paperList = super.papers;
            for (Paper paper : paperList) {
                total_cites += paper.getCites();
                years.add(paper.getYear());
            }
            no_cites_per_paper = total_cites/paperList.size();
            no_cites_per_year = total_cites/years.size();
        }
        public double getCitesPerYear(){
            showstats();
            return no_cites_per_year;
        }
        public double getCitesPerPaper(){
            showstats();
            return no_cites_per_paper;
        }
}