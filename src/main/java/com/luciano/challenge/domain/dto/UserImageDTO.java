package com.luciano.challenge.domain.dto;

import java.io.InputStream;
import java.io.Serializable;

public class UserImageDTO implements Serializable {

	private static final long serialVersionUID = 1919110443930840854L;
	private String idUser;
	private InputStream stream;

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	@Override
	public String toString() {
		return "ImageDTO [idUser=" + idUser + ", stream=" + stream + "]";
	}

}
