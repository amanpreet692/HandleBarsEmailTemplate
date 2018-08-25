package com.aps.template.engine;

import com.aps.template.engine.TemplateEngine;
import com.aps.template.factory.TemplateLoaderFactory;
import com.aps.template.factory.TemplateLoaderType;
import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by APS on 25-08-2018.
 */
public class TemplateEngineTest {

    private TemplateEngine templateEngine;

    private TemplateLoader htmlLoader;
    private TemplateLoader cssLoader;

    private final String templateString = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    {{>testStyle}}\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>{{this}}</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "</body>\n" +
            "</html>";


    @Before
    public void setup() throws Exception {
        htmlLoader = TemplateLoaderFactory.getInstance(TemplateLoaderType.CLASSPATH,"/templates/",".html");
        cssLoader = TemplateLoaderFactory.getInstance(TemplateLoaderType.CLASSPATH,"/templates/",".css");
        templateEngine = new TemplateEngine(htmlLoader,cssLoader);
    }

    @Test
    public void testGetTemplateString() throws IOException {
        String generateString  = templateEngine.getTemplateString("test");
        int fromIndex = 0 ;
        int toIndex = 0;
        while(toIndex >= 0){
            toIndex = templateString.indexOf("\n",fromIndex);
            if(fromIndex < toIndex) {
                String sub = templateString.substring(fromIndex, toIndex + 1).trim();
                if(!sub.equals("")) {
                   Assert.assertTrue(generateString.contains(sub));
                }
            }

            fromIndex = toIndex + 1;
        }

    }

    @Test
    public void testGenerateTemplate() throws IOException {
        Context context = Context.newBuilder("Test").build();
        String generatedTemplate = templateEngine.generateTemplate("test",context);
        System.out.println(generatedTemplate);
        Assert.assertTrue(generatedTemplate.contains("<title>Test</title>"));
        Assert.assertTrue(generatedTemplate.contains("border-collapse:collapse"));
    }

    @Test
    public void testGenerateTemplateIntoFile() throws IOException {
        Context context = Context.newBuilder("Test").build();
        String output = templateEngine.generateTemplate("test",context,"testOutput.html");
        Assert.assertTrue(output.equals("Template written to file"));
        FileReader reader = new FileReader("testOutput.html");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String generatedTemplate = "";
        String line = "";
        while ((line = bufferedReader.readLine()) != null){
            generatedTemplate = generatedTemplate + line;
        }
        Assert.assertTrue(generatedTemplate.contains("<title>Test</title>"));
        Assert.assertTrue(generatedTemplate.contains("border-collapse:collapse"));
    }

    @Test
    public void testDelimitedPrefixNSuffix() {
        templateEngine.setPrefixDelimited("/html,/css");
        htmlLoader.getPrefix().equals("/html/");
        cssLoader.getPrefix().equals("/css/");
        templateEngine.setPrefixDelimited("/templates");
        htmlLoader.getPrefix().equals("/templates/");
        cssLoader.getPrefix().equals("/templates/");

        templateEngine.setSuffixDelimited(".aps,.js");
        htmlLoader.getSuffix().equals(".aps");
        cssLoader.getSuffix().equals(".js");
        templateEngine.setSuffixDelimited(".report");
        htmlLoader.getSuffix().equals(".report");
        cssLoader.getSuffix().equals(".report");
    }

}
