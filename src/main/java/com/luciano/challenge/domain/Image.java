package com.luciano.challenge.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import com.luciano.challenge.domain.enumeration.FileTypeEnum;
import com.luciano.challenge.domain.enumeration.ImageStatusEnum;

public class Image implements Serializable {

	private static final long serialVersionUID = -3704743372562328009L;

	@Id
	private String id;

	private String idUser;
	private String fileName;
	private FileTypeEnum fileType;
	private ImageStatusEnum status;

	public Image() {
	}

	public Image(String idUser, String fileName, String fileType, ImageStatusEnum status) {
		super();
		this.idUser = idUser;
		this.fileName = fileName;
		this.fileType = FileTypeEnum.JPEG_JFIF; //TODO verificar o tipo para selecionar enum
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public FileTypeEnum getFileType() {
		return fileType;
	}

	public void setFileType(FileTypeEnum fileType) {
		this.fileType = fileType;
	}

	public ImageStatusEnum getStatus() {
		return status;
	}

	public void setStatus(ImageStatusEnum status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Image [id=" + id + ", idUser=" + idUser + ", fileName=" + fileName + ", fileType=" + fileType
				+ ", status=" + status + "]";
	}

}