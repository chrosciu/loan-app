open module eu.chrost.loan {
    requires static lombok;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.web;
    requires spring.context;

    //opens eu.chrost.loan to spring.context, spring.core, spring.beans, com.fasterxml.jackson.core;
}