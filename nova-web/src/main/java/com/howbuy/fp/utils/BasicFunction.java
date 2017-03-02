package com.howbuy.fp.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 基本双精度函数方法（最大值、最小值、计数、求和、求平均、方差、标准差、皮尔逊相关系数）
 * @author shijie.wang
 *
 */
public class BasicFunction {
//	public static void main(String[] args) {
//        double [] testData=new double[]{1,2,3,4,5,6,7,8,9};
//        System.out.println("最大值："+getMax(testData));
//        System.out.println("最小值："+getMin(testData));
//        System.out.println("计数："+getCount(testData));
//        System.out.println("求和："+getSum(testData));
//        System.out.println("求平均："+getAverage(testData));
//        System.out.println("方差："+getVariance(testData));
//        System.out.println("标准差："+getStandardDiviation(testData));
//       
//	}

	/**
	* 求给定双精度数组中值的最大值
	*
	* @param inputData
	*            输入数据数组
	* @return 运算结果,如果输入值不合法，返回为-1
	*/
	public static double getMax(double[] inputData) {
	if (inputData == null || inputData.length == 0)
	return -1;
	int len = inputData.length;
	double max = inputData[0];
	for (int i = 0; i < len; i++) {
	if (max < inputData[i])
	max = inputData[i];
	}
	return max;
	}
	
	/**
	* 求求给定双精度数组中值的最小值
	*
	* @param inputData
	*            输入数据数组
	* @return 运算结果,如果输入值不合法，返回为-1
	*/
	public static double getMin(double[] inputData) {
	if (inputData == null || inputData.length == 0)
	return -1;
	int len = inputData.length;
	double min = inputData[0];
	for (int i = 0; i < len; i++) {
	if (min > inputData[i])
	min = inputData[i];
	}
	return min;
	}
	
	/**
	* 求给定双精度数组中值的和
	*
	* @param inputData
	*            输入数据数组
	* @return 运算结果
	*/
	public static double getSum(double[] inputData) {
	if (inputData == null || inputData.length == 0)
	return -1;
	int len = inputData.length;
	double sum = 0;
	for (int i = 0; i < len; i++) {
	sum = sum + inputData[i];
	}
	
	return sum;
	
	}
	
	/**
	* 求给定双精度数组中值的数目
	*
	* @param input
	*            Data 输入数据数组
	* @return 运算结果
	*/
	public static int getCount(double[] inputData) {
	if (inputData == null)
	return -1;
	
	return inputData.length;
	}
	
	/**
	* 求给定双精度数组中值的平均值
	*
	* @param inputData
	*            输入数据数组
	* @return 运算结果
	*/
	public static double getAverage(double[] inputData) {
	if (inputData == null || inputData.length == 0)
	return -1;
	int len = inputData.length;
	double result;
	result = getSum(inputData) / len;
	
	return result;
	}
	
	/**
	* 求给定双精度数组中值的平方和
	*
	* @param inputData
	*            输入数据数组
	* @return 运算结果
	*/
	public static double getSquareSum(double[] inputData) {
	if(inputData==null||inputData.length==0)
	return -1;
	int len=inputData.length;
	double sqrsum = 0.0;
	for (int i = 0; i <len; i++) {
	sqrsum = sqrsum + inputData[i] * inputData[i];
	}
	
	
	return sqrsum;
	}
	
	/**
	* 求给定双精度数组中值的方差
	*
	* @param inputData
	*            输入数据数组
	* @return 运算结果
	*/
	public static double getVariance(double[] inputData) {
	int count = getCount(inputData);
	double sqrsum = getSquareSum(inputData);
	double average = getAverage(inputData);
	double result;
	result = (sqrsum - count * average * average) / count;
	
	return result;
	}

