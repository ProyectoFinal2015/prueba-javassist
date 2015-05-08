package org.uqbar.wollokui.domain;

public class GameBoard {
	private boolean modified;

	public GameBoard(){
		this.setModified(false);
	}
	
	public void updateGameBoard(Object anObject){
		this.setModified(true);
	}
	
	//Getters & Setters
	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}
}
