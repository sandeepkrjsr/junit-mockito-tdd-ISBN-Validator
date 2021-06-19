package com.sandeepkrjsr.isbntools;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class StockManagementTests {

	@Test
	void testCanGetACorrectLocatorCode() {
		
		ExternalISBNDataService testWebService = new ExternalISBNDataService() {	
			@Override
			public Book lookup(String isbn) {
				return new Book(isbn, "Of Mice And Men", "J. Steinbeck");
			}
		};
		
		ExternalISBNDataService testDatabaseService = new ExternalISBNDataService() {	
			@Override
			public Book lookup(String isbn) {
				return null;
			}
		};
		
		StockManager stockManager = new StockManager();
		stockManager.setDatabaseService(testDatabaseService);
		stockManager.setWebService(testWebService);
		
		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		assertEquals("7396J4", locatorCode);
	}
	
	@Test
	void databaseIsUsedIfDataIsPresent() {
		ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
		ExternalISBNDataService webService = mock(ExternalISBNDataService.class);
		
		when(databaseService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));
		
		StockManager stockManager = new StockManager();
		stockManager.setDatabaseService(databaseService);
		stockManager.setWebService(webService);
		
		String isbn = "0140177396";
		stockManager.getLocatorCode(isbn);
		
		verify(databaseService).lookup("0140177396");
		verify(webService, never()).lookup(anyString());
	}
	
	@Test
	void webserviceIsUsedIfDataIsNotPresentInDatabase() {
		ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
		ExternalISBNDataService webService = mock(ExternalISBNDataService.class);
		
		when(databaseService.lookup("0140177396")).thenReturn(null);
		when(webService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));
		
		StockManager stockManager = new StockManager();
		stockManager.setDatabaseService(databaseService);
		stockManager.setWebService(webService);
		
		String isbn = "0140177396";
		stockManager.getLocatorCode(isbn);
		
		verify(databaseService).lookup("0140177396");
		verify(webService).lookup(anyString());
	}

}
