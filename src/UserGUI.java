package newLearning;

import java.util.HashSet;

/*
 * Interface for tutor gui
 */
public interface UserGUI {
	void setAgent(UserAgent tutorAgent);

	void show();

	void hide();

	void notifyUser(String message);
	
	public void notifyUserWithDialogueBox(String message);
	
	public void openInsuranceCB(HashSet<Insuarance> availableProviders);

	public void updateLogger(Insuarance insuarance);
	
	void dispose();
}
