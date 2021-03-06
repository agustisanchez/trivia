package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Game {
	private static final int WINNING_PURSE = 6;

	public static final int QUESTIONS_SIZE = 50;

	List<Player> players = new ArrayList<>();

	Map<String, LinkedList<String>> categoriesMap = new HashMap<>();
	Map<Integer, String> placesCategoriesMap = new HashMap<>();

	int currentPlayerIndex = 0;

	public Game() {
		initCategories();
	}

	private void initCategories() {
		String[] categories = { "Pop", "Science", "Sports", "Rock" };
		for (String category : categories) {
			LinkedList<String> questions = new LinkedList<>();
			categoriesMap.put(category, questions);
			for (int i = 0; i < QUESTIONS_SIZE; i++) {
				questions.addLast(category + " Question " + i);
			}
		}
		placesCategoriesMap.put(0, "Pop");
		placesCategoriesMap.put(4, "Pop");
		placesCategoriesMap.put(8, "Pop");
		placesCategoriesMap.put(1, "Science");
		placesCategoriesMap.put(5, "Science");
		placesCategoriesMap.put(9, "Science");
		placesCategoriesMap.put(2, "Sports");
		placesCategoriesMap.put(6, "Sports");
		placesCategoriesMap.put(10, "Sports");
		placesCategoriesMap.put(3, "Rock");
		placesCategoriesMap.put(7, "Rock");
		placesCategoriesMap.put(11, "Rock");
	}

	/**
	 * Returns an immutable collection of the available categories.
	 * 
	 * @return
	 */
	public Set<String> getCategories() {
		return Collections.unmodifiableSet(categoriesMap.keySet());
	}

	/**
	 * Returns an immutable collection of the player names.
	 * 
	 * @return
	 */
	public Set<String> getPlayerNames() {
		return players.stream().map(Player::getName).collect(Collectors.toSet());
	}

	/**
	 * Return true if the game is playable.
	 * 
	 * @return true if the game is playable.
	 */
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public void add(String playerName) {

		players.add(new Player(playerName));

		log(playerName + " was added");
		log("They are player number " + players.size());
	}

	/**
	 * Removes player with given name
	 * 
	 * @param playerName
	 *            the player's name
	 * @throws IllegalArgumentException
	 *             if player not found
	 * @throws IllegalStateException
	 *             if more than one player found
	 */
	public void remove(String playerName) {
		List<Player> playersFound = players.stream().filter(p -> p.getName().equals(playerName))
				.collect(Collectors.toList());
		if (playersFound.isEmpty()) {
			throw new IllegalArgumentException("No such player '" + playerName + "'.");
		} else if (playersFound.size() > 1) {
			throw new IllegalStateException(
					"Several players wit name '" + playerName + "' (" + playersFound.size() + ").");
		} else {
			players.remove(playersFound.get(0));
		}
	}

	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		Player currentPlayer = currentPlayer();
		log(currentPlayer + " is the current player");
		log("They have rolled a " + roll);

		if (currentPlayer.isInPenaltyBox()) {
			if (roll % 2 != 0) {
				currentPlayer.setGettingOutOfPenaltyBox(true);

				log(currentPlayer + " is getting out of the penalty box");

				currentPlayer.movePlace(roll);
				log(currentPlayer + "'s new location is " + currentPlayer.getPlace());
				log("The category is " + currentCategory());

				askQuestion();
			} else {
				log(currentPlayer + " is not getting out of the penalty box");
				currentPlayer.setGettingOutOfPenaltyBox(true);
			}

		} else {

			currentPlayer.movePlace(roll);
			log(currentPlayer + "'s new location is " + currentPlayer.getPlace());
			log("The category is " + currentCategory());

			askQuestion();
		}

	}

	private void askQuestion() {
		String category = currentCategory();
		LinkedList<String> categoryQuestions = categoriesMap.get(category);
		if (categoryQuestions.isEmpty()) {
			throw new IllegalStateException("Ran out of questions for category " + category);
		}
		categoryQuestions.removeFirst();
	}

	// randomly return a category
	// TODO No es realmente random porque viene determinado por la posición del
	// jugador. ¿Debe ser independiente de la posición?
	private String currentCategory() {
		int place = currentPlayer().getPlace();
		return placesCategoriesMap.get(place);
	}

	public boolean wasCorrectlyAnswered() {
		Player currentPlayer = currentPlayer();
		if (currentPlayer.isInPenaltyBox()) {
			if (currentPlayer.isGettingOutOfPenaltyBox()) {
				correctAnswerIncrementPurse(currentPlayer);

				boolean winner = didPlayerWin();
				nextPlayer();

				return winner;
			} else {
				nextPlayer();
				return true;
			}

		} else {

			correctAnswerIncrementPurse(currentPlayer);

			boolean winner = didPlayerWin();
			nextPlayer();

			return winner;
		}
	}

	private void correctAnswerIncrementPurse(Player currentPlayer) {
		log("Answer was correct!!!!");
		currentPlayer.incrementPurse();
		log(currentPlayer + " now has " + currentPlayer.getPurse() + " Gold Coins.");
	}

	private void nextPlayer() {
		currentPlayerIndex++;
		if (currentPlayerIndex == players.size())
			currentPlayerIndex = 0;
	}

	public boolean wrongAnswer() {
		Player currentPlayer = currentPlayer();
		log("Question was incorrectly answered");
		log(currentPlayer + " was sent to the penalty box");
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

	private void log(String message) {
		System.out.println(message);
	}
}
