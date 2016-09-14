package com.adaptionsoft.games.uglytrivia;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PlayerTest {

	@Test
	public void whenPlaceMovesWithinThanMaximumPlaces_thenPlaceIsSetToSpecifiedLocation() {
		Player player = new Player("test");
		int test = Player.MAX_PLACES >> 1;
		player.movePlace(test);
		Assert.assertEquals(test, player.getPlace());
	}

	@Test
	public void whenPlaceMovesFurtherThanMaximumPlaces_thenCycleFromHead() {

		Player player = new Player("test");
		int delta = 3;
		int shift = Player.MAX_PLACES + delta;
		player.movePlace(shift);
		Assert.assertEquals(delta - 1, player.getPlace());
	}

}
