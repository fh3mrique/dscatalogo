package com.pessoalprojeto.dscatalog.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "tb_user")
/*
 * A interface UserDetails faz parte do módulo Spring Security e é usada para
 * representar os detalhes do usuário durante o processo de autenticação e
 * autorização. Ela fornece informações essenciais sobre o usuário, como nome de
 * usuário, senha, autorizações (ou seja, os papéis ou permissões do usuário) e
 * se a conta do usuário está habilitada ou expirada.
 */
public class User implements UserDetails, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	@Column(unique = true)
	/*
	 * A anotação @Column(unique = true) no JPA garante que o valor de uma coluna em
	 * um banco de dados seja único. Ao aplicá-la a um campo ou propriedade em uma
	 * entidade, evita-se a duplicação desse valor em outros registros do banco de
	 * dados. Isso é especialmente útil para campos que precisam ser exclusivos,
	 * como endereços de e-mail em uma tabela de usuários.
	 */
	private String email;
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tb_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User() {

	}

	public User(Long id, String firstName, String lastName, String email, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}

   //A interface UserDetails define os seguintes métodos:

	/*getAuthorities(): Retorna uma coleção de objetos GrantedAuthority que representam as autorizações do usuário. 
	 Essas autorizações podem ser usadas pelo Spring Security para verificar se o usuário tem permissão para acessar um 
	 determinado recurso.*/
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// A linha está retornando uma coleção de objetos GrantedAuthority que representam as autorizações do usuário.
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());
	}

	/*getUsername(): Retorna o nome de usuário do usuário*/
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	/*isAccountNonExpired(): Retorna um booleano indicando se a conta do usuário não está expirada. Se a conta estiver 
	 expirada, o usuário não poderá fazer login.*/
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	/*isAccountNonLocked(): Retorna um booleano indicando se a conta do usuário não está bloqueada. Se a conta estiver 
	 bloqueada, o usuário não poderá fazer login.*/
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	/*isCredentialsNonExpired(): Retorna um booleano indicando se as credenciais (ou seja, a senha) do usuário não estão 
	 expiradas. Se as credenciais estiverem expiradas, o usuário não poderá fazer login.*/
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	/*isEnabled(): Retorna um booleano indicando se a conta do usuário está habilitada. Se a conta estiver desabilitada, 
	 o usuário não poderá fazer login.*/
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
