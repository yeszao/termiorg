package org.termi.admin.service;

import com.google.common.io.Files;
import org.termi.common.repository.AttachmentRepository;
import org.termi.common.entity.Attachment;
import org.termi.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    private AttachmentRepository repository;

    @Value("${upload.dir}")
    private String uploadDir;

    @Value("${upload.base-url}")
    private String baseUrl;

    @Override
    public Attachment upload(MultipartFile file) throws IOException {
        Assert.notNull(file, "Upload file cannot be empty");

        String name = file.getOriginalFilename();
        Assert.notNull(name, "Filename can not be empty");
        String extension = Files.getFileExtension(name);

        // Todo: validate file extension

        String newFilename = StringUtil.getUuid() + "." + extension;
        Path relativePath = Paths.get(newFilename.substring(0, 2), newFilename);
        Path fullPath = Paths.get(uploadDir, relativePath.toString());
        Files.createParentDirs(fullPath.toFile());

        // save to local
        file.transferTo(fullPath);

        // save to database
        Dimension dimension = getImageDimension(file);
        Attachment storage = Attachment.builder()
                .name(name)
                .uri(relativePath.toString())
                .width((int) dimension.getWidth())
                .height((int) dimension.getHeight())
                .size(file.getSize())
                .mime(file.getContentType())
                .extension(extension)
                .build();
        repository.save(storage);

        return storage;
    }

    private static Dimension getImageDimension(MultipartFile file) throws IOException {
        if (Objects.isNull(file.getContentType()) || !file.getContentType().startsWith("image/")) {
            return new Dimension(0, 0);
        }

        if ("image/webp".equals(file.getContentType())) {
            Optional<Dimension> webpDimension = getWebpDimension(file.getInputStream());
            return webpDimension.orElseGet(() -> new Dimension(0, 0));
        }

        BufferedImage bi = ImageIO.read(file.getInputStream());
        if (Objects.isNull(bi)) {
            return new Dimension(0, 0);
        }
        return new Dimension(bi.getWidth(), bi.getHeight());
    }

    // todo: it is always 0 now, need to improve
    public static Optional<Dimension> getWebpDimension(InputStream is) throws IOException {
        byte[] data = is.readNBytes(30);
        if (new String(Arrays.copyOfRange(data, 0, 4)).equals("RIFF") && data[15] == 'X') {
            int width = 1 + get24bit(data, 24);
            int height = 1 + get24bit(data, 27);

            if ((long) width * height <= 4294967296L) {
                return Optional.of(new Dimension(width, height));
            }
        }

        return Optional.empty();
    }

    private static int get24bit(byte[] data, int index) {
        return data[index] & 0xFF | (data[index + 1] & 0xFF) << 8 | (data[index + 2] & 0xFF) << 16;
    }
}