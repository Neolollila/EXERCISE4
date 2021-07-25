package net.mandrik;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {

	@Autowired
	private UserRepository userRepo;

	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		
		return "signup_form";
	}
	
	@PostMapping("/process_register")
	public String processRegister(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		user.setStatus("1");
		user.setDatelastlogin(LocalDateTime.now());
		user.setDateregistration(LocalDateTime.now());
		userRepo.save(user);
		return "register_success";
	}
	@PostMapping("/block")
	public String blockUsers(@RequestParam("listUsers") String param) {
		List<Long> list = new ArrayList<>();
		if (param.length() != 0) {
			for(String value : Arrays.asList(param.split(","))) {
				list.add(Long.parseLong(value));
			}
		}

		User curUser = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		curUser = userRepo.findByIdCurUser(curUser.getId());
		if (curUser == null || curUser.getStatus().equals("0")){
			SecurityContextHolder.clearContext();
			return "redirect:/login";
		}
		userRepo.setStatusForUsersByIds("0",list);
		if (list.indexOf(curUser.getId())>=0) {
			SecurityContextHolder.clearContext();
			return "redirect:/login";
		}

		return "redirect:/users";
	}
	@PostMapping("/unblock")
	public String unblockUsers(@RequestParam("listUsers") String param) {
		List<Long> list = new ArrayList<>();
		if (param.length() != 0) {
			for(String value : Arrays.asList(param.split(","))) {
				list.add(Long.parseLong(value));
			}
		}
		User curUser = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		curUser = userRepo.findByIdCurUser(curUser.getId());
		if (curUser == null || curUser.getStatus().equals("0")){
			SecurityContextHolder.clearContext();
			return "redirect:/login";
		}
		userRepo.setStatusForUsersByIds("1",list);
		return "redirect:/users";
	}

	@PostMapping("/delete")
	public String deleteUsers(@RequestParam("listUsers") String param) {
		List<Long> list = new ArrayList<>();
		if (param.length() != 0) {
			for(String value : Arrays.asList(param.split(","))) {
				list.add(Long.parseLong(value));
			}
		}
		User curUser = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		curUser = userRepo.findByIdCurUser(curUser.getId());
		if (curUser == null || curUser.getStatus().equals("0")){
			SecurityContextHolder.clearContext();
			return "redirect:/login";
		}
		userRepo.deleteUsersWithIds(list);
		if (list.indexOf(curUser.getId())>=0) {
			SecurityContextHolder.clearContext();
			return "redirect:/login";
		}

		return "redirect:/users";
	}
	
	@GetMapping("/users")
	public String listUsers(Model model) {
		List<User> listUsers = userRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
		model.addAttribute("listUsers", listUsers);
		User curUser = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		curUser = userRepo.findByIdCurUser(curUser.getId());
		if (curUser == null || curUser.getStatus().equals("0")){
			SecurityContextHolder.clearContext();
			return "redirect:/login";
		}
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication().getDetails();
		//String username = auth.getName();
		return "users";
	}
}
