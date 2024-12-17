package se.amanda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkRoleDAOImpl implements WorkRoleDAO{


    @Override
    public void insertWorkRole(WorkRole workRole) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = JDBCUtil.getConnection();
            String sql = "INSERT INTO Work_role(title,description, salary, creation_date) VALUES (?,?,?,?)";
            pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, workRole.getTitle());
            pstmt.setString(2,workRole.getDescription());
            pstmt.setDouble(3, workRole.getSalary());
            pstmt.setDate(4, workRole.getCreationDate());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0){
                rs= pstmt.getGeneratedKeys();       //Här väljer jag att hämta det genererade role-Idt och sätta det genom setRoleId (förenklar när jag ska testa)
                if (rs.next()){
                    workRole.setRoleId(rs.getInt(1));
                }
                System.out.println("Rows inserted: " + rowsInserted);
            } else{
                System.out.println("Problem when adding new work role.");
            }

            conn.commit();

        } catch (SQLException e){
            e.printStackTrace();
            conn.rollback();
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            JDBCUtil.closeConnection(conn);
        }
    }

    @Override
    public void updateWorkRole(WorkRole workRole) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try{
            conn = JDBCUtil.getConnection();
            String sql = "UPDATE Work_role SET title = ?, description =?, salary=?, creation_date=? WHERE role_id = ? ";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, workRole.getTitle());
            pstmt.setString(2, workRole.getDescription());
            pstmt.setDouble(3, workRole.getSalary());
            pstmt.setDate(4, workRole.getCreationDate());
            pstmt.setInt(5, workRole.getRoleId());

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Successfully updated work role with id: " + workRole.getRoleId());
            } else {
                System.out.println("Work role with id: " + workRole.getRoleId() + " not found.");
            }

            conn.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
            JDBCUtil.closeConnection(conn);
        }
    }

    @Override
    public WorkRole selectWorkRole(int role_id) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        WorkRole workRole = null;

        try{
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM Work_role WHERE role_id = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,role_id);

            rs = pstmt.executeQuery();

            if (rs.next()){
                workRole = new WorkRole(
                        rs.getInt("role_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDouble("salary"),
                        rs.getDate("creation_date")
                );

                System.out.println("Successfully fetched work role.\n" + workRole);

            } else {
                System.out.println("No work role with role id: " + role_id + " can be found.");
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw e;

        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            JDBCUtil.closeConnection(conn);
        }
            return workRole;
    }

    @Override
    public void deleteWorkRole(int role_id) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try{
            conn = JDBCUtil.getConnection();
            String sql = "DELETE FROM Work_role WHERE role_id = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, role_id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Rows affected: " + rowsAffected);
                System.out.println("Successfully deleted role with role id: " + role_id);
            } else {
                System.out.println("No work role found with id: " + role_id);
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
            JDBCUtil.closeConnection(conn);
        }

    }

    @Override
    public List<WorkRole> selectAllWorkRoles() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs= null;
        List <WorkRole> workRoles = new ArrayList<>();

        try{
            conn = JDBCUtil.getConnection();
            String sql = "SELECT * FROM Work_role";
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while(rs.next()){
                WorkRole workRole = new WorkRole(
                        rs.getInt("role_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDouble("salary"),
                        rs.getDate("creation_date")
                );

                workRoles.add(workRole);
            }

        } catch (SQLException e){
            e.printStackTrace();
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            JDBCUtil.closeConnection(conn);
        }

        return workRoles;
    }




}
