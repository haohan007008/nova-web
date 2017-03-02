package com.howbuy.fp.utils;

public class commonFunctions {
	/**
	 * 查找指定日期在数组中的位置（如指定日期找不到则取其之前的最后一个数据,如之前没有数据，则返回错误提示-1）
	 * @param 	dateArray			数组
	 * @param 	dateColumnIndex		日期字段在数组中的位置
	 * @param 	searchDate			要查找的日期
	 * @return	searchDate在数组dateArray中的位置 
	 */
	public int getDatePosInArray(String[][] dateArray, int dateColumnIndex, String searchDate) {
		int n = dateArray.length;
		
		if (n == 1 || searchDate.compareTo(dateArray[0][dateColumnIndex]) < 0) {
			//数据量太少，不支持计算，或要查找的日期比数组中首个元素的日期更早
			return -1;
		}
		
		//找计算的起始日期
		int i_loop = 0;	//循环变量
		int i_loop_count = 0;	//循环次数控制
		int mid = Math.round((float) n / 2);
//		System.out.println(mid);
		int start_pos = 0;		//起始位置
		int end_pos = n - 1;	//结束位置
		int medien = 0;
		while (mid >= 0 && i_loop_count <= 500000) {
			String qsrqCompare = dateArray[mid][dateColumnIndex];
			if (searchDate.compareTo(qsrqCompare) == 0) {
				//日期相同
				i_loop = mid;
				break;
			} else if (searchDate.compareTo(qsrqCompare) < 0) {
				//searchDate早于净值日期
				end_pos = mid;
				medien = Math.round((float)(end_pos - start_pos) / 2);
				mid = start_pos + medien;
				
				if (mid >= end_pos) {
					//已找到相邻位置，不能再继续查找
					i_loop = end_pos;
					break;
				}
			} else {
				//searchDate大于净值日期
				start_pos = mid;
				medien = Math.round((float)(end_pos - start_pos) / 2);
				mid = start_pos + medien;
				
				if (mid >= end_pos) {
					//已找到相邻位置，不能再继续查找
					i_loop = end_pos;
					break;
				}
				
			}
			i_loop_count += 1;
		}
		return i_loop;
	}
}
