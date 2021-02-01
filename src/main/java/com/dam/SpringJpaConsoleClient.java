package com.dam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.dam.modelos.Categoria;
import com.dam.modelos.Perfil;
import com.dam.modelos.Usuario;
import com.dam.modelos.Vacante;
import com.dam.repositorios.RepositorioCategoria;
import com.dam.repositorios.RepositorioPerfil;
import com.dam.repositorios.RepositorioUsuario;
import com.dam.repositorios.RepositorioVacante;
import com.dam.utilidades.Libreria;

import daw.com.Teclado;

@Component
public class SpringJpaConsoleClient implements CommandLineRunner {
	
	DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-uuuu");

	@Autowired
	private RepositorioCategoria repositorioCategoria;

	@Autowired
	private RepositorioVacante repositorioVacante;
	
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@Autowired
	private RepositorioPerfil repositorioPerfil;

	@Override
	public void run(String... args) throws Exception {
		// insertarCategoria();
		// buscarCategoriaPorId();
		// actualizarCategoria();
		// eliminarCategoria();
		// eliminarCategorias();
		// eliminarCategoriasLote();
		listaCategorias();
		// listaCategoriasOrdenadas();
		// listaCategoriasPaginadas();
		// listaCategoriasPaginadasOrdenadas();
		// listaCategoriasPorId();
		// existeId();
		// insertarListaCategorias();
		// contarCategorias();

		// insertarVacante();
		// actualizarVacante();
		// eliminarVacante();
		// eliminarVacantes();
		// eliminarVacantesLote();
		listaVacantes();
		// buscarVacantePorID();
		// listaVacantesOrdenadas();
		// listaVacantesPaginadas();
		// listaVacantesPaginadasOrdenadas();
		// listaVacantesPorEstatus();
		// listaVacantesPorEstatusDestacada();
		// listaVacantesVariosEstatus();
		// listaVacantesPorSalario();
		// listaVacantesPorId();
		// contarVacantes();
		
		// insertarPerfiles();
		// listaPerfiles();
		
		// insertarUsuario();
		// listaUsuarios();
		// listaUsuariosOrenados();
		// buscarUsuarioId();
	}

	/* Categorías */

	// Insertar
	private void insertarCategoria() {
		String continuar;
		do {
			Categoria categoria = new Categoria();
			categoria.setNombre(Teclado.leerString("\nIntroduce el nombre de la categoría:"));
			categoria.setDescripcion(Teclado.leerString("\nIntroduce la descripción de la categoría:"));
			repositorioCategoria.save(categoria);
			System.out.println("\nRegistro insertado correctamente");
			do {
				continuar = Teclado.leerString("\n¿Quieres insertar otra categoría? S/N:");
				if (!continuar.equalsIgnoreCase("S") && !continuar.equalsIgnoreCase("N")) {
					System.out.println("\nERROR!! Respuesta no válida.");
					System.out.println("\nPor favor introduce S o N");
				}
			} while (!continuar.equalsIgnoreCase("S") && !continuar.equalsIgnoreCase("N"));
		} while (continuar.equalsIgnoreCase("S"));
	}

	// Actualizar
	private void actualizarCategoria() {
		int id;
		id = Teclado.leerInt("\nIntroduce el identificador de la categoría a actualizar:");
		Optional<Categoria> categoria = repositorioCategoria.findById(id);
		if (categoria.isPresent()) {
			Categoria c = categoria.get();
			System.out.println("\nDatos actuales de la categoría");
			System.out.println(c);
			System.out.println("\nIntroduce los nuevos datos");
			c.setNombre(Teclado.leerString("\nIntroduce el nombre de la categoría:"));
			c.setDescripcion(Teclado.leerString("\nIntroduce la descripción de la categoría:"));
			repositorioCategoria.save(c);
			System.out.println("\nRegistro actualizado correctamente");
			System.out.println(categoria.get());
		} else {
			System.out.println("\nEl identificador de la categoría no existe en la base de datos\n");
		}

	}

	// Eliminar
	private void eliminarCategoria() {
		int id;
		id = Teclado.leerInt("\nIntroduce el identificador de la categoría a eliminar:");
		Optional<Categoria> categoria = repositorioCategoria.findById(id);

		if (categoria.isPresent()) {
			repositorioCategoria.deleteById(categoria.get().getId());
			System.out.println("\nRegistro eliminado correctamente");
		} else {
			System.out.println("\nEl identificador de la categoría no existe en la base de datos");
		}
	}

