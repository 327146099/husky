package com.neusiri.husky.core.util.ognl;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

/**
 * <p>ognl工具类</p>
 * <p>创建日期：2019-09-26</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
public class OgnlUtils {

    private static final OgnlContext OGNL_CONTEXT = new OgnlContext(null, null, new DefaultMemberAccess(true));

    /**
     * 根据ognl获取值
     *
     * @param expression ognl表达式
     * @param root       根对象
     * @return 获取的结果
     * @throws OgnlException OgnlException
     */
    public static Object getValue(String expression, Object root) throws OgnlException {
        return Ognl.getValue(expression, OGNL_CONTEXT, root);
    }

    /**
     * 根据ognl表达式赋值
     *
     * @param expression ognl表达式
     * @param root       根对象
     * @param value      需要赋的值
     * @throws OgnlException OgnlException
     */
    public static void setValue(String expression, Object root, Object value) throws OgnlException {
        Ognl.setValue(expression, OGNL_CONTEXT, root, value);
    }


}
