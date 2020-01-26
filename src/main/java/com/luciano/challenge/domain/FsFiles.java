package com.luciano.challenge.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "fs.files")
@SuppressWarnings("unused")
public class FsFiles {

	@Id
	private String _id;

	private String filename;
	private Long length;
	private Long chunkSize;
	private LocalDateTime uploadDate;
	private String md5;
	private Metadata metadata;

	public Metadata getMetadata() {
		return metadata;
	}

}
