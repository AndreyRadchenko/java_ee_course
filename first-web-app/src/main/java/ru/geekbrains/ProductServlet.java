package ru.geekbrains;

import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/product/*")
public class ProductServlet extends HttpServlet {

    private static final Pattern pathParam = Pattern.compile("/(\\d*)$");

    ProductRepository productRepository = new ProductRepository();

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getPathInfo() == null || req.getPathInfo().equals("") || req.getPathInfo().equals("/")) {
            resp.getWriter().println("<table>");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<th>id</th>");
            resp.getWriter().println("<th>name</th>");
            resp.getWriter().println("<th>description</th>");
            resp.getWriter().println("<th>price</th>");
            resp.getWriter().println("</tr>");

            for (Product product : productRepository.findAll()) {
                resp.getWriter().println("<tr>");
                resp.getWriter().println("<td><a href = '" + getServletContext().getContextPath() + "/product/" + product.getId() + "'>" + product.getId() + "</td>");
                resp.getWriter().println("<td>" + product.getName() + "</td>");
                resp.getWriter().println("<td>" + product.getDescription() + "</td>");
                resp.getWriter().println("<td>" + product.getPrice() + "</td>");
                resp.getWriter().println("<tr>");
            }
            resp.getWriter().println("</table>");
            return;
        } else {

            Matcher matcher = pathParam.matcher(req.getPathInfo());

            if (matcher.matches()) {
                long id;
                try {
                    id = Long.parseLong(matcher.group(1));
                } catch (NumberFormatException ex) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                Product product = productRepository.findById(id);
                resp.getWriter().println("<p>Product Info</p>");
                resp.getWriter().println("<p>Name: " + product.getName() + "</p>");
                resp.getWriter().println("<p>Description: " + product.getDescription() + "</p>");
                resp.getWriter().println("<p>Price: " + product.getPrice() + "</p>");
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
