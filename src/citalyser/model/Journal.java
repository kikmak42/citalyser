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
import java.util.Set;
import java.util.TreeSet;

public class Journal implements Serializable {
        private String description;
	private String name;
        private String id;
        private double cites_per_author;
        private double papers_per_author;
        private String h5Link;
        private int h5index, h5median;
        private PaperCollection paper_collection;
	public Journal(String name){
            this.description = null;
            this.id = null;
            this.cites_per_author = 0.0;
            this.papers_per_author = 0.0;
            this.paper_collection = null;
            this.name = name;
	}
        public void setH5index(int i){
            this.h5index = i;
        }
        public void setH5median(int i ){
            this.h5median=i;
        }
        public void setH5Link(String s){
            this.h5Link =s;
        }
        public String getH5Link(){
            return this.h5Link;
        }
        public int getH5index(){
            return this.h5index;
        }
        public int getH5median(){
            return this.h5median;
        }
        public String getDescription(){
            return this.description;
	}
	public void setDescription(String s){
            description = s;
	}
        
        public String getId(){
            return this.id;
        }
        public void setId(String s){
            this.id = s;
        }
	public String getName(){
            return this.name;
	}
	public void setName(String s){
            name = s;
	}
        public double getCitesPerAuthor(){
            showstats();
            return cites_per_author;
        }
        public double getPapersPerAuthor(){
            showstats();
            return papers_per_author;
        }
        public PaperCollection getPaperCollection(){
            return this.paper_collection;
        }
        public void setPaperCollection(PaperCollection p){
            this.paper_collection = p;
        }
        private void showstats(){
            ArrayList<Paper> paperList = this.paper_collection.getPapers();
            Set<Author> diff_authors = new TreeSet<>();
            int total_cites = 0;
            for (Paper paper : paperList) {
                ArrayList<Author> authors = paper.getAuthors();
                diff_authors.addAll(authors);
                total_cites += paper.getNumCites();
            }
            cites_per_author = total_cites/diff_authors.size();
            papers_per_author = paperList.size()/diff_authors.size();
        }
        @Override
        public String toString() {
            return "";
            //return "Journal{" + "description=" + description + ", name=" + name + ", id=" + id + ", cites_per_author=" + cites_per_author + ", papers_per_author=" + papers_per_author + ", h5Link=" + h5Link + ", h5index=" + h5index + ", h5median=" + h5median + ", paper_collection=" + paper_collection + '}';
        }
        
        public void appendPaperCollection(PaperCollection pc)
        {
            for(Paper p : pc.getPapers()){
                this.paper_collection.addPaper(p);
            }
                
        }
}