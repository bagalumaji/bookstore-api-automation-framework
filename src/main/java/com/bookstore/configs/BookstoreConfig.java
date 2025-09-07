package com.bookstore.configs;

import org.aeonbits.owner.Config;


@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:${user.dir}/src/test/resources/configs/application.properties",
})
public interface BookstoreConfig extends Config {

}
