package citalyser.api;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class Author{
        private String name;
        private double no_cites_per_paper;
        private double no_cites_per_year;
        private PaperCollection paper_collection;
        
        public Author(String name){
            this.paper_collection = new PaperCollection();
            this.name = name;
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
        private void showstats(){
            ArrayList<Paper> paperList;
            Set<Integer> years = new TreeSet<>();
            int total_cites = 0;
            paperList = this.paper_collection.getPapers();
            for (Paper paper : paperList) {
                total_cites += paper.getCites();
                years.add(paper.getYear());
            }
            no_cites_per_paper = (double)total_cites/paperList.size();
            no_cites_per_year = (double)total_cites/years.size();
        }
}