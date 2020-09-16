package newLearning;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jade.content.ContentManager;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.ContractNetResponder;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class MatchMaker extends Agent{

	private HashMap userData = new HashMap();

	@Override
	protected void setup() {
		String localname = this.getLocalName();
		System.out.println("matchmaker" + localname);
		final Agent a= this;



		addBehaviour(new CyclicBehaviour(this) {
			@Override
			public void action(){
				ACLMessage msg = receive();
				if(msg!=null){
					if(msg.getConversationId()!=null) {
						String convoId = msg.getConversationId();
						switch (convoId) {
						case "addUser":
							System.out.println("adding user");
							try {

								userData = ((HashMap)msg.getContentObject());
								System.out.println("in adduser of matchmaker"+userData);
								if(userData!=null) {
									Set<String> keys = userData.keySet();
									System.out.println("the keys in matchmaker are: "+ keys);
									addBehaviour(new UserInformer(a, userData));	

								}
							} catch (UnreadableException e) {
								e.printStackTrace();
							}
							break;

						case "removeUser":
							try {
								String keyToRemove = (String)msg.getContentObject();
								userData.remove(keyToRemove);
								Set<String> keys = userData.keySet();
								System.out.println("the keys in removed matchmaker are: "+ keys);
							} catch (UnreadableException e) {
								e.printStackTrace();
							}
							break;

						case "deleteAgent":
							System.out.println("matchmaker is deleted");
							doDelete();
							break;

						case "askForUsers":				
							ACLMessage reply = new ACLMessage( ACLMessage.INFORM );
							try {
								reply.setContentObject(userData);
								reply.setConversationId("addingUsers");
							} catch (IOException e1) {
								e1.printStackTrace();
							};
							reply.addReceiver( msg.getSender() );
							send(reply);
							break;

						case "validateUsers":
							ACLMessage validationReply = new ACLMessage( ACLMessage.INFORM );

							try {
								String [] credentials = (String[])msg.getContentObject();
								System.out.println("before getting data in validateusers, username: "+ credentials[0]);
								Users user = (Users) userData.get(credentials[0]);
								if(user!=null) {
									if(credentials[1].equals(user.getUserPassword())){
										System.out.println(user.getUserID().substring(0, 1)+" inside if after checking credentials "+user.getUserID().substring(1));
										AgentContainer c = getContainerController();	
										if("U-".equals(user.getUserID().substring(0, 2))) {
											try {
												AgentController a = ((ContainerController) c).createNewAgent( user.getUserName(), "newLearning.UserAgent", null );
												a.start();
												validationReply.setConversationId("deleteAgent");
											}
											catch (Exception e){
												e.printStackTrace();
											}
										}
										else if("P-".equals(user.getUserID().substring(0, 2))) {
											try {
												System.out.println(user.getUserID()+"username is "+user.getUserName());
												AgentController a = ((ContainerController) c).createNewAgent( user.getUserName(), "newLearning.ProvidersAgent", null );
												a.start();
												validationReply.setConversationId("deleteAgent");
											}
											catch (Exception e){
												e.printStackTrace();
											}
										}
										else {
											System.out.println("User ID not found in matchmaker");
											validationReply.setConversationId("Error");
										}
									}
									else {
										validationReply.setConversationId("wrongCredentials");
									}
								}
								else {
									System.out.println("user not in database");
								}
							} catch (UnreadableException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							validationReply.addReceiver( msg.getSender() );
							send(validationReply);
							break;

						default:

						}
					} else {
						System.out.println("no conversation id in matchmaker");
					}
				}
			}

		});
	}
	/*
	 * class is responsible for getting all the user data by getUserData() method 
	 */
	private class UserInformer extends TickerBehaviour {

		public UserInformer(Agent a, Map user) {
			super(a, 3000);
		}

		@Override
		protected void onTick() {
			// TODO Auto-generated method stub

		}

		public Map getUserData() {
			return userData;
		}

		public void setUserData(Map userData) {
			userData = userData;
		}
	}


	public void deleteAgent() {
		this.doDelete();
	}
}
