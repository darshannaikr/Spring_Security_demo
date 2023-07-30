package com.example.demo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.utility.JwtUtility;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtility jwtUtility;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private SecurityServiceImpl securityServiceImpl;

	@Override
	public User signUp(User user) throws Exception {

		User u = userRepository.findByUsername(user.getUsername());

		if (u == null) {
			User u1 = new User();
			u1.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			u1.setUsername(user.getUsername());
			u1.setEmail(user.getEmail());
			u1.setPlace(user.getPlace());

			return userRepository.save(u1);
		} else {
			throw new Exception("user alreday exists...");
		}
	}

	@Override
	public String userLogin(String username, String password) throws Exception {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}

		final UserDetails userDetails = securityServiceImpl.loadUserByUsername(username);

		final String token = jwtUtility.generateToken(userDetails);

		return token;
	}

	@Override
	public List<User> getAllUser() throws Exception {

		List<User> users = userRepository.findAll();
		if (users.size() > 0) {
			return users;
		} else {
			throw new Exception("user data not found...");
		}
	}
}
