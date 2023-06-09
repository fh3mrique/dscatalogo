package com.pessoalprojeto.dscatalog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
/*A anotação @Configuration no Spring é usada para fornecer configurações para o ambiente de execução 
do aplicativo. Ela marca uma classe como uma classe de configuração, permitindo a definição de beans 
gerenciados pelo Spring. Esses beans são objetos instanciados, configurados e gerenciados pelo contêiner do 
Spring, e são usados para fornecer dependências e funcionalidades na aplicação. Dentro de uma classe 
anotada com @Configuration, os métodos anotados com @Bean são usados para definir e configurar beans, 
onde a lógica necessária para criar e configurar os objetos desejados é colocada. O Spring cuida do 
processamento desses métodos e do gerenciamento das instâncias dos beans correspondentes.*/
public class AppConfig {
	
	@Value("${jwt.secret}")
	private String jwtsecret;
	
	@Bean
	/*A anotação @Bean no Spring indica que um método em uma classe anotada com @Configuration cria e 
	configura um bean gerenciado pelo Spring. O retorno desse método é registrado como um bean e pode ser 
	injetado em outras partes da aplicação. Essa anotação permite configurar o bean de forma flexível, incluindo 
	a lógica necessária dentro do método para criar e configurar o objeto desejado.*/
	public BCryptPasswordEncoder passwordConverter() {
		/*O BCryptPasswordEncoder é uma classe do Spring Security que usa o algoritmo bcrypt para criptografar 
		senhas de forma segura. Ele implementa a interface PasswordEncoder. Ao usar o BCryptPasswordEncoder, 
		você pode gerar senhas criptografadas com o método encode(). Isso ajuda a proteger as senhas contra 
		ataques de força bruta.*/
		return new BCryptPasswordEncoder();
	}
	
	/*Beans para token JWT (Spring Boot 2 apenas)*/
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(jwtsecret);
		return tokenConverter;
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

}
