package com.pessoalprojeto.dscatalog.components;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.pessoalprojeto.dscatalog.entities.User;
import com.pessoalprojeto.dscatalog.repositories.UserRepository;

/*A interface TokenEnhancer do Spring Security OAuth2 permite personalizar o token de acesso gerado durante a 
 autenticação e autorização. Ela adiciona informações extras ao token, além das informações padrão como escopo, 
 tempo de expiração e dados do usuário. Essas informações adicionais podem ser adaptadas de acordo com os 
 requisitos específicos da aplicação.*/
@Component
public class JwtTokenEnhancer  implements TokenEnhancer{
	@Autowired
	private UserRepository userRepository;
	
	/*A interface TokenEnhancer define um único método chamado enhance(), que recebe dois parâmetros: o 
	token de acesso (OAuth2AccessToken) e a autenticação (OAuth2Authentication). O método enhance() é responsável 
	por adicionar informações extras ao token de acesso.*/
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		User user = userRepository.findByEmail(authentication.getName());
		
		Map<String, Object> map = new HashMap<>();
		map.put("userFirstName", user.getFirstName());
		map.put("userId", user.getId());
		
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
		
		token.setAdditionalInformation(map);
		
		return token;
	}
	

}
