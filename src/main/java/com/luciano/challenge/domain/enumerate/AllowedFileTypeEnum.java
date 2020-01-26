package com.luciano.challenge.domain.enumerate;

import java.util.HashMap;
import java.util.Map;

public enum AllowedFileTypeEnum {
	JPEG_JFIF("image/jpeg", "JPEG/JFIF"), GIF("image/gif", "GIF"), BMP("image/bmp", "BMP"),PNG("image/png", "PNG");

	private String contentType;
	private String fileType;

	private AllowedFileTypeEnum(String status, String fileType) {
		this.contentType = status;
		this.fileType = fileType;
	}

	public String getType() {
		return this.contentType;
	}
	
	public String getFileType() {
		return this.fileType;
	}
	
    private static final Map<String, AllowedFileTypeEnum> lookup = new HashMap<>();
  
    static
    {
        for(AllowedFileTypeEnum type : AllowedFileTypeEnum.values())
        {
            lookup.put(type.getType(), type);
        }
    }
  
    public static AllowedFileTypeEnum get(String type) 
    {
        return lookup.get(type);
    }
}
