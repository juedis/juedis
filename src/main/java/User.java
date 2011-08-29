import java.io.Serializable;
import java.util.Date;

/**
 *
 * @Description: 
 *
 * @author ÇïÄê 
 *
 * @date 2011-8-23 ÏÂÎç02:08:06
 *
 */
public class User implements Serializable{

	
	private static final long serialVersionUID = -1090835913343767431L;

	private String id ;
	
	private String userName ; 
	
	private String password; 
	
	private Date createDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
