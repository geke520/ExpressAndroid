package club.codeapes.common.lang;

import java.math.BigDecimal;

public class DoubleUtil {

	/**
	 * 相乘
	 * @Title: multiply 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Type:Method
	 * @param num1
	 * @param num2
	 * @param keepDecimalPlaces
	 * @return
	 * .setScale(keepDecimalPlaces)
	 */
	public static Double multiply(Object num1,Object num2,int keepDecimalPlaces){
		BigDecimal n1 = new BigDecimal(num1.toString()),n2 = new BigDecimal(num2.toString());
		return n1.multiply(n2).setScale(keepDecimalPlaces,BigDecimal.ROUND_HALF_EVEN).doubleValue();
	}
	
	public static Double multiply(Object num1,Object num2,int keepDecimalPlaces,int type){
		BigDecimal n1 = new BigDecimal(num1.toString()),n2 = new BigDecimal(num2.toString());
		return n1.multiply(n2).setScale(keepDecimalPlaces,type).doubleValue();
	}
	
	public static Double multiply(Object num1,Object num2){
		BigDecimal n1 = new BigDecimal(num1.toString()),n2 = new BigDecimal(num2.toString());
		return n1.multiply(n2).doubleValue();
	}
	
	public static Double add(Object num1,Object num2,int keepDecimalPlaces){
		BigDecimal n1 = new BigDecimal(num1.toString()),n2 = new BigDecimal(num2.toString());
		return n1.add(n2).setScale(keepDecimalPlaces,BigDecimal.ROUND_HALF_EVEN).doubleValue();
	}
	
	public static Double add(Object num1,Object num2,int keepDecimalPlaces,int type){
		BigDecimal n1 = new BigDecimal(num1.toString()),n2 = new BigDecimal(num2.toString());
		return n1.add(n2).setScale(keepDecimalPlaces,type).doubleValue();
	}
	
	public static Double add(Object num1,Object num2){
		BigDecimal n1 = new BigDecimal(num1.toString()),n2 = new BigDecimal(num2.toString());
		return n1.add(n2).doubleValue();
	}
	
	public static Double divide(Object num1,Object num2,int keepDecimalPlaces){
		BigDecimal n1 = new BigDecimal(num1.toString()),n2 = new BigDecimal(num2.toString());
		return n1.divide(n2).setScale(keepDecimalPlaces,BigDecimal.ROUND_HALF_EVEN).doubleValue();
	}
	
	public static Double divide(Object num1,Object num2,int keepDecimalPlaces,int type){
		BigDecimal n1 = new BigDecimal(num1.toString()),n2 = new BigDecimal(num2.toString());
		return n1.divide(n2,keepDecimalPlaces,type).doubleValue();
	}
	
	public static Double divide(Object num1,Object num2){
		BigDecimal n1 = new BigDecimal(num1.toString()),n2 = new BigDecimal(num2.toString());
		return n1.divide(n2).doubleValue();
	}
	
	
	public static Double subtract(Object num1,Object num2,int keepDecimalPlaces){
		BigDecimal n1 = new BigDecimal(num1.toString()),n2 = new BigDecimal(num2.toString());
		return n1.subtract(n2).setScale(keepDecimalPlaces,BigDecimal.ROUND_HALF_EVEN).doubleValue();
	}
	
	public static Double subtract(Object num1,Object num2){
		BigDecimal n1 = new BigDecimal(num1.toString()),n2 = new BigDecimal(num2.toString());
		return n1.subtract(n2).doubleValue();
	}
	
	public static Double parse(Object num){
		return new BigDecimal(num.toString()).doubleValue();
	}
}
