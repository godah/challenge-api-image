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
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.luciano.challenge.domain.dto.UserImageResponseDTO;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;

@Service
public class UserImageService {
	private final Logger log = LoggerFactory.getLogger(UserImageService.class);

	@Autowired
	private GridFsTemplate gridFsTemplate;

	@Autowired
	private GridFsOperations operations;

	public String createImage(MultipartFile file, String idUser) throws IOException {
		try {
			DBObject metaData = new BasicDBObject();
			metaData.put("id_user", idUser);
			metaData.put("file_name", file.getName());
			metaData.put("file_type", file.getContentType());
			metaData.put("upload_status", "Em Andamento");
			ObjectId id = gridFsTemplate.store(file.getInputStream(), file.getName(), file.getContentType(), metaData);
			return id.toString();
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new IOException(e.getMessage());
		}
	}

	public List<UserImageResponseDTO> getImage(String id) {
		GridFSFindIterable files;
		if(id == null || id.isEmpty()) {
			files = gridFsTemplate.find(new Query(Criteria.where("_id").all(id)));//TODO verificar
		}else {			
			files = gridFsTemplate.find(new Query(Criteria.where("_id").is(id)));
		}
		if(!files.iterator().hasNext())
			return new ArrayList<>();
		
		List<UserImageResponseDTO> response = new ArrayList<>();
		for (GridFSFile file : files) {
			UserImageResponseDTO image = new UserImageResponseDTO();
			Document doc = file.getMetadata();
			image.setIdUser(doc.getString("id_user").toString());
			image.setFileName(doc.getString("file_name").toString());
			image.setFileType(doc.getString("file_type").toString());
			image.setUploadStatus(doc.getString("upload_status"));
			response.add(image);
		}
		return response;
	}
}
