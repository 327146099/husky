package com.neusiri.husky.core.export.excel.filter;

import com.neusiri.husky.core.export.excel.dict.DictHolder;
import com.neusiri.husky.core.export.excel.style.HuskyExcelExportStyler;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * <p>style数据清除拦截器</p>
 * <p>创建日期：2019-09-27</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Component
public class StyleClearFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } finally {
            // 清除样式数据
            HuskyExcelExportStyler.clear();
            // 清除自定义字典数据
            DictHolder.clear();
        }
    }

}
