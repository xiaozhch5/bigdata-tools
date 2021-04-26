package com.zh.ch.bigdata.dom4j.util;


import com.zh.ch.bigdata.base.util.file.FileWriteUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class XmlFileAnalysisUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlFileAnalysisUtil.class);

    public static Document load(String xmlFilePath) throws DocumentException {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(xmlFilePath);
            return document;
        } catch (DocumentException e) {
            LOGGER.error("xml文件解析异常, xml文件路径为{}", xmlFilePath, e);
            throw e;
        }
    }

    public static Document load(URL xmlFileUrl) throws DocumentException {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(xmlFileUrl);
            return document;
        } catch (DocumentException e) {
            LOGGER.error("xml文件解析异常, xml文件路径为{}", xmlFileUrl, e);
            throw e;
        }
    }




}
