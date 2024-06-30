/**
 * Principal module with all requireds and exports
 */
module jrouterfx {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires lombok;

    exports jrouterfx.Loaders;
    exports jrouterfx.hooks;
    exports jrouterfx.reactive;
    exports jrouterfx.Api;
    exports jrouterfx.Apps;
    exports jrouterfx.GuiProviders;
    exports jrouterfx;
}