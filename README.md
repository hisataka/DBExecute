DBExecute
======================
データを大量に更新するための簡易ツールを作ろうとした際に、
自分でprepareStatementをラッピングしてみた。

中途半端に作ってみて思ったことは、
やっぱりORマッパーって優秀だねということ。

こんな車輪の再発明みたいなことはするもんじゃないなということで、
単一表からのselectとupdateを作ったところで止めました。

select使用例
--------------
        DatabaseConnector dbConn = new DatabaseConnector(jdbcurl, user, pass);

        // SELECTカラム
        List<ColumnBean> columns = new ArrayList<ColumnBean>();
        columns.add(new ColumnBean("ID"));
        columns.add(new ColumnBean("NAME"));
        columns.add(new ColumnBean("VALUE"));

        List<ColumnBean> conditions = new ArrayList<ColumnBean>();

        // WHERE条件
        for (int i = 0; i < ids.size(); i++) {
            String pre = "(";
            if (i > 0) {
                pre = "OR (";
            }
            conditions.add(new ColumnBean("ID", ids.get(i), "=", ColumnBean.Type.STRING,
                    pre));
            conditions.add(new ColumnBean("NAME", "testname1", "=",
                    ColumnBean.Type.STRING, "AND ( "));
            conditions.add(new ColumnBean("NAME", "testname2", "=",
                    ColumnBean.Type.STRING, "OR", "))"));

        }

        // 抽出
        DataExecute.select(dbConn, "SAMPLE_TABLE", columns, conditions);

        dbConn.close();


update使用例
--------------
        DatabaseConnector dbConn = new DatabaseConnector(jdbcurl, user, pass);

        // UPDATEカラム
        List<ColumnBean> columns = new ArrayList<ColumnBean>();
        columns
                .add(new ColumnBean("VALUE", rec.get("ITEM_VALUE"), ColumnBean.Type.STRING));

        // WHERE条件
        List<ColumnBean> conditions = new ArrayList<ColumnBean>();
        conditions
                .add(new ColumnBean("ID", "100", "=", ColumnBean.Type.STRING));
        conditions.add(new ColumnBean("NAME", "testname2", "=",
                ColumnBean.Type.STRING, "AND"));

        // 更新
        DataExecute.update(dbConn, "SAMPLE_TABLE", columns, conditions);