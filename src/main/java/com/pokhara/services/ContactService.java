package com.pokhara.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pokhara.dtos.AddressDto;
import com.pokhara.dtos.ContactDto;
import com.pokhara.dtos.NameDto;
import com.pokhara.dtos.PhoneDto;
import com.pokhara.entities.AddressEntity;
import com.pokhara.entities.ContactEntity;
import com.pokhara.entities.NameEntity;
import com.pokhara.entities.PhoneEntity;
import com.pokhara.repositories.ContactRepository;

@Service
public class ContactService {

	@Autowired
	private ContactRepository contactRepository;

	public void createContact(ContactDto dto) {

			ContactEntity contactEntity = new ContactEntity();
			contactEntity.setEmail(dto.getEmail());

			NameEntity nameEntity = new NameEntity();
			NameDto nameDto = dto.getName();
			nameEntity.setFirst(nameDto.getFirst());
			nameEntity.setMiddle(nameDto.getMiddle());
			nameEntity.setLast(nameDto.getLast());
			nameEntity.setContactEntity(contactEntity);

			contactEntity.setNameEntity(nameEntity);

			AddressEntity addressEntity = new AddressEntity();
			AddressDto addressDto = dto.getAddress();

			addressEntity.setStreet(addressDto.getStreet());
			addressEntity.setCity(addressDto.getCity());
			addressEntity.setState(addressDto.getState());
			addressEntity.setZip(addressDto.getZip());
			addressEntity.setContactEntity(contactEntity);

			contactEntity.setAddressEntity(addressEntity);

			List<PhoneEntity> phoneEntities = new ArrayList<PhoneEntity>();
			List<PhoneDto> phoneDto = dto.getPhone();

			for (PhoneDto phone : phoneDto) {
				PhoneEntity phoneEntity = new PhoneEntity();
				phoneEntity.setNumber(phone.getNumber());
				phoneEntity.setType(phone.getType());
				phoneEntity.setContactEntity(contactEntity);

				phoneEntities.add(phoneEntity);
			}
			contactEntity.setPhoneEntity(phoneEntities);

			contactRepository.save(contactEntity);
		
	}

}
