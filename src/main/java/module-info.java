/**
 * Principal module with all requireds and exports
 */
module jrouter {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires lombok;

    exports jrouter.Loaders;
    exports jrouter.hooks;
    exports jrouter.reactive;
    exports jrouter.Api;
    exports jrouter.Apps;
    exports jrouter.GuiProviders;
    exports jrouter;
}