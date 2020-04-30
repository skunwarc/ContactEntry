package com.pokhara.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pokhara.dtos.ContactDto;
import com.pokhara.entities.ContactEntity;
import com.pokhara.services.ContactService;

@RestController
@RequestMapping("/v2")
public class ContactController {

    @Autowired
    ContactService contactService;

    @PostMapping("/contact")
    public void createContactInfo(@RequestBody ContactDto contactDto) {
        contactService.createContact(contactDto);
		
    }
    //@GetMapping("/contacts")
//	public void getContact(@PathVariable int id) {
//		contactService.findAll();
//	}

}
