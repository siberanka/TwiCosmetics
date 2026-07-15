package com.siberanka.twicosmetics.mysql.query;

import java.util.List;

public interface InsertItem {
    public String toSQL(List<Object> objects);
}
