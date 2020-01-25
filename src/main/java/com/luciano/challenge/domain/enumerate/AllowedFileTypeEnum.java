package com.luciano.challenge.domain.enumerate;

import java.util.HashMap;
import java.util.Map;

public enum AllowedFileTypeEnum {
	JPEG_JFIF("image/jpeg"), GIF("image/gif"), BMP("image/bmp"),PNG("image/png");

	private String type;

	private AllowedFileTypeEnum(String status) {
		this.type = status;
	}

	public String getType() {
		return this.type;
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