	// Eliminar todas las categorías
	private void eliminarCategorias() {
		repositorioCategoria.deleteAll();
		System.out.println("\nTodos los registros se han eliminado correctamente\n");
	}

	private void eliminarCategoriasLote() {
		repositorioCategoria.deleteAllInBatch();
		System.out.println("\nTodos los registros se han eliminado correctamente\n");
	}

	// Mostrar todas las categorías
	private void listaCategorias() {
		System.out.println("\nLista de categorías");
		repositorioCategoria.findAll().forEach(System.out::println);
	}

	// Mostrar todas las categorías ordenadas por nombre
	private void listaCategoriasOrdenadas() {
		System.out.println("\nLista de categorías ordenadas por nombre");
		repositorioCategoria.findAll(Sort.by("nombre")).forEach(System.out::println);
	}

	// Mostrar todas las categorías con paginación
	private void listaCategoriasPaginadas() {
		System.out.println("\nLista de categorías con paginación");
		Page<Categoria> list = repositorioCategoria.findAll(PageRequest.of(0, 5));
		System.out.println("Total registros: " + list.getTotalElements());
		System.out.println("Total páginas: " + list.getTotalPages());
		list.stream().forEach(System.out::println);
	}

	// Mostrar todas las categorías con paginación y ordenadas por nombre
	private void listaCategoriasPaginadasOrdenadas() {
		System.out.println("\nLista de categorías con paginación y ordenadas por nombre");
		Page<Categoria> list = repositorioCategoria.findAll(PageRequest.of(0, 5, Sort.by("nombre")));
		System.out.println("Total registros: " + list.getTotalElements());
		System.out.println("Total páginas: " + list.getTotalPages());
		list.stream().forEach(System.out::println);
	}

	// Mostrar categoría por ID
	private void buscarCategoriaPorId() {
		int id;
		id = Teclado.leerInt("\nIntroduce el identificador de la categoría a consultar:");
		Optional<Categoria> categoria = repositorioCategoria.findById(id);
		if (categoria.isPresent()) {
			System.out.println("\nDatos de la categoría consultada");
			System.out.println(categoria.get());
		} else {
			System.out.println("\nEl identificador de la categoría no existe en la base de datos");
		}
	}

	// Mostrar lista de categorías por ID
	private void listaCategoriasPorId() {
		System.out.println("\nLista categorías por ID");
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(4);
		list.add(8);
		list.add(9);
		Iterable<Categoria> categorias = repositorioCategoria.findAllById(list);
		for (Categoria c : categorias) {
			System.out.println(c);
		}
	}

	// Comprobar si existe una categoría con un determinado ID
	private void existeId() {
		int id = Teclado.leerInt("\nIntroduce el identificador de la categoría:");
		boolean existe = repositorioCategoria.existsById(id);
		System.out.println("\nExiste la categoría con id " + id + ": " + existe);
	}

	// Insertar una colección de categorías
	private void insertarListaCategorias() {
		List<Categoria> categories = obtenerListaCategorias();
		repositorioCategoria.saveAll(categories);
		System.out.println("\nRegistros insertados correctamente");
	}

	/**
	 * Metodo que devuelve una lista de 3 categorías
	 * 
	 * @return lista de categorías
	 */
	private List<Categoria> obtenerListaCategorias() {
		List<Categoria> lista = new ArrayList<Categoria>();

		Categoria categoria1 = new Categoria();
		categoria1.setNombre("Desarrollo MERN Stack");
		categoria1.setDescripcion("Ofertas de trabajo relacionadas con desarrollo MERN Stack");

		Categoria categoria2 = new Categoria();
		categoria2.setNombre("Desarrollo MEAN Stack");
		categoria2.setDescripcion("Ofertas de trabajo relacionadas con desarrollo MEAN Stack");

		Categoria categoria3 = new Categoria();
		categoria3.setNombre("Blockchain/Bitcoin");
		categoria3.setDescripcion("Ofertas de trabajo relacionadas con blockchain y bitcoin");

		lista.add(categoria1);
		lista.add(categoria2);
		lista.add(categoria3);

		return lista;
	}

