///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

record OrangeState(int state, String desc, String flag) {
}

class Solution {

	static final OrangeState EMPTY = new OrangeState(0, "空单元格", "⬜");
	static final OrangeState FRESH = new OrangeState(1, "新鲜橘子", "🟢");
	static final OrangeState ROTTEN = new OrangeState(2, "腐烂橘子", "🟠");

	static final Map<Integer, OrangeState> states = Map.of(
			0, EMPTY,
			1, FRESH,
			2, ROTTEN);

	public int orangesRotting(int[][] grid) {
		final int IMPOSSIABLE = -1;
		int step = 0, emptyCount = 0;
		int rows = grid.length, columns = grid[0].length;
		Queue<List<Integer>> queue = new ArrayDeque<>();

		BiConsumer<Integer, Integer> doOrangeRotting = (x, y) -> {
			if (x > -1 && x < rows && y > -1 && y < columns && grid[x][y] == FRESH.state()) {
				grid[x][y] = ROTTEN.state();
				queue.add(List.of(x, y));
			}
		};

		// init queue
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (ROTTEN.state() == grid[i][j]) {
					queue.add(List.of(i, j));
				}
				if (EMPTY.state() == grid[i][j]) {
					emptyCount++;
				}
			}
		}


        while(!queue.isEmpty()){
            printGrid(grid);
            // 核心处理步骤
            for (int i = queue.size(); i > 0; i--) {
                List<Integer> element = queue.remove();
                int x = element.get(0), y = element.get(1);

                // 添加感染的橘子
                // 上下左右
                doOrangeRotting.accept(x - 1, y);
                doOrangeRotting.accept(x + 1, y);
                doOrangeRotting.accept(x, y - 1);
                doOrangeRotting.accept(x, y + 1);
            }

            step += 1;
        }
		
		return 1;
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

void main(String... args) {
	int[][] grid = { { 2, 1, 1 }, { 1, 1, 0 }, { 0, 1, 1 } };
	new Solution().orangesRotting(grid);
}
