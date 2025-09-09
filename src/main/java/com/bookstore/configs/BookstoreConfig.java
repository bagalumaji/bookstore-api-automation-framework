package com.bookstore.configs;

import org.aeonbits.owner.Config;
import org.checkerframework.checker.units.qual.K;


@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:${user.dir}/src/test/resources/configs/application.properties",
})
public interface BookstoreConfig extends Config {
    @Key("base.url")
    String baseUri();

    @Key("test.id")
    int id();

    @Key("endpoint.signup")
    String signupEndPoint();

    @Key("test.username")
    String email();

    @Key("test.password")
    String password();

    @Key("endpoint.login")
    String loginEndpoint();

    @Key("endpoint.books")
    String booksEndPoint();

    @Key("endpoint.health")
    String healthEndPoint();

    @Key("environment")
    String environment();

    @Key("invalid.token")
    String invalidToken();

    @Key("endpoint.books.id")
    String booksEndPointWithParamId();

}
