package com.saurav.bankingapp.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.saurav.bankingapp.model.User;
import com.saurav.bankingapp.model.enums.UserType;
import com.saurav.bankingapp.repository.UserRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
	
	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;
	
	User mockUser = new User("Simbha", "lion@king", "HakunaMatata", "12345678", "123 abc avenue", new Date(), UserType.REGULAR );
	Optional<User> opMockUser = Optional.of(mockUser);
	
	@Test
	public void testGetUserById_001() throws Exception {
		
		Mockito.when(
				userRepository.findById(Mockito.anyLong())
				).thenReturn(opMockUser);
		
		User testUser = userService.getById(0);
		
		assertEquals("Incorrect value","Simbha",testUser.getName());
		assertEquals("Incorrect value","lion@king",testUser.getEmail());
		assertEquals("Incorrect value","12345678",testUser.getPhone());
	}

}