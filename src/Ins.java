package newLearning;

import javax.swing.Action;

import jade.content.AgentAction;

/*
 * This is an action on Users.java for UserOntology
 */
public class Ins implements AgentAction{

	private Insuarance item;

	public Insuarance getItem() {
		return item;
	}

	public void setItem(Insuarance item) {
		this.item = item;
	}
	
}
