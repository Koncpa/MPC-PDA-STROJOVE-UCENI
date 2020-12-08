package project;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;

public class RobocodeRunner {

	private static String robots = "project.Collector*, sample.Walls";

	public static void main(String[] args) {
		System.setProperty("NOSECURITY", "true");

		RobocodeEngine engine = new RobocodeEngine();

		engine.addBattleListener(new BattleObserver());

		engine.setVisible(true);

		BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600);

		RobotSpecification[] selectedRobots = engine.getLocalRepository(robots);

		BattleSpecification battleSpec = new BattleSpecification(1000, battlefield, selectedRobots);

		engine.runBattle(battleSpec, true);

		engine.close();

		System.exit(0);
	}
}