	// Obtener el número total de categorías
	private void contarCategorias() {
		long count = repositorioCategoria.count();
		System.out.println("\nTotal de categorías: " + count);
	}

	/* Final Categorías */

	/* Vacantes */

	/* Insertar */
	private void insertarVacante() {
		int idCategoria;
		String estatus, continuar;
		int mEstatus;
		Categoria categoria;

		do {
			Vacante vacante = new Vacante();
			vacante.setNombre(Teclado.leerString("\nIntroduce el nombre de la vacante:"));
			vacante.setDescripcion(Teclado.leerString("\nIntroduce la descripción de la vacante:"));
			vacante.setFecha(LocalDate.parse(Teclado.leerString("\nIntroduce la fecha de publicación de la vacante 'dd-MM-uuuu':"), formato));
			vacante.setSalario(Teclado.leerFloat("\nIntroduce el salario de la vacante:"));
			mEstatus = Libreria.leerEntre(1, 3, "\nIntroduce el estatus de la vacante: " + "\n1.Creada " + "\n2.Aprobada " + "\n3.Eliminada");

			if (mEstatus == 1) {
				estatus = "Creada";
			} else if (mEstatus == 2) {
				estatus = "Aprobada";
			} else {
				estatus = "Eliminada";
			}
			
			vacante.setEstatus(estatus);
			vacante.setDestacada(Libreria.leerEntre(0, 1,"\nIntroduce si la vacante está o no destacada: 0.No destacada - 1.Destacada"));
			vacante.setImagen(Teclado.leerString("\nIntroduce la imagen de la vacante:"));
			vacante.setDetalles(Teclado.leerString("\nIntroduce los detalles de la vacante:"));
			categoria = new Categoria();
			idCategoria = Libreria.leerEntre(1, 15,"\nIntroduce el ID de la categoría: " + 
					"\n1.Sistemas" + 
					"\n2.Desarrollo Web" + 
					"\n3.Desarrollo Back End" + 
					"\n4.Desarrollo Front End" +
					"\n5.Desarrollo Full-Satck" + 
					"\n6.Desarrollo MERN Stack" + 
					"\n7.Desarrollo MEAN Stack" + 
					"\n8.Desarrollo Android/IOS" + 
					"\n9.Big Data" + 
					"\n10.Negocio" +
					"\n11.Diseño Gráfico" +
					"\n12.Comunicaciones" +
					"\n13.Base de Datos" +
					"\n14.Blockchain/Bitcoin" +
					"\n15.Gerencia/Administración");

			categoria.setId(idCategoria);
			vacante.setCategoria(categoria);
			repositorioVacante.save(vacante);
			System.out.println("\nRegistro insertado correctamente");
			
			do {
				continuar = Teclado.leerString("\n¿Quieres insertar otra vacante? S/N:");
				if (!continuar.equalsIgnoreCase("S") && !continuar.equalsIgnoreCase("N")) {
					System.out.println("\nERROR!! Respuesta no válida.");
					System.out.println("\nPor favor introduce S o N");
				}
			} while (!continuar.equalsIgnoreCase("S") && !continuar.equalsIgnoreCase("N"));
			
		} while (continuar.equalsIgnoreCase("S"));
	}
	
	// Actualizar
	private void actualizarVacante() {
		int id;
		String estatus;
		int mEstatus;

		id = Teclado.leerInt("\nIntroduce el identificador de la vacante a actualizar:");
		Optional<Vacante> vacante = repositorioVacante.findById(id);

		if (vacante.isPresent()) {
			Vacante v = vacante.get();
			System.out.println("\nDatos actuales de la vacante");
			System.out.println(v);
			System.out.println("\nIntroduce los nuevos datos");
			v.setNombre(Teclado.leerString("\nIntroduce el nombre de la vacante:"));
			v.setDescripcion(Teclado.leerString("\nIntroduce la descripción de la vacante:"));
			v.setFecha(LocalDate.parse(Teclado.leerString("\nIntroduce la fecha de publicación de la vacante 'dd-MM-uuuu':"), formato));
			v.setSalario(Teclado.leerFloat("\nIntroduce el salario de la vacante:"));
			mEstatus = Libreria.leerEntre(1, 3, "\nIntroduce el estatus de la vacante: "
					+ "\n1.Creada "
					+ "\n2.Aprobada "
					+ "\n3.Eliminada");

			if (mEstatus == 1) {
				estatus = "Creada";
			}
			else if (mEstatus == 2) {
				estatus = "Aprobada";
			}
			else {
				estatus = "Eliminada";
			}
			
			v.setEstatus(estatus);
			v.setDestacada(Libreria.leerEntre(0, 1, "\nIntroduce si la vacante está o no destacada: 0.No destacada - 1.Destacada"));
			v.setImagen(Teclado.leerString("\nIntroduce la imagen de la vacante:"));
			v.setDetalles(Teclado.leerString("\nIntroduce los detalles de la vacante:"));
			repositorioVacante.save(v);
			System.out.println("\nRegistro actualizado correctamente");
			System.out.println(vacante.get());
		} else {
			System.out.println("\nEl identificador de la vacante no existe en la base de datos");
		}
	}
	
