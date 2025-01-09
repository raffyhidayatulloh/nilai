package controller;

import com.google.gson.Gson;
import dao.JurusanDao;
import dao.KelasDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Jurusan;

@WebServlet(name = "JurusanCtr", urlPatterns = {"/JurusanCtr"})
public class JurusanCtr extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        String page =   request.getParameter("page");
        PrintWriter out = response.getWriter();
        JurusanDao jurdao = new JurusanDao();
        Gson gson = new Gson();
        
        if(page == null){
            List<Jurusan> listJurusan = jurdao.getAllJurusan();
            String jsonJurusan = gson.toJson(listJurusan);
            out.println(jsonJurusan);
        }else if(page.equals("tambah")){
            String kodejurusan = request.getParameter("kodejurusan");
            String namajurusan = request.getParameter("namajurusan");

            if(jurdao.getRecordByKodejurusan(kodejurusan).getKodejurusan() != null){
                response.setContentType("text/html; charset=UTP-8");
                out.println("Kode Jurusan : '"+kodejurusan+ " - "+jurdao.getRecordByKodejurusan(kodejurusan).getNamajurusan()+"' sudah ada");
            }
            else{
                Jurusan jur = new Jurusan();
                jur.setKodejurusan(request.getParameter("kodejurusan"));
                jur.setNamajurusan(request.getParameter("namajurusan"));  

                jurdao.simpanData(jur, page);

                response.setContentType("text/html;charset=UTF-8");
                out.println("Data Berhsasil disimpan");
            }
        }else if(page.equals("tampil")){
            String jSonJurusan = gson.toJson(jurdao.getRecordByKodejurusan(request.getParameter("kodejurusan")));
            response.setContentType("application/json");
            out.println(jSonJurusan);
        }else if(page.equals("edit")){
            Jurusan jur = new Jurusan();
            jur.setKodejurusan(request.getParameter("kodejurusan"));
            jur.setNamajurusan(request.getParameter("namajurusan"));  
            
            jurdao.simpanData(jur, page);
            response.setContentType("text/html;charset=UTF-8;");
            out.println("Data berhasil disimpan");
        }else if(page.equals("hapus")){
            String kodejurusan = request.getParameter("kodejurusan");
            KelasDao keldao = new KelasDao();
            if(keldao.getForeignKodejurusan(kodejurusan).getKodejurusan() != null){
                response.setContentType("text/html; charset=UTP-8");
                out.println("Tidak dapat dihapus, terdapat foreign key pada Kelas");
            }else{
                jurdao.hapusData(request.getParameter("kodejurusan"));
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
