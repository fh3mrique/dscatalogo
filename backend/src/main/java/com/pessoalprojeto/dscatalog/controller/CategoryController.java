package com.pessoalprojeto.dscatalog.controller;

/*O ResponseEntity<> é uma classe genérica do Spring Framework que permite retornar uma 
resposta HTTP personalizada com informações sobre o status, cabeçalhos e corpo da 
resposta. O tipo genérico especifica o tipo de dados que será retornado como corpo 
da resposta, e suporta conversores de mensagens para serializar o objeto em diferentes 
formatos, como JSON ou XML. Ele permite que o desenvolvedor tenha controle total sobre
a resposta HTTP retornada para o cliente.*/

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pessoalprojeto.dscatalog.dto.CategoryDTO;
import com.pessoalprojeto.dscatalog.services.CategoryService;

@RestController
/*A anotação @RestController é usada em classes do Spring para criar controladores de RESTful 
Web Services, combinando as anotações @Controller e @ResponseBody. A primeira marca uma 
classe como controlador no Spring MVC, enquanto a segunda indica que o valor de retorno 
do método deve ser vinculado diretamente à resposta HTTP. Com @RestController, cada método é
mapeado para um endpoint RESTful e o valor de retorno é automaticamente serializado em um 
formato apropriado para a resposta HTTP.*/
@RequestMapping(value = "categories")
/*A anotação @RequestMapping é utilizada em classes e métodos do Spring MVC para mapear 
solicitações HTTP a métodos ou controladores específicos de uma aplicação web. É uma das 
anotações mais utilizadas no Spring MVC e pode ser personalizada com diversos parâmetros, 
sendo o mais comum o value, que especifica o URI a ser mapeado. O @RequestMapping também 
permite mapear métodos HTTP específicos, como GET, POST, PUT e DELETE.*/
public class CategoryController {
	
	//ATRIBUTOS
	
	@Autowired
	private CategoryService service;
	
	//MÉTODOS //implementações do método de acesso ao banco do Controller
	
	// 1°  Método/operação para buscar uma lista de categorias do banco
	@GetMapping
	/*A anotação @GetMapping é usada em métodos de um controlador Spring para mapear uma 
	  solicitação HTTP GET a um método específico. O valor do parâmetro da anotação 
	  especifica o URI que será mapeado, ou seja, o caminho no qual o método será acionado.*/
	public ResponseEntity<List <CategoryDTO>> findAll(){
		
		/*declara uma variável chamada "lista" que é uma instância de 
		 uma classe ArrayList vazia que contém uma lista de objetos do tipo "Category".*/
		List<CategoryDTO> lista;
		
		//ESSE MÉTODO ESTÁ IMPLEMENTADO NA CAMADA SERVICE
		lista = service.findAll();
		
		/*Essa linha de código retorna uma resposta HTTP personalizada com um status 200 OK 
		 (.ok())e o corpo(.body) da resposta contendo uma lista de objetos do tipo Category.*/
		return ResponseEntity.ok().body(lista);
	}
	
	//2° Método/operação para buscar uma Categoria por id do  do banco
	@GetMapping(value = "/{id}")
	/*A anotação @PathVariable do Spring Framework vincula parâmetros de um método em um controlador de uma 
	 aplicação web a variáveis de caminho de uma URI. Por exemplo, @PathVariable("id") pode vincular o valor "123"
	 a uma variável chamada "id" no método correspondente do controlador a partir da URI "/api/users/123".*/
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){
		
		//ESSE MÉTODO ESTÁ IMPLEMENTADO NA CAMADA SERVICE
		CategoryDTO dto = service.findById(id);
		
		return ResponseEntity.ok().body(dto);
	}
	
	//3° Método/operação que serve para o cliente inserir uma categoria ao banco
	@PostMapping
	/*"@PostMapping" é uma anotação do Spring Framework que indica que um método de controle deve tratar as 
	 solicitações HTTP do tipo POST para um endpoint específico. O Spring Framework irá mapear a solicitação para o 
	 *método de controle correspondente, que pode manipular os dados e retornar uma resposta HTTP adequada.*/
	public ResponseEntity<CategoryDTO> insert (@RequestBody CategoryDTO dto)
	/*@RequestBody" é uma anotação do Spring Framework para o desenvolvimento de aplicativos web em Java que 
	 indica que o parâmetro do método de controle de requisições HTTP deve ser vinculado ao corpo da solicitação HTTP.*/
	{
		dto = service.insert(dto);
		
		/*o método trata as solicitações HTTP do tipo POST enviadas para o endpoint "/categories", insere uma nova 
		categoria no banco de dados, cria uma URI para a nova categoria e retorna uma resposta HTTP com o código
		de status 201 (CREATED) e o objeto "CategoryDTO" atualizado como corpo da resposta.*/
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		
		
		return ResponseEntity.created(uri).body(dto);
	}

	/*4° Método/operação  PUT que serve para o cliente atualizar  um categoria do banco é uma mistura do método 
	 GET e POST, uma vez que precisamos chamar a categoria do banco (GET), edita-la e depois inserir no banco 
	 novamente(POST). */
	
	@PutMapping(value = "/{id}")
	/*@PutMapping é uma anotação do Spring Framework usada em métodos de controladores em Java para lidar 
	 com requisições HTTP PUT. Essa anotação mapeia uma requisição PUT HTTP para o método anotado, indicando 
	 que o método deve ser executado quando um cliente faz uma solicitação PUT para um determinado recurso 
	 da API.*/
	public ResponseEntity<CategoryDTO> update (@PathVariable Long id, @RequestBody CategoryDTO dto ){
		
		dto = service.update(id, dto);
		
		return ResponseEntity.ok().body(dto);
	}
}