	// Eliminar
	private void eliminarVacante() {
		int id;

		id = Teclado.leerInt("\nIntroduce el identificador de la vacante a eliminar:");
		Optional<Vacante> vacante = repositorioVacante.findById(id);

		if (vacante.isPresent()) { 
			repositorioVacante.deleteById(vacante.get().getId());
			System.out.println("\nRegistro eliminado correctamente");
		} else {
			System.out.println("\nEl identificador de la vacante no existe en la base de datos");
		}

	}
	
	// Eliminar todas las vacantes
	private void eliminarVacantes() {
		repositorioVacante.deleteAll();
		System.out.println("\nTodos los registros se han eliminado correctamente");
		System.out.println();
	}
	
	private void eliminarVacantesLote() {
		repositorioVacante.deleteAllInBatch();
		System.out.println("\nTodos los registros se han eliminado correctamente\n");
	}

	// Mostrar todas las vacantes
	private void listaVacantes() {
		System.out.println("\nLista de vacantes");
		repositorioVacante.findAll().forEach(System.out::println);
	}
	
	// Mostrar vacante por ID
	private void buscarVacantePorID() {
		System.out.println("Consultar vacante por ID");
		int id;

		id = Teclado.leerInt("\nIntroduce el identificador de la vacante a consultar:");
		Optional<Vacante> vacante = repositorioVacante.findById(id);

		if (vacante.isPresent()) {
			System.out.println("\nDatos de la categoria consultada");
			System.out.println(vacante.get());
		}
		else {
			System.out.println("\nEl identificador de la categoría no está en la base de datos");
		}
	}
	
	// Mostrar todas las vacantes ordenadas por nombre
	private void  listaVacantesOrdenadas() {
		System.out.println("\nLista de vacantes ordenadas por nombre");
		repositorioVacante.findAll(Sort.by("nombre")).forEach(System.out::println);
	}
	
	// Mostrar todas las categorías con paginación
	private void listaVacantesPaginadas() {
		System.out.println("\nLista de vacantes con paginación");
		Page<Vacante> list = repositorioVacante.findAll(PageRequest.of(0, 5));
		System.out.println("Total registros: " + list.getTotalElements());
		System.out.println("Total páginas: " + list.getTotalPages());
		list.stream().forEach(System.out::println);
	}
	
	// Mostrar todas las categorías con paginación y ordenadas por nombre
	private void listaVacantesPaginadasOrdenadas() {
		System.out.println("\nLista de vacantes con paginación y ordenadas por nombre");
		Page<Vacante> list = repositorioVacante.findAll(PageRequest.of(0, 5,Sort.by("nombre")));
		System.out.println("Total registros: " + list.getTotalElements());
		System.out.println("Total paginas: " + list.getTotalPages());
		list.stream().forEach(System.out::println);
	}
	
	// Mostrar vacantes por estatus
	private void listaVacantesPorEstatus() {
		System.out.println("\nConsultar vacantes por estatus");
		int tipo;
		String estatus;
		List<Vacante> lista;

		tipo = Libreria.leerEntre(1, 3, "\nIntroduce el valor del estatus de las vacantes"
				+ "\n1.Creada"
				+ "\n2.Aprobada"
				+ "\n3.Eliminada");

		if (tipo == 1) {
			estatus = "Creada";
		}
		else if (tipo == 2) {
			estatus = "Aprobada";
		}
		else {
			estatus = "Eliminada";
		}

		lista = repositorioVacante.findByEstatus(estatus);

		System.out.println("\nTotal de registros encontrados: " + lista.size());

		lista.stream()
		.forEach(v -> System.out.println(v.getId() + " Vacante: " + v.getNombre() + " | Estatus: " + v.getEstatus()));
	}
	
