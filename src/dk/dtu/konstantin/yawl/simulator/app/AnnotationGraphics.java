package dk.dtu.konstantin.yawl.simulator.app;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.pnml.tools.epnk.annotations.netannotations.ObjectAnnotation;
import org.pnml.tools.epnk.applications.ui.IPresentationHandler;
import org.pnml.tools.epnk.applications.ui.figures.PolylineOverlay;

import dk.dtu.konstantin.yawl.simulator.yawlannotations.SelectArc;

public class AnnotationGraphics implements IPresentationHandler {

	@Override
	public IFigure handle(ObjectAnnotation annotation, AbstractGraphicalEditPart graphicalEditPart) {

	if (annotation instanceof SelectArc) {
		SelectArc slArc = (SelectArc) annotation;
	
				if (!slArc.isSelected()) {
					PolylineOverlay overlay = new PolylineOverlay((ConnectionNodeEditPart) graphicalEditPart);
					overlay.setForegroundColor(ColorConstants.lightGray);
					overlay.setBackgroundColor(ColorConstants.lightGray);
				
				return overlay;
			}
		
		} 
		return null;
	}
}




