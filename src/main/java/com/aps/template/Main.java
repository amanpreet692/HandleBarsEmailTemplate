package com.aps.template;

import com.aps.template.data.NamedDataMap;
import com.aps.template.engine.TemplateEngine;
import com.aps.template.factory.TemplateLoaderFactory;
import com.aps.template.factory.TemplateLoaderType;
import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.FileWriter;
import java.util.*;

import static com.aps.template.factory.TemplateLoaderFactory.getInstance;
import static com.aps.template.factory.TemplateLoaderType.CLASSPATH;

/**
 * Created by APS on 13-08-2018.
 */
public class Main {

    private static final String TEMPLATE_PREFIX = "/templates";
    private static final String HTML_SUFFIX = ".html";
    private static final String CSS_SUFFIX = ".css";

    public static void main(String[] args) throws Exception {

        TemplateLoader htmlTemplateLoader = getInstance(CLASSPATH,TEMPLATE_PREFIX,HTML_SUFFIX);
        TemplateLoader cssTemplateLoader = getInstance(CLASSPATH,TEMPLATE_PREFIX,CSS_SUFFIX);
        TemplateEngine templateEngine = new TemplateEngine(htmlTemplateLoader,cssTemplateLoader);

        Template template = templateEngine.compileTemplate("report");

        Context context = Context.newBuilder("Sample Report").build();

        Collection<NamedDataMap> dataMapList =  new ArrayList<>();
        dataMapList.add(new NamedDataMap());
        dataMapList.add(new NamedDataMap());
        dataMapList.add(new NamedDataMap());
        context.data("dataMapList",dataMapList);

        FileWriter fileWriter = new FileWriter("./output/result.html");
        template.apply(context,fileWriter);
        fileWriter.close();
        System.out.println("Template written to file");
    }


}
