package newLearning;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import jade.content.ContentElementList;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.ContractNetInitiator;
import jade.proto.ContractNetResponder;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

/*
 * Agent communicating with InsuranceManager and MatchMaker and passing values to set up a insurance with selected users
 */
public class UserAgent extends Agent{

	private Codec codec = new SLCodec();
	private Ontology ontology = UserOntology.getInstance();
	private UserGUI userGUI;

	private Vector attendeeAgents = new Vector();
	private boolean userFlag;
	private HashMap userData = new HashMap();
	private HashSet<Insuarance> availableProviders = new HashSet<Insuarance>();
	private HashSet<Insuarance> myInsuarances = new HashSet<Insuarance>();
	Set<String> keys = new HashSet<String>();
	Insuarance insuaranceToCheck = new Insuarance();

//	Set<String> attendeeList = new HashSet<>();

	public void setup() {
		System.out.println("User agent is created");
		final UserAgent agent = this;
		addBehaviour(new User(this));

		addBehaviour(new CyclicBehaviour(this) {
			@Override
			public void action(){
				ACLMessage msg = receive();

				if(msg!=null){
					if("acceptedProposal".equals(msg.getConversationId())){
						try {
							myInsuarances.add((Insuarance)msg.getContentObject());
							System.out.println("added in myInsurance "+myInsuarances.size());
							userGUI.updateLogger((Insuarance)msg.getContentObject());
						} catch (UnreadableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if("addingUsers".equals(msg.getConversationId())) {
						try {

							userData = ((HashMap)msg.getContentObject());
							if(userData!= null) {
								keys = userData.keySet();
							} else {
								System.out.println("userdata is null");
							}
						} catch (UnreadableException e) {
							e.printStackTrace();
						}
					}
					if("availableProviders".equals(msg.getConversationId())) {
						try {
							System.out.println("in available providers");
							availableProviders = (HashSet<Insuarance>) msg.getContentObject();
							userGUI.openInsuranceCB(availableProviders);
							
						} catch (UnreadableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					if(msg.getPerformative() == ACLMessage.REJECT_PROPOSAL ) {
						if(	"proposalOutput".equals(msg.getConversationId())) {
							userGUI.notifyUser("Sorry!  can't be set today!:(");							
						}
					} 
					if(msg.getPerformative() == ACLMessage.AGREE) {
						if(msg.getConversationId().equals("addAgendaResult")) {
							Insuarance insurance = new Insuarance();
							try {
								insurance = (Insuarance)msg.getContentObject();
							} catch (UnreadableException e) {
								e.printStackTrace();
							}
//							userGUI.updateAgenda(insurance);
						}
					}
					if(msg.getPerformative() == ACLMessage.CANCEL) {
						userGUI.notifyUserWithDialogueBox("Already insurance set at this time");
					}
				}
			}       
		});

		addBehaviour(new TickerBehaviour(this, 60000) {
			protected void onTick() {
				// Update the list of seller agents
				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType("user-interface");
				template.addServices(sd);
				try {
					DFAgentDescription[] result = DFService.search(myAgent,
							template);
				}
				catch (FIPAException fe) {
					fe.printStackTrace();
				}
			}
		} );

		addBehaviour(new TickerBehaviour(this, 3000) {
			protected void onTick() {
				boolean flag = false;
				userGUI = new UserGuiImpl();
				userGUI.setAgent(agent);
				userGUI.show();
				flag= true;
				userFlag = true;
				removeBehaviour(this);
				if(keys!=null && !keys.isEmpty()) {
					for(String key:keys) {
						
						if(getLocalName().equals(key)) {
							// Create and show the GUI 
//							if("T".equals(key.charAt(0)))
//							userGUI = new UserGuiImpl();
//							userGUI.setAgent(agent);
//							userGUI.show();
//							flag= true;
//							userFlag = true;
						}
					}
//					removeBehaviour(this);
				}
				if(!flag) {
					System.out.println("User not in Database "+getLocalName());
					System.out.println("Current users are "+keys);
				}
			}
		} );

	}
	public void initiateContract(final String insId) {
		addBehaviour(new OneShotBehaviour() {
			public void action() {
				System.out.println(" initiating contract with ."+insId);
				insuaranceToCheck.setInsId(insId);
				ACLMessage aclmsg = new ACLMessage(ACLMessage.REQUEST);
				aclmsg.addReceiver(new AID("InsuranceManager", AID.ISLOCALNAME));
				aclmsg.setPerformative(ACLMessage.REQUEST);
				aclmsg.setConversationId("initiateContract");
				try {
					aclmsg.setContentObject(insuaranceToCheck);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				send(aclmsg);
			}
		} );
	}
	public void addAgenda(String title, String location, Date insuranceStartTime, Date insuranceEndTime) {
		Insuarance insurance = new Insuarance();
	/*	insurance.setInsuranceTitle(title);
		insurance.setLocation(location);
		insurance.setInsuranceStartTime(insuranceStartTime);
		insurance.setInsuranceEndTime(insuranceEndTime);
	*/	ACLMessage aclmsg = new ACLMessage(ACLMessage.REQUEST);
		aclmsg.addReceiver(new AID("InsuranceManager", AID.ISLOCALNAME));
		try {
			aclmsg.setPerformative(ACLMessage.REQUEST);
			aclmsg.setConversationId("addAgenda");
			aclmsg.setContentObject(insurance);
			send(aclmsg);

		}  catch (IOException e) {
			e.printStackTrace();
			userGUI.notifyUserWithDialogueBox("Not able to connect to server!");
		}
	}

	public void deleteAgent() {
//		ACLMessage aclmsg = new ACLMessage(ACLMessage.INFORM);
//		aclmsg.addReceiver(new AID("MatchMaker", AID.ISLOCALNAME));
//		aclmsg.setContent("delete");
//		send(aclmsg);

		doDelete();
	}

	public HashMap askUser() {

		return userData;
	}
	
	public void findOptions(String insType, int maxRate, int timePeriod, int coverage) {
		ACLMessage aclmsg = new ACLMessage(ACLMessage.PROPOSE);
		insuaranceToCheck.setInsType(insType);
		insuaranceToCheck.setInsMaxPrice(maxRate);
		insuaranceToCheck.setTimePeriod(timePeriod);
		insuaranceToCheck.setCoverage(coverage);
		
		aclmsg.addReceiver(new AID("InsuranceManager", AID.ISLOCALNAME));
		try {
			aclmsg.setContent(insType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		aclmsg.setPerformative(ACLMessage.REQUEST);
		aclmsg.setConversationId("findInsProviders");
		send(aclmsg);
	}
	public void showAvailableInsurances() {
		System.out.println("in showavailable");
		userGUI.openInsuranceCB(myInsuarances);
	}
	
	public void proposeInsurance(InsuranceInfo insuranceInfo) {


		ACLMessage aclmsg = new ACLMessage(ACLMessage.PROPOSE);
		aclmsg.addReceiver(new AID("InsuranceManager", AID.ISLOCALNAME));
		try {
			aclmsg.setContentObject(insuranceInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		aclmsg.setPerformative(ACLMessage.PROPOSE);
		aclmsg.setConversationId("proposeInsurance");
		send(aclmsg);
	}
	public class User extends CyclicBehaviour {


		public User(UserAgent userInterfaceAgent) {

		}

		@Override
		public void action() {
			ACLMessage aclmsg = new ACLMessage(ACLMessage.REQUEST);
			aclmsg.addReceiver(new AID("MatchMaker", AID.ISLOCALNAME));
			aclmsg.setConversationId("askForUsers");
			send(aclmsg);
			if (userFlag) {
				System.out.println("removing behaviour");
				removeBehaviour(this);
			}
		}
	}

	private class ManagerClass extends TickerBehaviour {

		private String insId, location;
		private int insuranceMaxPrice, insuranceminPrice;
		private long deadline, initTime, deltaT;

		private ManagerClass(UserAgent a, String eId, String loc, int insuranceST, int insuranceET) {
			super(a, 3000); 
			insId = eId;
			location = loc;
			insuranceMaxPrice = insuranceST;
			insuranceminPrice = insuranceET;
		}

		public void onTick() {
			long currentTime = System.currentTimeMillis();
			long elapsedTime = currentTime - initTime;
			System.out.println(insId+"in else 1 "+insuranceMaxPrice);
			myAgent.addBehaviour(new ProposeInsuranceInitiator(getLocalName(), insuranceMaxPrice,insuranceminPrice, this));
		}
	}

	public ACLMessage cfp = new ACLMessage(ACLMessage.CFP);

	public class ProposeInsuranceInitiator extends ContractNetInitiator {

		private String insId;

		private int insuranceMaxPrice, insuranceMinPrice;
		private ManagerClass manager;

		public ProposeInsuranceInitiator(String eId, int insMax, int insMin, ManagerClass m) {
			super(UserAgent.this, cfp);
			insId = eId;
			insuranceMaxPrice = insMax;
			insuranceMinPrice = insMin;
			manager = m;
			System.out.println("in ProposeInsuranceInitiator "+insId);
			Insuarance insurance = new Insuarance();
/*			insurance.setEmpId(insId);
			insurance.setInsuranceStartTime(insuranceST);
			insurance.setInsuranceEndTime(insuranceET);
*/			Ins meetAction = new Ins();
			meetAction.setItem(insurance);
			Action act = new Action(UserAgent.this.getAID(), meetAction);
			try {
				cfp.setLanguage(codec.getName());
				cfp.setOntology(ontology.getName());
				UserAgent.this.getContentManager().fillContent(cfp, act);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		protected Vector prepareCfps(ACLMessage cfp) {
			cfp.clearAllReceiver();
			System.out.println("preparingcfs"+attendeeAgents.size());
			for (int i = 0; i < attendeeAgents.size(); ++i) {
				cfp.addReceiver((AID) attendeeAgents.get(i));
			}

			Vector v = new Vector();
			v.add(cfp);
			if (attendeeAgents.size() > 0)
				userGUI.notifyUser("Sent Call for Proposal to "+attendeeAgents.size()+" users.");
			return v;
		}

		protected void handleAllResponses(Vector responses, Vector acceptances) {
			ACLMessage bestOffer = null;
			int bestPrice = -1;
			Date startTime = new Date();
			Date endTime  = new Date();

			for (int i = 0; i < responses.size(); i++) {
				ACLMessage rsp = (ACLMessage) responses.get(i);
				if (rsp.getPerformative() == ACLMessage.PROPOSE) {

					try {
						ContentElementList cel = (ContentElementList)myAgent.getContentManager().extractContent(rsp);
//						startTime = ((ConnectedUser) cel.get(1)).getInsuranceStartTime();
//						endTime = ((ConnectedUser) cel.get(1)).getInsuranceEndTime();
						if(bestOffer == null) {
							bestOffer = rsp;
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	
protected void handleInform(ACLMessage inform) {

			int price = Integer.parseInt(inform.getContent());
			userGUI.notifyUser("Book "+insId+" successfully purchased. Price =" + price);
			manager.stop();
		}
		
	}
}
