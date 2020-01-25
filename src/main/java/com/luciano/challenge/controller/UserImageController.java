package com.luciano.challenge.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.luciano.challenge.domain.dto.UserImageResponseDto;
import com.luciano.challenge.exception.NoContentException;
import com.luciano.challenge.exception.NotFoundException;
import com.luciano.challenge.exception.PreconditionRequiredException;
import com.luciano.challenge.service.UserImageService;
import com.luciano.challenge.utils.HeaderUtil;

@RestController
@RequestMapping("/user-images")
public class UserImageController {
	
	@Autowired
	private UserImageService userImageService;
	
	@PostMapping("/")
	public ResponseEntity<UserImageResponseDto> createImage(@RequestParam("idUser") String idUser, 
			  @RequestParam("file") MultipartFile file, Model model) {
		try {
			UserImageResponseDto response = userImageService.createImage(file, idUser);
			return new ResponseEntity<>(response,
					HeaderUtil.getHeader(), HttpStatus.CREATED);
		} catch (PreconditionRequiredException | IOException e) {
			return new ResponseEntity<>(null, 
					HeaderUtil.getHeader(), HttpStatus.PRECONDITION_REQUIRED);
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserImageResponseDto>> list(@RequestParam(required = false) String fileName){
		try {
			List<UserImageResponseDto> response = userImageService.getImage(fileName);
			return new ResponseEntity<>(response,
					HeaderUtil.getHeader(), HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new ArrayList<>(), 
					HeaderUtil.getHeader(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/")
	public ResponseEntity<List<UserImageResponseDto>> delete(@RequestParam("idUser") String idUser) {
		try {
			List<UserImageResponseDto> response = userImageService.delete(idUser);
			return new ResponseEntity<>(response,
					HeaderUtil.getHeader(), HttpStatus.OK);
		} catch (NoContentException e) {
			return new ResponseEntity<>(null, 
					HeaderUtil.getHeader(), HttpStatus.NO_CONTENT);
		} catch (PreconditionRequiredException e) {
			return new ResponseEntity<>(null, 
					HeaderUtil.getHeader(), HttpStatus.PRECONDITION_REQUIRED);
		}
	}

}
