package tw.edu.chit.gui;

import tw.edu.chit.model.Module;

public class MenuItem {

	//private String  name;
	//private String  labelKey;
	//private String  iconKey;
	//private String  actionKey;
	private Module  module;
	private boolean collapse = false;
	private Menu	subMenu  = null;
	
	/*
	public MenuItem(String aName, String aLabelKey, String aIconKey, String aActionKey) {
		name     = aName;
		labelKey = aLabelKey;
		iconKey  = aIconKey;
		actionKey  = aActionKey;
	}
	*/
	
	public MenuItem(Module module) {
		this.module = module;
	}
	
	/*
	public String getName() {
		return name;
	}
	
	public String getLabelKey() {
		return labelKey;
	}

	public String getIconKey() {
		return iconKey;
	}

	public String getActionKey() {
		return actionKey;
	}
	*/
	
	public boolean isCollapse() {
		return collapse;
	}
	
	public Menu getSubMenu() {
		return subMenu;
	}
	
	public void setSubMenu(Menu aSubMenu) {
		subMenu = aSubMenu;
	}
	
	public void setCollapse(boolean aCollapse) {
		collapse = aCollapse;
	}

	public Module getModule() {
		return module;
	}
}
