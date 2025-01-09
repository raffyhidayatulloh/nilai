package controller;

import com.google.gson.Gson;
import dao.KelasDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Kelas;

@WebServlet(name = "KelasCtr", urlPatterns = {"/KelasCtr"})
public class KelasCtr extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        String page =   request.getParameter("page");
        PrintWriter out = response.getWriter();
        KelasDao keldao = new KelasDao();
        Gson gson = new Gson();
        
        if(page == null){
            List<Kelas> listKelas = keldao.getAllKelas();
            String jsonKelas = gson.toJson(listKelas);
            out.println(jsonKelas);
        }else if(page.equals("tambah")){
            String kodejurusan = request.getParameter("kodejurusan");
            String kodekelas = request.getParameter("kodekelas");
            String namakelas = request.getParameter("namakelas");

            if(keldao.getRecordByKodejurusankelas(kodejurusan,kodekelas).getKodejurusan() != null && keldao.getRecordByKodejurusankelas(kodejurusan,kodekelas).getKodekelas() != null){
                response.setContentType("text/html; charset=UTP-8");
                out.println("Kode Jurusan : '" + kodejurusan + " - " + kodekelas + " - " + keldao.getRecordByKodejurusankelas(kodejurusan,kodekelas).getNamakelas()+" sudah ada");
            }
            else{
                Kelas kel = new Kelas();
                kel.setKodejurusan(request.getParameter("kodejurusan"));
                kel.setKodekelas(request.getParameter("kodekelas"));
                kel.setNamakelas(request.getParameter("namakelas"));

                keldao.simpanData(kel, page);

                response.setContentType("text/html;charset=UTF-8");
                out.println("Data Berhsasil disimpan");
            }
        }else if(page.equals("tampil")){
            String jSonKelas = gson.toJson(keldao.getRecordByKodejurusankelas(request.getParameter("kodejurusan"),request.getParameter("kodekelas")));
            response.setContentType("application/json");
            out.println(jSonKelas);
        }else if(page.equals("edit")){
            Kelas kel = new Kelas();
            kel.setKodejurusan(request.getParameter("kodejurusan"));
            kel.setKodekelas(request.getParameter("kodekelas"));
            kel.setNamakelas(request.getParameter("namakelas"));
            
            keldao.simpanData(kel, page);
            response.setContentType("text/html;charset=UTF-8;");
            out.println("Data berhasil disimpan, OK");
        }else if(page.equals("hapus")){
            keldao.hapusData(request.getParameter("kodejurusan"),request.getParameter("kodekelas"));
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
