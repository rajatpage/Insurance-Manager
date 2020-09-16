package newLearning;

import java.util.HashSet;

/*
 * Interface for Provider gui
 */
public interface ProviderGUI {
	void setAgent(ProvidersAgent providersAgent);

	void show();

	void hide();

	void notifyUser(String message);
	
	public void notifyUserWithDialogueBox(String message);
	
	public void openInsuranceBox(HashSet<Insuarance> availableProviders);
	
	void dispose();
}
