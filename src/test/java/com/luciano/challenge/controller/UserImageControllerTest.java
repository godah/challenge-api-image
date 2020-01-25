package com.luciano.challenge.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luciano.challenge.domain.dto.UserImageResponseDto;
import com.luciano.challenge.domain.enumerate.AllowedFileTypeEnum;
import com.luciano.challenge.domain.enumerate.StatusUploadEnum;

@SpringBootTest
class UserImageControllerTest {

	private static final String ID_USER = "1234";
	private MockMvc mockMvc;
	ObjectMapper mapper;
	
	private MockMultipartFile bmpFile;
	private UserImageResponseDto bmpDto;
	
	private MockMultipartFile pngFile;
	private UserImageResponseDto pngDto;
	
	private MockMultipartFile jpegFile;
	private UserImageResponseDto jpegDto;
	
	private MockMultipartFile gifFile;
	private UserImageResponseDto gifDto;
	
	private MockMultipartFile tifFile;
	
	
	@Autowired
	private UserImageController userImageController;
	
	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(userImageController).build();
		this.mapper = new ObjectMapper();
		
		this.bmpFile = new MockMultipartFile("file", "bmpFile.bmp", AllowedFileTypeEnum.BMP.getType(), 
												"Here is my file".getBytes());
		this.bmpDto = this.createResponseDTO(bmpFile);
		
		this.pngFile = new MockMultipartFile("file", "pngFile.png", AllowedFileTypeEnum.PNG.getType(), 
				"Here is my file".getBytes());
		this.pngDto = this.createResponseDTO(pngFile);
		
		this.jpegFile = new MockMultipartFile("file", "jpegFile.jpg", AllowedFileTypeEnum.JPEG_JFIF.getType(), 
				"Here is my file".getBytes());
		this.jpegDto = this.createResponseDTO(jpegFile);
		
		this.gifFile = new MockMultipartFile("file", "gifFile.gif", AllowedFileTypeEnum.GIF.getType(), 
				"Here is my file".getBytes());
		this.gifDto = this.createResponseDTO(gifFile);
		
		this.tifFile = new MockMultipartFile("file", "tifFile.tif", "image/tif", 
				"Here is my file".getBytes());
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCreateImageBmp() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.multipart("/user-images/")
						.file(bmpFile)
						.param("idUser",bmpDto.getIdUser()))
					.andExpect(MockMvcResultMatchers.status().isCreated())
					.andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(bmpDto)));
	}
	
	@Test
	void testCreateImagePng() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.multipart("/user-images/")
						.file(pngFile)
						.param("idUser",pngDto.getIdUser()))
					.andExpect(MockMvcResultMatchers.status().isCreated())
					.andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(pngDto)));
	}
	
	@Test
	void testCreateImageJpeg() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.multipart("/user-images/")
						.file(jpegFile)
						.param("idUser",jpegDto.getIdUser()))
					.andExpect(MockMvcResultMatchers.status().isCreated())
					.andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(jpegDto)));
	}
	
	@Test
	void testCreateImageGif() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.multipart("/user-images/")
						.file(gifFile)
						.param("idUser",gifDto.getIdUser()))
					.andExpect(MockMvcResultMatchers.status().isCreated())
					.andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(gifDto)));
	}
	
	@Test
	void testCreateImageTif() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.multipart("/user-images/")
						.file(tifFile)
						.param("idUser",ID_USER))
					.andExpect(MockMvcResultMatchers.status().isPreconditionRequired());
	}
	
	@Test
	void testList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/user-images/"))
					.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void testListWithId() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/user-images/")
						.param("fileName", pngDto.getFileName()))
					.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void testListWithIdNotFound() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/user-images/")
						.param("fileName", "xxxxx.xxx"))
					.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	
	@Test
	void testDeleteNoContent() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/user-images/")
						.param("idUser", "!@#$!@#$!@#$!@#$"))
					.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	void testDeletePreConditionRequired() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/user-images/")
						.param("idUser", ""))
					.andExpect(MockMvcResultMatchers.status().isPreconditionRequired());
	}
	
	@Test
	void testDelete() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/user-images/")
						.param("idUser", ID_USER))
					.andExpect(MockMvcResultMatchers.status().isOk());
	}

	private UserImageResponseDto createResponseDTO(MockMultipartFile file) {
		UserImageResponseDto dto = new UserImageResponseDto();
		dto.setFileName(file.getOriginalFilename());
		dto.setFileType(file.getContentType());
		dto.setUploadStatus(StatusUploadEnum.EM_ANDAMENTO.getStatus());
		dto.setIdUser(ID_USER);
		return dto;
	}
}