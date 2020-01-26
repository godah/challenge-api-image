package com.luciano.challenge.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.luciano.challenge.UserImageRepository;
import com.luciano.challenge.domain.dto.UserImageResponseDto;
import com.luciano.challenge.domain.enumerate.AllowedFileTypeEnum;
import com.luciano.challenge.domain.enumerate.StatusUploadEnum;
import com.luciano.challenge.exception.NoContentException;
import com.luciano.challenge.exception.NotFoundException;
import com.luciano.challenge.exception.PreconditionRequiredException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;

@Service
public class UserImageService {

	private final Logger log = LoggerFactory.getLogger(UserImageService.class);

	private static final String UPLOAD_STATUS = "uploadStatus";
	private static final String FILE_TYPE = "fileType";
	private static final String FILE_NAME = "fileName";
	private static final String ID_USER = "idUser";

	@Autowired
	private UserImageRepository userImageRepository;

	public UserImageResponseDto createImage(MultipartFile file, String idUser)
			throws PreconditionRequiredException, IOException {
		ObjectId id = new ObjectId();
		try {
			if (AllowedFileTypeEnum.get(file.getContentType()) == null)
				throw new PreconditionRequiredException("Content Type not allowed.");
			DBObject metaData = new BasicDBObject();
			metaData.put(ID_USER, idUser);
			metaData.put(FILE_NAME, file.getOriginalFilename());
			metaData.put(FILE_TYPE, file.getContentType());
			metaData.put(UPLOAD_STATUS, StatusUploadEnum.EM_ANDAMENTO.getStatus());
			id = userImageRepository.save(file, metaData);
			userImageRepository.updateStatusByID(id, StatusUploadEnum.CONCLUIDO.getStatus());
			GridFSFile fileSaved = userImageRepository.findById(id);
			return createResponse(fileSaved);
		} catch (IOException e) {
			userImageRepository.updateStatusByID(id, StatusUploadEnum.FALHA.getStatus());
			log.error(e.getMessage());
			throw new IOException(e.getMessage());
		} catch (PreconditionRequiredException e) {
			throw new PreconditionRequiredException(e.getMessage());
		}
	}

	private UserImageResponseDto createResponse(GridFSFile fileSaved) {
		UserImageResponseDto image = buildUserImageResponseDto(fileSaved);
		return image;
	}

	public List<UserImageResponseDto> getImage(String fileName) throws NotFoundException {
		GridFSFindIterable files;
		if (fileName == null || fileName.isEmpty()) {
			files = userImageRepository.findAll();
		} else {
			files = userImageRepository.findByFileName(fileName);
		}
		if (!files.iterator().hasNext())
			throw new NotFoundException();

		List<UserImageResponseDto> response = createResponse(files);
		return response;
	}

	public List<UserImageResponseDto> delete(String idUser) throws NoContentException, PreconditionRequiredException {
		if (idUser == null || idUser.isEmpty()) {
			throw new PreconditionRequiredException();
		} else {
			GridFSFindIterable files = userImageRepository.findByIdUser(idUser);
			if (!files.iterator().hasNext())
				throw new NoContentException();
			List<UserImageResponseDto> response = createResponse(files);
			userImageRepository.deleteByUserId(idUser);
			return response;
		}
	}

	private List<UserImageResponseDto> createResponse(GridFSFindIterable files) {
		List<UserImageResponseDto> response = new ArrayList<>();
		for (GridFSFile file : files) {
			UserImageResponseDto image = buildUserImageResponseDto(file);
			response.add(image);
		}
		return response;
	}

	private UserImageResponseDto buildUserImageResponseDto(GridFSFile fileSaved) {
		UserImageResponseDto image = new UserImageResponseDto();
		Document doc = fileSaved.getMetadata();
		image.setIdUser(doc.getString(ID_USER).toString());
		image.setFileName(doc.getString(FILE_NAME).toString());
		AllowedFileTypeEnum fileType = AllowedFileTypeEnum.get(doc.getString(FILE_TYPE).toString());
		image.setFileType(fileType.getFileType());
		image.setUploadStatus(doc.getString(UPLOAD_STATUS));
		return image;
	}
}
