package com.pessoalprojeto.dscatalog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pessoalprojeto.dscatalog.entities.Category;

@RestController
/*A anotação @RestController é usada em classes do Spring para criar controladores de RESTful 
Web Services, combinando as anotações @Controller e @ResponseBody. A primeira marca uma 
classe como controlador no Spring MVC, enquanto a segunda indica que o valor de retorno 
do método deve ser vinculado diretamente à resposta HTTP. Com @RestController, cada método é
mapeado para um endpoint RESTful e o valor de retorno é automaticamente serializado em um 
formato apropriado para aresposta HTTP.*/
@RequestMapping(value = "categories")
/*A anotação @RequestMapping é utilizada em classes e métodos do Spring MVC para mapear 
solicitações HTTP a métodos ou controladores específicos de uma aplicação web. É uma das 
anotações mais utilizadas no Spring MVC e pode ser personalizada com diversos parâmetros, 
sendo o mais comum o value, que especifica o URI a ser mapeado. O @RequestMapping também 
permite mapear métodos HTTP específicos, como GET, POST, PUT e DELETE.*/
public class CategoryController {
	/*O ResponseEntity<> é uma classe genérica do Spring Framework que permite retornar uma 
	  resposta HTTP personalizada com informações sobre o status, cabeçalhos e corpo da 
	  resposta. O tipo genérico especifica o tipo de dados que será retornado como corpo 
	  da resposta, e suporta conversores de mensagens para serializar o objeto em diferentes 
	  formatos, como JSON ou XML. Ele permite que o desenvolvedor tenha controle total sobre
	  a resposta HTTP retornada para o cliente.*/
	//       MÉTODO
	//        Tipo                         NomeDoMetodo
	@GetMapping
	/*A anotação @GetMapping é usada em métodos de um controlador Spring para mapear uma 
	  solicitação HTTP GET a um método específico. O valor do parâmetro da anotação 
	  especifica o URI que será mapeado, ou seja, o caminho no qual o método será acionado.*/
	public ResponseEntity<List <Category>> findAll(){
		/*declara uma variável chamada "lista" que é uma instância de 
		 uma classe ArrayList vazia que contém objetos do tipo "Category".*/
		List<Category> lista = new ArrayList<>();
		
		lista.add(new Category(1L, "Livros"));
		lista.add(new Category(2L, "Eletrônicos"));
		
		/*Essa linha de código retorna uma resposta HTTP personalizada com um status 200 OK 
		 (.ok())e o corpo(.body) da resposta contendo uma lista de objetos do tipo Category.*/
		return ResponseEntity.ok().body(lista);
	}

}
