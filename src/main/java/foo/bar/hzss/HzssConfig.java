package foo.bar.hzss;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.MapAttributeConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapIndexConfig;
import com.hazelcast.core.HazelcastInstance;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.hazelcast.HazelcastSessionRepository;
import org.springframework.session.hazelcast.PrincipalNameExtractor;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

@Configuration
@EnableHazelcastHttpSession
public class HzssConfig {


    // works
//    @Bean
//    public HazelcastInstance hazelcastInstance() {
//        MapAttributeConfig attributeConfig = new MapAttributeConfig()
//                .setName(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
//                .setExtractor(PrincipalNameExtractor.class.getName());
//
//        Config config = new Config();
//
//        config.getMapConfig("spring:session:sessions")
//                .addMapAttributeConfig(attributeConfig)
//                .addMapIndexConfig(new MapIndexConfig(
//                        HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE, false));
//
//        return Hazelcast.newHazelcastInstance(config);
//    }
    
    @Bean
    public HazelcastInstance hazelcastInstance() {

        ClientConfig config = new ClientConfig();
        config.getNetworkConfig().addAddress("127.0.0.1");


        MapAttributeConfig attributeConfig = new MapAttributeConfig()
                .setName(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
                .setExtractor(PrincipalNameExtractor.class.getName());

        MapIndexConfig indexConfig = new MapIndexConfig(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE, false);

        MapConfig sessionMapConfig = new MapConfig("spring:session:sessions")
                .addMapAttributeConfig(attributeConfig)
                .addMapIndexConfig(indexConfig);

        HazelcastInstance instance = HazelcastClient.newHazelcastClient(config);

        instance.getConfig().addMapConfig(sessionMapConfig);

        return instance;
    }

}
