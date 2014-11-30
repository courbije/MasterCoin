package fr.ufrima.m2pgi.ecom.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Constraint(validatedBy = { GtZeroValidator.class })
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface GtZero {

	String message() default "Number must be > 0";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	boolean value() default true;

}