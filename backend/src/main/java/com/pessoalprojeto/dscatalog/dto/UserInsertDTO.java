package com.pessoalprojeto.dscatalog.dto;

/*só vamos conseguir instanciar uma senha no método insert(nos outros não terão senha)*/
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
