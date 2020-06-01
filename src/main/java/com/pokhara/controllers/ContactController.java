package com.pokhara.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pokhara.dtos.ContactDto;
import com.pokhara.entities.ContactEntity;
import com.pokhara.services.ContactService;

import javassist.NotFoundException;

@CrossOrigin(origins = {"http://localhost:8080/", "http://localhost:4200"})
@RestController
@RequestMapping("/v2")
public class ContactController {

	@Autowired
	ContactService contactService;

	@PostMapping("/contacts")
	public void createContactInfo(@RequestBody ContactDto contactDto) {
			contactService.createContact(contactDto);
	}

	@GetMapping("/contacts/")
	public List<ContactDto> getContact()throws NotFoundException {
		return contactService.findAll();
	}

	@GetMapping("/contacts/{id}")
	public ContactDto getContactById(@PathVariable int id) throws NotFoundException {
		return contactService.findById(id);
	}

	@PutMapping("/contacts/{id}")
	public void updateContact(@PathVariable int id, @RequestBody ContactDto contactDto) throws NotFoundException {
		contactService.updateContactById(id, contactDto);
	}

	@DeleteMapping("/contacts/{id}")
	public void deleteContact(@PathVariable int id) {
		contactService.deleteById(id);
	}

}
