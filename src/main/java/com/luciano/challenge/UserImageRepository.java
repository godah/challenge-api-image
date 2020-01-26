package com.luciano.challenge;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.luciano.challenge.domain.FsFiles;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;

@Repository
public class UserImageRepository {
	
	private static final String METADATA_FILE_NAME = "metadata.fileName";
	private static final String METADATA_ID_USER = "metadata.idUser";
	
	@Autowired
	private GridFsTemplate gridFsTemplate;
	
	@Autowired
	private MongoOperations mongoOperations;

	public ObjectId save(MultipartFile file, DBObject metaData) throws IOException {
		return gridFsTemplate.store(file.getInputStream(), file.getName(), file.getContentType(), metaData);
	}

	public GridFSFile findById(ObjectId id) {
		return gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id.toString())));
	}

	public GridFSFindIterable findAll() {
		return gridFsTemplate.find(new Query());
	}

	public GridFSFindIterable findByFileName(String fileName) {
		return gridFsTemplate.find(new Query(Criteria.where(METADATA_FILE_NAME).is(fileName)));
	}

	public GridFSFindIterable findByIdUser(String idUser) {
		return gridFsTemplate.find(new Query(Criteria.where(METADATA_ID_USER).is(idUser)));
	}

	public void deleteByUserId(String idUser) {
		gridFsTemplate.delete(new Query(Criteria.where(METADATA_ID_USER).is(idUser)));
	}
	
	public void updateStatusByID(ObjectId id, String status) {
		FsFiles file = mongoOperations.findOne(new Query(Criteria.where("_id").is(id.toString())), FsFiles.class);
		file.getMetadata().setUploadStatus(status);
		mongoOperations.save(file);
	}
	
}
