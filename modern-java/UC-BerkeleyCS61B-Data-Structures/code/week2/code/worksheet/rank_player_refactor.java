///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

class Player {
	public double score;

	public Player(double score) {
		this.score = score;
	}

	public static Map<Player, Integer> rankAbove(List<Player> players) {
		var results = new HashMap<Player, Integer>();

		// 复制一份用来排序
		var sortedPlayers = new ArrayList<>(players);
		sortedPlayers.sort((p1, p2) -> Double.compare(p2.score, p1.score));

		for (int i = 0; i < sortedPlayers.size(); i++) {
			results.put(sortedPlayers.get(i), i + 1);
		}
		return results;
	}
}

void main(String... args) {
	Player p1 = new Player(500);
	Player p2 = new Player(800);
	Player p3 = new Player(1200);
	Player p4 = new Player(100);
	List<Player> players = List.of(p1, p2, p3, p4);

	Map<Player, Integer> ret = Player.rankAbove(players);
	for (Map.Entry<Player, Integer> entry : ret.entrySet()) {
		System.out.println("Player with score %.2f : %d".formatted(
				entry.getKey().score, entry.getValue()));
	}
}
