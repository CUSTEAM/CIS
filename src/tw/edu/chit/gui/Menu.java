package tw.edu.chit.gui;

import java.util.ArrayList;
import java.util.List;

public class Menu {

	private ArrayList items = new ArrayList();
		
	public void addItem(MenuItem aItem) {
		items.add(aItem);
	}
		
	public List getItems() {
		return items;
	}
	
	public MenuItem getItem(int i) {
		return (MenuItem)items.get(i);
	}
}
