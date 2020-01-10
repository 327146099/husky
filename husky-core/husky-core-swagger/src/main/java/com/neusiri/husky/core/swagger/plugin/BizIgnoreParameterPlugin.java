package com.neusiri.husky.core.swagger.plugin;


import com.github.xiaoymin.knife4j.spring.plugin.AbstractOperationBuilderPlugin;
import com.github.xiaoymin.knife4j.spring.plugin.OperationIgnoreParameterPlugin;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.neusiri.husky.core.common.restful.model.AbstractBizEntity;
import com.neusiri.husky.core.swagger.annotations.ApiOperationEnhance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import springfox.documentation.service.ListVendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.OperationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>biz实体字段忽略插件</p>
 * <p>创建日期：2019-08-21</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 13)
public class BizIgnoreParameterPlugin extends AbstractOperationBuilderPlugin {

    public BizIgnoreParameterPlugin() {
    }

    @Override
    public void apply(OperationContext context) {
        Optional<ApiOperationEnhance> apiOperationSupportOptional = context.findAnnotation(ApiOperationEnhance.class);
        if (apiOperationSupportOptional.isPresent()) {
            ApiOperationEnhance apiOperationEnhance = apiOperationSupportOptional.get();

            List<Map<String, Boolean>> maps = new ArrayList<>();

            if (apiOperationEnhance.ignoreBiz()) {

                Map<String, Boolean> map = new HashMap<String, Boolean>(16) {
                    {
                        //如果不显示id,设置id隐藏
                        if (!apiOperationEnhance.showId()) {
                            put("id", true);
                        }
                        for (AbstractBizEntity.Fields field : AbstractBizEntity.Fields.values()) {
                            String name = field.name();
                            put(name, true);
                        }
                    }
                };
                maps.add(map);
            }

            if (apiOperationEnhance.ignorePaging()) {
                Map<String, Boolean> map = new HashMap<String, Boolean>(16) {
                    {
                        put("pageNum", true);
                        put("pageSize", true);
                    }
                };
                maps.add(map);
            }

            if (!CollectionUtils.isEmpty(maps)) {
                ListVendorExtension<Map<String, Boolean>> listVendorExtension = new ListVendorExtension<>(OperationIgnoreParameterPlugin.IGNORE_PARAMETER_EXTENSION_NAME, maps);
                context.operationBuilder().extensions(Lists.newArrayList(listVendorExtension));
            }
        }
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }
}
