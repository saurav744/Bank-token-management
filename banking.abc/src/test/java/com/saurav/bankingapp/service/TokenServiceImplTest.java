package com.saurav.bankingapp.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.saurav.bankingapp.model.Job;
import com.saurav.bankingapp.model.Token;
import com.saurav.bankingapp.model.enums.TokenState;
import com.saurav.bankingapp.repository.TokenRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenServiceImplTest {
	
	@Autowired
	private TokenService tokenService;

	@MockBean
	private TokenRepository tokenRepository;
	
	private static Token mockToken = new Token();
	private static Optional<Token> opMockToken = Optional.of(mockToken);

	private static List<Job> tokenJobs = new ArrayList<Job>();
	
	private static Job job1 = new Job();
	private static Job job2 = new Job();
	
	@BeforeClass
	public static void setup() {
		
		job1.setId(1);
		job2.setId(2);
		tokenJobs.add(job1);
		tokenJobs.add(job2);
		mockToken.setStatus(TokenState.VALID);
		mockToken.setCurrentJob(job1);
		mockToken.setTokenJobs(tokenJobs);
		
	}
	/**
	 * These tests test multiple counter services. When all the jobs are completed only then Token is marked completed.
	 * 
	 */
	@Test
	public void testCompleteCurrentJob_001() throws Exception {
		
		Mockito.when(
				tokenRepository.findById(Mockito.anyLong())
				).thenReturn(opMockToken);
		
		long tokenId = 1;
		Token testToken = tokenService.completeCurrentJob(tokenId);
		
		assertEquals("Status values do not match", "VALID", testToken.getStatus().toString());
		assertEquals("Current jobs do not match", job2, testToken.getCurrentJob());
	}
	
	@Test
	public void testCompleteCurrentJob_002() throws Exception {
		
		Mockito.when(
				tokenRepository.findById(Mockito.anyLong())
				).thenReturn(opMockToken);
		
		long tokenId = 1;
		Token testToken = tokenService.completeCurrentJob(tokenId);
		
		assertEquals("Status values do not match", "COMPLETED", testToken.getStatus().toString());
		assertEquals("Current jobs do not match", job2, testToken.getCurrentJob());
	}
	
}
