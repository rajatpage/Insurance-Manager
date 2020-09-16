package newLearning;

import java.io.IOException;
import java.util.HashSet;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ProvidersAgent extends Agent{
	
		private HashSet<Insuarance> availableInsurances;
		private ProviderGUI providerGUI;

		// Put agent initializations here
		protected void setup() {
			// Create the availableInsurances
			availableInsurances = new HashSet<Insuarance>();

			// Create and show the GUI 
			providerGUI = new ProvidersGuiImpl(this);
			providerGUI.show();

			// Register the insurance-provider service in the yellow pages
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			ServiceDescription sd = new ServiceDescription();
			sd.setType("insurance-provider");
			sd.setName("JADE-Insurance-Management");
			dfd.addServices(sd);
			try {
				DFService.register(this, dfd);
			}
			catch (FIPAException fe) {
				fe.printStackTrace();
			}

			// Add the behaviour serving queries from buyer agents
			addBehaviour(new OfferRequestsServer());

			// Add the behaviour serving purchase orders from buyer agents
			addBehaviour(new PurchaseOrdersServer());
		}

		// Put agent clean-up operations here
		protected void takeDown() {
			// Deregister from the yellow pages
			try {
				DFService.deregister(this);
			}
			catch (FIPAException fe) {
				fe.printStackTrace();
			}
			// Close the GUI
			providerGUI.dispose();
			// Printout a dismissal message
			System.out.println("Provider-agent "+getAID().getName()+" terminating.");
		}

		/**
	     This is invoked by the GUI when the user adds a new book for sale
		 */
		public void updateavailableInsurances(final String insType, final int insPrice, final int insMinPrice,
				final int coverage, final int timePeriod, final String description) {
			addBehaviour(new OneShotBehaviour() {
				public void action() {
					Insuarance insToAdd = new Insuarance();
					insToAdd.setInsId("IN00"+availableInsurances.size()+1);
					insToAdd.setInsType(insType);
					insToAdd.setInsPrice(insPrice);
					insToAdd.setInsMinPrice(insMinPrice);
					insToAdd.setCoverage(coverage);
					insToAdd.setTimePeriod(timePeriod);
					insToAdd.setInsDescription(description);
					insToAdd.setInsCompanyName(myAgent.getLocalName());
					availableInsurances.add(insToAdd);
					System.out.println(" inserted into availableInsurances.");
					ACLMessage aclmsg = new ACLMessage(ACLMessage.REQUEST);
					aclmsg.addReceiver(new AID("InsuranceManager", AID.ISLOCALNAME));
					try {
						aclmsg.setPerformative(ACLMessage.REQUEST);
						aclmsg.setConversationId("addInsuranceOptions");
						aclmsg.setContentObject(insToAdd);
						send(aclmsg);

					}  catch (IOException e) {
						e.printStackTrace();
//						userGUI.notifyUserWithDialogueBox("Not able to connect to server!");
					}
				}
			} );
		}

		/**
		   Inner class OfferRequestsServer.
		   This is the behaviour used by Book-seller agents to serve incoming requests 
		   for offer from buyer agents.
		   If the requested book is in the local availableInsurances the seller agent replies 
		   with a PROPOSE message specifying the price. Otherwise a REFUSE message is
		   sent back.
		 */
		private class OfferRequestsServer extends CyclicBehaviour {
			public void action() {
				MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
				ACLMessage msg = myAgent.receive(mt);
				if (msg != null) {
					// CFP Message received. Process it
					String title = msg.getContent();
					ACLMessage reply = msg.createReply();
/*
					Integer price = (Integer) availableInsurances.get(title);
					if (price != null) {
						// The requested book is available for sale. Reply with the price
						reply.setPerformative(ACLMessage.PROPOSE);
						reply.setContent(String.valueOf(price.intValue()));
					}
					else {
						// The requested book is NOT available for sale.
						reply.setPerformative(ACLMessage.REFUSE);
						reply.setContent("not-available");
					}
*/					myAgent.send(reply);
				}
				else {
					block();
				}
			}
		}  // End of inner class OfferRequestsServer

		/**
		   Inner class PurchaseOrdersServer.
		   This is the behaviour used by Book-seller agents to serve incoming 
		   offer acceptances (i.e. purchase orders) from buyer agents.
		   The seller agent removes the purchased book from its availableInsurances 
		   and replies with an INFORM message to notify the buyer that the
		   purchase has been sucesfully completed.
		 */
		private class PurchaseOrdersServer extends CyclicBehaviour {
			public void action() {
				MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
				ACLMessage msg = myAgent.receive(mt);
				if (msg != null) {
					// ACCEPT_PROPOSAL Message received. Process it
					String title = msg.getContent();
					ACLMessage reply = msg.createReply();
/*
					Integer price = (Integer) availableInsurances.remove(title);
					if (price != null) {
						reply.setPerformative(ACLMessage.INFORM);
						System.out.println(title+" sold to agent "+msg.getSender().getName());
					}
					else {
						// The requested book has been sold to another buyer in the meanwhile .
						reply.setPerformative(ACLMessage.FAILURE);
						reply.setContent("not-available");
					}
*/					myAgent.send(reply);
				}
				else {
					block();
				}
			}
		}  // End of inner class OfferRequestsServer


}
