package com.aps.template.factory;

import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

/**
 * Created by APS on 19-08-2018.
 */
public enum TemplateLoaderType {
    CLASSPATH {
        @Override
        protected TemplateLoader getInstance(String prefix, String suffix) {
            return new ClassPathTemplateLoader(prefix, suffix);
        }
    },FILE {
        @Override
        protected TemplateLoader getInstance(String prefix, String suffix) {
            return new FileTemplateLoader(prefix, suffix);
        }
    };

    protected TemplateLoader getInstance(String prefix){
        return this.getInstance(prefix, TemplateLoader.DEFAULT_SUFFIX);
    }
    protected TemplateLoader getInstance(String prefix, String suffix){
        return null;
    }
}
