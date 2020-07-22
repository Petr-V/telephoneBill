package com.phonecompany.billing;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.phonecompany.billing.TelephoneBillCalculator;
import com.phonecompany.billing.impl.TelephoneBillCalculatorImpl;

public class TelephoneBillCalculatorTest {

	String st1 = "420774567453 13-01-2020 18:10:00 13-01-2020 18:12:00";
	String st2 = "420774567453 13-01-2020 18:10:00 13-01-2020 18:12:50";
	String st3 = "420774567453 13-01-2020 18:10:00 13-01-2020 18:17:50";
	String st4 = "420774567453 13-01-2020 08:10:00 13-01-2020 08:17:50";
	TelephoneBillCalculator parser = new TelephoneBillCalculatorImpl();
	

	@Test
	public void test1() {
		BigDecimal ammount = parser.calculate(st1);
		Assert.assertTrue("valid ammount", ammount.compareTo(new BigDecimal("1")) == 0);
	}
	
	@Test
	public void test2() {
		BigDecimal ammount = parser.calculate(st2);
		Assert.assertTrue("valid ammount in cheap time", ammount.compareTo(new BigDecimal("1.5")) == 0);
	}
	
	@Test
	public void test3() {
		BigDecimal ammount = parser.calculate(st3);
		Assert.assertTrue("valid ammount long time", ammount.compareTo(new BigDecimal("3.1")) == 0);
	}
	
	@Test
	public void test4() {
		BigDecimal ammount = parser.calculate(st4);
		Assert.assertTrue("valid ammount notcheap and long time", ammount.compareTo(new BigDecimal("5.6")) == 0);
	}


}
