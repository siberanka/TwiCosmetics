package com.siberanka.twicosmetics.mysql.tables;

import com.siberanka.twicosmetics.cosmetics.Category;
import com.siberanka.twicosmetics.cosmetics.type.CosmeticType;
import com.siberanka.twicosmetics.mysql.column.Column;
import com.siberanka.twicosmetics.mysql.column.StringColumn;
import com.siberanka.twicosmetics.mysql.column.UniqueConstraint;
import com.siberanka.twicosmetics.mysql.query.InsertQuery;
import com.siberanka.twicosmetics.mysql.query.InsertValue;
import com.siberanka.twicosmetics.mysql.query.SelectSubquery;

import javax.sql.DataSource;
import java.util.Locale;

public class CosmeticTable extends Table {

    public CosmeticTable(DataSource dataSource, String name) {
        super(dataSource, name);
    }

    @Override
    public void setupTableInfo() {
        tableInfo.add(new Column<>("id", "INTEGER AUTO_INCREMENT PRIMARY KEY", Integer.class));
        tableInfo.add(new StringColumn("category", 32, true));
        tableInfo.add(new StringColumn("type", 32, true));
        tableInfo.add(new UniqueConstraint("category", "type"));
        tableInfo.add(new UniqueConstraint("id", "category"));
    }

    @Override
    public void loadBaseData() {
        InsertQuery insert = insertIgnore("category", "type");
        for (Category cat : Category.values()) {
            InsertValue catItem = new InsertValue(cat.toString().toLowerCase(Locale.ROOT));
            for (CosmeticType<?> type : cat.getValues()) {
                insert.insert(catItem, new InsertValue(type.getConfigName().toLowerCase(Locale.ROOT)));
            }
        }
        insert.execute();
    }

    public SelectSubquery subqueryFor(CosmeticType<?> type, boolean insert) {
        return new SelectSubquery(insert ? null : "id", getWrappedName(), "id")
                .where("category", cleanCategoryName(type)).where("type", cleanCosmeticName(type));
    }

    /*
     * Debugging only. For actual database interactions, use `subqueryFor`
     */
    public int getCosmeticID(CosmeticType<?> type) {
        return select("id").where("category", cleanCategoryName(type)).where("type", cleanCosmeticName(type)).asInt();
    }
}
