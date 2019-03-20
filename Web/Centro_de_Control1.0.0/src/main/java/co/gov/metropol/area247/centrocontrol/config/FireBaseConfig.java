package co.gov.metropol.area247.centrocontrol.config;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

@Configuration
public class FireBaseConfig {
	
	public DatabaseReference firebaseDatabse() {
		DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
		return firebase;
	}

	@Value("${rs.pscode.firebase.database.url}")
	private String databaseUrl;

	@Value("${rs.pscode.firebase.config.path}")
	private Resource resource;
    
	@PostConstruct
	public void init() {	

		/*try {			
			FirebaseOptions options = new FirebaseOptions.Builder()
			.setCredential(FirebaseCredentials.fromCertificate(resource.getInputStream()))
			.setDatabaseUrl(databaseUrl).build();
			
			System.out.println("options: " + options);
			
			FirebaseApp.initializeApp(options);
									
		} catch (IOException e) {			
			System.out.print ("ERROR: " + e);
		}*/
						
	}

}
