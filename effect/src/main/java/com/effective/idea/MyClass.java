package com.effective.idea;

import com.effective.idea.builderpattern.Car;
import com.effective.idea.builderpattern.House;
import com.effective.idea.factory.HashMapFactory;
import com.effective.idea.unnecessaryobject.PersonBirthDate;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class MyClass {
    public static void main(String[] args) throws Exception {
        testFactory();
        testBuilderPattern();
        testPersonBirth();
    }

    private static void testPersonBirth() {
        PersonBirthDate personBirthDate = new PersonBirthDate(new Date());
        boolean isContains = personBirthDate.isContains();
        System.out.print("\ntestPersonBirth1:" + isContains + "\n");

        Calendar calendar = Calendar.getInstance();
        calendar.set(1965, Calendar.JANUARY, 1, 0, 0, 0);

        personBirthDate = new PersonBirthDate(calendar.getTime());
        isContains = personBirthDate.isContains();
        System.out.print("\ntestPersonBirth2:" + isContains + "\n");

    }

    private static void testFactory() {
        Map<String, String> mTestString;
        Map<String, Integer> mTestInteger;

        mTestInteger = HashMapFactory.newInstance();
        mTestString = HashMapFactory.newInstance();

        mTestInteger.put("INTEGER", 12);
        mTestInteger.put("INTEGER1", 13);

        mTestString.put("String", "sfsfs");
        mTestString.put("String1", "哈哈哈哈");
        System.out.print(mTestInteger.get("INTEGER") + ">>>>" + mTestString.get("String1") + "\n========================\n");
    }

    private static void testBuilderPattern() {
        // 使用的时候因为我们的类已经私有化构造函数,所以只能通过New 出来当前类的建造者来配置,和builder
        Car mCar = new Car.Builder("高级轮胎", "进口引擎", "24k金方向盘")
                .graffiti("全裸涂鸦")
                .sticker("豪放贴纸")
                .build();

        // 不传递可选参数
        Car otherCar = new Car.Builder("普通轮胎", "大众引擎", "拖拉机改造方向盘")
                .build();

        House mHouse = new House.Builder("350平米地基", "全进口材料")
                .bigDoor("镀金双开大门")
                .ceilingLamp("南非镶钻吊灯")
                .setP("进口大理石台阶")
                .build();

        House otherHouse = new House.Builder("10平米厕所旁地基", "国内高含量超标材料")
                .bigDoor("破大门")
                .ceilingLamp("普通灯泡")
                .setP("蓝翔挖掘的红砖台阶")
                .build();


        System.out.print("我的车:" + mCar.toString() + "\n别人的车:" + otherCar.toString() + "\n我的房子:" + mHouse.toString() + "\n别人房子:" + otherHouse.toString());
    }

}
