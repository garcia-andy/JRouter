# JRouter Framework

JRouter is for easy ways to create Reactive GUIs agnostic of the GUI Provider.

## We Have:
### Route Annotation: 
#### For annotate class and automatic load a view (of fxml for example)
```java
import jrouter.Api.Route;

@Route(url = "/", view = "init.fxml")
public class Index {
    // The constructor recive abstract class MainLoader automatically called
    public Index(MainLoader m){
        //... initialization code
    }
}

// or
import jrouter.Api.Route;

@Route("/")
public class Index {
    // The constructor recive abstract class MainLoader automatically called
    public Index(MainLoader m){
        m.loadView("init.fxml",this);
    }
}
```

### Abstractions Layers: 
#### App for Automatics Loaders
```java
App<Scene> app = new App<Scene>("Title of The Window", new AbsoluteLoader<Scene>());
// same of
App<Scene> app = new App<Scene>("Title of The Window");

// for lazy loading use
App<Scene> app = new App<Scene>("Title of The Window", new LazyLoader<Scene>());
```

#### Hooks for create the Application (Most recommended)
```java
// for absolute loader use
App<Scene> app = JRouter.Hooks.App.useApp("Title of The Window");
app.start(GuiProvider);

// for lazy loading use
App<Scene> app = JRouter.Hooks.App.useLazyApp("Title of The Window");
app.start(GuiProvider);

// OR

// for absolute loader use
App<Scene> app = JRouter.Hooks.App.useApp("Title of The Window",GuiProvider);
app.start();

// for lazy loading use
App<Scene> app = JRouter.Hooks.App.useLazyApp("Title of The Window",GuiProvider);
app.start();
```

#### Your own Application Providers
```java
public class MyAbsoluteLoader <SceneT> implements ApplicationProvider<SceneT>{
    @Override
    public MainLoader<SceneT> provide(GuiProvider<SceneT> prov) 
    { // do somethings }
}
```

### GUI Providers
#### Your own GUI Provider
```java
public class JFXProvider extends GuiProvider<Scene>
```

### Observers
#### API for Observer objects, implement the interface Observer
```java
public class MyObserver implements Observer{
    @Override
    public void update(){
        // do somethings...
    }
}
```

### Observables
#### API for Observable objects, extend the generic type Observable
```java
public class MyObservableType extends Observable<MyObserver>{
    // do somethings...
}
```

### States: 
#### Easy reactivity on one value
```java
State<String> name = JRouter.Hooks.useState("Andy");
// or
State<String> name = new State<String>("Andy");
```

#### Observer object with States
```java
State<String> name = JRouter.Hooks.useState("Andy", ObserverObj);
// or
State<String> name = new State<String>("Andy");
name.subscribe(ObserverObj);
```

### Signals: 
#### Reactivity with events callbacks
```java
Signal<String> sign = JRouter.Hooks.useSignal("Andy",
    (s) -> { System.out.println("onChange: " + s.get()); },
    (s) -> { System.out.println("onRequest: " + s.get()); },
    (s) -> { System.out.println("onSubscribe: " + s.get()); },
    (s) -> { System.out.println("onGet: Some One get the value!"); }
);
// or
Signal<String> sign = new Signal<String>("Andy");
sign.onChange((s) -> { System.out.println("onChange: " + s.get()); });
sign.onRequest((s) -> { System.out.println("onRequest: " + s.get()); });
sign.onSubscribe((s) -> { System.out.println("onSubscribe: " + s.get()); });
sign.listen(
    Signal.SignalType.onGet, 
    (s) -> { System.out.println("onGet: Some One get the value!"); }
);
```

## The class JRouter for easy access to Hooks:
### JRouter.Hook: State, Signal, App & Loaders 

```java
/**
 * Global Class for access to Hooks and all Apis
*/
public class JRouter {

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

        /**
         * Class with the useApp & useLazyApp apis
         */
        public static class App extends AppHook{}

        /**
         * Class for wrapper Loaders Hooks
         */
        public static class Loaders extends LoadersHook {} 
    }
}
```
