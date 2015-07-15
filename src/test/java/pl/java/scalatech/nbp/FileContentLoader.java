package pl.java.scalatech.nbp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.apache.camel.Body;
import org.apache.camel.Headers;

import pl.java.scalatech.spring_camel.nbp.Tabela_kursow;

import com.google.common.io.CharStreams;

@Slf4j
public class FileContentLoader {
    public void customLoad(@Body InputStream inputStream, @Headers Map<String, Object> header) throws IOException {
        log.debug("+++          customLoad  {}", header);
        String text = null;
        try (final Reader reader = new InputStreamReader(inputStream)) {
            text = CharStreams.toString(reader);

        }
        log.info("{}", text);
    }

    @SneakyThrows
    public String file2XmlAsString(String path) {
        File xmlFile = new File(path);
        JAXBContext jc = JAXBContext.newInstance(pl.java.scalatech.spring_camel.nbp.Tabela_kursow.class);
        Unmarshaller u = jc.createUnmarshaller();
        Tabela_kursow tk = (Tabela_kursow) u.unmarshal(xmlFile);
        return tk.toString();
    }
}