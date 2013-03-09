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
        /**
     * Returns The Maximum cited paper from the collection
     * 
     * @param none
     * 
     * @return A Paper Object
     */
        public Paper maximumCitedPaper(){
            int i,size;
            int max=papers.get(0).getCites();
            Paper paper=papers.get(0);
            size=papers.size();
            for(i=0;i<size;i++){
                if(papers.get(i).getCites()>max){
                    max=papers.get(i).getCites();
                    paper=papers.get(i);
                }
            }
            return paper;
        }
        /**
        * Finds top ten cited papers of a year
        * 
        * @param Required Year
        *            
        *
        * @return A Paper ArrayList
        */
        public ArrayList<Paper> topTenForAYear(int year){
            ArrayList<Paper> retval=new ArrayList<Paper>();
            ArrayList<Paper> paperOfYear=extractPaperByCitation(year,year);
            int i=0,size,max1,max2,curmax,j,k;
            size=paperOfYear.size();
            max1=0;max2=9999999;curmax=-1;j=0;k=0;
        if(size>10){
            ArrayList<Integer> index =new ArrayList<Integer>();
            while(j<10){
                i=0;
            while(i<size){
                if(max1<paperOfYear.get(i).getCites() && max2>paperOfYear.get(i).getCites()){
                    if(!isThere(i,index)){
                    max1=paperOfYear.get(i).getCites();
                    curmax=i;
                }
                }
                i++;
            }
            index.add(curmax);
            retval.add(paperOfYear.get(curmax));
            max1=0;max2=paperOfYear.get(curmax).getCites();
            j++;
        }
        }else{
            i=0;
            while(i<size){
                retval.add(paperOfYear.get(i));
                i++;
            }
        }
        return retval;
     }
        /**
         *  Function used by topTenInAYear
         * @param integer
         * @param integer array
         * @return true if integer exist in integer array
         */
        private boolean isThere(int k,ArrayList<Integer> index){
        int size=index.size();
        int i=0;
        while(i<size){
            if(index.get(i)==k){
                return true;
            }
            i++;
        }
        return false;
    }
                //maxcited paper in a year
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
