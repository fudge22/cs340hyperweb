package Phase6;
public class SetUpDatabase {
	public static void main(String[] args){
		ObjectDB.setFileLocation("Database.db");
		ObjectDB.getSingleton().clear();
		TestClass testClass = new TestClass(58, "Scott");
		ObjectDB.getSingleton().store(testClass.getLocalObjectId(), testClass);
		ObjectDB.getSingleton().save("Database.db");
	}
}
