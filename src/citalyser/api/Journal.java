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

public class Journal extends PaperCollection{
	private String name;
        private double cites_per_author;
        private double papers_per_author;
	public Journal(String name){
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
            ArrayList<Paper> paperList = super.papers;
            Set<Author> diff_authors = new TreeSet<>();
            int total_cites = 0;
            for (Paper paper : paperList) {
                ArrayList<Author> authors = paper.getAuthors();
                diff_authors.addAll(authors);
                total_cites += paper.getCites();
            }
            cites_per_author = total_cites/diff_authors.size();
            papers_per_author = paperList.size()/diff_authors.size();
        }
        public double get_cites_per_author(){
            showstats();
            return cites_per_author;
        }
        public double get_papers_per_author(){
            showstats();
            return papers_per_author;
        }
}