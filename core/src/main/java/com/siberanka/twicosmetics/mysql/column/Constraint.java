package com.siberanka.twicosmetics.mysql.column;

import com.siberanka.twicosmetics.mysql.tables.TableInfo;

public class Constraint implements TableInfo {
    private final String constraint;

    public Constraint(String constraint) {
        this.constraint = constraint;
    }

    @Override
    public String toSQL() {
        return constraint;
    }

}
