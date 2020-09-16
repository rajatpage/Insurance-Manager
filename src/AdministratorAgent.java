package newLearning;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

// this class is responsible to handle tasks related to admin Users like adding removing and grouping users
public class AdministratorAgent extends Agent {

	AdministratorGui administratorGui;

	private Hashtable<String, String> nameCatalogue, groupCatalouge;
	private HashMap userData = new HashMap();

	public void setup() {
		System.out.println("Hello. My name is "+getLocalName());

		String matchMakerName = "MatchMaker" ;
		AgentContainer c = getContainerController();
		try {
			AgentController a = ((ContainerController) c).createNewAgent( matchMakerName, "newLearning.MatchMaker", null );
			a.start();
			AgentController b = ((ContainerController) c).createNewAgent( "InsuranceManager", "newLearning.InsuranceManager", null );
			b.start();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		nameCatalogue = new Hashtable<String, String>();
		groupCatalouge = new Hashtable<String, String>();

		// Create and show the GUI 
		administratorGui = new AdministratorGuiImpl();
		administratorGui.setAgent(this);
		administratorGui.show();

		//Registering Admin agent to yellow pages
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Register-User");
		sd.setName("JADE-Register-User");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

	}

	public boolean addUers(String userId, String userName, String userPassword, String userType) {
		if(userData!=null && userData.containsKey(userId)) {
			return false;
		}
		else {
			addBehaviour(new SystemManager(this, userId, userName, userPassword, userType, "addUser"));			
			return true;			
		}
	}

	public void removeUers(String insId, String insName, String insGroup) {
		addBehaviour(new SystemManager(this, insId, insName, insGroup,"", "removeUser"));	
	}

	protected void takeDown() {
		// Dispose the GUI if it is there
		if (administratorGui != null) {
			administratorGui.dispose();
		}
		// Printout a dismissal message
		System.out.println("Admin-agent "+getAID().getName()+"terminated.");
	}
	public void deleteAgent() {
		ACLMessage aclmsg = new ACLMessage(ACLMessage.REQUEST);
		System.out.println("before ttry in userInterface");
		aclmsg.addReceiver(new AID("MatchMaker", AID.ISLOCALNAME));
		aclmsg.setConversationId("deleteAgent");
		send(aclmsg);

		doDelete();
	}

	public void showNames() {
		Users user = (Users) userData.get("T-123");
		System.out.println("userdata is "+ user.getUserPassword());
	}
	public Hashtable<String, String> getNameCatalouge(){
		return nameCatalogue;
	}

	public Hashtable<String, String> getGroupCatalouge(){
		return groupCatalouge;
	}

	// ********	SystemManager Class to add/delete users to database***************************************************************************

	private class SystemManager extends OneShotBehaviour {

		private String userID, userName, userPassword, userType, action;

		public SystemManager(Agent a, String uId, String uName, String uPass, String uType, String act) {	
			userID = uId;
			userName = uName;
			userPassword = uPass;
			userType = uType;
			action = act;
		}

		@Override
		public void action() {
			Users user = new Users();
			user.setUserID(userID);
			user.setUserName(userName);
			user.setUserPassword(userPassword);
			user.setUserType(userType);
			String[] groupNames = new String[25];
			userData.put(userID, user);
			ACLMessage aclmsg = new ACLMessage(ACLMessage.INFORM);
			try {
				aclmsg.addReceiver(new AID("MatchMaker", AID.ISLOCALNAME));
				aclmsg.setConversationId(action);
				if(action.equals("removeUser")) {
					aclmsg.setContentObject(userID);
				}else {
					aclmsg.setContentObject(userData);
					
					ACLMessage ac = new ACLMessage(ACLMessage.INFORM);
					ac.addReceiver(new AID("InsuranceManager", AID.ISLOCALNAME));
					ac.setConversationId("addingUsersToDB");
					ac.setContent(userID);
					send(ac);
				}

			} catch (IOException e) {
				System.out.println("in IOException");
				userData.remove(userID);
				administratorGui.removeLastEntry();
				administratorGui.notifyUserWithDialogueBox(" Not connected to the server!");
			} catch (NullPointerException e){
				System.out.println("in null pointer");
				userData.remove(userID);
				administratorGui.removeLastEntry();
				administratorGui.notifyUserWithDialogueBox(" Not connected to the server!");
				e.printStackTrace();
			}	catch (Exception e) {
				System.out.println("in exception");
				userData.remove(userID);
				administratorGui.removeLastEntry();
				administratorGui.notifyUserWithDialogueBox(" Not connected to the server!");
				e.printStackTrace();
			}
			send(aclmsg);
			if(action.equals("removeUser")) {
				userData.remove(userID);
			}
		}


	}

	// ********	UserManager Class to makes changes to users ***************************************************************************

	private class UserManager extends TickerBehaviour {

		public UserManager(Agent a, long period) {
			super(a, period);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onTick() {
			// TODO Auto-generated method stub

		}

	}

}
