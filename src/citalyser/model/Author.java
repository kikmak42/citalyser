package citalyser.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author rohan
 */
public class Author implements Serializable {
        private String name;
        private String id;
        private double no_cites_per_paper;
        private double no_cites_per_year;
        private String imgsrc;
        private String proilelink;
        private String univandemail;
        private int totalcitaions;
        private PaperCollection paper_collection;
        private ArrayList<Author> co_authors;
    
    @Override
    public String toString() {
        return name;
    }
        
        public Author(String name){
            this.co_authors=null;
            this.paper_collection = null;
            this.name = name;
            this.imgsrc=null;
            this.proilelink=null;
            this.univandemail=null;
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
            this.name = s;
        }
        public double getCitesPerYear(){
            showstats();
            return no_cites_per_year;
        }
        public double getCitesPerPaper(){
            showstats();
            return no_cites_per_paper;
        }
        public PaperCollection getPaperCollection(){
            return this.paper_collection;
        }
        public void setPaperCollection(PaperCollection p){
            this.paper_collection = p;
        }
        private void showstats(){
            ArrayList<Paper> paperList;
            Set<Integer> years = new TreeSet<>();
            int total_cites = 0;
            paperList = this.paper_collection.getPapers();
            for (Paper paper : paperList) {
                total_cites += paper.getNumCites();
                years.add(paper.getYear());
            }
            no_cites_per_paper = (double)total_cites/paperList.size();
            no_cites_per_year = (double)total_cites/years.size();
        }
        public String getUniversity(){
            return this.univandemail;
        }
        public String getImageSrc(){
            return this.imgsrc;
        }
        public String getProfileLink(){
            return this.proilelink;
        }
        public int getTotalCitations(){
            return this.totalcitaions;
        }
        public void setTotalCitations(int i){
            this.totalcitaions = i;
        }
        public void setUniversity(String s){
            this.univandemail = s;
        }
        public void setImagesrc(String s){
            this.imgsrc = s;
        }
        public void setProfilelink(String s){
            this.proilelink = s;
        }
        public ArrayList<Author> getCoAuthors(){
            return this.co_authors;
        }
        public void setCoAuthors(ArrayList<Author> coauthors){
            this.co_authors = coauthors;
        }
        
        
        
        
}