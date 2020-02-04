package magic_book.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<T> {

	List<T> listObservers;

	public Observable() {
		listObservers = new ArrayList<>();
	}

	public void addObserver(T observer) {
		listObservers.add(observer);
	}

	public void removeObserver(T observer) {
		listObservers.remove(observer);
	}

	public void clearObserver() {
		listObservers.clear();
	}
}
