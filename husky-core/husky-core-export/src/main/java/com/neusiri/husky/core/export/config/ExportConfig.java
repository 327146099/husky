package com.neusiri.husky.core.export.config;

import com.neusiri.husky.core.common.properties.ExportProperties;
import com.neusiri.husky.core.common.properties.HuskyProperties;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.resource.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

/**
 * <p>导出配置</p>
 * <p>创建日期：2019-09-06</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@org.springframework.context.annotation.Configuration
public class ExportConfig {

    @Autowired
    private HuskyProperties huskyProperties;

    /**
     * beetl模板加载器
     *
     * @return 模板加载器
     */
    @Bean
    public ResourceLoader resourceLoader() {
        CompositeResourceLoader compositeResourceLoader = new CompositeResourceLoader();

        //设置类加载器
        ClasspathResourceLoader classpathResourceLoader = new ClasspathResourceLoader();
        compositeResourceLoader.addResourceLoader(new StartsWithMatcher("classpath:"), classpathResourceLoader);

        //设置文件加载器
        FileResourceLoader fileResourceLoader = new FileResourceLoader();
        compositeResourceLoader.addResourceLoader(new StartsWithMatcher("file:"), fileResourceLoader);

        //设置默认加载器为类加载器
        compositeResourceLoader.addResourceLoader(new AllowAllMatcher(), classpathResourceLoader);

        return compositeResourceLoader;
    }

    /**
     * 创建模板引擎
     *
     * @return beetl模板引擎
     * @throws IOException IOException
     */
    @Bean
    public GroupTemplate groupTemplate() throws IOException {
        Configuration configuration = Configuration.defaultConfiguration();
        //获取导出属性
        ExportProperties exportProperties = huskyProperties.getExport();
        //如果导出设置不为空,设置属性
        if (exportProperties != null) {
            //是否检查模板变更
            configuration.getResourceMap().put("autoCheck", Boolean.TRUE.equals(exportProperties.getAutoCheck()) ? "true" : "false");
        }

        GroupTemplate groupTemplate = new GroupTemplate(resourceLoader(), configuration);
        return groupTemplate;
    }

}
