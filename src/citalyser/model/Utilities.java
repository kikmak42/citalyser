package citalyser.model;

import java.util.*;

class CompareYear implements Comparator<Paper> {

    @Override
    public int compare(Paper p1, Paper p2) {
        return p1.getYear() - p2.getYear();
    }
}

class CompareCite implements Comparator<Paper>{
    @Override
	public int compare(Paper p1,Paper p2){
		return p1.getNumCites()-p2.getNumCites();
	}
}

/**
 * Sets System Proxy
 * @param host=proxy host
 *        port=proxy port
 *        user=username
 *        password
 * 
 * @return null
 * 
 * @author vikassearchPanel.getSearchString()
 */
