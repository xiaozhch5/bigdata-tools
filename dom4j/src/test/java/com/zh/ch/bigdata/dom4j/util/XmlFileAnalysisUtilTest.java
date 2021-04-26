package com.zh.ch.bigdata.dom4j.util;

import junit.framework.TestCase;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.dom4j.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class XmlFileAnalysisUtilTest extends TestCase {

    public void testLoad() {

        try {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            Model model = reader.read(new FileReader("pom.xml"));
            System.out.println(model.getDependencies());
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }



    }

    public void testTestLoad() {
    }
}