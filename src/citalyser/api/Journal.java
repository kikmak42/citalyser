package citalyser.api;

import java.util.*;

public class Journal extends PaperCollection{
	private String name;

	public Journal(String name){
		super();
		this.name = new String(name);
	}

	public String getName(){
		return this.name;
	}
	public void setName(String s){
		name = new String(s);
	}
}
