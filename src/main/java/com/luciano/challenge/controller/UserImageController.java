package com.luciano.challenge.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.luciano.challenge.domain.dto.UserImageResponseDTO;
import com.luciano.challenge.service.UserImageService;
import com.luciano.challenge.utils.HeaderUtil;

@RestController
@RequestMapping("/user-image")
public class UserImageController {
	
	@Autowired
	private UserImageService userImageService;
	
	@PostMapping("/")
	public ResponseEntity<String> createImage(@RequestParam("iduser") String idUser, 
			  @RequestParam("file") MultipartFile file, Model model) {
		String id;
		try {
			id = userImageService.createImage(file, idUser);
			String response = "redirect:/user-image/" + id;
			return ResponseEntity.created(new URI(response))
	            .headers(HeaderUtil.getHeader())
	            .body(response);
		} catch (IOException | URISyntaxException e) {
			return new ResponseEntity<>(new String(), 
					HeaderUtil.getHeader(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserImageResponseDTO>> list(@RequestParam("iduser") String idUser){
		if(idUser == null || idUser.isEmpty())
			return new ResponseEntity<>(new ArrayList<>(), 
					HeaderUtil.getHeader(), HttpStatus.PRECONDITION_REQUIRED);

		try {
			List<UserImageResponseDTO> response = userImageService.getImage(idUser);
			if(response.isEmpty())
				return new ResponseEntity<>(response, 
						HeaderUtil.getHeader(), HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(response,
					HeaderUtil.getHeader(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ArrayList<>(), 
					HeaderUtil.getHeader(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
