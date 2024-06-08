# JRouterFX Framework

JRouterFX is for easy ways to use JavaFX & FXML files for controllers and views.

## We Have:
### JRouter Annotation: 
>For annotate and class and automatic load a view
```java
@JRouter(url = "/", view = "init.fxml")
public class Index {
    // The constructor recive abstract class MainLoader automatically called
    public Index(MainLoader m){
        //... initialization code
    }
}

// or
@JRouter("/")
public class Index {
    // The constructor recive abstract class MainLoader automatically called
    public Index(MainLoader m){
        m.loadView("init.fxml",this);
    }
}
```
### Normal Loader: 
>For first load all routes and controllers
```java
Loader ld = JRouterFX.Loaders.useLoader(App.class,"projectName","routesFolder");
```
### Lazy Loader: 
>For load controllers and views on demand
```java
LazyLoader ld = JRouterFX.Loaders.useLazyLoader(App.class,"projectName","routesFolder");
```
### States: 
>For reactivity on one value
```java
State<String> name = JRouterFX.Hooks.useState("Andy");
```
### Signals: 
>For reactivity with events callbacks
```java
Signal<String> sign = JRouterFX.Hooks.useSignal("Andy",
    (s) -> { System.out.println("onChange: " + s.get()); },
    (s) -> { System.out.println("onRequest: " + s.get()); },
    (s) -> { System.out.println("onSubscribe: " + s.get()); },
    (s) -> { System.out.println("onGet: Some One get the value!"); }
);
```

## The class JRouterFX for access to sub packages:
### JRouterFX.Hook: State & Signal Hooks easy access
### JRouterFX.Loaders: Loader & LazyLoader Hooks easy access

```java
/**
 * Global Class for access to Hooks and all Apis
*/
public class JRouterFX {

    /**
     * Class for wrapper of Hooks
     */
    public static class Hooks{

        /**
         * Class with the useState apis
         */
        public static class State extends StatesHook{}
        /**
         * Class with the useSignal apis
         */
        public static class Signal extends SignalHook{}
    }
    /**
     * Class for wrapper Loaders Hooks
     */
    public static class Loaders extends LoadersHook {} 
}
```