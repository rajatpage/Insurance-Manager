package newLearning;

import jade.content.onto.*;
import jade.content.schema.*;

/*
 * UserOntology class
 */
public class UserOntology extends Ontology{
	// The name identifying this ontology

	public static final String USERS = "Users";

	public static final String INSURANCE_SCHEDULE = "insuranceSchedule";

	public static final String INSURANCE_ID = "insId";

	public static final String INSURANCE_NAME= "insName";

	public static final String INSURANCE_GROUP = "insGroup";

	public static final String COSTS_ITEM = "item";

	public static final String COSTS_PRICE = "price";

	public static final String SET_INSURANCE = "setInsuarance";

	public static final String SELL_ITEM = "item";

	public static final String ONTOLOGY_NAME = "User-ontology";
	private static Ontology theInstance = new UserOntology();

	public static Ontology getInstance() {

		return theInstance;
	}

	private UserOntology() {
		super(ONTOLOGY_NAME, BasicOntology.getInstance());
		try {
			add(new ConceptSchema(USERS), Users.class);
			add(new PredicateSchema(INSURANCE_SCHEDULE), Users.class);
			add(new AgentActionSchema(SET_INSURANCE), SetInsurance.class);



			// Structure of the schema for the Users concept
			ConceptSchema cs = (ConceptSchema) getSchema(USERS);
			cs.add(INSURANCE_ID, (PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(INSURANCE_NAME, (PrimitiveSchema) getSchema(BasicOntology.STRING), 0,
					ObjectSchema.UNLIMITED);
			cs.add(INSURANCE_GROUP, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);



			// Structure of the schema for the Costs predicate

			PredicateSchema ps = (PredicateSchema) getSchema(INSURANCE_SCHEDULE);

			ps.add(COSTS_ITEM, (ConceptSchema) cs);

			ps.add(COSTS_PRICE, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));



			// Structure of the schema for the Sell agent action

			AgentActionSchema as = (AgentActionSchema) getSchema(SET_INSURANCE);

			as.add(SELL_ITEM, (ConceptSchema) getSchema(USERS));

		}

		catch (OntologyException oe) {

			oe.printStackTrace();

		}

	}


}
