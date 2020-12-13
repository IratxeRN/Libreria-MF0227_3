package ejercicio_MF0227_3.controladores.admin;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ejercicio_MF0227_3.accesodatos.Dao;
import ejercicio_MF0227_3.accesodatos.LibroDaoTreeMap;
import ejercicio_MF0227_3.modelo.Libro;

/**
 * Servlet implementation class NuevoServlet
 */
@WebServlet("/admin/nuevo-editar")
public class NuevoEditarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");

		if (id != null) {

			Dao<Libro> dao = LibroDaoTreeMap.getInstancia();

			Libro libro = dao.buscar(Integer.parseInt(id));

			request.setAttribute("libro", libro);

		}

		request.getRequestDispatcher("/WEB-INF/vistas/admin/nuevo_editar.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		final String URL_IMG = "img/libros/";
		final String FILE_IMG = "imagen_defecto.jpg";

		Integer id = Integer.parseInt(request.getParameter("id"));
		String nombre = request.getParameter("nombre");
		String autor = request.getParameter("autor");
		BigDecimal precio = new BigDecimal(request.getParameter("precio"));
		Integer descuento = Integer.parseInt(request.getParameter("descuento"));
		String urlImagen = request.getParameter("urlImagen");

		if (urlImagen.isEmpty()) {
			urlImagen = URL_IMG + FILE_IMG;
		} else {
			urlImagen = URL_IMG + urlImagen;
		}

		Libro libro = new Libro(id, nombre, precio, descuento, autor, urlImagen);
		Dao<Libro> dao = LibroDaoTreeMap.getInstancia();

		String mensaje;

		if (libro.getId() == null) {
			dao.crear(libro);
			mensaje = "Libro creado con exito";
		} else {
			dao.modificar(libro);
			mensaje = "Libro modificado con exito";
		}
		request.setAttribute("alertaTexto", mensaje);
		request.setAttribute("alertaNivel", "success");

		request.getRequestDispatcher("/WEB-INF/vistas/admin/gestion.jsp").forward(request, response);
	}

}
