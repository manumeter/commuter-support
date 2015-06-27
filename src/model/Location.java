package ch.zhaw.mas8i.pendlersupport.model;

import java.io.Serializable;

/**
 * Location can be a Train, Bus, Ship or whatever station.
 */
public class Location implements Serializable {

	private String name;
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor needs a string do identify the location.
	 * 
	 * @param name Name to set
	 */
	public Location(String name) {
		setName(name);
	}
	
	/**
	 * @return Current name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name Name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
