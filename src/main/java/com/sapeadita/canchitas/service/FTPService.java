package com.sapeadita.canchitas.service;

import com.sapeadita.canchitas.exceptions.FTPErrors;
import java.io.File;

public interface FTPService {
	public void connectToFTP(String host, String user, String pass) throws FTPErrors;
	public void uploadFileToFTP(File file, String ftpHostDir , String serverFilename) throws FTPErrors;
	public void downloadFileFromFTP(String ftpRelativePath, String copytoPath) throws FTPErrors;
	public void disconnectFTP() throws FTPErrors;
}
