/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal.util;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * @author rushan
 *
 */
public class EmfHelper {

	public static class EInterface implements EObject {

		@Override
		public EList<Adapter> eAdapters() {
			return null;
		}

		@Override
		public boolean eDeliver() {
			return false;
		}

		@Override
		public void eSetDeliver(boolean deliver) {
		}

		@Override
		public void eNotify(Notification notification) {
		}

		@Override
		public EClass eClass() {
			return null;
		}

		@Override
		public Resource eResource() {
			return null;
		}

		@Override
		public EObject eContainer() {
			return null;
		}

		@Override
		public EStructuralFeature eContainingFeature() {
			return null;
		}

		@Override
		public EReference eContainmentFeature() {
			return null;
		}

		@Override
		public EList<EObject> eContents() {
			return null;
		}

		@Override
		public TreeIterator<EObject> eAllContents() {
			return null;
		}

		@Override
		public boolean eIsProxy() {
			return false;
		}

		@Override
		public EList<EObject> eCrossReferences() {
			return null;
		}

		@Override
		public Object eGet(EStructuralFeature feature) {
			return null;
		}

		@Override
		public Object eGet(EStructuralFeature feature, boolean resolve) {
			return null;
		}

		@Override
		public void eSet(EStructuralFeature feature, Object newValue) {

		}

		@Override
		public boolean eIsSet(EStructuralFeature feature) {
			return false;
		}

		@Override
		public void eUnset(EStructuralFeature feature) {

		}

		@Override
		public Object eInvoke(EOperation operation, EList<?> arguments)
				throws InvocationTargetException {
			return null;
		}

	}

}
