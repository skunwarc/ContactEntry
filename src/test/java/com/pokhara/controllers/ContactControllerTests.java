package com.pokhara.controllers;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.pokhara.services.ContactService;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ContactControllerTests {

	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ContactService contactService;
	
	@Test
	public void getContact_WithName() throws Exception{
	}
}
