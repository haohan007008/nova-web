package com.howbuy.fp.utils;

/**
 * 统计维度
 * 
 * @author meng.lv
 * 
 */
public enum Dimension {
	MONTH1("M1",1101), // 近一月
	MONTH3("M3",1102), // 近3月
	MONTH6("M6",1103), // 近6月
	CURYEAR("Y0",1204), // 今年以来
	YEAR1("Y1",1201), // 近一年
	YEAR2("Y2",1202), // 近两年
	YEAR3("Y3",1203), // 近三年
	YEAR5("Y5",1205), // 近5年
	YEAR10("Y10",1210), // 近10年
	ALL("ALL_",1401),// 成立以来
	WEEK1("W1",1002);// 近1周

	private String name;
	private int value;

	public static Dimension forValue(int value) {
		Dimension cmdEnum = null;
		Dimension[] enums = Dimension.values();
		for (int i = 0; i < enums.length; i++) {
			cmdEnum = enums[i];
			if (cmdEnum.getValue() == value) {
				break;
			}
		}

		return cmdEnum;
	}

	private Dimension(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
