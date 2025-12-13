module model {
    requires static lombok;
    requires javafaker;
    requires java.sql;
    exports org.shop.model.entity;
    exports org.shop.model.util;
}