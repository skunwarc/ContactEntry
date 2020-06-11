package com.pokhara.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
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
	
//	public ContactService(@Autowired ContactRepository contactRepository) {
//		this.contactRepository = contactRepository;
//	}

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

	public List<ContactDto> findAll() {
		Iterable<ContactEntity> contactEntity = contactRepository.findAll();
		List<ContactDto> contactDtos = new ArrayList<>();
		for (ContactEntity entity : contactEntity) {
			ContactDto contactDto = new ContactDto();
			NameDto nameDto = new NameDto();
			NameEntity nameEntity = entity.getNameEntity();
			nameDto.setFirst(nameEntity.getFirst());
			nameDto.setMiddle(nameEntity.getMiddle());
			nameDto.setLast(nameEntity.getLast());
			contactDto.setName(nameDto);

			AddressDto addressDto = new AddressDto();
			AddressEntity addressEntity = entity.getAddressEntity();
			BeanUtils.copyProperties(addressEntity, addressDto);
			contactDto.setAddress(addressDto);

			List<PhoneDto> phoneDto = new ArrayList<>();
			List<PhoneEntity> phoneEntity = entity.getPhoneEntity();
			BeanUtils.copyProperties(phoneEntity, phoneDto);
			contactDto.setPhone(phoneDto);

			contactDto.setEmail(entity.getEmail());
			contactDtos.add(contactDto);
		}

		return contactDtos;
	}

	public ContactDto findById(int id) {
		Optional<ContactEntity> entities = contactRepository.findById(id);
		ContactDto contactDto = null;
		if (entities.isPresent()) {
			contactDto = new ContactDto();
			NameDto nameDto = new NameDto();
			NameEntity nameEntity = entities.get().getNameEntity();
//			nameDto.setFirst(nameEntity.getFirst());
//			nameDto.setMiddle(nameEntity.getMiddle());
//			nameDto.setLast(nameEntity.getLast());
			BeanUtils.copyProperties(nameEntity, nameDto);
			contactDto.setName(nameDto);
			AddressDto addressDto = new AddressDto();
			AddressEntity addressEntity = entities.get().getAddressEntity();
			BeanUtils.copyProperties(addressEntity, addressDto);
			contactDto.setAddress(addressDto);
			List<PhoneDto> phoneDto = new ArrayList<PhoneDto>();
			List<PhoneEntity> phoneEntity = entities.get().getPhoneEntity();
			BeanUtils.copyProperties(phoneEntity, phoneDto);
			contactDto.setPhone(phoneDto);
			contactDto.setEmail(entities.get().getEmail());
		}
		return contactDto;
	}

	public void updateContactById(int id, ContactDto contactDto) {
		ContactEntity contactEntity = contactRepository.findById(id).orElseThrow(()-> new  RuntimeException("Your are trying to uodate id not present."));
		//ContactEntity contactEntity = null;
		//if (entities.isPresent()) {
			//contactEntity = entities.get();
			NameDto nameDto = contactDto.getName();
			NameEntity nameEntity = new NameEntity();
			nameEntity.setFirst(nameDto.getFirst());
			nameEntity.setMiddle(nameDto.getMiddle());
			nameEntity.setLast(nameDto.getLast());
			contactEntity.setNameEntity(nameEntity);
			AddressEntity addressEntity = new AddressEntity();
			AddressDto addressDto = contactDto.getAddress();
			addressEntity.setStreet(addressDto.getStreet());
			addressEntity.setCity(addressDto.getCity());
			addressEntity.setState(addressDto.getState());
			addressEntity.setZip(addressDto.getZip());
			contactEntity.setAddressEntity(addressEntity);
			List<PhoneEntity> phoneEntities = contactEntity.getPhoneEntity();
			List<PhoneDto> phoneDto = contactDto.getPhone();
			for (PhoneDto pDto : phoneDto) {
				PhoneEntity phoneEntity = null;
				for (PhoneEntity pEntity : phoneEntities) {
					if (pDto.getType().equals(pEntity.getType())) {
						phoneEntity = pEntity;
						break;
					}
				}
				if (phoneEntity == null) {
					phoneEntity = new PhoneEntity();
				}
				phoneEntity.setNumber(pDto.getNumber());
				phoneEntity.setType(pDto.getType());
				phoneEntity.setContactEntity(contactEntity);
				phoneEntities.add(phoneEntity);
			}
			contactEntity.setPhoneEntity(phoneEntities);
			contactEntity.setEmail(contactDto.getEmail());

		//}
		contactRepository.save(contactEntity);
	}

	public void deleteById(int id) {
		contactRepository.deleteById(id);
	}

}
