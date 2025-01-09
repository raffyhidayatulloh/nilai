package controller;

import com.google.gson.Gson;
import dao.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;

@WebServlet(name = "UserCtr", urlPatterns = {"/UserCtr"})
public class UserCtr extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        String page = request.getParameter("page");
        PrintWriter out = response.getWriter();
        UserDao usdao = new UserDao();
        Gson gson = new Gson();
        
        if(page == null){
            List<User> listUser = usdao.getAllUser();
            String jsonUser = gson.toJson(listUser);
            out.println(jsonUser);
        }else if(page.equals("tambah")){
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if(usdao.getRecordByUsername(username).getUsername() != null){
                response.setContentType("text/html; charset=UTP-8");
                out.println("Username : '"+username+"' sudah ada");
            }
            else{
                User us = new User();
                us.setUsername(request.getParameter("username"));
                us.setPassword(request.getParameter("password"));
                us.setLevel(request.getParameter("level"));

                usdao.simpanData(us, page);

                response.setContentType("text/html;charset=UTF-8");
                out.println("Data berhasil disimpan");
            }
        }else if(page.equals("tampil")){
            String jSonUser = gson.toJson(usdao.getRecordByUsername(request.getParameter("username")));
            response.setContentType("application/json");
            out.println(jSonUser);
        }else if(page.equals("edit")){
            User us = new User();
            us.setUsername(request.getParameter("username"));
            us.setPassword(request.getParameter("password"));
            us.setLevel(request.getParameter("level"));
            
            usdao.simpanData(us, page);
            response.setContentType("text/html;charset=UTF-8;");
            out.println("Data berhasil disimpan");
        }else if(page.equals("hapus")){
            usdao.hapusData(request.getParameter("username"));
            response.setContentType("text/html;charset=UTF-8");
            out.println("Data berhasil dihapus");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
