package com.example.demo.serviceImpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repository.UserRepository;

@Service
public class SecurityServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		com.example.demo.entities.User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("user not found...");
		} else {
			return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
		}
	}

}