	// Mostrar vacantes por estatus y si está destacada o no
	private void listaVacantesPorEstatusDestacada() {
		System.out.println("\nConsultar vacantes por estatus y si está destacada o no");
		int tipo, destacada;
		String estatus;
		List<Vacante> lista;

		destacada = Libreria.leerEntre(1, 2, "Introduce el valor"
				+ "\n1.Destacada"
				+ "\n2.No destacada");

		tipo = Libreria.leerEntre(1, 3, "\nIntroduce el valor del estatus de las vacantes"
				+ "\n1.Creada"
				+ "\n2.Aprobada"
				+ "\n3.Eliminada");

		if (tipo == 1) {
			estatus = "Creada";
		}
		else if (tipo == 2) {
			estatus = "Aprobada";
		}
		else {
			estatus = "Eliminada";
		}

		lista = repositorioVacante.findByDestacadaAndEstatusOrderById(destacada, estatus);

		System.out.println("\nTotal de registros encontrados: " + lista.size());

		lista.stream()
		.forEach(v -> System.out.println(v.getId() + " Vacante: " + v.getNombre() + 
				" | Destacada: " + v.getDestacada() + " | Estatus: " + v.getEstatus()));
	}
	
	// Mostrar vacantes por varios estatus
	private void listaVacantesVariosEstatus() {
		System.out.println("\nConsultar vacantes por varios estatus");
		int tipo;
		String estatus = "";
		List<String> listaEstatus = new ArrayList<String>();
		List<Vacante> lista;
		
		System.out.println("Introduce dos valores para los estatus");

		for (int i = 0; i < 2; i++) {
			tipo = Libreria.leerEntre(1, 3, "\nIntroduce el valor del estatus:"
					+ "\n1.Creada"
					+ "\n2.Aprobada"
					+ "\n3.Eliminada");

			if (tipo == 1) {
				estatus = "Creada";
			}
			else if (tipo == 2) {
				estatus = "Aprobada";
			}
			else {
				estatus = "Eliminada";
			}
			
			listaEstatus.add(estatus);
		}

		lista = repositorioVacante.findByEstatusIn(listaEstatus);
		
		System.out.println("\nTotal de registros encontrados: " + lista.size());

		lista.stream()
		.forEach(v -> System.out.println(v.getId() + " Vacante: " + v.getNombre() + 
				" | Destacada: " + v.getDestacada() + " | Estatus: " + v.getEstatus()));
	}
	
	// Mostrar vacantes por rango de salarios
	private void listaVacantesPorSalario() {
		System.out.println("\nConsultar vacantes por salario");
		float salario1, salario2;
		List<Vacante> lista;

		salario1 = Teclado.leerFloat("\nIntroduce el salario mínimo:");
		salario2 = Teclado.leerFloat("\nIntroduce el salario máximo:");
		lista = repositorioVacante.findBySalarioBetweenOrderBySalarioDesc(salario1, salario2);

		System.out.println("\nTotal de registros encontrados: " + lista.size());

		lista.stream()
		.forEach(v -> System.out.println(v.getId() + " Vacante: " + v.getNombre() + " | Salario: " + v.getSalario() + " euros"));
	}
	
	// Mostar una lista de vacantes por ID
	private void listaVacantesPorId() {
		System.out.println("\nConsultar lista vacantes por ID");
		List<Integer> list = new ArrayList<Integer>();
		list.add(2);
		list.add(4);
		list.add(6);
		list.add(8);
		list.add(9);
		Iterable<Vacante> vacancies = repositorioVacante.findAllById(list);

		for (Vacante v : vacancies) {
			System.out.println(v);
		}
	}
	
	// Contar 
	private void contarVacantes() {
		long count = repositorioVacante.count();
		System.out.println("\nTotal de vacantes: " + count);
	}

	/* Final Vacantes */
	
	/* Perfiles */
	
	// Insertar
	private void insertarPerfiles() {
		System.out.println("\nLista de perfiles o roles de la aplicación");
		repositorioPerfil.saveAll(obtenerPerfiles()).forEach(System.out::println);
	}
	
