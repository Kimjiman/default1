package com.example.default1.constants.enumYypeHandler;

import com.example.default1.constants.enumModel.YN;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Enum으로 받은 변수를 mybatis에서 인식하게 하기위해서 typeHandler에 명시한다
public class YnTypeHandler extends BaseTypeHandler<YN> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, YN yn, JdbcType jdbcType) throws SQLException {
        ps.setString(i, yn.getValue());
    }

    @Override
    public YN getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return YN.fromValue(rs.getString(columnName));
    }

    @Override
    public YN getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return YN.fromValue(rs.getString(columnIndex));
    }

    @Override
    public YN getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return YN.fromValue(cs.getString(columnIndex));
    }
}
