package com.neusiri.husky.core.mybatis.interceptor;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.SqlInfo;
import com.neusiri.husky.core.mybatis.util.SqlEscapeUtils;
import com.neusiri.husky.core.mybatis.util.SqlParserUtils;
import com.neusiri.husky.core.util.ognl.OgnlUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import ognl.OgnlException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.reflection.MetaObject;

import java.util.List;

/**
 * <p>like查询条件特殊字符处理器</p>
 * <p>创建日期：2019-09-26</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Slf4j
public class SpecialCharacterSqlParser implements ISqlParser {


    @Override
    public SqlInfo parser(MetaObject metaObject, String sql) {

        // 针对定义了rowBounds，做为mapper接口方法的参数
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Object parameterObject = boundSql.getParameterObject();

        try {
            // 获取like预编译参数的位置
            List<Integer> indexList = SqlParserUtils.getJdbcParamsWithLikeIndex(sql);

            // 如果条件含有like预编译参数
            if (CollectionUtils.isNotEmpty(indexList)) {
                for (Integer index : indexList) {
                    // 获取参数访问的key
                    ParameterMapping parameterMapping = parameterMappings.get(index);
                    String key = parameterMapping.getProperty();

                    Object value = OgnlUtils.getValue(key, parameterObject);

                    if (value instanceof String) {
                        String str = value.toString();
                        // 转义字符串
                        String escapeStr = escape((str));
                        if (!str.equals(escapeStr)) {
                            // 重新设值
                            OgnlUtils.setValue(key, parameterObject, escapeStr);
                        }
                    }
                }
            }

        } catch (JSQLParserException | OgnlException e) {
            log.warn("sql解析错误", e);
        }

        return null;
    }

    /**
     * 是否进行拦截
     *
     * @param metaObject metaObject
     * @param sql        sql
     * @return 判断结果
     */
    @Override
    public boolean doFilter(MetaObject metaObject, String sql) {
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        return SqlCommandType.SELECT == mappedStatement.getSqlCommandType() && StatementType.CALLABLE != mappedStatement.getStatementType();
    }

    private static String escape(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        // 删除串首的%
        boolean start = str.startsWith("%");
        StringBuilder sb = new StringBuilder(str);
        if (start) {
            sb.deleteCharAt(0);
        }
        // 删除串尾的%
        boolean end = str.endsWith("%");
        if (end) {
            sb.deleteCharAt(sb.length() - 1);
        }
        String escapeStr = SqlEscapeUtils.escape(sb.toString());
        sb = new StringBuilder(escapeStr);

        if (start) {
            sb.insert(0, '%');
        }
        if (end) {
            sb.append('%');
        }
        return sb.toString();
    }


}

