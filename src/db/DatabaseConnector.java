package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * �f�[�^�x�[�X�R�l�N�^�[�N���X �R�l�N�V�����̐��������b�s���O
 * 
 * @since
 */
public class DatabaseConnector {
    public Connection conn;
    
    /**
     * �R���X�g���N�^
     * 
     * @param connectString jdbcurl
     * @param user ���[�U
     * @param pass �p�X���[�h
     */
    public DatabaseConnector(String connectString, String user, String pass) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("JDBC�h���C�o�����[�h���܂���...");
            
            this.conn = DriverManager.getConnection(connectString, user, pass);
            System.out.println("�f�[�^�x�[�X�ɐڑ����܂���...");

        } catch (Exception e) {
            System.out.println(e.toString());
            try {
                if (this.conn != null) {
                    this.conn.close();
                }
            } catch (SQLException se) {
            }
        }
    }
    
    /**
     * �R�l�N�V�����N���[�Y���\�b�h
     * 
     */
    public void close() {
        try {
            this.conn.close();
        } catch (Exception e) {
        } finally {
            try {
                if (this.conn != null) {
                    this.conn.close();
                }
            } catch (SQLException se) {
            }
        }
    }
}

