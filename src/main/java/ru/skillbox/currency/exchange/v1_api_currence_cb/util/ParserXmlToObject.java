package ru.skillbox.currency.exchange.v1_api_currence_cb.util;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParserXmlToObject<T> {

    private XmlMapper xmlMapper;
    private Class<T> clazz;

    public ParserXmlToObject(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T parseXmlToObject (String xml) {
        xmlMapper = new XmlMapper();
        T result;
        try {
            result = xmlMapper.readValue(xml, clazz);
        } catch (Exception e) {
            log.info("Error parsing xml to object");
            throw new RuntimeException(e);
        }

        return result;
    }



}
