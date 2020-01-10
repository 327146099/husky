package com.neusiri.husky.core.mybatis.util;

import com.neusiri.husky.core.mybatis.visitor.JdbcParameterLikeExpressionVisitor;
import com.neusiri.husky.core.mybatis.visitor.SelectVisitor;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.select.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>sql解析工具类</p>
 * <p>创建日期：2019-09-26</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
public class SqlParserUtils {

    /**
     * 获取sql中like查询条件的位置
     *
     * @return 位置集合
     */
    public static List<Integer> getJdbcParamsWithLikeIndex(String sql) throws JSQLParserException {
        // 遍历查找like预编译参数的位置
        Statements statements = CCJSqlParserUtil.parseStatements(sql);
        List<Integer> list = new ArrayList<>();
        for (Statement statement : statements.getStatements()) {
            if (statement instanceof Select) {
                Select select = (Select) statement;
                // 设置SelectVisitor
                JdbcParameterLikeExpressionVisitor expressionVisitor = new JdbcParameterLikeExpressionVisitor(list);

                SelectVisitor selectVisitor = new SelectVisitor(expressionVisitor);
                expressionVisitor.setSelectVisitor(selectVisitor);

                select.getSelectBody().accept(selectVisitor);
            }
        }
        return list;
    }

    public static void main(String[] args) throws JSQLParserException {
        String sql = "SELECT id,gmt_modified,create_by,deleted,welfare_payment_unit_name,welfare_payment_unit_no,modified_by,gmt_create,version,data_permission FROM T_WELFARE_PAYMENT_UNIT WHERE deleted='0' AND (WELFARE_PAYMENT_UNIT_NAME like concat(concat('%',?),'%') ) ORDER BY GMT_CREATE ASC";
        List<Integer> index = SqlParserUtils.getJdbcParamsWithLikeIndex(sql);
        System.out.println(index);
    }

}
