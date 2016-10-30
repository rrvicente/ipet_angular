package br.com.ipet.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ipet.entidade.Produto;
import br.com.ipet.json.JSONRead;

/**
 * Servlet implementation class ProdutoServlet
 */
@WebServlet("/ProdutoServlet")
public class ProdutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ProdutoServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String acao = request.getParameterMap().get("acao")[0];

		if ("new".equalsIgnoreCase(acao)) {
			String descricao = request.getParameterMap().get("produto[descricao]")[0];
			String estoque = request.getParameterMap().get("produto[estoque]")[0];
			String valor = request.getParameterMap().get("produto[valor]")[0];
			String tipo = request.getParameterMap().get("produto[tipo]")[0];

			Produto produto = new Produto();
			produto.setId(produto.key);
			produto.setDescricao(descricao);
			produto.setValor(Double.parseDouble(valor));
			produto.setEstoque(Integer.parseInt(estoque));
			produto.setTipo(Integer.parseInt(tipo));

			JSONRead.gravaJSONObject(produto.toJSONObject(), "C:\\json\\saida.json");
		} else if ("delete".equalsIgnoreCase(acao)) {
			int id = Integer.parseInt(request.getParameterMap().get("id")[0]);
			JSONRead.deleteJSONObjectById(id, "C:\\json\\saida.json");
		} else {
			response.setContentType("application/json");
			response.getWriter().write(JSONRead.listarJSONObject("C:\\json\\saida.json"));
		}

	}

}
