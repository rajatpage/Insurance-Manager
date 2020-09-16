package newLearning;

import jade.content.AgentAction;

public class SetInsurance implements AgentAction {

	private Insuarance item;

	public Insuarance getItem() {
		return item;
	}

	public void Insuarance(Insuarance item) {
		this.item = item;
	}
}
