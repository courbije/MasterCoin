package fr.ufrima.m2pgi.ecom.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class GtZeroValidator implements ConstraintValidator<GtZero, Double> {

	@Override
	public void initialize(GtZero constraint) {

	}

	@Override
	public boolean isValid(Double value, ConstraintValidatorContext ctx) {
		if (value == null) {
			return false;
		}
		return value > 0;

	}

}