package controller;

import com.google.gson.Gson;
import dao.DaftarKelasDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DaftarKelas;

@WebServlet(name = "DaftarKelasCtr", urlPatterns = {"/DaftarKelasCtr"})
public class DaftarKelasCtr extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        String page =   request.getParameter("page");
        PrintWriter out = response.getWriter();
        DaftarKelasDao dkdao = new DaftarKelasDao();
        Gson gson = new Gson();
        
        if(page == null){
            List<DaftarKelas> listDaftarKelas = dkdao.getAllDaftarKelas();
            String jsonDaftarKelas = gson.toJson(listDaftarKelas);
            out.println(jsonDaftarKelas);
        }else if(page.equals("tambah")){
            String nis = request.getParameter("nis");
            String kodekelas = request.getParameter("kodekelas");
            String tahunpel = request.getParameter("tahunpel");

            if(dkdao.getCekNis(nis).getNis() != null && dkdao.getCekKodekelas(kodekelas).getKodekelas() != null && dkdao.getCekData(nis, kodekelas, tahunpel).getTahunpel() != null){
                response.setContentType("text/html;charset=UTP-8");
                out.println("Data sudah tersedia");
            }else if(dkdao.getCekNis(nis).getNis() == null && dkdao.getCekKodekelas(kodekelas).getKodekelas() == null){
                response.setContentType("text/html;charset=UTP-8");
                out.println("NIS dan Kode Kelas tidak ada pada database");
            }else if(dkdao.getCekNis(nis).getNis() == null){
                response.setContentType("text/html;charset=UTP-8");
                out.println("NIS : '"+nis+"' - Tidak ada pada database");
            }else if(dkdao.getCekKodekelas(kodekelas).getKodekelas() == null){
                response.setContentType("text/html;charset=UTP-8");
                out.println("Kode kelas : '"+kodekelas+"' - Tidak ada pada database");
            }else{
                DaftarKelas dk = new DaftarKelas();
                dk.setNis(request.getParameter("nis"));
                dk.setKodekelas(request.getParameter("kodekelas"));
                dk.setTahunpel(request.getParameter("tahunpel"));

                dkdao.simpanData(dk, page);

                response.setContentType("text/html;charset=UTF-8");
                out.println("Data berhasil disimpan");
            }
        }else if(page.equals("tampil")){
            String jSonDaftarKelas = gson.toJson(dkdao.getRecordByNiskodekelas(request.getParameter("nis"),request.getParameter("kodekelas")));
            response.setContentType("application/json");
            out.println(jSonDaftarKelas);
        }else if(page.equals("edit")){
            DaftarKelas dk = new DaftarKelas();
            dk.setNis(request.getParameter("nis"));
            dk.setKodekelas(request.getParameter("kodekelas"));
            dk.setTahunpel(request.getParameter("tahunpel"));
            
            dkdao.simpanData(dk, page);
            response.setContentType("text/html;charset=UTF-8;");
            out.println("Data berhasil disimpan");
        }else if(page.equals("hapus")){
            dkdao.hapusData(request.getParameter("nis"),request.getParameter("kodekelas"),request.getParameter("tahunpel"));
            response.setContentType("text/html;charset=UTF-8");
            out.println("Data berhasil dihapus");
        }else if(page.equals("tahunpel")){
            List<DaftarKelas> listTahunPelajaran = dkdao.getAllTahunPelajaran();
            String jsonTahunPelajaran = gson.toJson(listTahunPelajaran);
            out.println(jsonTahunPelajaran);
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
