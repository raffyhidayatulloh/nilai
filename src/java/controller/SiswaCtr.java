package controller;

import com.google.gson.Gson;
import dao.DaftarKelasDao;
import dao.SiswaDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Siswa;

@WebServlet(name = "SiswaCtr", urlPatterns = {"/SiswaCtr"})
public class SiswaCtr extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        response.setContentType("application/json");
        String page =   request.getParameter("page");
        PrintWriter out = response.getWriter();
        SiswaDao sisdao = new SiswaDao();
        Gson gson = new Gson();
        
        if(page == null){
            List<Siswa> listSiswa = sisdao.getAllSiswa();
            String jsonSiswa = gson.toJson(listSiswa);
            out.println(jsonSiswa);
        }else if(page.equals("tambah")){
            String nis = request.getParameter("nis");
            String nama = request.getParameter("nama");

            if(sisdao.getRecordByNis(nis).getNis() != null){
                response.setContentType("text/html; charset=UTP-8");
                out.println("NIS : "+nis+ " - "+sisdao.getRecordByNis(nis).getNama()+" sudah ada");
            }
            else{
                Siswa sis = new Siswa();
                sis.setNis(request.getParameter("nis"));
                sis.setNama(request.getParameter("nama"));
                sis.setJenkel(request.getParameter("jenkel"));
                sis.setTelepon(request.getParameter("telepon"));
                sis.setAlamat(request.getParameter("alamat"));    

                sisdao.simpanData(sis, page);

                response.setContentType("text/html;charset=UTF-8");
                out.println("Data berhasil disimpan");
            }
        }else if(page.equals("tampil")){
            String jSonSiswa = gson.toJson(sisdao.getRecordByNis(request.getParameter("nis")));
            response.setContentType("application/json");
            out.println(jSonSiswa);
        }else if(page.equals("edit")){
            Siswa sis = new Siswa();
            sis.setNis(request.getParameter("nis"));
            sis.setNama(request.getParameter("nama"));
            sis.setNis(request.getParameter("nis"));
            sis.setJenkel(request.getParameter("jenkel"));
            sis.setTelepon(request.getParameter("telepon"));
            sis.setAlamat(request.getParameter("alamat")); 
            
            sisdao.simpanData(sis, page);
            response.setContentType("text/html;charset=UTF-8;");
            out.println("Data berhasil diedit");
        }else if(page.equals("hapus")){
            String nis = request.getParameter("nis");
            DaftarKelasDao dkdao = new DaftarKelasDao();
            if(dkdao.getForeignNis(nis).getNis() != null){
                response.setContentType("text/html; charset=UTP-8");
                out.println("Tidak dapat dihapus, terdapat foreign key pada Daftar Kelas");
            }else{
                sisdao.hapusData(request.getParameter("nis"));
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
