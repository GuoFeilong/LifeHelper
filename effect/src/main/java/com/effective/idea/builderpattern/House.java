package com.effective.idea.builderpattern;

/**
 * Created by jsion on 16/3/19.
 */
public class House {
    // 必选的参数
    private String foundation;
    private String material;

    // 可选的参数
    private String bigDoor;
    private String setp;
    private String ceilingLamp;

    // 内部提供一个静态的builder类,提供参数的设置,以及返回实例
    public static class Builder {
        private String foundation;
        private String material;

        // 可选参数赋默认值
        private String bigDoor = "普通大门";
        private String setp = "普通台阶";
        private String ceilingLamp = "普通吊灯";

        /**
         * 构造参数中传递必选参数
         *
         * @param foundation
         * @param material
         */
        public Builder(String foundation, String material) {
            this.foundation = foundation;
            this.material = material;
        }

        // 可选参数的可选builder方法

        public Builder bigDoor(String bigDoor) {
            this.bigDoor = bigDoor;
            return this;
        }

        public Builder setP(String setp) {
            this.setp = setp;
            return this;
        }

        public Builder ceilingLamp(String ceilingLamp) {
            this.ceilingLamp = ceilingLamp;
            return this;
        }

        // 上述必选和可选的参数全部配置完毕,最后开始建造房子,返回当前房子对象
        public House build() {
            return new House(this);
        }

    }

    // 构造函数私有化返回当前的对象
    private House(Builder builder) {
        this.foundation = builder.foundation;
        this.material = builder.material;
        this.bigDoor = builder.bigDoor;
        this.setp = builder.setp;
        this.ceilingLamp = builder.ceilingLamp;
    }

    @Override
    public String toString() {
        return "House{" +
                "foundation='" + foundation + '\'' +
                ", material='" + material + '\'' +
                ", bigDoor='" + bigDoor + '\'' +
                ", setp='" + setp + '\'' +
                ", ceilingLamp='" + ceilingLamp + '\'' +
                '}';
    }
}
