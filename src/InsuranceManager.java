package newLearning;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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


public class InsuranceManager extends Agent{

	private Codec codec = new SLCodec();
	//	HashMap<String, HashSet<Insuarance>> storedData = new HashMap<String, HashSet<Insuarance>>();
	HashSet<Insuarance> storedData = new HashSet<Insuarance>();
	MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative( ACLMessage.INFORM ),
			MessageTemplate.MatchPerformative( ACLMessage.PROPOSE )) ;

	protected void setup() {
		final InsuranceManager a = this;
		getContentManager().registerLanguage(codec);

		//Registering InsuranceManager to Yellow pages
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Insuarance-Manager");
		sd.setName(getLocalName()+"-Insuarance-Manager");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}

		addBehaviour(new CyclicBehaviour() {
			public void action() {
				ACLMessage msg = receive();
				if(msg!=null){
					if(msg.getPerformative()== ACLMessage.REQUEST ) {
						if(msg.getConversationId()!=null) {
							String convoId = msg.getConversationId();
							switch (convoId) {
							case "initiateContract":
								Insuarance userDemandInsuarance = new Insuarance();
								try {
									userDemandInsuarance = (Insuarance) msg.getContentObject();
								} catch (UnreadableException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								for(Insuarance ins: storedData) {
									if(userDemandInsuarance.getInsId().equals(ins.getInsId())) {
										boolean flag= false;
										int sellerPrice = ins.getInsPrice();
										for(int i= 0; i<=10; i++) {
											if(userDemandInsuarance.getInsMaxPrice()>= sellerPrice) {
												AID senderAid = msg.getSender();
												ACLMessage reply = msg.createReply();
												reply.setConversationId("acceptedProposal");
												Insuarance insuaranceFinal = ins;
												insuaranceFinal.setInsPrice(sellerPrice);
												try {
													reply.setContentObject(insuaranceFinal);
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												flag=true;
												send(reply);
												break;
											} else {
												sellerPrice = sellerPrice-10;
												
											}
										}
										if(!flag) {
											System.out.println("Not possible in your range");
										}
									}
								}
								break;
							case "findInsProviders":
								HashSet<Insuarance> matchedProviders = new HashSet<Insuarance>();
								String insTypeToSearch = msg.getContent();
								AID senderAid = msg.getSender();
								String sender = senderAid.getLocalName();
								ACLMessage reply = msg.createReply();
								reply.setConversationId("availableProviders");
								for(Insuarance ins: storedData) {
									if(ins.getInsType().equals(insTypeToSearch)) {
										matchedProviders.add(ins);
									}
								}
								try {
									reply.setContentObject(matchedProviders);

								} catch(ArrayStoreException e) {
									reply.setPerformative(ACLMessage.CANCEL);

								} catch (IOException e) {
									// TODO Auto-generated catch block
									reply.setPerformative(ACLMessage.CANCEL);
									e.printStackTrace();
								} 
								System.out.println("sending reply with providers: "+matchedProviders.size());
								send(reply);
								break;

							case "addInsuranceOptions":
								try {
									Insuarance insToAdd = (Insuarance) msg.getContentObject();
									boolean presentFlag = false;
									for(Insuarance ins: storedData) {
										if(insToAdd.getInsId().equals(ins.getInsId())) {
											presentFlag= true;
											break;
										}
									}
									if(!presentFlag) {
										storedData.add(insToAdd);
									}
									else {
										System.out.println("this insurance is already present in database");
										//										ACLMessage replyToAdder = msg.createReply();
										//										replyToAdder.setConversationId("availableProviders");
										//										send(reply);
									}
									System.out.println("added insuarance count: "+ storedData.size());
								} catch (UnreadableException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								break;
							}
						}
						if(msg.getConversationId().equals("")) {

						}
						String msgContent = msg.getContent();
					} 
				}
			}
		});
	}
}
