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
import java.lang.*;

public class Paper{
	private String title;
	private int year;
	private int num_cites;
	private ArrayList<Author> authors;
	private ArrayList<Journal> journals;
        private ArrayList<Paper> citations;
        
        public Paper(String t){
            this.title = t;
            this.num_cites = 0;
            this.authors = null;
            this.journals = null;
            this.citations = null;
        }
	
	public String getTitle(){
		return this.title;
	}
	public void setTitle(String t){
		this.title = t;
	}
	public int getYear(){
		return this.year;
	}
	public void setYear(int y){
		this.year = y;
	}
	public int getNumCites(){
		return this.num_cites;
	}
	public void setNumCites(int c){
		this.num_cites = c;
	}
	public ArrayList<Author> getAuthors(){
		return this.authors;
	}
	public void setAuthors(ArrayList<Author> l){
		this.authors = new ArrayList<>(l);
	}
	public ArrayList<Journal> getJournals(){
		return this.journals;
	}
	public void setJournals(ArrayList<Journal> l){
		this.journals = new ArrayList<>(l);
	}
        public ArrayList<Paper> getCitations(){
            return this.citations;
        }
        public void setCitations(ArrayList<Paper> p){
            this.citations = p;
        }
}