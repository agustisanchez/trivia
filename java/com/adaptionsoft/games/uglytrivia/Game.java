package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
	private static final int WINNING_PURSE = 6;

	public static final int QUESTIONS_SIZE = 50;

	List<Player> players = new ArrayList<>();

	LinkedList<String> popQuestions = new LinkedList<>();
	LinkedList<String> scienceQuestions = new LinkedList<>();
	LinkedList<String> sportsQuestions = new LinkedList<>();
	LinkedList<String> rockQuestions = new LinkedList<>();

	int currentPlayerIndex = 0;

	public Game() {
		for (int i = 0; i < QUESTIONS_SIZE; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
		}
	}

	public String createRockQuestion(int index) {
		return "Rock Question " + index;
	}

	/**
	 * Return true if the game is playable.
	 * 
	 * @return true if the game is playable.
	 */
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {

		players.add(new Player(playerName));

		System.out.println(playerName + " was added");
		System.out.println("They are player number " + players.size());
		return true;
	}

	// TODO review
	public boolean remove(String playerName) {
		players.remove(howManyPlayers());
		return true;
	}

	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		// TODO global??
		Player currentPlayer = currentPlayer();
		System.out.println(currentPlayer + " is the current player");
		System.out.println("They have rolled a " + roll);

		if (currentPlayer.isInPenaltyBox()) {
			if (roll % 2 != 0) {
				currentPlayer.setGettingOutOfPenaltyBox(true);

				System.out.println(currentPlayer + " is getting out of the penalty box");

				currentPlayer.movePlace(roll);
				System.out.println(currentPlayer + "'s new location is " + currentPlayer.getPlace());
				System.out.println("The category is " + currentCategory());

				askQuestion();
			} else {
				System.out.println(currentPlayer + " is not getting out of the penalty box");
				currentPlayer.setGettingOutOfPenaltyBox(true);
			}

		} else {

			currentPlayer.movePlace(roll);
			System.out.println(currentPlayer + "'s new location is " + currentPlayer.getPlace());
			System.out.println("The category is " + currentCategory());

			askQuestion();
		}

	}

	private void askQuestion() {
		if (currentCategory() == "Pop")
			System.out.println(popQuestions.removeFirst());
		if (currentCategory() == "Science")
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory() == "Sports")
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory() == "Rock")
			System.out.println(rockQuestions.removeFirst());
	}

	// randomly return a category
	private String currentCategory() {
		int place = currentPlayer().getPlace();
		switch (place) {
		case 0:
		case 4:
		case 8:
			return "Pop";
		case 1:
		case 5:
		case 9:
			return "Science";
		case 2:
		case 6:
		case 10:
			return "Sports";
		default:
			return "Rock";
		}
	}

	public boolean wasCorrectlyAnswered() {
		Player currentPlayer = currentPlayer();
		if (currentPlayer.isInPenaltyBox()) {
			if (currentPlayer.isGettingOutOfPenaltyBox()) {
				System.out.println("Answer was correct!!!!");
				currentPlayer.incrementPurse();
				System.out.println(currentPlayer + " now has " + currentPlayer.getPurse() + " Gold Coins.");

				boolean winner = didPlayerWin();
				nextPlayer();

				return winner;
			} else {
				nextPlayer();
				return true;
			}

		} else {

			System.out.println("Answer was corrent!!!!");
			currentPlayer.incrementPurse();
			System.out.println(currentPlayer + " now has " + currentPlayer.getPurse() + " Gold Coins.");

			boolean winner = didPlayerWin();
			nextPlayer();

			return winner;
		}
	}

	private void nextPlayer() {
		currentPlayerIndex++;
		if (currentPlayerIndex == players.size())
			currentPlayerIndex = 0;
	}

	public boolean wrongAnswer() {
		Player currentPlayer = currentPlayer();
		System.out.println("Question was incorrectly answered");
		System.out.println(currentPlayer + " was sent to the penalty box");
		currentPlayer.setInPenaltyBox();

		nextPlayer();
		return true;
	}

	/**
	 * Tells if the last player won.
	 */
	private boolean didPlayerWin() {
		return !(currentPlayer().getPurse() == WINNING_PURSE);
	}

	private Player currentPlayer() {
		return players.get(currentPlayerIndex);
	}
}
