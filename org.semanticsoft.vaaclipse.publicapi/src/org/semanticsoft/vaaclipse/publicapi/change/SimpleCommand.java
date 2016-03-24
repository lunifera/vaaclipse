package org.semanticsoft.vaaclipse.publicapi.change;

import org.eclipse.emf.common.command.AbstractCommand;

/**
 * Uses the {@link SimpleCommand} to record notifications.
 */
public abstract class SimpleCommand extends AbstractCommand {

	public SimpleCommand(String name) {
		super(name);
	}

	@Override
	public void execute() {
		doExecute();
	}

	@Override
	public void redo() {
		doRedo();
	}

	@Override
	protected boolean prepare() {
		return true;
	}

	@Override
	public void undo() {
		doUndo();
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	protected abstract void doExecute();

	/**
	 * Override if required.
	 */
	protected void doRedo() {
		doExecute();
	}

	protected abstract void doUndo();

}
