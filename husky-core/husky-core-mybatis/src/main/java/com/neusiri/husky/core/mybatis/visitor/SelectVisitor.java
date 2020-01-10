package com.neusiri.husky.core.mybatis.visitor;

import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;
import net.sf.jsqlparser.statement.select.SubSelect;

/**
 * <p>sqlParser查询访问器</p>
 * <p>创建日期：2019-09-26</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@AllArgsConstructor
public class SelectVisitor extends SelectVisitorAdapter {

    private ExpressionVisitor expressionVisitor;

    @Override
    public void visit(PlainSelect plainSelect) {

        // 访问where
        if (plainSelect.getWhere() != null) {
            plainSelect.getWhere().accept(expressionVisitor);
        }

        FromItem fromItem = plainSelect.getFromItem();
        // 访问子查询
        if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect) fromItem;
            subSelect.getSelectBody().accept(this);
        }
    }

}
