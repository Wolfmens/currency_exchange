package ru.skillbox.currency.exchange.api_currence_cb.util;

import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

@Slf4j
public class ParserXmlToObject<T> {

    private final Class<T> clazz;

    public ParserXmlToObject(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T parseXmlToObject(String xml) {
        T result;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            result = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            log.info("Error parsing xml to object");
            throw new RuntimeException(e);
        }

        return result;
    }


}
