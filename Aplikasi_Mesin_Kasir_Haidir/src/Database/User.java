package Database;

public class User {
	private String username, password;
	private boolean admin;

	public User(String username, String password, boolean admin) {
		this.username = username;
		this.password = password;
		this.admin = admin;
	}
	
	public User(String username) {
		this.username = username;
	}

	public User(User usr) {
		this.username = usr.getUsername();
		this.password = usr.getPassword();
		this.admin = usr.admin();
	}

	public String getUsername() {
		return username;
	}

	public boolean admin() {
		return admin;
	}

	public String getPassword() {
		return password;
	}
}
