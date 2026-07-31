///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//JAVA_OPTIONS -ea

/**
 * 腐烂的橘子问题解决方案
 * LeetCode 994: https://leetcode.cn/problems/rotting-oranges/description/
 */
record OrangeState(int state, String desc, String flag) {
}

OrangeState EMPTY = new OrangeState(0, "空单元格", "⬜");
OrangeState FRESH = new OrangeState(1, "新鲜橘子", "🟢");
OrangeState ROTTEN = new OrangeState(2, "腐烂橘子", "🟠");

Map<Integer, OrangeState> states = Map.of(
		0, EMPTY,
		1, FRESH,
		2, ROTTEN);

void main(String... args) {
	var solution = new Solution();
	int[][] grid1 = { { 2, 1, 1 }, { 1, 1, 0 }, { 0, 1, 1 } };
	assert solution.orangesRotting(grid1) == 4 : "计算错误";

	System.out.println("\n" + "*".repeat(30) + "\n");

	int[][] grid2 = { { 2, 1, 1 }, { 0, 1, 1 }, { 1, 0, 1 } };
	assert solution.orangesRotting(grid2) == -1 : "计算错误";

}

class Solution {
	private static final int IMPOSSIBLE = -1;
	private static final int[][] DIRECTIONS = {
			{ -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }
	};

	public int orangesRotting(int[][] grid) {
		int step = 0, freshCount = 0;
		int rows = grid.length, columns = grid[0].length;
		Queue<List<Integer>> queue = new ArrayDeque<>();

		// init queue
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (ROTTEN.state() == grid[i][j]) {
					queue.add(List.of(i, j));
				} else if (FRESH.state() == grid[i][j]) {
					freshCount++;
				}
			}
		}

		// 核心处理步骤
		while (!queue.isEmpty()) {
			printGrid(grid);
			for (int i = queue.size(); i > 0; i--) {
				List<Integer> cell = queue.remove();
				int x = cell.get(0), y = cell.get(1);

				// 添加感染的橘子
				for (int[] direction : DIRECTIONS) {
					int newX = x + direction[0];
					int newY = y + direction[1];
					if (isValidPosition(newX, newY, grid) &&
							grid[newX][newY] == FRESH.state()) {
						grid[newX][newY] = ROTTEN.state();
						queue.add(List.of(newX, newY));
						freshCount--;
					}
				}
			}
			// 仍然有值代表感染了
			if (!queue.isEmpty()) {
				step += 1;
			}
		}
		return freshCount == 0 ? step : IMPOSSIBLE;
	}

	public boolean isValidPosition(int x, int y, int[][] grid) {
		return x >= 0 && x < grid.length &&
				y >= 0 && y < grid[0].length;
	}

	public void printGrid(int[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				var v = grid[i][j];
				System.out.print("%s ".formatted(states.get(v).flag()));
			}
			System.out.println();
		}
		System.out.println("-".repeat(30));
	}
}
