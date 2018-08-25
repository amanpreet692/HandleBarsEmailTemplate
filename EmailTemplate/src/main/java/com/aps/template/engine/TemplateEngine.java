package com.aps.template.engine;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.aps.template.factory.TemplateLoaderFactory.resolveTemplateLoader;
import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * Created by APS on 13-08-2018.
 */
public class TemplateEngine {

    private final List<TemplateLoader> templateLoaderList ;
    private final Handlebars handlebars;

    public TemplateEngine(TemplateLoader... templateLoaders) throws Exception {
        this.handlebars = new Handlebars(resolveTemplateLoader(templateLoaders));
        handlebars.prettyPrint(true);
        this.templateLoaderList = Arrays.asList(templateLoaders);
    }

    public void setPrefixDelimited(String prefixDelimited) {
        String [] prefixes = prefixDelimited.split(",");
        for(int i =0, j =0; i< templateLoaderList.size(); i++) {
            j = getIndexToAppend(j, prefixes) ;
            templateLoaderList.get(i).setPrefix(prefixes[j]);
        }
    }

    public void setSuffixDelimited(String suffixDelimited) {
        String [] suffixes = suffixDelimited.split(",");
        for(int i =0, j =0; i< templateLoaderList.size(); i++) {
            j = getIndexToAppend(j, suffixes) ;
            templateLoaderList.get(i).setSuffix(suffixes[j]);
        }
    }

    private int getIndexToAppend(int index, String[] delimited) {
        if(index < delimited.length){
            ++index;
        }
        return index-1;
    }

    private Template compileTemplate(String input) throws IOException {
        return handlebars.compile(input);
    }

    private String generateTemplate(String templateName, Object context, Optional<String> outputFilePath) throws IOException {
        Template template = compileTemplate(templateName);
        if(outputFilePath.isPresent()){
            try(FileWriter writer = new FileWriter(outputFilePath.get())) {
                template.apply(context, writer);
                return "Template written to file";
            } catch (IOException e) {
                throw new IOException("Not able to write template to file");
            }
        } else {
            return template.apply(context);
        }
    }

    public String getTemplateString(String templateName) throws IOException {
        return compileTemplate(templateName).text();
    }

    public String generateTemplate(String templateName, Object context) throws IOException {
        return generateTemplate(templateName, context, empty());
    }

    public String generateTemplate(String templateName, Object context, String outputFilePath) throws IOException {
        return generateTemplate(templateName, context, of(outputFilePath));
    }



}
