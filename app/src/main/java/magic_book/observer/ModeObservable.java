package magic_book.observer;

import magic_book.window.gui.Mode;

public class ModeObservable extends Observable<ModeObserver> {

	public ModeObservable() {
		super();
	}

	public void notifyModeChanged(Mode mode) {
		for (ModeObserver modeObserver : listObservers) {
			modeObserver.modeChanged(mode);
		}
	}
}
