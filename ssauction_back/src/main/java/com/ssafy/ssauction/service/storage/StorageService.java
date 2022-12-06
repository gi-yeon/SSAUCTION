package com.ssafy.ssauction.service.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;


public interface StorageService {

    void init();

    void store(MultipartFile file, String saveName, String type);

    Stream<Path> loadAll();

    Path load(String filename, String type);

    Resource loadAsResource(String filename, String type);

    void deleteAll();

}