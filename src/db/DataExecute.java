package db;

import java.sql.Blob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Pair;

/**
 * SQL実行用クラス
 * 
 * @since
 */
public class DataExecute {
    
    /**
     * select用（単テーブル用）
     * 
     * @param dbConn DBコネクション
     * @param tableName テーブル名
     * @param columns セレクト対象カラム
     * @param conditions where条件
     * @return 取得結果をマップのリストで返す
     */
    public static List<Map<String, Object>> select(DatabaseConnector dbConn, String tableName,
            List<ColumnBean> columns,
            List<ColumnBean> conditions) {

        List<Pair> setObject = new ArrayList<Pair>();
        List<Map<String, Object>> result;
        ResultSet rset = null;
        PreparedStatement pstmt = null;
        
        try {
            StringBuilder sqlStr = new StringBuilder("select ");
            
            // SELECT句を組み立てる
            StringBuilder columnsQuestion = new StringBuilder();
            for (ColumnBean column : columns) {
                columnsQuestion.append(",");
                columnsQuestion.append(column.name);
            }
            sqlStr.append(columnsQuestion.toString().replaceFirst(",", ""));

            // FROM句を組み立てる
            sqlStr.append(" from ");
            sqlStr.append(tableName);
            
            // WHERE句を組み立てる
            sqlStr.append(" where ");
            for (ColumnBean condition : conditions) {
                sqlStr.append(" ");
                sqlStr.append(condition.pre);
                sqlStr.append(" ");
                sqlStr.append(condition.name);
                sqlStr.append(condition.condition);
                sqlStr.append(" ? ");
                sqlStr.append(" ");
                sqlStr.append(condition.post);
                sqlStr.append(" ");
                setObject.add(new Pair(condition.type, condition.value));
            }

            // バインドする
            pstmt = dbConn.conn.prepareStatement(sqlStr.toString());
            for (int i = 0; i < setObject.size(); i++) {
                if ((ColumnBean.Type) setObject.get(i).first == ColumnBean.Type.STRING) {
                    pstmt.setString(i + 1, (String) setObject.get(i).second);
                } else if ((ColumnBean.Type) setObject.get(i).first == ColumnBean.Type.INTEGER) {
                    pstmt.setInt(i + 1, (Integer) setObject.get(i).second);
                } else if ((ColumnBean.Type) setObject.get(i).first == ColumnBean.Type.BLOB) {
                    pstmt.setBytes(i + 1, (byte[]) setObject.get(i).second);
                } else if ((ColumnBean.Type) setObject.get(i).first == ColumnBean.Type.DATE) {
                    pstmt.setDate(i + 1, (Date) setObject.get(i).second);
                }
            }
            
            // SQL実行
            rset = pstmt.executeQuery();

            // 返却用LISTを作成する
            result = new ArrayList<Map<String, Object>>();
            while (rset.next()) {
                Map<String, Object> rec = new HashMap<String, Object>();
                for (ColumnBean column : columns) {
                    if (rset.getObject(column.name) instanceof Blob) {
                        Blob data = (Blob) rset.getObject(column.name);
                        rec.put(column.name, data.getBytes(1, (int) data.length()));
                    } else {
                        rec.put(column.name, rset.getObject(column.name));
                    }
                }
                result.add(rec);
            }

            rset.close();
            pstmt.close();
            
        } catch (Exception e) {
            System.out.println(e.toString());

            result = null;
        } finally {
            try {
                if (rset != null) {
                    rset.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ee) {
            }
        }

        return result;
    }
    
    /**
     * update用
     * 
     * @param dbConn DBコネクション
     * @param tableName テーブル名
     * @param columns アップデート対象カラム
     * @param conditions where条件
     */
    public static void update(DatabaseConnector dbConn, String tableName,
            List<ColumnBean> columns,
            List<ColumnBean> conditions) {

        List<Pair> setObject = new ArrayList<Pair>();
        ResultSet rset = null;
        PreparedStatement pstmt = null;

        try {
            StringBuilder sqlStr = new StringBuilder("update ");
            sqlStr.append(tableName);
            sqlStr.append(" SET ");

            // SET句を組み立てる
            StringBuilder columnsQuestion = new StringBuilder();
            for (ColumnBean column : columns) {
                columnsQuestion.append(",");
                columnsQuestion.append(column.name);
                columnsQuestion.append("=?");
                setObject.add(new Pair(column.type, column.value));
            }
            sqlStr.append(columnsQuestion.toString().replaceFirst(",", ""));

            // WHERE句を組み立てる
            sqlStr.append(" where ");
            for (ColumnBean condition : conditions) {
                sqlStr.append(" ");
                sqlStr.append(condition.pre);
                sqlStr.append(" ");
                sqlStr.append(condition.name);
                sqlStr.append(condition.condition);
                sqlStr.append(" ? ");
                sqlStr.append(" ");
                sqlStr.append(condition.post);
                sqlStr.append(" ");
                setObject.add(new Pair(condition.type, condition.value));
            }

            // バインドする
            pstmt = dbConn.conn.prepareStatement(sqlStr.toString());
            for (int i = 0; i < setObject.size(); i++) {
                if ((ColumnBean.Type) setObject.get(i).first == ColumnBean.Type.STRING) {
                    pstmt.setString(i + 1, (String) setObject.get(i).second);
                } else if ((ColumnBean.Type) setObject.get(i).first == ColumnBean.Type.INTEGER) {
                    pstmt.setInt(i + 1, (Integer) setObject.get(i).second);
                } else if ((ColumnBean.Type) setObject.get(i).first == ColumnBean.Type.BLOB) {
                    pstmt.setBytes(i + 1, (byte[]) setObject.get(i).second);
                } else if ((ColumnBean.Type) setObject.get(i).first == ColumnBean.Type.DATE) {
                    pstmt.setDate(i + 1, (Date) setObject.get(i).second);
                }
            }

            // SQL実行
            rset = pstmt.executeQuery();

            rset.close();
            pstmt.close();

        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            try {
                if (rset != null) {
                    rset.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ee) {
            }
        }
    }
}
