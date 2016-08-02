package dk.dtu.konstantin.yawl.simulator.app;

import org.pnml.tools.epnk.applications.ApplicationFactory;
import org.pnml.tools.epnk.applications.ApplicationWithUIManager;
import org.pnml.tools.epnk.pnmlcoremodel.PetriNet;

import yawl.YAWLNet;

public class YawlSimulatorFactory extends ApplicationFactory {

	@Override
	public String getName() {
		return "YAWL Simulator";
	}

	@Override
	public String getDescription() {
		return "A simple YAWL simulator not yet taken XOR, OR and RESET arcs into account";
	}

	@Override
	public boolean isApplicable(PetriNet net) {
		return net.getType() instanceof YAWLNet;
	}

	@Override
	public ApplicationWithUIManager startApplication(PetriNet net) {
		return new YawlSimulator(net);
	}

}