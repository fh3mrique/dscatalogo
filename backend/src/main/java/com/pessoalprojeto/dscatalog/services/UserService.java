package com.pessoalprojeto.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pessoalprojeto.dscatalog.dto.RoleDTO;
import com.pessoalprojeto.dscatalog.dto.UserDTO;
import com.pessoalprojeto.dscatalog.dto.UserInsertDTO;
import com.pessoalprojeto.dscatalog.dto.UserUpdateDTO;
import com.pessoalprojeto.dscatalog.entities.Role;
import com.pessoalprojeto.dscatalog.entities.User;
import com.pessoalprojeto.dscatalog.repositories.RoleRepository;
import com.pessoalprojeto.dscatalog.repositories.UserRepository;
import com.pessoalprojeto.dscatalog.services.exceptions.DatabaseException;
import com.pessoalprojeto.dscatalog.services.exceptions.EntityNotFoundExceptions;

@Service
/*
 * A interface UserDetailsService faz parte do módulo Spring Security e é usada
 * para recuperar os detalhes do usuário durante o processo de autenticação. Ela
 * fornece um método principal, loadUserByUsername(), que é responsável por
 * carregar os detalhes do usuário com base no nome de usuário fornecido.
 */
public class UserService implements UserDetailsService {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository repository;

	@Autowired
	private RoleRepository roleRepository;

	// beans de métodos
	@Autowired
	private BCryptPasswordEncoder passwordEncriptar;

	// MÉTODOS
	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(Pageable pageable) {

		Page<User> listaUser = repository.findAll(pageable);

		return listaUser.map(x -> new UserDTO(x));

	}

	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		Optional<User> obj = repository.findById(id);

		User entity = obj.orElseThrow(() -> new EntityNotFoundExceptions("Entidade não encontrada"));

		return new UserDTO(entity);
	}

	@Transactional()
	public UserDTO insert(UserInsertDTO dto) {

		User entity = new User();
		copyDTOtoEnrity(dto, entity);
		entity.setPassword(passwordEncriptar.encode(dto.getPassword()));
		entity = repository.save(entity);

		return new UserDTO(entity);
	}

	@Transactional
	public UserDTO update(Long id, UserUpdateDTO dto) {
		try {

			User entity = repository.getOne(id);

			copyDTOtoEnrity(dto, entity);

			entity = repository.save(entity);

			return new UserDTO(entity);
		}

		catch (EntityNotFoundException e)

		{
			throw new EntityNotFoundExceptions("Id não encontrado");
		}
	}

	public void delete(Long id) {
		try {

			repository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {

			throw new EntityNotFoundExceptions("id não encontrado");

		} catch (DataIntegrityViolationException e) {

			throw new DatabaseException("Violação Integridade do banco ");

		}

	}

	private void copyDTOtoEnrity(UserDTO dto, User entity) {
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());

		entity.getRoles().clear();

		for (RoleDTO roleDto : dto.getRoles()) {
			Role role = roleRepository.getOne(roleDto.getId());
			entity.getRoles().add(role);
		}

	}
	/* A interface UserDetailsService define o seguinte método: */

	/*
	 * loadUserByUsername(String username): Este método é chamado pelo Spring
	 * Security quando um usuário tenta fazer login no sistema. O parâmetro username
	 * representa o nome de usuário fornecido no processo de autenticação. O método
	 * é responsável por retornar um objeto que implementa a interface UserDetails,
	 * contendo os detalhes do usuário correspondente ao nome de usuário fornecido.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByEmail(username);
		if (user == null) {
			logger.error("User não encontrado: " + username);
			throw new UsernameNotFoundException("Email not foud");
		}

		logger.info("User encontrado: " + username);
		return user;
	}

}