	/**
	* 求给定双精度数组中值的标准差
	*
	* @param inputData
	*            输入数据数组
	* @return 运算结果
	*/
	public static double getStandardDiviation(double[] inputData) {
	double result;
	int count = getCount(inputData);
	double sqrsum = getSquareSum(inputData);
	double sumData = getSum(inputData);
	if(count!=1 && count!=0 && !Double.isNaN(count) && !Double.isInfinite(count) && !Double.isNaN(sqrsum) && !Double.isInfinite(sqrsum) && !Double.isNaN(sumData) && !Double.isInfinite(sumData) ){
		//绝对值化很重要
		result = Math.sqrt((Math.abs((sqrsum - sumData*sumData/count)/(count-1))));
	}else{
		result = 0;
	}
	return result;
	}
    
	/**
	 * 皮尔逊相关系数
	 * @param rating_map1
	 * @param rating_map2
	 * @return
	 */
    public static double getBasicFunction_bydim(Map<String, Double> rating_map1,Map<String, Double> rating_map2) {  
        double sim = 0d;  
        double common_items_len = 0;  
        double this_sum = 0d;  
        double u_sum = 0d;  
        double this_sum_sq = 0d;  
        double u_sum_sq = 0d;  
        double p_sum = 0d;  
          
        Iterator<String> rating_map_iterator = rating_map2.keySet().iterator();  
        while(rating_map_iterator.hasNext()){  
            String rating_map_iterator_key = rating_map_iterator.next();  
            Iterator<String> u_rating_map_iterator = rating_map1.keySet().iterator();  
            while(u_rating_map_iterator.hasNext()){  
                String u_rating_map_iterator_key = u_rating_map_iterator.next();  
                if(rating_map_iterator_key.equals(u_rating_map_iterator_key)){  
                    double this_grade = rating_map2.get(rating_map_iterator_key);  
                    double u_grade = rating_map1.get(u_rating_map_iterator_key);  
                    //评分求和  
                    //平方和  
                    //乘积和  
                    this_sum += this_grade;  
                    u_sum += u_grade;  
                    this_sum_sq += Math.pow(this_grade, 2);  
                    u_sum_sq += Math.pow(u_grade, 2);  
                    p_sum += this_grade * u_grade;    
                    common_items_len++;  
                }  
            }  
        }  
        //如果等于零则无相同条目，返回sim=0即可  
        if(common_items_len > 0){  
            double num = common_items_len * p_sum - this_sum * u_sum;  
            double den = Math.sqrt((common_items_len * this_sum_sq - Math.pow(this_sum, 2)) * (common_items_len * u_sum_sq - Math.pow(u_sum, 2)));  
            sim = (den == 0) ? 1 : num / den;  
        }  
          
        //如果等于零则无相同条目，返回sim=0即可  
        return sim;  
    }  
    
    /**
     * 四舍五入双精度值
     * @param calValue 传入值
     * @param countNum 四舍五入位数
     * @return
     */
    public static double getDoubleRound(double calValue,int countNum){
    	if(!Double.isNaN(calValue)&&!Double.isInfinite(calValue)){
    		//转换中间值
    		BigDecimal calValueBig = new BigDecimal(calValue); 
    		//转换后值
    		calValue = calValueBig.setScale(countNum,BigDecimal.ROUND_HALF_UP).doubleValue();
    	}else{
    		calValue = 0;
    	}
    	return calValue;
    }
    
    
    /**
     * list深度COPY
     * @param src
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {  
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream out = new ObjectOutputStream(byteOut);  
        out.writeObject(src);  
      
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());  
        ObjectInputStream in = new ObjectInputStream(byteIn);  
        @SuppressWarnings("unchecked")  
        List<T> dest = (List<T>) in.readObject();  
        return dest;  
    }  
    
    
    /**
     * 获取指标配置所对应的最早的日期
     * @param ZbpzDataArray 指标配置列表
     * @param calendar 日历日
     * @return 起始日期，YYYYMMDD
     */
    public String GetEarliestDate(String[][] ZbpzDataArray, Calendar calendar) {
    	int pv = 0;
    	String pvdw ="";
    	
    	Calendar calendarMin = new GregorianCalendar();    	
    	calendarMin.setTime(calendar.getTime());
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	
    	try {
    	   	for (int i = 0; i < ZbpzDataArray.length; i++) {
    			pv = Integer.parseInt(ZbpzDataArray[i][1]);
    			pvdw = ZbpzDataArray[i][2];
    			
    			Calendar calendarInit = new GregorianCalendar();
    	    	calendarInit.setTime(calendar.getTime());
    	    	String PreviousDate[] = GetPreviousDate(sdf.format(calendarInit.getTime()), pv, pvdw);
    	    	calendarInit.setTime(sdf.parse(PreviousDate[0]));

    			if (calendarMin.after(calendarInit)) {
    				calendarMin.setTime(calendarInit.getTime());
    			}
    		}
    	   	calendarMin.add(Calendar.MONTH, -1);  //往前推一个月，用于取出早于最小日期对应的钱一个交易日
		} catch (Exception e) {
			e.printStackTrace();
		}
//    	System.out.println("calendarMin :" + sdf.format(calendarMin.getTime()));
    	return sdf.format(calendarMin.getTime());
    }

