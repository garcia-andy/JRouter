/**
 * Principal module with all requireds and exports
 */
module jrouterfx {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires lombok;
    requires java.compiler;
    requires auto.service;
    requires auto.service.annotations;

    exports jrouterfx.Loaders;
    exports jrouterfx.hooks;
    exports jrouterfx.reactive;
    exports jrouterfx.Annotations;
    exports jrouterfx;
}