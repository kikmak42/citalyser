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
import java.util.*;

public class Paper implements Serializable {

    private String info;
    private String url;
    private String cited_by_url;
    private String title;
    private int year;
    private int num_cites;
    private ArrayList<Author> authors;
    private ArrayList<Journal> journals;
    private String abstracttext;
    private ArrayList<Paper> citations;

    public Paper() {
        this.cited_by_url = null;
        this.url = null;
        this.title = null;
        this.num_cites = 0;
        this.authors = null;
        this.journals = null;
        this.citations = null;
        this.abstracttext = null;
    }

    public Paper(String t) {
        this.url = null;
        this.cited_by_url = null;
        this.title = t;
        this.num_cites = 0;
        this.authors = null;
        this.journals = null;
        this.citations = null;
        this.abstracttext = null;
    }

    public String getcitedByUrl() {
        return this.cited_by_url;
    }

    public void setCitedByUrl(String t) {
        this.cited_by_url = t;
    }
    
    
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String t) {
        this.url = t;
    }
    
       
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String t) {
        this.title = t;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int y) {
        this.year = y;
    }

    public int getNumCites() {
        return this.num_cites;
    }

    public void setNumCites(int c) {
        this.num_cites = c;
    }

    public ArrayList<Author> getAuthors() {
        return this.authors;
    }

    public void setAuthors(ArrayList<Author> l) {
        this.authors = new ArrayList<>(l);
    }

    public ArrayList<Journal> getJournals() {
        return this.journals;
    }

    public void setJournals(ArrayList<Journal> l) {
        this.journals = new ArrayList<>(l);
    }

    public ArrayList<Paper> getCitations() {
        return this.citations;
    }

    public void setCitations(ArrayList<Paper> p) {
        this.citations = new ArrayList<>(p);
    }

    public String getAbstract() {
        return this.abstracttext;
    }

    public void setAbstract(String t) {
        this.abstracttext = t;
    }

    @Override
    public String toString() {
        String s = "<html>Title : <b>" + title + "</b><br />";
        s += authors +", "
           + year +"<br />";
        s += "Cited by " + num_cites + "<br /><hr />";
        return s;
    }
    
    public String getInfo()
    {
        return this.info;
    }
}