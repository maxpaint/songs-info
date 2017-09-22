package com.maxpaint.fileinfo.upload.flat.service.parser;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.Objects.nonNull;

@Service
public class TxtFileParser {

    public List<FileInfo> parse(byte[] bytes) {
        String fileContent = new String(bytes);

        return stream(fileContent.split("\n"))
                .map(row -> {
                    String[] split = row.split(",");
                    String composition = null;
                    String author = null;
                    for (int i = 0; i < split.length; i++) {
                        String value = nonNull(split[i]) ? split[i].trim() : "";
                        switch (i) {
                            case 0:
                                composition = value;
                                break;
                            case 1:
                                author = value;
                                break;
                        }
                    }
                    return new FileInfo(composition, author);
                })
                .collect(Collectors.toList());

    }
}
