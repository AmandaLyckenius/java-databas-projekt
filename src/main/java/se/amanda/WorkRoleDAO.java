package se.amanda;

import java.sql.SQLException;
import java.util.List;

public interface WorkRoleDAO {
    void insertWorkRole(WorkRole workRole) throws SQLException;
    void updateWorkRole(WorkRole workRole) throws SQLException;
    WorkRole selectWorkRole(int role_id) throws SQLException;
    void deleteWorkRole(int role_id) throws SQLException;
    List<WorkRole> selectAllWorkRoles() throws SQLException;

}
