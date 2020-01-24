package com.luciano.challenge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.luciano.challenge.domain.Image;
import com.luciano.challenge.domain.enumeration.ImageStatusEnum;
import com.luciano.challenge.repository.ImageRepository;

@Service
public class ImageService {
	private final Logger log = LoggerFactory.getLogger(ImageService.class);
	
	@Autowired
	private ImageRepository imageRepository;
	
	public Image createImage(MultipartFile multipartFile, String idUser) {
		Image image = new Image(idUser, multipartFile.getOriginalFilename(),
				multipartFile.getContentType(), ImageStatusEnum.EM_ANDAMENTO);
		try {
			image = imageRepository.save(image);
		} catch (Exception e) {
			log.error(e.getMessage());
			image.setStatus(ImageStatusEnum.FALHA);
			return imageRepository.save(image);
		}finally {
			image.setStatus(ImageStatusEnum.CONCLUIDO);
		}
		return imageRepository.save(image);
		
	}
}
