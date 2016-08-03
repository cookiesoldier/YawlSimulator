package dk.dtu.konstantin.yawl.simulator.app;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.draw2d.MouseEvent;
import org.pnml.tools.epnk.annotations.netannotations.NetAnnotation;
import org.pnml.tools.epnk.annotations.netannotations.NetAnnotations;
import org.pnml.tools.epnk.annotations.netannotations.ObjectAnnotation;
import org.pnml.tools.epnk.applications.ui.IActionHandler;
import org.pnml.tools.epnk.helpers.FlatAccess;

import dk.dtu.konstantin.yawl.simulator.yawlannotations.EnabledTransition;
import dk.dtu.konstantin.yawl.simulator.yawlannotations.SelectArc;
import dk.dtu.konstantin.yawl.simulator.app.YawlSimulator;
import yawl.Arc;
import yawl.Place;
import yawl.Transition;
import yawl.TypeOfT;

public class ClickHandler implements IActionHandler {

	YawlSimulator application;

	public ClickHandler(YawlSimulator application) {
		super();		
		this.application = application;
	}

	@Override
	public boolean mouseDoubleClicked(MouseEvent arg0, ObjectAnnotation annotation) {

		NetAnnotations netAnnotations = application.getNetAnnotations();
		NetAnnotation current = netAnnotations.getCurrent();
		FlatAccess flatNet = new FlatAccess(application.getPetrinet());

		if (current.getObjectAnnotations().contains(annotation)) {
			Object object = annotation.getObject();
			if (object instanceof Transition) {

				Transition transition = (Transition) object;
				ArrayList<SelectArc> inArcs = new ArrayList<SelectArc>();
				ArrayList<SelectArc> outArcs = new ArrayList<SelectArc>();
				EnabledTransition et = (EnabledTransition) annotation;
				for(Object tempArc: et.getInArcs()){
					if(tempArc instanceof SelectArc){
						if(((SelectArc) tempArc).isSelected()){
							inArcs.add((SelectArc)tempArc);
						}
					}
				}
				for(Object tempArc: et.getOutArcs()){
					if(tempArc instanceof SelectArc){
						if(((SelectArc) tempArc).isSelected()){
							outArcs.add((SelectArc)tempArc);
						}
					}
				}

				Map<Place,Integer> marking1 = application.computeMarking();

				if (application.enabled(flatNet, marking1, transition)) {
					Map<Place,Integer> marking2 = application.fireTransition(flatNet, marking1, transition, inArcs, outArcs);
					NetAnnotation netAnnotation = application.computeAnnotation(flatNet, marking2);
					netAnnotation.setNet(application.getPetrinet());


					application.deleteNetAnnotationAfterCurrent();
					application.addNetAnnotationAsCurrent(netAnnotation);
				}
			}

		} 

		return false;

	}

	@Override
	public boolean mousePressed(MouseEvent arg0, ObjectAnnotation annotation) {
		Map<Place, Integer> marking =  application.computeMarking();
		if (annotation instanceof SelectArc) {

			SelectArc slArc= (SelectArc) annotation;
			EnabledTransition etTarget = null;
			EnabledTransition etSource = null;

			if(null != slArc.getTargetTransition()){
				etTarget =(EnabledTransition) slArc.getTargetTransition();
				Transition targetTransition = (Transition) etTarget.getObject();

				// Logik for at v�lge SelectArcs

				// XOR JOIN
				if(null != targetTransition.getTypeOfJoin() && targetTransition.getTypeOfJoin().getText() == TypeOfT.XOR){
					if(marking.containsKey(((Arc)slArc.getObject()).getSource()) && !slArc.isSelected()){
						slArc.setSelected(true);
						for (int i = 0; i < etTarget.getInArcs().size(); i++) {
							if(etTarget.getInArcs().get(i) != slArc){
								((SelectArc) etTarget.getInArcs().get(i)).setSelected(false);
							}
						}

					}
					application.update();
					return true;

				}else if(null != targetTransition.getTypeOfJoin() && targetTransition.getTypeOfJoin().getText() == TypeOfT.AND){
					//AND JOIN
					boolean enabledFlag = true;
					slArc.setSelected(false); // Should be false at the beginning
					// Should only be true if all are true
					for (SelectArc tempSlArc: etTarget.getInArcs()) {

						Place place = ((Place)((Arc)tempSlArc.getObject()).getSource());
						if(marking.getOrDefault(place, -1) < 1){
							enabledFlag = false;
						}
					}
					if(enabledFlag){
						for (SelectArc tempSlArc : etTarget.getInArcs()) {
							tempSlArc.setSelected(true);
						}	
					}
					application.update();
					return true;
				}else if(null != targetTransition.getTypeOfJoin() && targetTransition.getTypeOfJoin().getText() == TypeOfT.OR){
					//OR JOIN
					
					if (slArc.isSelected()) {

						// Alle starter med at være true.
						//skal kunne fra vælge en til der kun er 1 valgt tilbage
						slArc.setSelected(false);
						boolean allGood = false;
						for (SelectArc tempArc : etTarget.getOutArcs()) {
							if (tempArc.isSelected()) {
								allGood = true;
							}
						}
						if(!allGood){
							slArc.setSelected(true);
						}else{
							slArc.setSelected(false);
						}
					}else{
						slArc.setSelected(true);
					}


					application.update();
					return true;
				}
				else if(null == targetTransition.getTypeOfJoin()){
					slArc.setSelected(true);
					application.update();
					return true;
				}
			}
			else if(null != slArc.getSourceTransition()){
				etSource = (EnabledTransition) slArc.getSourceTransition();
				Transition sourceTransition = (Transition) etSource.getObject();

				if(null != sourceTransition.getTypeOfSplit() && sourceTransition.getTypeOfSplit().getText() == TypeOfT.XOR){
					slArc.setSelected(true);
					for (int i = 0; i < sourceTransition.getOut().size(); i++) {
						if(etSource.getOutArcs().get(i) != slArc){
							((SelectArc) etSource.getOutArcs().get(i)).setSelected(false);

						}


					}
					application.update();
					return true;
				}
				else if(null != sourceTransition.getOut() && sourceTransition.getTypeOfSplit().getText() == TypeOfT.OR){
					//OR split
					if (slArc.isSelected()) {

						// Alle starter med at være true.
						//skal kunne fra vælge en til der kun er 1 valgt tilbage
						slArc.setSelected(false);
						boolean allGood = false;
						for (SelectArc tempArc : etSource.getOutArcs()) {
							if (tempArc.isSelected()) {
								allGood = true;
							}
						}
						if(!allGood){
							slArc.setSelected(true);
						}else{
							slArc.setSelected(false);
						}
					}else{
						slArc.setSelected(true);
					}


					application.update();
					return true;
				}

			}
			application.update();
			return true; 
		}
		return false;
	}

	@Override
	public boolean mouseReleased(MouseEvent arg0, ObjectAnnotation annotation) {

		return false; 
	}

}
