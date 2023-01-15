package com.turkcell.northwind.business.concretes;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.turkcell.northwind.business.abstracts.UserService;
import com.turkcell.northwind.business.dtos.UserDto;
import com.turkcell.northwind.core.utilities.mapping.ModelMapperService;
import com.turkcell.northwind.dataAccess.abstracts.UserDao;
import com.turkcell.northwind.entities.concretes.UserEntity;

@Service
public class UserManager implements UserService {

	private UserDao userDao;
	private BCryptPasswordEncoder brBCryptPasswordEncoder;
	private ModelMapperService modelMapperService;

	@Autowired
	public UserManager(UserDao userDao, BCryptPasswordEncoder brBCryptPasswordEncoder,
			ModelMapperService modelMapperService) {
		this.userDao = userDao;
		this.brBCryptPasswordEncoder = brBCryptPasswordEncoder;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity userEntity = this.userDao.findByEmail(username);

		if (userEntity == null) {
			throw new UsernameNotFoundException(username);
		}

		// ArrayList de yetkilerin okunması yapılır.. ( Veri tabanında rol tablosu ile
		// yapılır )

		return new User(userEntity.getEmail(), userEntity.getEncrpytedPassword(), true, true, true, true,
				new ArrayList<>());
	}

	@Override
	public UserDto createUser(UserDto userDto) {

		userDto.setId(UUID.randomUUID().toString());
		userDto.setEncryptedPassword(this.brBCryptPasswordEncoder.encode(userDto.getPassword()));

		UserEntity user = this.modelMapperService.forRequest().map(userDto, UserEntity.class);

		this.userDao.save(user);

		return this.modelMapperService.forDto().map(user, UserDto.class);
	}

	@Override
	public UserDto getUserDetailsByEmail(String email) {

		UserEntity user = this.userDao.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException(email);
		}

		return this.modelMapperService.forDto().map(user, UserDto.class);
	}

}
