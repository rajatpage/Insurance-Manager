package newLearning;

import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.AgentActionSchema;
import jade.content.schema.ConceptSchema;
import jade.content.schema.ObjectSchema;
import jade.content.schema.PredicateSchema;
import jade.content.schema.PrimitiveSchema;

/*
 * Insuarance Ontology to set insurances and negotiate
 */
public class InsuaranceOntology extends Ontology  {

	public static final String ONTOLOGY_NAME = "Insuarance-ontology";
	
	private static Ontology theInstance = new InsuaranceOntology();

	public static Ontology getTheInstance() {
		return theInstance;
	}

	public static void setTheInstance(Ontology theInstance) {
		InsuaranceOntology.theInstance = theInstance;
	}
	
	private InsuaranceOntology() {
		super(ONTOLOGY_NAME, BasicOntology.getInstance());
		try {
			 add(new ConceptSchema("Insuarance"), Insuarance.class);
			    add(new PredicateSchema("ConectedUser"), Ins.class);
			    add(new AgentActionSchema("Meet"), Ins.class);
			    
			    ConceptSchema cs = (ConceptSchema) getSchema("Insuarance");
			    cs.add("insId", (PrimitiveSchema) getSchema(BasicOntology.STRING));
			    cs.add("insuranceTitle", (PrimitiveSchema) getSchema(BasicOntology.STRING), 0, ObjectSchema.UNLIMITED);
			    cs.add("location", (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
			    cs.add("insuranceStartTime", (PrimitiveSchema) getSchema(BasicOntology.DATE), 0, ObjectSchema.UNLIMITED);
			    cs.add("insuranceEndTime", (PrimitiveSchema) getSchema(BasicOntology.DATE), 0, ObjectSchema.UNLIMITED);
			    
			    PredicateSchema ps = (PredicateSchema) getSchema("ConnectedUser");
			    ps.add("item", (ConceptSchema) cs);
			    ps.add("insuranceStartTime", (PrimitiveSchema) getSchema(BasicOntology.DATE));
			    ps.add("insuranceEndTime", (PrimitiveSchema) getSchema(BasicOntology.DATE));
			    
			    AgentActionSchema as = (AgentActionSchema) getSchema("Meet");
			    as.add("item", (ConceptSchema) getSchema("Insuarance"));
			   
		}  catch (OntologyException oe) {

		    oe.printStackTrace();

		  }
	}
}
