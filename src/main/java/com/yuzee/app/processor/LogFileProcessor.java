package com.yuzee.app.processor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service("LogFileProcessor")
@Slf4j
public class LogFileProcessor {

	String tmpDir = System.getProperty("java.io.tmpdir");
	
	@Autowired
	EmailProcessor emailProcessor;
	
	public void appendToLogFile(String fileId, List<String> errors) throws IOException {
		log.debug("FileId for appending to file : "+fileId);
		try(FileWriter writer = new FileWriter(tmpDir+File.separator+fileId+".csv",true)) {
			errors.stream().forEach(error -> {
				try {
					writer.append(error+"\n");
				} catch (IOException e) {
					log.error("Error while writing to file {}",fileId,e);
				}
			});	
		}
	}
	
	public void sendFailureEmail(String fileId, String subject) {
		List<File> files = new ArrayList<>();
		files.add(new File(tmpDir+File.separator+fileId+".csv"));
		emailProcessor.sendEmailWithAttachment(subject, "API", files);
	}
}
