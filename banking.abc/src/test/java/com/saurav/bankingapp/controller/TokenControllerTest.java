package com.saurav.bankingapp.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.saurav.bankingapp.model.BankService;
import com.saurav.bankingapp.model.Counter;
import com.saurav.bankingapp.model.Job;
import com.saurav.bankingapp.model.Token;
import com.saurav.bankingapp.model.User;
import com.saurav.bankingapp.model.enums.CounterPriority;
import com.saurav.bankingapp.model.enums.TokenState;
import com.saurav.bankingapp.model.enums.UserType;
import com.saurav.bankingapp.service.BankServiceService;
import com.saurav.bankingapp.service.CounterService;
import com.saurav.bankingapp.service.TokenService;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "12345678", password = "HakunaMatata", authorities = { "MANAGER", "OPERATOR" })
public class TokenControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TokenService tokenService;
	@MockBean
	private BankServiceService bankServiceService;
	@MockBean
	private CounterService counterService;
	
	static User mockUser = new User("Simbha", "lion@king", "HakunaMatata", "12345678", "123 abc avenue",new Date(), UserType.REGULAR );
	
	String userJson = "{\"name\": \"Simbha\",\"email\": \"lion@king\",\"password\": \"HakunaMatata\",\"phone\": \"12345678\",\"type\": \"REGULAR\"}";
	
	static Token mockToken1 = new Token();
	static Token mockToken2 = new Token();
	static Counter counter1 = new Counter(1, CounterPriority.NORMAL);
	static Counter counter2 = new Counter(2, CounterPriority.NORMAL);
	static Counter counter3 = new Counter(3, CounterPriority.NORMAL);
	static List<Counter> counters = new ArrayList<Counter>();
	static BankService mockService = new BankService();
	static Job mockJob = new Job();
	
	@BeforeClass
	public static void setup() {
		
		counters.add(counter2);
		counters.add(counter3);
		mockService.setCounters(counters);
		mockService.setName("ServiceA");
		mockJob.setService(mockService);
		mockToken1.setCurrentJob(mockJob);
		mockToken2.setCurrentJob(mockJob);
		mockToken1.setCurrentCounter(counter1);
		mockToken2.setCurrentCounter(counter2);
		mockToken1.setUser(mockUser);
		mockToken2.setUser(mockUser);	
		mockToken1.setStatus(TokenState.VALID);
		mockToken2.setStatus(TokenState.VALID);
	}
	
	@Test
	public void testCompleteToken_001() throws Exception {
		
		Mockito.when(
				tokenService.isValid(Mockito.anyLong())
				).thenReturn(true);
		Mockito.when(
				tokenService.completeCurrentJob(1)
				).thenReturn(mockToken1);
		Mockito.when(
				tokenService.completeCurrentJob(2)
				).thenReturn(mockToken2);
		Mockito.when(
				bankServiceService.allocateCounter(Mockito.anyString(), Mockito.any(CounterPriority.class))
				).thenReturn(counter3);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/tokens/1/complete")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals("Http status does not match",HttpStatus.OK.value(), response.getStatus());
		assertEquals("Counter alloted does not match", counter3, mockToken1.getCurrentCounter());
		
		 requestBuilder = MockMvcRequestBuilders
				.put("/tokens/2/complete")
				.accept(MediaType.APPLICATION_JSON);
		
		 result = mockMvc.perform(requestBuilder).andReturn();
		
		 response = result.getResponse();
		 
		 assertEquals("Http status does not match",HttpStatus.OK.value(), response.getStatus());
		 assertEquals("Counter alloted does not match", counter2, mockToken2.getCurrentCounter());		
		
	}

}
