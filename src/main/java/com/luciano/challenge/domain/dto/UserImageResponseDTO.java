package com.luciano.challenge.domain.dto;

import java.io.Serializable;

public class UserImageResponseDTO implements Serializable {

	private static final long serialVersionUID = -2232886320323739437L;
	private String idUser;
	private String fileName;
	private String fileType;
	private String uploadStatus;

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "UserImageResponseDTO [idUser=" + idUser + ", fileName=" + fileName + ", fileType=" + fileType
				+ ", uploadStatus=" + uploadStatus + "]";
	}

}
