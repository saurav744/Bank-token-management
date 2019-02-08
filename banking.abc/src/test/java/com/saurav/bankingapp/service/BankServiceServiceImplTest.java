package com.saurav.bankingapp.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.saurav.bankingapp.model.BankService;
import com.saurav.bankingapp.model.Counter;
import com.saurav.bankingapp.model.enums.CounterPriority;
import com.saurav.bankingapp.repository.BankServiceRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankServiceServiceImplTest {
	
	@Autowired
	private BankServiceService bankServiceService;

	@MockBean
	private BankServiceRepository bankServiceRepository;
	
	private Counter counter1 = new Counter(1, CounterPriority.HIGH);
	private Counter counter2 = new Counter(2, CounterPriority.NORMAL);
	private Counter counter3 = new Counter(3, CounterPriority.NORMAL);
	private Counter counter4 = new Counter(4, CounterPriority.NORMAL);
	
	List<Counter> counters = new ArrayList<Counter>();
	
	BankService bankService = new BankService("MockService", 1L, counters);
	
	@Test
	public void testAllocateCounter_001() {
		
		counter2.setQueueSize(2);
		counter3.setQueueSize(1);
		counter4.setQueueSize(3);
		    
		counters.add(counter1);
		counters.add(counter2);
		counters.add(counter3);
		counters.add(counter4);
		
		Mockito.when(
				bankServiceRepository.findByName(Mockito.anyString())
				).thenReturn(bankService);
		
		Counter allocated = bankServiceService.allocateCounter("MockService", CounterPriority.NORMAL);
		
		assertEquals("Incorrect value", counter3, allocated);
	}

}