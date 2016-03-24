package org.semanticsoft.vaaclipse.publicapi.change;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Uses the {@link ChangeCommand} to record notifications.
 */
public abstract class ChangeCommand extends AbstractCommand {

	private Resource resource;
	private ChangedRecorder recorder;

	public ChangeCommand(String name, Resource resource) {
		super(name);
		this.resource = resource;
	}

	@Override
	public void execute() {
		recorder.start();

		doExecute();

		recorder.stop();
	}
	
	/**
	 * Execute your operations.
	 */
	protected abstract void doExecute();

	@Override
	public void redo() {
		recorder.redo();
	}

	@Override
	protected boolean prepare() {
		recorder = new ChangedRecorder(resource);
		return true;
	}

	@Override
	public void undo() {
		recorder.undo();
	}

	@Override
	public boolean canUndo() {
		return super.canUndo();
	}

}
