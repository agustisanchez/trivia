package com.adaptionsoft.games.uglytrivia;

public class Player {

	public static final int MAX_PLACES = 11;
	private String name;
	private int place = 0;
	private int purse = 0;
	private boolean inPenaltyBox = false;
	private boolean isGettingOutOfPenaltyBox = false;

	public Player(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void movePlace(int shift) {
		if (place + shift > MAX_PLACES) {
			place += shift - MAX_PLACES - 1;
		} else {
			place += shift;
		}
	}

	public int getPlace() {
		return place;
	}

	public void incrementPurse() {
		purse++;
	}

	public int getPurse() {
		return purse;
	}

	public void setInPenaltyBox() {
		inPenaltyBox = true;
	}

	public boolean isInPenaltyBox() {
		return inPenaltyBox;
	}

	public void setGettingOutOfPenaltyBox(boolean isGettingOutOfPenaltyBox) {
		this.isGettingOutOfPenaltyBox = isGettingOutOfPenaltyBox;
	}

	public boolean isGettingOutOfPenaltyBox() {
		return isGettingOutOfPenaltyBox;
	}

	@Override
	public String toString() {
		return name;
	}

}
