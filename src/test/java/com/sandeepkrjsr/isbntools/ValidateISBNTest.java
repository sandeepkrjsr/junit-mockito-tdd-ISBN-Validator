package com.sandeepkrjsr.isbntools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ValidateISBNTest {

	@Test
	void checkAValid10DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("0140449116");
		assertTrue(result, "first value");
		result = validator.checkISBN("0140177396");
		assertTrue(result, "second value");
	}
	
	@Test
	void checkAValid13DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("9781853260087");
		assertTrue(result, "first value");
		result = validator.checkISBN("9781853267338");
		assertTrue(result, "second value");
	}
	
	@Test
	void tenDigitISBNNumberEndingWithXAreValid() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("012000030X");
		assertTrue(result);
	}
	
	@Test
	void checkAnInvalid10DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("0140449117");
		assertFalse(result);
	}
	
	@Test
	void checkAnInvalid13DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("9781853267336");
		assertFalse(result);
	}
	
	@Test
	void nineDigitsNumberAreNotAllowed() {
		ValidateISBN validator = new ValidateISBN();
		assertThrows(NumberFormatException.class, () -> {
			validator.checkISBN("123456789");
		});
	}
	
	@Test
	void nonNumericISBNsAreNotAllowed() {
		ValidateISBN validator = new ValidateISBN();
		assertThrows(NumberFormatException.class, () -> {
			validator.checkISBN("HelloWorld");
		});
	}

}
