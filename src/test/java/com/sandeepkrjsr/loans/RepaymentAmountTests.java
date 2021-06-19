package com.sandeepkrjsr.loans;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestTemplate;

class RepaymentAmountTests {
	
	@Spy
	LoanApplication loanApplication;
	
	LoanCalculatorController controller;
	LoanRepository data;
	JavaMailSender mailSender;
	RestTemplate restTemplate;
	
	@BeforeEach
	public void setup() {
		data = mock(LoanRepository.class);
		mailSender = mock(JavaMailSender.class);
		restTemplate = mock(RestTemplate.class);
		
		loanApplication = spy(new LoanApplication());
		
		controller = new LoanCalculatorController();
		controller.setData(data);
		controller.setMailSender(mailSender);
		controller.setRestTemplate(restTemplate);
	}

	@Test
	void test1YearLoanWholePounds() {
		loanApplication.setPrincipal(1200);
		loanApplication.setTermInMonths(12);
		doReturn(new BigDecimal(10)).when(loanApplication).getInterestRate();
		
		controller.processNewLoanApplication(loanApplication);
		
		assertEquals(new BigDecimal(110), loanApplication.getRepayment());
	}
	
	@Test
	void test2YearLoanWholePounds() {
		loanApplication.setPrincipal(1200);
		loanApplication.setTermInMonths(24);
		doReturn(new BigDecimal(10)).when(loanApplication).getInterestRate();
		
		controller.processNewLoanApplication(loanApplication);
		
		assertEquals(new BigDecimal(60), loanApplication.getRepayment());
	}
	
	@Test
	void test5YearLoanWithRounding() {
		loanApplication.setPrincipal(5000);
		loanApplication.setTermInMonths(60);
		doReturn(new BigDecimal(6.5)).when(loanApplication).getInterestRate();
		
		controller.processNewLoanApplication(loanApplication);
		
		assertEquals(new BigDecimal(111), loanApplication.getRepayment());
	}

}
