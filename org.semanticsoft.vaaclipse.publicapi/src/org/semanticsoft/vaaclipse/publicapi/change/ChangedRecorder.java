package org.semanticsoft.vaaclipse.publicapi.change;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EContentAdapter;

/**
 * Implementation of {@link EContentAdapter} that records all notifications. And
 * implements logic to {@link #undo()} and {@link #redo()} the changes.
 */
public class ChangedRecorder extends EContentAdapter {

	private final Notifier notifier;

	private List<Notification> notifications = new ArrayList<Notification>();

	public ChangedRecorder(Notifier notifier) {
		super();
		this.notifier = notifier;
	}

	/**
	 * Starts the recording.
	 */
	public void start() {
		setTarget(notifier);
	}

	/**
	 * Stops the recording.
	 */
	public void stop() {
		unsetTarget(notifier);
	}

	/**
	 * Returns all notifications recorded.
	 * 
	 * @return
	 */
	public List<Notification> getNotifications() {
		return notifications;
	}

	@Override
	public void notifyChanged(Notification notification) {
		super.notifyChanged(notification);

		if (notification.isTouch()) {
			return;
		}

		if (notification.getEventType() == Notification.REMOVING_ADAPTER) {
			return;
		}

		if (notification.getEventType() == Notification.RESOLVE) {
			return;
		}

		notifications.add(notification);
	}

	/**
	 * Undos all notifications.
	 */
	public void undo() {
		for (int i = notifications.size() - 1; i >= 0; i--) {
			Notification change = notifications.get(i);
			undo(change);
		}
	}

	/**
	 * Redos all notifications.
	 */
	public void redo() {
		for (int i = 0; i < notifications.size(); i++) {
			Notification change = notifications.get(i);
			redo(change);
		}
	}

	@SuppressWarnings("unchecked")
	protected void undo(Notification change) {
		EObject notifier = (EObject) change.getNotifier();
		switch (change.getEventType()) {
		case Notification.ADD: {
			List<Object> list = (List<Object>) notifier
					.eGet((EStructuralFeature) change.getFeature());
			list.remove(change.getNewValue());
		}
			break;
		case Notification.ADD_MANY:
			throw new IllegalStateException("Implement me");
		case Notification.MOVE:
			throw new IllegalStateException("Implement me");
		case Notification.REMOVE: {
			List<Object> list = (List<Object>) notifier
					.eGet((EStructuralFeature) change.getFeature());
			if (change.getPosition() > 0) {
				list.add(change.getPosition(), change.getOldValue());
			} else {
				list.add(change.getOldValue());
			}
		}
			break;
		case Notification.REMOVE_MANY:
			throw new IllegalStateException("Implement me");
		case Notification.SET: {
			Object value = change.getOldValue();
			notifier.eSet((EStructuralFeature) change.getFeature(), value);
		}
			break;
		case Notification.UNSET: {
			Object value = change.getOldValue();
			notifier.eSet((EStructuralFeature) change.getFeature(), value);
		}
			break;
		}
	}

	@SuppressWarnings("unchecked")
	protected void redo(Notification change) {
		EObject notifier = (EObject) change.getNotifier();
		switch (change.getEventType()) {
		case Notification.ADD: {
			List<Object> list = (List<Object>) notifier
					.eGet((EStructuralFeature) change.getFeature());
			if (change.getPosition() > 0) {
				list.add(change.getPosition(), change.getNewValue());
			} else {
				list.add(change.getNewValue());
			}
		}
			break;
		case Notification.ADD_MANY:
			throw new IllegalStateException("Implement me");
		case Notification.MOVE:
			throw new IllegalStateException("Implement me");
		case Notification.REMOVE: {
			List<Object> list = (List<Object>) notifier
					.eGet((EStructuralFeature) change.getFeature());
			list.remove(change.getOldValue());
		}
			break;
		case Notification.REMOVE_MANY:
			throw new IllegalStateException("Implement me");
		case Notification.SET: {
			Object value = change.getNewValue();
			notifier.eSet((EStructuralFeature) change.getFeature(), value);
		}
		case Notification.UNSET: {
			notifier.eUnset((EStructuralFeature) change.getFeature());
		}
		}
	}
}
