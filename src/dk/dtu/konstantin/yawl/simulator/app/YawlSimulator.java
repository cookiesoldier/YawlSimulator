package dk.dtu.konstantin.yawl.simulator.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.pnml.tools.epnk.annotations.netannotations.NetAnnotation;
import org.pnml.tools.epnk.annotations.netannotations.NetannotationsFactory;
import org.pnml.tools.epnk.annotations.netannotations.ObjectAnnotation;
import org.pnml.tools.epnk.applications.ApplicationWithUIManager;
import org.pnml.tools.epnk.helpers.FlatAccess;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNet;
import org.pnml.tools.epnk.pnmlcoremodel.PlaceNode;
import org.pnml.tools.epnk.pnmlcoremodel.RefPlace;

import dk.dtu.konstantin.yawl.simulator.yawlannotations.EnabledTransition;
import dk.dtu.konstantin.yawl.simulator.yawlannotations.Marking;
import dk.dtu.konstantin.yawl.simulator.yawlannotations.SelectArc;
import dk.dtu.konstantin.yawl.simulator.yawlannotations.YawlannotationsFactory;
import yawl.helpers.YawlFunctions;
import yawl.Arc;
import yawl.TypeOfC;
import yawl.TypeOfT;
import yawl.Transition;
import yawl.TypeOfA;
import yawl.Place;

public class YawlSimulator extends ApplicationWithUIManager {

	public YawlSimulator(PetriNet petrinet) {
		super(petrinet);
		// TODO Auto-generated constructor stub
	}


	@Override
	protected void initializeContents() {
		FlatAccess flatNet = new FlatAccess(this.getPetrinet());
		Map<Place,Integer> initialMarking = computeInitialMarking(flatNet);

		NetAnnotation initialAnnotation = computeAnnotation(flatNet, initialMarking);
		initialAnnotation.setNet(this.getPetrinet());


		this.getNetAnnotations().getNetAnnotations().add(initialAnnotation);
		this.getNetAnnotations().setCurrent(initialAnnotation);
	}
	
	
	
	
	public  NetAnnotation initialAnnotation() {
		
		return null;
	}
	
	
	
	
	public Map <Place, Integer> initialMarking() {
		
		Map <Place, Integer> result = new HashMap <Place, Integer>();
		
		Iterator <EObject> contents = this.getPetrinet().eAllContents();
				
		while (contents.hasNext()){
			EObject object=(EObject) contents.next();
			
			if (object instanceof Place) {
				Place place = (Place) object;
				
				if (YawlFunctions.isStartPlace(place)){
				result.put(place, 1);	
				}
					
				
			}
			
			
		}
		
		return result;
	/* implement method of initial marking  to */
	}
	
	public Map<Place, Integer> computeMarking() {
		Map<Place,Integer> marking = new HashMap<Place,Integer>();
		for (ObjectAnnotation annotation: this.getNetAnnotations().getCurrent().getObjectAnnotations()) {
			if (annotation instanceof Marking) {
				Marking markingAnnotation = (Marking) annotation;
				Object object = markingAnnotation.getObject();
				if (object instanceof Place && markingAnnotation.getValue() > 0) {
					Place ptPlace = (Place) object;
					marking.put(ptPlace, markingAnnotation.getValue());
				}
			}
		}
		return marking;
	}

	
	public Map<Place, Integer> computeInitialMarking(FlatAccess flatNet) {
		Map<Place,Integer> markings = new HashMap<Place,Integer>();
		// Place startPlace = null; //TODO find det
		// Place defaultPlace = null;
		
		// flatNet.getPlaces(); alternative 
		// Place endPlace = null;

		Iterator<EObject> it = getPetrinet().eAllContents();

		while(it.hasNext()){
			Object obj = it.next();  
			if(obj instanceof Place){
				/* if(null == ((Place)obj).getType()){
					defaultPlace = (Place) obj;
					// Marking marking = YawlannotationsFactory.eINSTANCE.createMarking();		
					// marking.setObject(defaultPlace);
					// marking.setValue(0);
					markings.put(defaultPlace, marking.getValue());
				}
				else */ 
				if(((Place)obj).getType() != null &&
						((Place)obj).getType().getText() == TypeOfC.START){
					Place  startPlace = (Place) obj;
					// Marking marking = YawlannotationsFactory.eINSTANCE.createMarking();		
					// marking.setObject(startPlace);
					// marking.setValue(1);
					markings.put(startPlace, 1);
				} 
			}  
		}
		return markings;
	}
	
	
	
