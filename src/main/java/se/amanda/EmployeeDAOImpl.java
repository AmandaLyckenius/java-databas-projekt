package se.amanda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public Employee getEmployeeDetailsWithLeftJoin(String email, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        WorkRole workRole = null;
        Employee employee = null;

        try {
            conn = JDBCUtil.getConnection();
            String sql = """
                    SELECT e.employee_id, e.name, e.email, e.password, e.role_id, 
                           w.title, w.description, w.salary, w.creation_date
                           FROM Employee e 
                           LEFT JOIN Work_Role w ON e.role_id = w.role_id
                           WHERE e.email = ? AND e.password = ?""";

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                workRole = new WorkRole(
                        rs.getInt("role_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDouble("salary"),
                        rs.getDate("creation_date")
                );

                employee = new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        workRole
                );

                System.out.println("Signing in..");

            } else {
                System.out.println("User not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            JDBCUtil.closeConnection(conn);
        }

        return employee;
    }







}
