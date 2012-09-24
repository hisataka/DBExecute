package db;

/**
 * カラム情報保持用Bean
 * 
 * @since
 */
public class ColumnBean {
    
    /**
     * カラム型定義
     * 
     * @since
     */
    public enum Type {
        STRING, INTEGER, BLOB, DATE, NONE
    }

    public String name;
    public Object value;
    public String condition;
    public Type type;
    public String pre;
    public String post;
    
    /**
     * コンストラクタ
     * 
     * @param name カラム名
     * @param value カラム値
     * @param condition 条件
     * @param type カラム型
     * @param pre 接頭句
     * @param post 接尾句
     */
    public ColumnBean(String name, Object value, String condition, Type type,
            String pre, String post) {
        this.name = name;
        this.value = value;
        this.condition = condition;
        this.type = type;
        this.pre = pre;
        this.post = post;
    }

    public ColumnBean(String name, Object value, String condition, Type type,
            String pre) {
        this(name, value, condition, type, pre, "");
    }
    public ColumnBean(String name, Object value, String condition, Type type) {
        this(name, value, condition, type, "", "");
    }
    
    public ColumnBean(String name, Object value, Type type) {
        this(name, value, "", type, "", "");
    }

    public ColumnBean(String name) {
        this(name, null, "", Type.NONE, "", "");
    }
}