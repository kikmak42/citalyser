package citalyser.api;

import java.util.*;

class CompareYear implements Comparator<Paper>{
	public int compare(Paper p1,Paper p2){
		return p1.getYear()-p2.getYear();
	}
}

class CompareCite implements Comparator<Paper>{
	public int compare(Paper p1,Paper p2){
		return p1.getNumCites()-p2.getNumCites();
	}
}

class Query{
    public int flag;
    public String name;
    public int max_year;
    public int min_year;
    public int start_page;
    public int end_page;
}
