package com.pessoalprojeto.dscatalog.dto;

import com.pessoalprojeto.dscatalog.services.validation.UserInsertValid;

/*só vamos conseguir instanciar uma senha no método insert(nos outros não terão senha)*/

/*essa é a uma anotaçõa peronalizada que criamos para testar se na hora de ecadastrar (método insert)
 já existe um email igual no banco. Para isso precisamos imple*/
@UserInsertValid
public class UserInsertDTO extends UserDTO {
	private static final long serialVersionUID = 1L;
	
	private String password;
	
	public UserInsertDTO () {
		super();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
