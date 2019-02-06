package com.saurav.bankingapp.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
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

import com.saurav.bankingapp.model.User;
import com.saurav.bankingapp.model.enums.UserType;
import com.saurav.bankingapp.service.UserService;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "lion@king", password = "HakunaMatata", authorities = { "MANAGER", "PREMIUM", "OPERATOR", "REGULAR" })
public class BankingAppControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	//(newUser.getName(), newUser.getEmail(), newUser.getPassword(), newUser.getPhone(), newUser.getAddress(), newUser.getType())
	User mockUser = new User("Simbha", "lion@king", "HakunaMatata","12345678", "123 abc avenue", UserType.REGULAR );
	
	String userJson = "{\"Name\": \"Simbha\",\"email\": \"lion@king\",\"password\": \"HakunaMatata\",\"type\": \"BLOGGER\"}";
	
	@Test
	public void testGetUserById_001() throws Exception {
		
		Mockito.when(
				userService.getById(Mockito.anyLong())
				).thenReturn(mockUser);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/users/id/0")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expected = "{id:0,Name:Simbha,email:lion@king}";
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
	
	@Test
	public void testCreateUser_002() throws Exception {
	
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/users")
				.accept(MediaType.APPLICATION_JSON).content(userJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

}
