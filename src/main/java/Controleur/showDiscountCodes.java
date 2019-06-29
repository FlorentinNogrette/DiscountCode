package controleur;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Modele.DAOException;
import Modele.DAO;
import Modele.DataSourceFactory;
import Modele.DiscountEntity;
import java.io.PrintWriter;

@WebServlet(name = "showDiscountCodes", urlPatterns = {"/showDiscountCodes"})
public class showDiscountCodes extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Créér le ExtendedDAO avec sa source de données
            DAO dao = new DAO(DataSourceFactory.getDataSource());

            List<DiscountEntity> DiscountList = dao.getDiscountList();

            request.setAttribute("listeCodeDiscount", DiscountList);

            // On continue vers la page JSP sélectionnée
            request.getRequestDispatcher("views/ViewDiscountCode.jsp").forward(request, response);
        } catch (DAOException ex) {
            Logger.getLogger("servlet").log(Level.SEVERE, "Erreur de traitement", ex);
        }
    }



    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        switch (action) {
            case "DELETE":
                System.out.println("DELETE");
                deleteDiscount(request, response);
                break;
            case "ADD":
                System.out.println("ADD");
                addDiscountRegister(request, response);
            default:
                
        }

        processRequest(request, response);

    }
    
        /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    public void addDiscountRegister(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String parametre1 = request.getParameter("code");
        double parametre2 = Double.parseDouble(request.getParameter("taux"));
        
        DAO dao = new DAO(DataSourceFactory.getDataSource());
        dao.addDiscount(parametre1, parametre2);
        processRequest(request, response);
        
    }
    
    public void deleteDiscount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String parametre = request.getParameter("code");
        DAO dao = new DAO(DataSourceFactory.getDataSource());
        dao.deleteDiscount(parametre);

    }
    
        /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
