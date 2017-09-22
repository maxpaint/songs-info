package com.maxpaint

import com.maxpaint.fileinfo.FileInfoApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.http.MediaType
import spock.lang.Specification
import wslite.rest.RESTClient

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(classes = [FileInfoApplication], webEnvironment = RANDOM_PORT)
class LoadMusicListEndpointTest extends Specification {

    @Autowired
    private ResourceLoader resourceLoader

    private String fileName = "playList.csv"

    private RESTClient client

    @LocalServerPort
    void restClient(int port) {
        client = new RESTClient("http://localhost:$port")
    }

    def "try load file"() {
        given:
        Resource resource = resourceLoader.getResource("classpath:" + fileName);
        File file = resource.getFile()
        when:
        client.setDefaultContentTypeHeader(MediaType.MULTIPART_FORM_DATA_VALUE)
        client.post(path: "upload/flat/file") {
            multipart 'file', file.bytes, 'text/txt', 'test.txt'
        }

        def response = client.get(path: "upload/flat/file")

        then:
        response.json[0].composition == "When The Children Cry"
        response.json[0].author == "White Lion"
    }
}
