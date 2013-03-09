package citalyser.api;

import java.util.*;

class CompareYear implements Comparator<Paper>{
	public int compare(Paper p1,Paper p2){
		return p1.getYear()-p2.getYear();
	}
}

class CompareCite implements Comparator<Paper>{
	public int compare(Paper p1,Paper p2){
		return p1.getCites()-p2.getCites();
	}
}
