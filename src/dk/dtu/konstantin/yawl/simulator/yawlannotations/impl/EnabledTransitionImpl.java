/**
 */
package dk.dtu.konstantin.yawl.simulator.yawlannotations.impl;

import dk.dtu.konstantin.yawl.simulator.yawlannotations.EnabledTransition;
import dk.dtu.konstantin.yawl.simulator.yawlannotations.SelectArc;
import dk.dtu.konstantin.yawl.simulator.yawlannotations.YawlannotationsPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.pnml.tools.epnk.annotations.netannotations.impl.ObjectAnnotationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Enabled Transition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link dk.dtu.konstantin.yawl.simulator.yawlannotations.impl.EnabledTransitionImpl#getOutArcs <em>Out Arcs</em>}</li>
 *   <li>{@link dk.dtu.konstantin.yawl.simulator.yawlannotations.impl.EnabledTransitionImpl#getInArcs <em>In Arcs</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EnabledTransitionImpl extends ObjectAnnotationImpl implements EnabledTransition {
	/**
	 * The cached value of the '{@link #getOutArcs() <em>Out Arcs</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutArcs()
	 * @generated
	 * @ordered
	 */
	protected EList<SelectArc> outArcs;

	/**
	 * The cached value of the '{@link #getInArcs() <em>In Arcs</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInArcs()
	 * @generated
	 * @ordered
	 */
	protected EList<SelectArc> inArcs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EnabledTransitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return YawlannotationsPackage.Literals.ENABLED_TRANSITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SelectArc> getOutArcs() {
		if (outArcs == null) {
			outArcs = new EObjectWithInverseResolvingEList<SelectArc>(SelectArc.class, this, YawlannotationsPackage.ENABLED_TRANSITION__OUT_ARCS, YawlannotationsPackage.SELECT_ARC__SOURCE_TRANSITION);
		}
		return outArcs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SelectArc> getInArcs() {
		if (inArcs == null) {
			inArcs = new EObjectWithInverseResolvingEList<SelectArc>(SelectArc.class, this, YawlannotationsPackage.ENABLED_TRANSITION__IN_ARCS, YawlannotationsPackage.SELECT_ARC__TARGET_TRANSITION);
		}
		return inArcs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case YawlannotationsPackage.ENABLED_TRANSITION__OUT_ARCS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutArcs()).basicAdd(otherEnd, msgs);
			case YawlannotationsPackage.ENABLED_TRANSITION__IN_ARCS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getInArcs()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case YawlannotationsPackage.ENABLED_TRANSITION__OUT_ARCS:
				return ((InternalEList<?>)getOutArcs()).basicRemove(otherEnd, msgs);
			case YawlannotationsPackage.ENABLED_TRANSITION__IN_ARCS:
				return ((InternalEList<?>)getInArcs()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case YawlannotationsPackage.ENABLED_TRANSITION__OUT_ARCS:
				return getOutArcs();
			case YawlannotationsPackage.ENABLED_TRANSITION__IN_ARCS:
				return getInArcs();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case YawlannotationsPackage.ENABLED_TRANSITION__OUT_ARCS:
				getOutArcs().clear();
				getOutArcs().addAll((Collection<? extends SelectArc>)newValue);
				return;
			case YawlannotationsPackage.ENABLED_TRANSITION__IN_ARCS:
				getInArcs().clear();
				getInArcs().addAll((Collection<? extends SelectArc>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case YawlannotationsPackage.ENABLED_TRANSITION__OUT_ARCS:
				getOutArcs().clear();
				return;
			case YawlannotationsPackage.ENABLED_TRANSITION__IN_ARCS:
				getInArcs().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case YawlannotationsPackage.ENABLED_TRANSITION__OUT_ARCS:
				return outArcs != null && !outArcs.isEmpty();
			case YawlannotationsPackage.ENABLED_TRANSITION__IN_ARCS:
				return inArcs != null && !inArcs.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //EnabledTransitionImpl
