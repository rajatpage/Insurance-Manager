package newLearning;

import java.io.IOException;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import newLearning.UserAgent.User;

public class LoginAgent extends Agent{

	String studentAgent;
	String tutuorAgent;
	LoginGui loginGui;

	public void setup() {

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Login-Agent");
		sd.setName(getLocalName()+"Login-Agent");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}

		loginGui= new LoginGuiImpl();
		loginGui.setAgent(this);
		loginGui.show();

		addBehaviour(new CyclicBehaviour(this) {
			@Override
			public void action(){
				ACLMessage msg = receive();
				if(msg!=null) {
					if(null!=msg.getConversationId()) {
						String convoId = msg.getConversationId();
						switch(convoId) {
						case "deleteAgent":
							System.out.println("deleting login agent");
							loginGui.dispose();
							doDelete();
							break;
						case "wrongCredentials":
							loginGui.notifyUserWithDialogueBox("Incorrect Password");
							break;
						case "Error":
							loginGui.notifyUserWithDialogueBox("Sorry! there is an error in system");
							break;
						default:
						}
					}
				}
			}
		});

	}

	public void ValidateUser(String userId, String Password) {
		System.out.println("in validatingUser userID: "+ userId +" and password "+Password);
		String [] credentials = {userId, Password};
		ACLMessage aclmsg = new ACLMessage(ACLMessage.REQUEST);
		aclmsg.addReceiver(new AID("MatchMaker", AID.ISLOCALNAME));
		aclmsg.setConversationId("validateUsers");
		try {
			aclmsg.setContentObject(credentials);
		} catch (IOException e) {
			System.out.println("error in sending credentials");

		}
		send(aclmsg);

	}

}
