package com.luciano.challenge.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	private static final String METADATA_FILE_NAME = "metadata.fileName";

	private static final String UPLOAD_STATUS = "uploadStatus";

	private static final String FILE_TYPE = "fileType";

	private static final String FILE_NAME = "fileName";

	private static final String ID_USER = "idUser";

	private static final String METADATA_ID_USER = "metadata.idUser";

	@Autowired
	private GridFsTemplate gridFsTemplate;

	public UserImageResponseDto createImage(MultipartFile file, String idUser) throws PreconditionRequiredException, IOException {
		try {
			if (AllowedFileTypeEnum.get(file.getContentType()) == null)
				throw new PreconditionRequiredException("Content Type not allowed.");
			DBObject metaData = new BasicDBObject();
			metaData.put(ID_USER, idUser);
			metaData.put(FILE_NAME, file.getOriginalFilename());
			metaData.put(FILE_TYPE, file.getContentType());
			metaData.put(UPLOAD_STATUS, StatusUploadEnum.EM_ANDAMENTO.getStatus());
			ObjectId id = gridFsTemplate.store(file.getInputStream(), file.getName(), file.getContentType(), metaData);
			GridFSFile fileSaved = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id.toString())));
			return createResponse(fileSaved);
		} catch (IOException |PreconditionRequiredException e) {
			throw new PreconditionRequiredException(e.getMessage());
		}
	}

	private UserImageResponseDto createResponse(GridFSFile fileSaved) {
		UserImageResponseDto image = new UserImageResponseDto();
		Document doc = fileSaved.getMetadata();
		image.setIdUser(doc.getString(ID_USER).toString());
		image.setFileName(doc.getString(FILE_NAME).toString());
		image.setFileType(doc.getString(FILE_TYPE).toString());
		image.setUploadStatus(doc.getString(UPLOAD_STATUS));
		return image;
	}

	public List<UserImageResponseDto> getImage(String fileName) throws NotFoundException {
		GridFSFindIterable files;
		if (fileName == null || fileName.isEmpty()) {
			files = gridFsTemplate.find(new Query());
		} else {
			files = gridFsTemplate.find(new Query(Criteria.where(METADATA_FILE_NAME).is(fileName)));
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
			GridFSFindIterable files = gridFsTemplate.find(new Query(Criteria.where(METADATA_ID_USER).is(idUser)));
			if (!files.iterator().hasNext())
				throw new NoContentException();

			List<UserImageResponseDto> response = createResponse(files);
			gridFsTemplate.delete(new Query(Criteria.where(METADATA_ID_USER).is(idUser)));
			return response;
		}
	}

	private List<UserImageResponseDto> createResponse(GridFSFindIterable files) {
		List<UserImageResponseDto> response = new ArrayList<>();
		for (GridFSFile file : files) {
			UserImageResponseDto image = new UserImageResponseDto();
			Document doc = file.getMetadata();
			image.setIdUser(doc.getString(ID_USER).toString());
			image.setFileName(doc.getString(FILE_NAME).toString());
			image.setFileType(doc.getString(FILE_TYPE).toString());
			image.setUploadStatus(doc.getString(UPLOAD_STATUS));
			response.add(image);
		}
		return response;
	}
}
