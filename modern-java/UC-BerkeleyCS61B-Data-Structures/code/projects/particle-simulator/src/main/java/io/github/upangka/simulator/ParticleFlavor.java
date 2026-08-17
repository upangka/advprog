package io.github.upangka.simulator;

/**
 * 表示模拟器中不同类型的粒子。
 * 每种粒子都有其独特的行为、颜色和交互方式。
 */
public enum ParticleFlavor {
    /** 沙粒，受重力影响下落 */
    SAND,
    /** 屏障，不可移动，用于构建边界 */
    BARRIER,
    /** 水，受重力影响并可以向两侧流动 */
    WATER,
    /** 植物，可以向周围生长 */
    PLANT,
    /** 火，会燃烧周围的可燃物并逐渐消亡 */
    FIRE,
    /** 空格子，代表没有粒子的位置，默认状态 */
    EMPTY,
    /** 喷泉，可以持续产生新的粒子 */
    FOUNTAIN,
    /** 花朵，由植物成熟后转化而来，会随时间凋零 */
    FLOWER
}
