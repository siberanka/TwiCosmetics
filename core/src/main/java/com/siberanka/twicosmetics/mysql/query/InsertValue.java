package com.siberanka.twicosmetics.mysql.query;

public class InsertValue extends ClauseItemLiteral implements InsertItem {

    public InsertValue(Object value) {
        super(null, value);
    }
}
