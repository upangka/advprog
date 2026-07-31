///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//JAVA_OPTIONS -ea

import java.util.*;

/**
 * 腐烂的橘子问题解决方案
 * LeetCode 994: https://leetcode.com/problems/rotting-oranges/
 */
class Solution {
    
    // ==================== 常量定义 ====================
    private static final int IMPOSSIBLE = -1;
    private static final int[][] DIRECTIONS = {
        {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };
    
    // ==================== 内部枚举 ====================
    enum OrangeState {
        EMPTY(0, "⬜"),
        FRESH(1, "🟢"),
        ROTTEN(2, "🟠");
        
        private final int code;
        private final String symbol;
        
        OrangeState(int code, String symbol) {
            this.code = code;
            this.symbol = symbol;
        }
        
        public int code() { return code; }
        public String symbol() { return symbol; }
        
        public static OrangeState fromCode(int code) {
            for (OrangeState state : values()) {
                if (state.code == code) {
                    return state;
                }
            }
            throw new IllegalArgumentException("Unknown state code: " + code);
        }
    }
    
    // ==================== 主方法 ====================
    public int orangesRotting(int[][] grid) {
        validateGrid(grid);
        
        int rows = grid.length;
        int cols = grid[0].length;
        Queue<int[]> queue = new ArrayDeque<>();
        int freshCount = 0;
        
        // 1. 初始化：收集所有腐烂橘子，并统计新鲜橘子数量
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == OrangeState.ROTTEN.code()) {
                    queue.offer(new int[]{i, j});
                } else if (grid[i][j] == OrangeState.FRESH.code()) {
                    freshCount++;
                }
            }
        }
        
        // 如果没有新鲜橘子，直接返回0
        if (freshCount == 0) {
            return 0;
        }
        
        // 2. BFS 计算腐烂时间
        int minutes = 0;
        while (!queue.isEmpty() && freshCount > 0) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] cell = queue.poll();
                int x = cell[0];
                int y = cell[1];
                
                // 检查四个方向
                for (int[] dir : DIRECTIONS) {
                    int newX = x + dir[0];
                    int newY = y + dir[1];
                    
                    if (isValidPosition(newX, newY, grid) && 
                        grid[newX][newY] == OrangeState.FRESH.code()) {
                        
                        // 感染新鲜橘子
                        grid[newX][newY] = OrangeState.ROTTEN.code();
                        queue.offer(new int[]{newX, newY});
                        freshCount--;
                    }
                }
            }
            minutes++;
        }
        
        // 3. 返回结果
        return freshCount == 0 ? minutes : IMPOSSIBLE;
    }
    
    // ==================== 辅助方法 ====================
    private boolean isValidPosition(int row, int col, int[][] grid) {
        return row >= 0 && row < grid.length && 
               col >= 0 && col < grid[0].length;
    }
    
    private void validateGrid(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            throw new IllegalArgumentException("Grid cannot be null or empty");
        }
    }
    
    // ==================== 打印工具 ====================
    public void printGrid(int[][] grid) {
        for (int[] row : grid) {
            for (int value : row) {
                OrangeState state = OrangeState.fromCode(value);
                System.out.print(state.symbol() + " ");
            }
            System.out.println();
        }
        System.out.println("-".repeat(30));
    }
}

// ==================== 主程序 ====================
void main(String... args) {
    Solution solution = new Solution();
    
    // 测试案例1: 应该需要2分钟
    int[][] grid1 = {
        {2, 1, 1},
        {1, 1, 0},
        {0, 1, 1}
    };
    
    System.out.println("测试案例1:");
    solution.printGrid(grid1);
    int result1 = solution.orangesRotting(grid1);
    System.out.println("结果: " + result1 + " 分钟\n");
    assert result1 == 4 : "测试案例1失败，期望4，实际" + result1;
    
    System.out.println("*".repeat(30) + "\n");
    
    // 测试案例2: 不可能全部腐烂
    int[][] grid2 = {
        {2, 1, 1},
        {0, 1, 1},
        {1, 0, 1}
    };
    
    System.out.println("测试案例2:");
    solution.printGrid(grid2);
    int result2 = solution.orangesRotting(grid2);
    System.out.println("结果: " + result2 + " (不可能全部腐烂)");
    assert result2 == -1 : "测试案例2失败";
    
    // 测试案例3: 已经全部腐烂
    int[][] grid3 = {
        {2, 2, 2},
        {2, 2, 2}
    };
    
    System.out.println("\n" + "*".repeat(30) + "\n");
    System.out.println("测试案例3 (已全部腐烂):");
    solution.printGrid(grid3);
    int result3 = solution.orangesRotting(grid3);
    System.out.println("结果: " + result3 + " 分钟");
    assert result3 == 0 : "测试案例3失败";
    
    System.out.println("\n所有测试通过！");
}