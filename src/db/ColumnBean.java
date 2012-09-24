package db;

/**
 * �J�������ێ��pBean
 * 
 * @since
 */
public class ColumnBean {
    
    /**
     * �J�����^��`
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
     * �R���X�g���N�^
     * 
     * @param name �J������
     * @param value �J�����l
     * @param condition ����
     * @param type �J�����^
     * @param pre �ړ���
     * @param post �ڔ���
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