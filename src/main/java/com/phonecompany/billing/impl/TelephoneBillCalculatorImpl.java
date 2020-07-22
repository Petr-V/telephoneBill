package com.phonecompany.billing.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.phonecompany.billing.TelephoneBillCalculator;

/**
 * @author Petr
 *
 */
public class TelephoneBillCalculatorImpl implements TelephoneBillCalculator {
	
	private DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
	private int MINUTE = 60000;
	private Calendar calendar = Calendar.getInstance();
	private BigDecimal FREE_HOUR = new BigDecimal("0.5");
	private BigDecimal LONG_TIME = new BigDecimal("0.2");
	

	@Override
	public BigDecimal calculate(String phoneLog) {
		String[] lines = phoneLog.split("[\r\n]+");

		BigDecimal ammount = BigDecimal.ZERO;
		for (String line : lines) {
			try {
				ammount = ammount.add(countCall(line));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//TODO log
				e.printStackTrace();
			}
		}
		return ammount;
	}

	/**
	 * calculates call cost
	 * @param line
	 * @return
	 * @throws ParseException
	 */
	private BigDecimal countCall(String line) throws ParseException {
		String[] callLog = line.split(" ");
		
		Date start = sdf.parse(callLog[2]);
		Date end = sdf.parse(callLog[4]);
		
		long min = countMinutes(start, end);
		BigDecimal tarif = getTarif(start, min);

		if (min <= 5) return tarif.multiply(new BigDecimal(min));
		
		return tarif.multiply(new BigDecimal(5)).add(new BigDecimal(min - 5).multiply(LONG_TIME));
	}

	/**
	 * gets the tarif of call according to start time
	 * @param start
	 * @param min 
	 * @return
	 */
	private BigDecimal getTarif(Date start, long min) {
		//XXX how should be tarif counted ie 15:55 --> 16:05 
		calendar.setTime(start);
		if (calendar.get(Calendar.HOUR_OF_DAY) >= 8 && calendar.get(Calendar.HOUR_OF_DAY) < 16) return BigDecimal.ONE;
			
		return FREE_HOUR;
	}

	private long countMinutes(Date start, Date end) {
		long offset = end.getTime() - start.getTime();
		
		long diffSec = offset / 1000;
		long min = diffSec / 60;
		long sec = diffSec % 60;
		
		if (sec > 0) { 
			min++;
		}
		return min;
	}

}
