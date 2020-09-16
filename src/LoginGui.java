package newLearning;

public interface LoginGui {
	void setAgent(LoginAgent loginAgent);

	void show();

	void hide();

	void dispose();
	
	void reset();
	
	public void notifyUserWithDialogueBox(String message);

}
