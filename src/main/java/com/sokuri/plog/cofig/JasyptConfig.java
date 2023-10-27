package com.sokuri.plog.cofig;

import com.ulisesbocchio.jasyptspringboot.properties.JasyptEncryptorConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class JasyptConfig {

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        JasyptEncryptorConfigurationProperties properties = new JasyptEncryptorConfigurationProperties();
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        config.setPassword(System.getenv("JASYPT_PASSWORD"));
        config.setAlgorithm(properties.getAlgorithm());
        config.setKeyObtentionIterations(properties.getKeyObtentionIterations());
        config.setPoolSize(properties.getPoolSize());
        config.setProviderName(properties.getProviderName());
        config.setSaltGeneratorClassName(properties.getSaltGeneratorClassname());
        config.setStringOutputType(properties.getStringOutputType());

        encryptor.setConfig(config);

        return encryptor;
    }
}

