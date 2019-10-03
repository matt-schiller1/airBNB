package com.banjo.transmittr.controllers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringJoiner;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.banjo.transmittr.models.HashTag;
import com.banjo.transmittr.models.Transmit;
import com.banjo.transmittr.models.User;
import com.banjo.transmittr.services.HashTagService;
import com.banjo.transmittr.services.TransmitService;
import com.banjo.transmittr.services.UserService;
import com.banjo.transmittr.validator.UserValidator;


@Controller
public class UserController {
	private UserService uService;
	private UserValidator uValidator;
	private TransmitService tService;
	private HashTagService hService;

	
	public UserController(UserService uService, UserValidator uValidator, TransmitService tService, HashTagService hService) {
		this.uService = uService;
		this.uValidator = uValidator;
		this.tService = tService;
		this.hService = hService;

	}
	
    @RequestMapping("/registration")
    public String registerForm(@Valid @ModelAttribute("user") User user) {
        return "registrationPage.jsp";
    }
    
    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
    	uValidator.validate(user, result);
    	if (result.hasErrors()) {

            return "registrationPage.jsp";
        }
        uService.saveWithUserRole(user);
        return "redirect:/login";
    }
    
    @RequestMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model) {
        if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successful!");
        }
        return "loginPage.jsp";
    }
    
    @RequestMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("currentUser", uService.findByUsername(username));
        return "adminPage.jsp";
    }
    
    @RequestMapping(value = {"/", "/home"})
    public String home(@Valid @ModelAttribute("newTransmit") Transmit transmit, @ModelAttribute("user") User user, Principal principal, Model model, HttpSession httpSession) {
        String username = principal.getName();
        uService.getFollowing(username);
        User usern = uService.findByUsername(username);
//        Transmit tmit = tService.findTransmit(usern.getId());
//        String post = tmit.getContent().toString();
//        String[] split = post.split(" ");
//        for (int i = 0; i < split.length; i++) {
//        	if (split[i].charAt(0) == '#'){
//        		split[i] = "<a href='/search'>" + split[i] + "</a>";
//        	}        	
//        }
//        String joined = String.join(" ", split);       
//        System.out.println(joined);

        model.addAttribute("currentUser", uService.findByUsername(username));
        model.addAttribute("allUsers", uService.allUsers());
        model.addAttribute("following", usern.getFollowings());
        model.addAttribute("whoFollowing", usern.getFollowings().size());
        model.addAttribute("followers", usern.getFollowers().size());
        List<Transmit> posts = tService.allPosts(usern.getId());
        model.addAttribute("posts", posts);

        return "home.jsp";
    }
    
    @RequestMapping("/transmit/{id}/{action}")
    public String follow(@PathVariable("id") Long id, @PathVariable("action") String action, Principal principal){
    	String username = principal.getName();
    	User logged_user = uService.findByUsername(username);
    	User followed_user = uService.findUser(id);
    	boolean follow = (action.equals("follow"));
    	uService.followUser(logged_user, followed_user, follow);
    	uService.updateUser(logged_user);
    	return "redirect:/home";
    }
    
    @RequestMapping("/transmit/{id}/{vote}/u")
    public String vote(@PathVariable("id") Long id, @PathVariable("vote") String vote, Principal principal){
    	String username = principal.getName();
    	User user = uService.findByUsername(username);
    	User userid = uService.findUser(user.getId());
    	Transmit tmit = tService.findTransmit(id);
    	tService.addVote(tmit, userid, vote);
    	return "redirect:/home";
    }
    
    @PostMapping("/post")
    public String post(@Valid @ModelAttribute("newTransmit") Transmit transmit, BindingResult result, Model model, HttpSession session) {
		if (result.hasErrors()) {	
	        return "registrationPage.jsp";
	    }
		//Split post to check for hashtags
		String[] split = transmit.getContent().split(" ");
		//Create Storage for Tags
		List<HashTag> hashtag = new ArrayList<>();
		//Iterate and Store Tags
		for(int i = 0; i < split.length; i++) {
			if(split[i].charAt(0) == ('#')) {
				HashTag hashTag = hService.findOrCreate(split[i]);	
				hService.addUniqueTag(hashtag, hashTag);
			}			
		}
		transmit.setHashtags(hashtag);
		
	    tService.createTransmit(transmit);	    
	    return "redirect:/home";
    }
    
    @RequestMapping("/{id}/edit")
    public String Edit(@Valid @ModelAttribute("editUser") User user, @PathVariable("id") Long id, Model model) {
    	User u = uService.findUser(id);
    	model.addAttribute("details", u);
    	return "edit.jsp";
    }
    
    @PutMapping("/updateinfo/u")
    public String update(@Valid @ModelAttribute("editUser") User user, @RequestParam("picture") MultipartFile imageFile, RedirectAttributes redirectAttributes ) {
    	if(imageFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
    	}
    	try {
    		String folder = "/img/";
    		byte[] bytes = imageFile.getBytes();
    		Path path = Paths.get(folder + imageFile.getOriginalFilename());
    		Files.write(path, bytes);
    		user.setPic("/img/" + imageFile.getOriginalFilename());
    	} catch (IOException e) {
            e.printStackTrace();
        }
    	
    	uService.updateUser(user);
    	return "redirect:/home";
    }
}