	public NetAnnotation computeAnnotation(FlatAccess flatNet, Map<Place, Integer> marking) {
		NetAnnotation annotation = NetannotationsFactory.eINSTANCE.createNetAnnotation();
		for (Place place: marking.keySet()) {
			int value = marking.get(place);
			// S�tter markering for hvor mange tokens der er p� en place
			if (value > 0) {
				Marking markingAnnotation = YawlannotationsFactory.eINSTANCE.createMarking();
				markingAnnotation.setValue(value);
				markingAnnotation.setObject(place);
				annotation.getObjectAnnotations().add(markingAnnotation);

				// also annotate reference places with the current marking of the place
				for (RefPlace ref: flatNet.getRefPlaces(place)) {
					// Place temp = ((Place)ref);
					Marking markingAnnotationRef = YawlannotationsFactory.eINSTANCE.createMarking();
					markingAnnotationRef.setValue(value);
					markingAnnotationRef.setObject(ref);
					annotation.getObjectAnnotations().add(markingAnnotationRef);	
				}

			}

		}

		for (Object transition: flatNet.getTransitions()) {

			if (transition instanceof Transition) {
				Transition transTemp = ((Transition)transition);

				if (enabled(flatNet, marking, (Transition) transition)) {
					EnabledTransition transitionAnnotation = YawlannotationsFactory.eINSTANCE.createEnabledTransition();
					transitionAnnotation.setObject(transTemp);
					annotation.getObjectAnnotations().add(transitionAnnotation); // Transition f�r farve
					if( null == transTemp.getTypeOfJoin()){ // Single join
						if(!transTemp.getIn().isEmpty()){
							// not needed in this case
							for(Object arc: transTemp.getIn()){
								Arc arcTemp = ((Arc)arc);
								SelectArc slArc = YawlannotationsFactory.eINSTANCE.createSelectArc();
								slArc.setObject(arcTemp);
								slArc.setTargetTransition(transitionAnnotation); // S�tter targetTransition p� selectArc
								annotation.getObjectAnnotations().add(slArc);
								slArc.setSelected(true);
							}
						}
					}

					if(null != transTemp.getTypeOfJoin() && transTemp.getTypeOfJoin().getText() == TypeOfT.XOR){
						if(!transTemp.getIn().isEmpty()){
							boolean selectFlag = false;
							for(Object arc: transTemp.getIn()){
								// check type
								Arc arcTemp = ((Arc)arc);
								Place place = ((Place) flatNet.resolve((PlaceNode) arcTemp.getSource()));
								if (marking.getOrDefault(place, -1) > 0) {

									SelectArc slArc = YawlannotationsFactory.eINSTANCE.createSelectArc();
									slArc.setObject(arcTemp);

									slArc.setTargetTransition(transitionAnnotation);
									annotation.getObjectAnnotations().add(slArc);
									if(!selectFlag){
										slArc.setSelected(true);
										selectFlag = true;
									}else{
										slArc.setSelected(false);
									}
								}
								

							}
						} 

					}
					if(null != transTemp.getTypeOfSplit() && transTemp.getTypeOfSplit().getText() == TypeOfT.XOR){
						if(!transTemp.getOut().isEmpty()){
							boolean selectFlag = false;
							for(Object arc: transTemp.getOut()){
								Arc arcTemp = ((Arc)arc);
								SelectArc slArc = YawlannotationsFactory.eINSTANCE.createSelectArc();
								slArc.setObject(arcTemp);
								slArc.setSourceTransition(transitionAnnotation);
								annotation.getObjectAnnotations().add(slArc);
								if(!selectFlag){
									slArc.setSelected(true);
									selectFlag = true;
								}else{
									slArc.setSelected(false);

								}

							}
						} 

					}
					
					// not checked below
					if(null != transTemp.getTypeOfSplit() && transTemp.getTypeOfSplit().getText() == TypeOfT.OR || null != transTemp.getTypeOfSplit() && ((Transition)transition).getTypeOfSplit().getText() == TypeOfT.AND){
						if(!transTemp.getOut().isEmpty()){
							for(Object arc: transTemp.getOut()){
								Arc arcTemp = ((Arc)arc);
								SelectArc slArc = YawlannotationsFactory.eINSTANCE.createSelectArc();
								slArc.setObject(arcTemp);
								slArc.setSourceTransition(transitionAnnotation);
								annotation.getObjectAnnotations().add(slArc);
								slArc.setSelected(true);

							}
						} 

					}


					// dont need these annotations in this case!
					if(null != transTemp.getTypeOfJoin() && transTemp.getTypeOfJoin().getText() == TypeOfT.OR){
						if(!transTemp.getIn().isEmpty()){
							for(Object arc: transTemp.getIn()){
								Arc arcTemp = ((Arc)arc);
								SelectArc slArc = YawlannotationsFactory.eINSTANCE.createSelectArc();
								slArc.setObject(arcTemp);
								slArc.setTargetTransition(transitionAnnotation);
								annotation.getObjectAnnotations().add(slArc);
								Place place =((Place) ((Arc)slArc.getObject()).getSource());
								if(marking.getOrDefault(place, -1) > 0){
									slArc.setSelected(true);
								}
							}
						} 

					}
					
					// nothing to select
					if(null != transTemp.getTypeOfJoin() && ((Transition)transition).getTypeOfJoin().getText() == TypeOfT.AND){
						if(!transTemp.getIn().isEmpty()){
							boolean allHasToken = true;
							ArrayList<SelectArc> arcsEnabled = new ArrayList<SelectArc>();
							
							for(Object arc: transTemp.getIn()){
								Arc arcTemp = ((Arc)arc);
								Place place = (Place) arcTemp.getSource();
								SelectArc slArc = YawlannotationsFactory.eINSTANCE.createSelectArc();
								slArc.setObject(arcTemp);
								slArc.setTargetTransition(transitionAnnotation);
								arcsEnabled.add(slArc);
								slArc.setSelected(false); // S�tter den til false, for at v�re sikker p� den er false, hvis ikke alle er gode
								annotation.getObjectAnnotations().add(slArc);
								if(marking.getOrDefault(place, -1) < 1 && null ==arcTemp.getType()){
									allHasToken = false;
								}else if(marking.getOrDefault(place, -1) >= 1 && null !=arcTemp.getType()){
									slArc.setSelected(true);
								}
							}
							if(allHasToken){
								for (SelectArc slArc : arcsEnabled) {
									if(slArc != null){
										slArc.setSelected(true);
									}
								}
							}


						}	

					}
					
					// nothing to do here either
					/*
					if(null == transTemp.getTypeOfSplit()){

						Arc arcTemp = (Arc) transTemp.getOut().get(0);
						SelectArc slArc = YawlannotationsFactory.eINSTANCE.createSelectArc();
						slArc.setObject(arcTemp);
						slArc.setSourceTransition(transitionAnnotation);
						annotation.getObjectAnnotations().add(slArc);
						slArc.setSelected(true);




					}
					*/


				}
			}
		}
		return annotation;
	}
	
