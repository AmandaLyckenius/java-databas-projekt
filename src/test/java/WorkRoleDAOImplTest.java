import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.amanda.JDBCUtil;
import se.amanda.WorkRole;
import se.amanda.WorkRoleDAOImpl;

import java.sql.*;
import java.util.List;

public class WorkRoleDAOImplTest {

    @AfterEach
    public void cleanUp(){
        Connection conn = null;
        Statement stmt = null;

        try{
            conn= JDBCUtil.getConnection();
            stmt= conn.createStatement();
            stmt.execute("DROP TABLE IF EXISTS Work_role");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeStatement(stmt);
            JDBCUtil.closeConnection(conn);
        }
    }

    @Test
    public void testSelectAllWorkRoles(){
        WorkRoleDAOImpl workRoleDAO = new WorkRoleDAOImpl();            //Skapar instans och en ny roll
        WorkRole workRole = new WorkRole("Brandman", "Arbete på brandstation", 32000, Date.valueOf("2024-10-21"));

        try{
            workRoleDAO.insertWorkRole(workRole);           //INSERT WORKROLE "brandman" I TABELL
            List<WorkRole> workRoleList = workRoleDAO.selectAllWorkRoles();     //Skapar en lista där resultatet från metoden selectAllWorkRoles lagras
            WorkRole roleFound = null;

            for (WorkRole role : workRoleList) {                    //Loopar igenom listan
                if (role.getRoleId() == workRole.getRoleId()){      //Väljer att kolla om matchande id:n förekommer, då id är unikt.
                    roleFound = role;           //Sparar den matchade workRolen i roleFound
                    break;                      //bryter loopen när rätt roll hittats
                }
            }
            Assertions.assertNotNull(roleFound);      //Kollar att roleFound inte fortsatt är null
            Assertions.assertEquals(workRole.getRoleId(), roleFound.getRoleId());           //Jämför id mellan den tidigare insatta workRole och den hittade roleFound

        } catch (SQLException e){
            e.printStackTrace();
        }


    }







}
