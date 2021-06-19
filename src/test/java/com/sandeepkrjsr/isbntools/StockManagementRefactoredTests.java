package com.sandeepkrjsr.isbntools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class StockManagementRefactoredTests {

	ExternalISBNDataService databaseService;
	ExternalISBNDataService webService;
	StockManager stockManager;
	
	@BeforeEach
	void setup() {
		databaseService = mock(ExternalISBNDataService.class);
		webService = mock(ExternalISBNDataService.class);
		stockManager = new StockManager();
		stockManager.setDatabaseService(databaseService);
		stockManager.setWebService(webService);
	}
	
	@Test
	void testCanGetACorrectLocatorCode() {
		
		when(webService.lookup(anyString())).thenReturn(new Book("0140177396", "Of Mice And Men", "J. Steinbeck"));
		when(databaseService.lookup(anyString())).thenReturn(null);
		
		StockManager stockManager = new StockManager();
		stockManager.setDatabaseService(databaseService);
		stockManager.setWebService(webService);
		
		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		assertEquals("7396J4", locatorCode);
	}
	
	@Test
	void databaseIsUsedIfDataIsPresent() {
		
		when(databaseService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));
		
		String isbn = "0140177396";
		stockManager.getLocatorCode(isbn);
		
		verify(databaseService).lookup("0140177396");
		verify(webService, never()).lookup(anyString());
	}
	
	@Test
	void webserviceIsUsedIfDataIsNotPresentInDatabase() {
		
		when(databaseService.lookup("0140177396")).thenReturn(null);
		when(webService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));
		
		String isbn = "0140177396";
		stockManager.getLocatorCode(isbn);
		
		verify(databaseService).lookup("0140177396");
		verify(webService).lookup(anyString());
	}

}
