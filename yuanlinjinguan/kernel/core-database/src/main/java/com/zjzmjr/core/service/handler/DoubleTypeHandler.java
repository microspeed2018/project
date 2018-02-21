package com.zjzmjr.core.service.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;

/**
 * 
 * 
 * @author liwen
 * @version $Id: DoubleTypeHandler.java, v 0.1 2015-10-26 涓婂崍10:45:24 liwen Exp $
 */
public class DoubleTypeHandler extends org.apache.ibatis.type.DoubleTypeHandler {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Double parameter, JdbcType jdbcType)
            throws SQLException {
        super.setNonNullParameter(ps, i, parameter, jdbcType);
    }

    @Override
    public Double getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convertScale(super.getNullableResult(rs, columnName));
    }

    @Override
    public Double getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convertScale(super.getNullableResult(rs, columnIndex));
    }

    @Override
    public Double getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convertScale(super.getNullableResult(cs, columnIndex));
    }

    /**
     * 淇濈暀5浣嶅皬鏁�
     *
     * @param d
     * @return
     */
    private Double convertScale(Double d) {
        return d == null ? null : Math.floor(d * 100000 + 0.5) / 100000.0;
    }

}
