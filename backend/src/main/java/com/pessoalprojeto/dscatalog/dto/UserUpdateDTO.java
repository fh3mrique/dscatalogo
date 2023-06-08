package com.pessoalprojeto.dscatalog.dto;

import com.pessoalprojeto.dscatalog.services.validation.UserUpdateValid;

/*só vamos conseguir instanciar uma senha no método insert(nos outros não terão senha)*/

/*essa é a uma anotaçõa peronalizada que criamos para testar se na hora de ecadastrar (método insert)
 já existe um email igual no banco. Para isso precisamos imple*/
@UserUpdateValid
public class UserUpdateDTO extends UserDTO {
	private static final long serialVersionUID = 1L;
	

}
