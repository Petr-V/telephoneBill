package com.phonecompany.billing;

import java.math.BigDecimal;

/**
 * @author Petr
 *
 */
public interface TelephoneBillCalculator {
	
	BigDecimal calculate (String phoneLog);

}
