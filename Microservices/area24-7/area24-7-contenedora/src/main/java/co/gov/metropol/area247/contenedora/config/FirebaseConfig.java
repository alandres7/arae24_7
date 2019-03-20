package co.gov.metropol.area247.contenedora.config;

import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

@Configuration
public class FirebaseConfig {
	public DatabaseReference firebaseDatabse() {
		DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
		return firebase;
	}

	private String databaseUrl = "https://xgep-91621.firebaseio.com";
	
	private String configPath = "src/main/resources/static/assets/json/credenciales.json";
    
	@PostConstruct
	public void init() {

		try {
		    FileInputStream serviceAccount = new FileInputStream(configPath);

			FirebaseOptions options = new FirebaseOptions.Builder()
			.setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
			.setDatabaseUrl(databaseUrl).build();
			
			FirebaseApp.initializeApp(options);
									
		} catch (IOException e) {			
			System.out.print ("ERROR: " + e);
		}
						
	}
}
