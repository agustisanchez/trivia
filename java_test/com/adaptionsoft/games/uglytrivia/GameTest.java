package com.adaptionsoft.games.uglytrivia;

import java.util.Random;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class GameTest {

	@Test
	public void whenMoreThanSixPlayersAreAdded_thenNoError() {

		Game game = new Game();
		for (int i = 0; i < 10; i++) {
			game.add("Player " + i);
		}

		play(game);

	}

	@Test
	public void whenALotMoreThanSixPlayersAreAdded_thenNoError() {

		Game game = new Game();
		for (int i = 0; i < Game.QUESTIONS_SIZE; i++) {
			game.add("Player " + i);
		}

		play(game);

	}

	@Test
	public void whenGameCreated_thenAllCategoriesAvailable() {

		Game game = new Game();
		Set<String> categories = game.getCategories();
		Assert.assertTrue(categories.contains("Pop"));
		Assert.assertTrue(categories.contains("Rock"));
		Assert.assertTrue(categories.contains("Science"));
		Assert.assertTrue(categories.contains("Sports"));
	}

	private void play(Game game) {
		Random rand = new Random();

		boolean notAWinner;
		do {

			game.roll(rand.nextInt(5) + 1);

			if (rand.nextInt(9) == 7) {
				notAWinner = game.wrongAnswer();
			} else {
				notAWinner = game.wasCorrectlyAnswered();
			}

		} while (notAWinner);
	}
}