	Map<Place, Integer> fireTransition(FlatAccess flatNet, Map<Place, Integer> marking1, Transition transition, ArrayList<SelectArc> inArcs,ArrayList<SelectArc> outArcs) {
		Map<Place,Integer> marking2 = new HashMap<Place, Integer>();
		for (Place place: marking1.keySet()) {
			marking2.put(place, marking1.put(place, marking1.get(place)));
		}
		int available = 0;
		boolean normalArcSelected = false;
		// Går igennem arcs og tjekker for om det er en resetArc,
		// hvis det er skal der fjernes markings
		for(SelectArc slArc : inArcs){
			Arc arc = (Arc)slArc.getObject();
			Place source = ((Place) ((Arc)slArc.getObject()).getSource());
			available = marking1.get(source);
			if(arc.getType() != null){
				marking1.put(source, 0);


			}if(null == arc.getType() && marking2.getOrDefault(source, -1) >0){
				normalArcSelected = true;
			}
		}


		for (SelectArc arc: inArcs) {

			Arc slArc  = (Arc) arc.getObject();
			Place source = (Place) slArc.getSource();
			available = 0;

			if (marking1.containsKey(source)) {
				available = marking1.get(source);
			}
			int needed = 1; 

			marking2.put(source, available-needed);
		}
		if(normalArcSelected){
			for (SelectArc arc: outArcs) {
				if (arc != null) {
					Arc slArc = (Arc)arc.getObject();

					Place target  =((Place) slArc.getTarget());
					if (target instanceof Place) {
						available = 0;
						if (marking1.containsKey(target)) {
							available = marking1.get(target);
						}
						int provided = 1; 
						marking2.put(target, available+provided);
					}
				}
			}
		}

		return marking2;
	}


	boolean enabled(FlatAccess flatNet, Map<Place, Integer> marking, Transition transition) {
		// TODO this does not work yet if there is more than one arc between the same
		//      place and the same transition!
		
		if(transition.getTypeOfJoin() == null || transition.getTypeOfJoin().getText() == TypeOfT.AND) {
			for (Object arc: flatNet.getIn(transition)) {
				if (arc instanceof Arc) {
					Arc ptArc = (Arc) arc;
					if (ptArc.getType() == null || ptArc.getType().getText() == TypeOfA.NORMAL) {
						Object source = ptArc.getSource();
						if (source instanceof PlaceNode) {
							source = flatNet.resolve((PlaceNode) source);
							if (source instanceof Place) {
								int m= marking.getOrDefault((Place) source, 0);
								if (m <= 0) {
									return false;
								}
							}
						}
					}
				}
			}
			return true;
		} else {
			for (Object arc: flatNet.getIn(transition)) {
				if (arc instanceof Arc) {
					Arc ptArc = (Arc) arc;
					if (ptArc.getType() == null || ptArc.getType().getText() == TypeOfA.NORMAL) {
						Object source = ptArc.getSource();
						if (source instanceof PlaceNode) {
							source = flatNet.resolve((PlaceNode) source);
							if (source instanceof Place) {
								int m= marking.getOrDefault((Place) source, 0);
								if (m > 0) {
									return true;
								}
							}
						}
					}
				}
			}
			return false;
		}
	
	}

	

}
