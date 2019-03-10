package com.iss.common.valid;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidationMessage<T> {

	public static <T> String message(T t, Class<?> clazz) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> validate = validator.validate(t, clazz);
		for (ConstraintViolation<T> violation : validate) {
			String message = violation.getMessage();
			System.out.println("message:" + message);
		}
		return "";
	}
}
