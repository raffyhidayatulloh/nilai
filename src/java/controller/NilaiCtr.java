package controller;

import com.google.gson.Gson;
import dao.NilaiDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Nilai;

@WebServlet(name = "NilaiCtr", urlPatterns = {"/NilaiCtr"})
public class NilaiCtr extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        String page =   request.getParameter("page");
        PrintWriter out = response.getWriter();
        NilaiDao nildao = new NilaiDao();
        Gson gson = new Gson();
        
        if(page == null){
            List<Nilai> listNilai = nildao.getAllNilai();
            String jsonNilai = gson.toJson(listNilai);
            out.println(jsonNilai);
        }else if(page.equals("tambah")){
            String kodejurusan = request.getParameter("kodejurusan");
            String tahunpel = request.getParameter("tahunpel");
            String kodekelas = request.getParameter("kodekelas");
            String nis = request.getParameter("nis");
            String idmapel = request.getParameter("idmapel");

            
            if(nildao.getRecordByLotsNilai(kodejurusan,tahunpel,kodekelas,nis,idmapel).getKodejurusan() != null && nildao.getRecordByLotsNilai(kodejurusan,tahunpel,kodekelas,nis,idmapel).getTahunpel() != null && nildao.getRecordByLotsNilai(kodejurusan,tahunpel,kodekelas,nis,idmapel).getKodekelas() != null){
                response.setContentType("text/html; charset=UTP-8");
                out.println("NIS: "+nis+" - "+idmapel+" - "+nildao.getRecordByLotsNilai(kodejurusan,tahunpel,kodekelas,nis,idmapel).getNilaisiswa()+" sudah ada");
            }else if(nildao.getCekKodejurusan(kodejurusan).getKodejurusan() == null){
                response.setContentType("text/html; charset=UTP-8");
                out.println("Kode Jurusan tidak terdapat pada database");
            }else if(nildao.getCekKodekelas(kodekelas, kodejurusan).getKodekelas() == null){
                response.setContentType("text/html; charset=UTP-8");
                out.println("Kode Kelas dengan jurusan tersebut tidak terdapat pada database");
            }else if(nildao.getCekNis(nis).getNis() == null){
                response.setContentType("text/html; charset=UTP-8");
                out.println("NIS tidak terdapat pada database");
            }else{
                Nilai nil = new Nilai();
                nil.setKodejurusan(request.getParameter("kodejurusan"));
                nil.setTahunpel(request.getParameter("tahunpel"));
                nil.setKodekelas(request.getParameter("kodekelas"));
                nil.setNis(request.getParameter("nis"));
                nil.setIdmapel(request.getParameter("idmapel"));
                nil.setNilaisiswa(request.getParameter("nilaisiswa"));

                nildao.simpanData(nil, page);

                response.setContentType("text/html;charset=UTF-8");
                out.println("Data Berhasil disimpan");
            }
        }else if(page.equals("tampil")){
            String jSonNilai = gson.toJson(nildao.getRecordByLotsNilai(request.getParameter("kodejurusan"),request.getParameter("tahunpel"),request.getParameter("kodekelas"),request.getParameter("nis"),request.getParameter("idmapel")));
            response.setContentType("application/json");
            out.println(jSonNilai);
        }else if(page.equals("edit")){
            Nilai nil = new Nilai();
            nil.setKodejurusan(request.getParameter("kodejurusan"));
            nil.setTahunpel(request.getParameter("tahunpel"));
            nil.setKodekelas(request.getParameter("kodekelas"));
            nil.setNis(request.getParameter("nis"));
            nil.setIdmapel(request.getParameter("idmapel"));
            nil.setNilaisiswa(request.getParameter("nilaisiswa"));
            
            nildao.simpanData(nil, page);
            response.setContentType("text/html;charset=UTF-8;");
            out.println("Data berhasil disimpan");
        }else if(page.equals("hapus")){
            nildao.hapusData(request.getParameter("kodejurusan"),request.getParameter("tahunpel"),request.getParameter("kodekelas"),request.getParameter("nis"),request.getParameter("idmapel"));
            response.setContentType("text/html;charset=UTF-8");
            out.println("Data berhasil dihapus");
        }else if(page.equals("tampilmapel")){
            // Mengambil nilai berdasarkan idmapel yang diberikan
            String idmapel = request.getParameter("idmapel");
            List<Nilai> listNilai = nildao.getNilaiByIdMapel(idmapel);
            String jsonNilai = gson.toJson(listNilai);
            response.setContentType("application/json");
            out.println(jsonNilai);
        }else if(page.equals("tampilnis")){
            String nis = request.getParameter("nis");
            List<Nilai> listNilai = nildao.getNilaiByNis(nis);
            String jsonNilai = gson.toJson(listNilai);
            response.setContentType("application/json");
            out.println(jsonNilai);
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
