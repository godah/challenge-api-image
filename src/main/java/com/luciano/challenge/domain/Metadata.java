package com.luciano.challenge.domain;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Metadata implements Serializable {

	private static final long serialVersionUID = -5148165623166560618L;

	private String _contentType;
	private String idUser;
	private String fileName;
	private String fileType;
	private String uploadStatus;
	private String _class;

	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

}
