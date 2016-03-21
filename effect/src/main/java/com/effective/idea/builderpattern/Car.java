package com.effective.idea.builderpattern;

/**
 * Created by jsion on 16/3/19.
 */
public class Car {
    // 轮胎,引擎,方向盘
    private String tires;
    private String engine;
    private String wheel;

    // 可选 涂鸦, 贴纸
    private String graffiti;
    private String sticker;

    // 静态builder类,用于构建可选和必选参数

    public static class Builder {
        // 必选参数用构造传入
        private String tires;
        private String engine;
        private String wheel;

        // 可选参数用构造传入
        private String graffiti = "无涂鸦";
        private String sticker = "无贴纸";

        public Builder(String tires, String engine, String wheel) {
            this.tires = tires;
            this.engine = engine;
            this.wheel = wheel;
        }

        public Builder graffiti(String graffiti) {
            this.graffiti = graffiti;
            return this;
        }

        public Builder sticker(String sticker) {
            this.sticker = sticker;
            return this;
        }

        // 配置完毕执行build方法返回当前具备属性的对象
        public Car build() {
            return new Car(this);
        }
    }

    private Car(Builder builder) {
        this.engine = builder.engine;
        this.graffiti = builder.graffiti;
        this.sticker = builder.sticker;
        this.tires = builder.tires;
        this.wheel = builder.wheel;
    }

    @Override
    public String toString() {
        return "Car{" +
                "tires='" + tires + '\'' +
                ", engine='" + engine + '\'' +
                ", wheel='" + wheel + '\'' +
                ", graffiti='" + graffiti + '\'' +
                ", sticker='" + sticker + '\'' +
                '}';
    }
}
