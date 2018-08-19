package com.aps.template.factory;

import com.github.jknack.handlebars.io.CompositeTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.util.Objects;

/**
 * Created by APS on 19-08-2018.
 */
public class TemplateLoaderFactory {

    public static TemplateLoader resolveTemplateLoader(TemplateLoader[] templateLoaders) throws Exception {
        TemplateLoader templateLoader;
        if(Objects.isNull(templateLoaders)){
            throw new Exception("templateLoaders Array can't be null");
        }
        int listSize = templateLoaders.length;
        switch (listSize){
            case 0 : throw new Exception("1 or more Template Loaders are required");
            case 1 : templateLoader = templateLoaders[1]; break;
            default: templateLoader = new CompositeTemplateLoader(templateLoaders);
        }
        return templateLoader;
    }

    public static TemplateLoader getInstance (TemplateLoaderType templateLoaderType, String prefix, String suffix) {
        return templateLoaderType.getInstance(prefix,suffix);
    }

    public static TemplateLoader getInstance (TemplateLoaderType templateLoaderType, String prefix) {
        return templateLoaderType.getInstance(prefix);
    }

}
