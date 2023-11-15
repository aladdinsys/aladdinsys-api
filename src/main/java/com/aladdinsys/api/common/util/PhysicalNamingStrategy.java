package com.aladdinsys.api.common.util;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.util.regex.Pattern;

public class PhysicalNamingStrategy implements org.hibernate.boot.model.naming.PhysicalNamingStrategy {
    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return null;
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return null;
    }

    @Override
    public Identifier toPhysicalTableName(final Identifier name, JdbcEnvironment jdbcEnvironment) {
        if(Pattern.matches("tb_.*", name.getText())) return name;
        return convertToSnakeCase(name, "tb_");
    }

    @Override
    public Identifier toPhysicalSequenceName(final Identifier name, JdbcEnvironment jdbcEnvironment) {
        return convertToSnakeCase(name, "seq_");
    }

    @Override
    public Identifier toPhysicalColumnName(final Identifier name, JdbcEnvironment jdbcEnvironment) {
        return convertToSnakeCase(name, "");
    }

    private Identifier convertToSnakeCase(final Identifier identifier, String prefix) {
        final String regex = "([a-z])([A-Z])";
        final String replacement = "$1_$2";
        final String newName = prefix +
                identifier.getText()
                        .replaceAll(regex, replacement)
                        .toLowerCase();
        return Identifier.toIdentifier(newName);
    }
}
