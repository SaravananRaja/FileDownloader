package com.download.spring.validation;

import com.download.spring.constant.FileDownloaderConstants;

public class FileDownloaderValidation {


	public boolean validateUserName(String pUserName) {
		return !FileDownloaderConstants.EMPTY_STRING.equalsIgnoreCase(pUserName);
	}

	public boolean validateUserPassword(String pPassword) {
		return !FileDownloaderConstants.EMPTY_STRING.equalsIgnoreCase(pPassword);
	}

	public boolean validateSourceLocation(String pSourceLocation) {
		return !FileDownloaderConstants.EMPTY_STRING.equalsIgnoreCase(pSourceLocation);
	}

	public boolean validateDestinationLocation(String pDestinationLocation) {
		return !FileDownloaderConstants.EMPTY_STRING.equalsIgnoreCase(pDestinationLocation);
	}


}
