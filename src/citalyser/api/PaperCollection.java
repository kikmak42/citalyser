package citalyser.api;

import java.util.*;

public class PaperCollection{
	private int h_index;
	private int i_index;
	private ArrayList<Paper> papers;

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
