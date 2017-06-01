package com.sky.tools.log.parse;

import java.util.Collection;

/**
 * [一句话描述类的作用]
 * [详述类的功能。]
 * Created by sky on 2017/5/28.
 */

public class CollectionParser implements Parser<Collection> {
    public static final CollectionParser COLLECTION_PARSER = new CollectionParser();

    @Override
    public Class<Collection> getParseType() {
        return Collection.class;
    }

    @Override
    public String parseToString(Collection collection) {
        return null;
    }
}
