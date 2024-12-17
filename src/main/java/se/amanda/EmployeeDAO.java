package se.amanda;

import java.sql.SQLException;

public interface EmployeeDAO {

    public Employee getEmployeeDetailsWithLeftJoin(String email, String password) throws SQLException;

}
