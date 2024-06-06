package io.github.andy030124.reactive;
import java.util.ArrayList;
import lombok.Getter;

public class Observable<T extends Observer> {

    @Getter
    private ArrayList<T> _observers = new ArrayList<T>();

    public void subscribe(T observer)
    { _observers.add(observer); }

    public void unsubscribe(T observer)
    { _observers.remove(observer); }

    public void notifyObservers(){
        _observers.forEach((observer) -> {
            observer.update();
        });
    }

}
