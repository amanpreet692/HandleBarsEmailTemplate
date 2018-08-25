package com.aps.sample;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.aps.sample.data.NamedDataMap;
import template.engine.TemplateEngine;

import java.util.ArrayList;
import java.util.Collection;

import static template.factory.TemplateLoaderFactory.getInstance;
import static template.factory.TemplateLoaderType.CLASSPATH;

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

        Context context = Context.newBuilder("Sample Report").build();

        Collection<NamedDataMap> dataMapList =  new ArrayList<>();
        dataMapList.add(new NamedDataMap());
        dataMapList.add(new NamedDataMap());
        dataMapList.add(new NamedDataMap());
        context.data("dataMapList",dataMapList);

        System.out.println(templateEngine.generateTemplate("report",context,"./output/result.html"));
        System.out.println(templateEngine.getTemplateString("report"));
    }


}
