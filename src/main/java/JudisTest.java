import java.io.IOException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import com.taobao.juedis.core.BinaryJudis;
import com.taobao.juedis.core.Judis;
import com.taobao.juedis.io.JuedisClient;

/**
 *
 * @Description: 
 *
 * @author ÇïÄê 
 *
 * @date 2011-8-26 ÉÏÎç10:03:15
 *
 */
public class JudisTest implements Runnable {
	
	public JuedisClient client;
	
	
	BinaryJudis judis ;
	
	public static AtomicInteger i =  new AtomicInteger();
	
	public JudisTest(JuedisClient client){
		this.client = client;
		judis = new BinaryJudis(client);
	}
	
	public void run() {
//			Integer v = i.getAndAdd(1);
//			judis.set(template.encode(String.valueOf(v)), template.encode(String.valueOf(v)));
//			System.out.println(v+"="+judis.get(template.encode(String.valueOf(v))));
	}
	
	
	public static void main(String[] args) throws IOException {
		JuedisClient client =  new JuedisClient();
		
		
		client.setUrl("10.13.34.62");
		client.setPort("6379");
		client.setSerType("hessian");
		
		client.connection();
		
		Judis judis = new Judis(client);

		User u =  new User();
		u.setId("123456");
		u.setPassword("123545");
		u.setUserName("13123213");
		u.setCreateDate(new Date());
		
		
		String  a = (String) judis.set(u.getId(),u);
		
		User user1 = (User)judis.get(u.getId());
		System.out.println(user1.getId());
		System.out.println(user1.getPassword());
		System.out.println(user1.getUserName());
		System.out.println(user1.getCreateDate());
		
//		ExecutorService service = Executors.newFixedThreadPool(25);
//		for (int i = 0; i <1; i++) {
//			service.execute(new Judistest(client));
//		})
		
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//		}
//		
//		service.execute(new Judistest(client));
	}

	
}
