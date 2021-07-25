package net.mandrik;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username,"1");
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		user.setDatelastlogin(LocalDateTime.now());
		userRepo.save(user);
		return new CustomUserDetails(user);
	}

}
