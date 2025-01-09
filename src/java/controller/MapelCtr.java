package controller;

import com.google.gson.Gson;
import dao.MapelDao;
import dao.NilaiDao;
import dao.GuruDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Mapel;

@WebServlet(name = "MapelCtr", urlPatterns = {"/MapelCtr"})
public class MapelCtr extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        String page =   request.getParameter("page");
        PrintWriter out = response.getWriter();
        MapelDao mapdao = new MapelDao();
        Gson gson = new Gson();
        
        if(page == null){
            List<Mapel> listMapel = mapdao.getAllMapel();
            String jsonMapel = gson.toJson(listMapel);
            out.println(jsonMapel);
        }else if(page.equals("tambah")){
            String idmapel = request.getParameter("idmapel");
            String namamapel = request.getParameter("namamapel");

            if(mapdao.getRecordByIdmapel(idmapel).getIdmapel() != null){
                response.setContentType("text/html; charset=UTP-8");
                out.println("Id Mapel : "+idmapel+ " - "+mapdao.getRecordByIdmapel(idmapel).getNamamapel()+" sudah ada");
            }
            else{
                Mapel map = new Mapel();
                map.setIdmapel(request.getParameter("idmapel"));
                map.setNamamapel(request.getParameter("namamapel"));  

                mapdao.simpanData(map, page);

                response.setContentType("text/html;charset=UTF-8");
                out.println("Data berhasil disimpan");
            }
        }else if(page.equals("tampil")){
            String jSonMapel = gson.toJson(mapdao.getRecordByIdmapel(request.getParameter("idmapel")));
            response.setContentType("application/json");
            out.println(jSonMapel);
        }else if(page.equals("edit")){
            Mapel map = new Mapel();
            map.setIdmapel(request.getParameter("idmapel"));
            map.setNamamapel(request.getParameter("namamapel"));  
            
            mapdao.simpanData(map, page);
            response.setContentType("text/html;charset=UTF-8;");
            out.println("Data berhasil disimpan");
        }else if(page.equals("hapus")){
            String idmapel = request.getParameter("idmapel");
            NilaiDao nildao = new NilaiDao();
            GuruDao gurdao = new GuruDao();
            if(nildao.getForeignIdmapel(idmapel).getIdmapel() != null){
                response.setContentType("text/html; charset=UTP-8");
                out.println("Tidak dapat dihapus, terdapat foreign key pada Nilai");
            } else if (gurdao.getForeignIdmapel(idmapel).getIdmapel() != null){
                response.setContentType("text/html; charset=UTP-8");
                out.println("Tidak dapat dihapus, terdapat foreign key pada Guru");
            } else {
                mapdao.hapusData(request.getParameter("idmapel"));
                response.setContentType("text/html;charset=UTF-8");
                out.println("Data berhasil dihapus");
            }
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
