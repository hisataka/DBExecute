package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * データベースコネクタークラス コネクションの生成をラッピング
 * 
 * @since
 */
public class DatabaseConnector {
    public Connection conn;
    
    /**
     * コンストラクタ
     * 
     * @param connectString jdbcurl
     * @param user ユーザ
     * @param pass パスワード
     */
    public DatabaseConnector(String connectString, String user, String pass) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("JDBCドライバをロードしました...");
            
            this.conn = DriverManager.getConnection(connectString, user, pass);
            System.out.println("データベースに接続しました...");

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
     * コネクションクローズメソッド
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

