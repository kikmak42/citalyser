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

public class PaperCollection{
	protected int h_index;
	protected int i_index;
	protected ArrayList<Paper> papers;

	public PaperCollection(){
		this.h_index = 0;
		this.i_index = 0;
		this.papers = null;
	}

	public int getHindex(){
		return this.h_index;
	}
	public int getIindex(){
		return this.i_index;
	}
	public ArrayList<Paper> extractPaperByYear(int low, int high){
		ArrayList<Paper> retval = new ArrayList<Paper>(); 
		int i, size;
		size = papers.size();
		for(i=0; i<size; i++){
			if(papers.get(i).getYear() >= low && papers.get(i).getYear() <= high){
				retval.add(papers.get(i));
			}
		}
		return retval;
	}
	public ArrayList<Paper> extractPaperByCitation(int low, int high){
		ArrayList<Paper> retval = new ArrayList<Paper>(); 
		int i, size;
		size = papers.size();
		for(i=0; i<size; i++){
			if(papers.get(i).getCites() >= low && papers.get(i).getCites() <= high){
				retval.add(papers.get(i));
			}
		}
		return retval;
	}
	public void setPapers(ArrayList<Paper> p){
		this.papers = new ArrayList<Paper>(p);
	}
	private void calcHIndex(){
		// TODO Implement the algo
	}
	private void calcIIndex(){
		// TODO Implement the algo		
	}
}