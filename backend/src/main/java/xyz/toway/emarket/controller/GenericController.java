package xyz.toway.emarket.controller;

import graphql.GraphQLContext;
import org.apache.logging.log4j.util.Strings;

import static xyz.toway.emarket.GenericConstants.*;

public class GenericController {
    public String lang(GraphQLContext context) {
        return context.getOrDefault(LANGUAGE_HEADER, LANGUAGE_DEFAULT);
    }

    public String customer(GraphQLContext context) {
        return context.getOrDefault(CUSTOMER_HEADER, Strings.EMPTY);
    }
}
