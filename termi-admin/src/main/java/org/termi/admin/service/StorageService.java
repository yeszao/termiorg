package org.termi.admin.service;

import org.termi.common.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    Attachment upload(MultipartFile file) throws IOException;
}