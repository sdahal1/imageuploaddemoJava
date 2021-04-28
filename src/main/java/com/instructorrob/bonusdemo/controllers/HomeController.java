package com.instructorrob.bonusdemo.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.instructorrob.bonusdemo.models.User;
import com.instructorrob.bonusdemo.services.PictureService;
import com.instructorrob.bonusdemo.services.UserService;

@Controller 
public class HomeController {
	
	private final UserService userService;
	private final PictureService pService;
	
	private String UPLOADED_FOLDER="src/main/resources/static/images/";
	
    
    public HomeController(UserService userService, PictureService pService) {
        this.userService = userService;
        this.pService=pService;
        
    }
    
    
	@GetMapping("/dashboard")
	public String dashboard(Model model, HttpSession session) {
		//retrieve the userobject from the db who'se id matches the id stored in session
		Long id = (Long)session.getAttribute("userid");
		User loggedinuser = this.userService.findUserById(id);
		
		model.addAttribute("allusers", this.userService.findAllUsers());
		model.addAttribute("loggedinuser", loggedinuser);
		return "dashboard.jsp";
		
	}
	
	@PostMapping("/dashboard/upload")
	public String uploadPic(@RequestParam("pic") MultipartFile file, @RequestParam("description") String desc, HttpSession session, RedirectAttributes redirectAttributes ) {
		Long id=(Long)session.getAttribute("userid");
		User user = this.userService.findUserById(id);
		if(file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Needz da file to upload");
			return "redirect:/dashboard";
		}
		
		try {
			//these just upload the file to our project's static folder + path we specify
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);
			
			//this is going to actually make a record in our DB about the location of the uploaded file and its description
			String url = "/images/" + file.getOriginalFilename();
			//we now need to send information to the picture service to create a new picture. We need to include the uploader(logged in user), image_url, and description(from the form)
			this.pService.uploadPic(user, url, desc);
			
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		return "redirect:/dashboard";
	}
	
	@GetMapping("/users/{id}")
	public String showUserPage(@PathVariable("id") Long id, Model model) {
		model.addAttribute("userToshow", this.userService.findUserById(id));
		
		
		return "userinfo.jsp";
	}
	
	
	@GetMapping("/follow/{id}")
	public String followUser(@PathVariable("id") Long id, HttpSession session) {
		//get the logged in user
		Long loggedinuserid=(Long)session.getAttribute("userid");
		User user = this.userService.findUserById(loggedinuserid);
		//get the user to be followed
		
		User userToFollow = this.userService.findUserById(id);
		
		this.userService.followAnothaUser(user, userToFollow);
		
		
		
		
		return "redirect:/dashboard";
	}
	
	
	
}