	/**
	 * Método que devuelve una lista de objetos que representa
	 * los diferentes perfiles o roles de la aplicación
	 * @return lista de perfiles
	 */
	private List<Perfil> obtenerPerfiles() {
		List<Perfil> lista = new ArrayList<Perfil>();
		Perfil supervisor, administrador, usuario;

		supervisor = Perfil.builder().id(1).perfil("Supervisor").build();
		administrador = Perfil.builder().id(2).perfil("Administrador").build();
		usuario = Perfil.builder().id(3).perfil("Usuario").build();

		lista.add(supervisor);
		lista.add(administrador);
		lista.add(usuario);

		return lista;
	}
	
	private void listaPerfiles() {
		System.out.println("\nLista de perfiles o roles de la aplicación");
		repositorioPerfil.findAll().forEach(System.out::println);
	}
	
	// Eliminar
	private void eliminarPerfiles() {
		repositorioPerfil.deleteAll();
		System.out.println("\nRegistros eliminados correctamente");
	}
	
	/* Final Perfiles */
	
	/* Usuarios */
	
	// Insertar
	private void insertarUsuario() {
		String continuar, seguir;
		System.out.println("\nCrear usuario");
		
		do {
			Usuario usuario = new Usuario();
			usuario.setNombre(Teclado.leerString("\nIntroduce el nombre:"));
			usuario.setEmail(Teclado.leerString("\nIntroduce el email:"));
			usuario.setFechaRegistro(LocalDate.now());
			usuario.setUsername(Teclado.leerString("\nIntroduce el nombre de usuario:"));
			usuario.setEstatus(1);
			usuario.setPassword(Teclado.leerString("\nIntroduce la contraseña:"));
			
			do {
				
				Perfil perfil = new Perfil();
				
				perfil.setId(Libreria.leerEntre(1, 3, "\nIntroduce el ROL del usuario:"
						+ "\n1.Supervisor"
						+ "\n2.Administrador"
						+ "\n3.Usuario"));
				
				usuario.incluirPerfil(perfil);
				
				do  {					
					seguir = Teclado.leerString("\n¿Quieres añadir otro ROL al usuario? S/N:");
					if (!seguir.equalsIgnoreCase("S") && !seguir.equalsIgnoreCase("N")) {
						System.out.println("\nERROR!! Respuesta no válida.");
						System.out.println("\nPor favor introduce S/N");
					}
				} while (!seguir.equalsIgnoreCase("S") && !seguir.equalsIgnoreCase("N"));
				
			} while (seguir.equalsIgnoreCase("S"));
			
			repositorioUsuario.save(usuario);
			System.out.println("\nRegistro insertado correctamente");
			
			do {
				continuar = Teclado.leerString("\n¿Quieres insertar otro usuario? S/N:");
				if (!continuar.equalsIgnoreCase("S") && !continuar.equalsIgnoreCase("N")) {
					System.out.println("\nERROR!! Respuesta no válida.");
					System.out.println("\nPor favor introduce S o N");
				}
			} while (!continuar.equalsIgnoreCase("S") && !continuar.equalsIgnoreCase("N"));
			
		} while (continuar.equalsIgnoreCase("S"));
	}
	
	// Mostrar todos los usuarios
	private void listaUsuarios() {
		System.out.println("\nListado de usuarios");
		repositorioUsuario.findAll().forEach(System.out::println);
	}
	
	// Mostrar usurios ordenados por nombre
	private void listaUsuariosOrenados() {
		System.out.println("\nListado de usuarios ordenados por nombre");
		repositorioUsuario.findAll(Sort.by("nombre")).forEach(System.out::println);
	}
	
	// Mostrar usuario y sus perfiles
	private void buscarUsuarioId() {
		int id;
		
		id = Teclado.leerInt("\nIntroduce el identificador del usuario a consultar:");
		Optional<Usuario> usuario = repositorioUsuario.findById(id);

		if (usuario.isPresent()) {
			System.out.println("\nDatos del usuario consultado");
			System.out.println(usuario.get());
			System.out.println("\nROLES del usuario consultado");

			usuario.stream().map(u -> u.getPerfiles())
			.flatMap(p -> p.stream())
			.forEach(p -> System.out.println(p.getPerfil()));
		}
		else {
			System.out.println("\nEl identificador del usuario no existe en la base de datos");
		}

	}
	
	/* Final Usuarios */
}
