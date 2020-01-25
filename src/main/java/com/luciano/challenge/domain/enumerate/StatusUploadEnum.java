package com.luciano.challenge.domain.enumerate;

public enum StatusUploadEnum {
	EM_ANDAMENTO("Em andamento"), FALHA("falha"), CONCLUIDO("concluído");

	private String status;

	private StatusUploadEnum(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}
}
