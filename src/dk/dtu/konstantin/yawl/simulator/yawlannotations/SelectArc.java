/**
 */
package dk.dtu.konstantin.yawl.simulator.yawlannotations;

import org.pnml.tools.epnk.annotations.netannotations.ObjectAnnotation;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Select Arc</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link dk.dtu.konstantin.yawl.simulator.yawlannotations.SelectArc#isSelected <em>Selected</em>}</li>
 *   <li>{@link dk.dtu.konstantin.yawl.simulator.yawlannotations.SelectArc#getSourceTransition <em>Source Transition</em>}</li>
 *   <li>{@link dk.dtu.konstantin.yawl.simulator.yawlannotations.SelectArc#getTargetTransition <em>Target Transition</em>}</li>
 * </ul>
 *
 * @see dk.dtu.konstantin.yawl.simulator.yawlannotations.YawlannotationsPackage#getSelectArc()
 * @model
 * @generated
 */
public interface SelectArc extends ObjectAnnotation {
	/**
	 * Returns the value of the '<em><b>Selected</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Selected</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Selected</em>' attribute.
	 * @see #setSelected(boolean)
	 * @see dk.dtu.konstantin.yawl.simulator.yawlannotations.YawlannotationsPackage#getSelectArc_Selected()
	 * @model required="true"
	 * @generated
	 */
	boolean isSelected();

	/**
	 * Sets the value of the '{@link dk.dtu.konstantin.yawl.simulator.yawlannotations.SelectArc#isSelected <em>Selected</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Selected</em>' attribute.
	 * @see #isSelected()
	 * @generated
	 */
	void setSelected(boolean value);

	/**
	 * Returns the value of the '<em><b>Source Transition</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link dk.dtu.konstantin.yawl.simulator.yawlannotations.EnabledTransition#getOutArcs <em>Out Arcs</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Transition</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Transition</em>' reference.
	 * @see #setSourceTransition(EnabledTransition)
	 * @see dk.dtu.konstantin.yawl.simulator.yawlannotations.YawlannotationsPackage#getSelectArc_SourceTransition()
	 * @see dk.dtu.konstantin.yawl.simulator.yawlannotations.EnabledTransition#getOutArcs
	 * @model opposite="outArcs"
	 * @generated
	 */
	EnabledTransition getSourceTransition();

	/**
	 * Sets the value of the '{@link dk.dtu.konstantin.yawl.simulator.yawlannotations.SelectArc#getSourceTransition <em>Source Transition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Transition</em>' reference.
	 * @see #getSourceTransition()
	 * @generated
	 */
	void setSourceTransition(EnabledTransition value);

	/**
	 * Returns the value of the '<em><b>Target Transition</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link dk.dtu.konstantin.yawl.simulator.yawlannotations.EnabledTransition#getInArcs <em>In Arcs</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Transition</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Transition</em>' reference.
	 * @see #setTargetTransition(EnabledTransition)
	 * @see dk.dtu.konstantin.yawl.simulator.yawlannotations.YawlannotationsPackage#getSelectArc_TargetTransition()
	 * @see dk.dtu.konstantin.yawl.simulator.yawlannotations.EnabledTransition#getInArcs
	 * @model opposite="inArcs"
	 * @generated
	 */
	EnabledTransition getTargetTransition();

	/**
	 * Sets the value of the '{@link dk.dtu.konstantin.yawl.simulator.yawlannotations.SelectArc#getTargetTransition <em>Target Transition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Transition</em>' reference.
	 * @see #getTargetTransition()
	 * @generated
	 */
	void setTargetTransition(EnabledTransition value);

} // SelectArc
