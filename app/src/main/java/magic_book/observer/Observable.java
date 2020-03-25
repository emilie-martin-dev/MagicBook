package magic_book.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<T> {

	private List<T> observers;

	public Observable() {
		observers = new ArrayList<>();
	}

	public void addObserver(T observer) {
		observers.add(observer);
	}

	public void removeObserver(T observer) {
		observers.remove(observer);
	}

	public void clearObserver() {
		observers.clear();
	}

	public List<T> getObservers() {
		return observers;
	}

	public void setObservers(List<T> observers) {
		this.observers = observers;
	}
	
}