    /**
     * 获取指定日期的前一频次的日期
     * @param jzrq	截止日期
     * @param pv	频率
     * @param pvdw	频率单位
     * @return  计算所得的日期数组（0：起始日期    1：截止日期）
     */
    public String[] GetPreviousDate(String jzrq, int pv, String pvdw) {
    	String PreviousDate[] = new String[2];	//起始日期，截止日期
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    		Calendar calendarInit = new GregorianCalendar();	//初始日期
    		Calendar calendarBegin = new GregorianCalendar();	//计算起始日期的日历
    		Calendar calendarEnd = new GregorianCalendar();		//计算结束日期的日历
    		calendarInit.setTime(sdf.parse(jzrq));
    		calendarBegin.setTime(sdf.parse(jzrq));
    		calendarEnd.setTime(sdf.parse(jzrq));
	    	
			if (pvdw.equals("N") == true) {
				if (pv > 0) {
					//近N年，终止日期往前推N年
					calendarBegin.add(Calendar.YEAR, 0 - pv);
				} else {
					//今年以来、去年等特殊年份 ，从指定年份的1月1日起
					calendarBegin.set(calendarBegin.get(Calendar.YEAR) + pv, Calendar.JANUARY, 1);
					calendarBegin.add(Calendar.DAY_OF_MONTH, -1);
					
					//截止日期
					calendarEnd.set(calendarBegin.get(Calendar.YEAR) + 1, Calendar.DECEMBER, 31);
				}					
			} else if (pvdw.equals("Y") == true) {
				if (pv > 0) {
					//近N月，终止日期往前推N月
					calendarBegin.add(Calendar.MONTH, 0 - pv);
				} else {
					//本月以来、上月等特殊月份
					calendarBegin.add(Calendar.MONTH, pv);	//往前推N个月
					calendarBegin.set(Calendar.DATE, 1);		//设置为月初
					calendarBegin.add(Calendar.DAY_OF_MONTH, -1);
					
					calendarEnd.add(Calendar.MONTH, pv - 1);	//往前推N-1个月
					calendarEnd.set(Calendar.DATE, 1);			//设置为月初
					calendarEnd.add(Calendar.DAY_OF_MONTH, -1);
				}
			}
			calendarBegin.add(Calendar.DAY_OF_MONTH, 1);	//加一天
			
			//起始日期
			PreviousDate[0] = sdf.format(calendarBegin.getTime());	//转成字符串格式
			//截止日期，取初始日期和计算所得日期中最早的那个
			if (calendarInit.before(calendarEnd)) {
				PreviousDate[1] = sdf.format(calendarInit.getTime());
			} else {
				PreviousDate[1] = sdf.format(calendarEnd.getTime());
			}
			calendarBegin = null;
			calendarInit = null;
			calendarEnd = null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return PreviousDate;
    }
    
    
}
