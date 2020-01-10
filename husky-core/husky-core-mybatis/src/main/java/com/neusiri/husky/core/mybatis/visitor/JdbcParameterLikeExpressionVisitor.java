package com.neusiri.husky.core.mybatis.visitor;

import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;

import java.util.List;

/**
 * <p>sqlParser条件访问器--处理like条件预编译参数位置</p>
 * <p>创建日期：2019-09-26</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@AllArgsConstructor
public class JdbcParameterLikeExpressionVisitor extends ExpressionVisitorAdapter {

    private List<Integer> list;

    @Override
    public void visit(LikeExpression likeExpression) {
        Expression rightExpression = likeExpression.getRightExpression();
        if (rightExpression instanceof JdbcParameter) {
            JdbcParameter jdbcParameter = (JdbcParameter) rightExpression;
            list.add(jdbcParameter.getIndex() - 1);
        }
        if (rightExpression instanceof Function) {
            Function function = (Function) rightExpression;
            handleFunction(function);

        }
    }


    private void handleFunction(Function function) {
        ExpressionList rightExpression = function.getParameters();
        List<Expression> expressions = rightExpression.getExpressions();
        for (Expression expression : expressions) {
            if (expression instanceof Function) {
                handleFunction((Function) expression);
                continue;
            }
            if (expression instanceof JdbcParameter) {
                JdbcParameter jdbcParameter = (JdbcParameter) expression;
                list.add(jdbcParameter.getIndex() - 1);
            }
        }
    }


}
