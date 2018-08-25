package com.aps.template;

import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.CompositeTemplateLoader;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import template.factory.TemplateLoaderFactory;
import template.factory.TemplateLoaderType;

import java.util.Arrays;

import static template.factory.TemplateLoaderFactory.getInstance;
import static template.factory.TemplateLoaderFactory.resolveTemplateLoader;

/**
 * Created by APS on 25-08-2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class TemplateLoaderFactoryTest {

    @Mock
    TemplateLoader templateLoaderA;
    @Mock
    TemplateLoader templateLoaderB;

    @Test(expected = Exception.class)
    public void testResolveTemplateLoaderException() throws Exception {
        resolveTemplateLoader(null);
        resolveTemplateLoader(new TemplateLoader[]{});
    }

    @Test
    public void testResolveTemplate() throws Exception {
        Assert.assertTrue(templateLoaderA.equals(resolveTemplateLoader(new TemplateLoader[]{templateLoaderA})));
        Assert.assertFalse(templateLoaderB.equals(resolveTemplateLoader(new TemplateLoader[]{templateLoaderA})));

        TemplateLoader[] templateLoaders = new TemplateLoader[]{templateLoaderA,templateLoaderB};
        TemplateLoader compTemplateLoaderActual = resolveTemplateLoader(templateLoaders);
        Assert.assertTrue(compTemplateLoaderActual instanceof CompositeTemplateLoader);
        Assert.assertTrue(((CompositeTemplateLoader)compTemplateLoaderActual).getDelegates().equals(Arrays.asList(templateLoaders)));
    }

    @Test(expected = NullPointerException.class)
    public void TestGetInstanceException() {
        getInstance(TemplateLoaderType.CLASSPATH,null,null);
    }

    @Test
    public void TestGetInstance() {
        TemplateLoader loader = getInstance(TemplateLoaderType.CLASSPATH,"/template");
        Assert.assertTrue(loader.getSuffix().equals(TemplateLoader.DEFAULT_SUFFIX));
        Assert.assertTrue(loader instanceof ClassPathTemplateLoader);
        loader = getInstance(TemplateLoaderType.CLASSPATH,"/template",".html");
        Assert.assertFalse(loader instanceof FileTemplateLoader);
        Assert.assertTrue(loader.getPrefix().equals("/template/"));
        Assert.assertTrue(loader.getSuffix().equals(".html"));
        loader = getInstance(TemplateLoaderType.FILE,"/template",".html");
        Assert.assertTrue(loader instanceof FileTemplateLoader);
        Assert.assertTrue(loader.getPrefix().equals("/template/"));
    }



}
