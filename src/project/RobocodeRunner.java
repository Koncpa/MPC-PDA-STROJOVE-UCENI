package project;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;

public class RobocodeRunner {

	private static String robots = "project.Robot*, sample.Walls";

	public static void main(String[] args) {
		RobocodeEngine engine = new RobocodeEngine();

		engine.addBattleListener(new BattleObserver());

		engine.setVisible(true);

		BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600);

		RobotSpecification[] selectedRobots = engine.getLocalRepository(robots);

		BattleSpecification battleSpec = new BattleSpecification(5, battlefield, selectedRobots);

		engine.runBattle(battleSpec, true);

		engine.close();
	}
}
