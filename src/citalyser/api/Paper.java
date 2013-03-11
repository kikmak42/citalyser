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
	private int cites;
	private ArrayList<Author> authors;
	private ArrayList<Journal> journals;  
	private String abstracttext;
        
	public String getTitle(){
		return this.title;
	}
	public void setTitle(String t){
		this.title = new String(t);
	}
	public int getYear(){
		return this.year;
	}
	public void setYear(int y){
		this.year = y;
	}
	public int getCites(){
		return this.cites;
	}
	public void setCites(int c){
		this.cites = c;
	}
	public ArrayList<Author> getAuthors(){
		return this.authors;
	}
	public void setAuthors(ArrayList<Author> l){
		this.authors = new ArrayList<Author>(l);
	}
	public ArrayList<Journal> getJournals(){
		return this.journals;
	}
	public void setJournals(ArrayList<Journal> l){
		this.journals = new ArrayList<Journal>(l);
	}
        public String getAbstract(){
		return this.abstracttext;
	}
	public void setAbstract(String t){
		this.abstracttext = new String(t);
	}
        
}