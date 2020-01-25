package com.luciano.challenge.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.luciano.challenge.domain.dto.UserImageResponseDTO;
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

	private final Logger log = LoggerFactory.getLogger(UserImageService.class);

	@Autowired
	private GridFsTemplate gridFsTemplate;

	public UserImageResponseDTO createImage(MultipartFile file, String idUser) throws IOException, PreconditionRequiredException {
		if (AllowedFileTypeEnum.get(file.getContentType()) == null)
			throw new PreconditionRequiredException();
		try {
			DBObject metaData = new BasicDBObject();
			metaData.put(ID_USER, idUser);
			metaData.put(FILE_NAME, file.getOriginalFilename());
			metaData.put(FILE_TYPE, file.getContentType());
			metaData.put(UPLOAD_STATUS, StatusUploadEnum.EM_ANDAMENTO.getStatus());
			ObjectId id = gridFsTemplate.store(file.getInputStream(), file.getName(), file.getContentType(), metaData);
			GridFSFile fileSaved = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id.toString())));
			return createResponse(fileSaved);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new IOException(e.getMessage());
		}
	}

	private UserImageResponseDTO createResponse(GridFSFile fileSaved) {
		UserImageResponseDTO image = new UserImageResponseDTO();
		Document doc = fileSaved.getMetadata();
		image.setIdUser(doc.getString(ID_USER).toString());
		image.setFileName(doc.getString(FILE_NAME).toString());
		image.setFileType(doc.getString(FILE_TYPE).toString());
		image.setUploadStatus(doc.getString(UPLOAD_STATUS));
		return image;
	}

	public List<UserImageResponseDTO> getImage(String fileName) throws NotFoundException {
		GridFSFindIterable files;
		if (fileName == null || fileName.isEmpty()) {
			files = gridFsTemplate.find(new Query());
		} else {
			files = gridFsTemplate.find(new Query(Criteria.where(METADATA_FILE_NAME).is(fileName)));
		}
		if (!files.iterator().hasNext())
			throw new NotFoundException();

		List<UserImageResponseDTO> response = createResponse(files);
		return response;
	}

	public List<UserImageResponseDTO> delete(String idUser) throws NoContentException, PreconditionRequiredException {
		if (idUser == null || idUser.isEmpty()) {
			throw new PreconditionRequiredException();
		} else {
			GridFSFindIterable files = gridFsTemplate.find(new Query(Criteria.where(METADATA_ID_USER).is(idUser)));
			if (!files.iterator().hasNext())
				throw new NoContentException();

			List<UserImageResponseDTO> response = createResponse(files);
			gridFsTemplate.delete(new Query(Criteria.where(METADATA_ID_USER).is(idUser)));
			return response;
		}
	}

	private List<UserImageResponseDTO> createResponse(GridFSFindIterable files) {
		List<UserImageResponseDTO> response = new ArrayList<>();
		for (GridFSFile file : files) {
			UserImageResponseDTO image = new UserImageResponseDTO();
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
