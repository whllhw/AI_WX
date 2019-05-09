package xyz.whllhw.util;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoUtil<E extends Enum<E>> extends BaseTypeHandler<E> {
    private final Class<E> type;

    public DaoUtil(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.ordinal());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int s = rs.getInt(columnName);
        return type.getEnumConstants()[s];
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int s = rs.getInt(columnIndex);
        return type.getEnumConstants()[s];
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int s = cs.getInt(columnIndex);
        return type.getEnumConstants()[s];
    }
}
