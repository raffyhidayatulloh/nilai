package controller;

import com.google.gson.Gson;
import dao.GuruDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Guru;

@WebServlet(name = "GuruCtr", urlPatterns = {"/GuruCtr"})
public class GuruCtr extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        String page =   request.getParameter("page");
        PrintWriter out = response.getWriter();
        GuruDao gurdao = new GuruDao();
        Gson gson = new Gson();
        
        if(page == null){
            List<Guru> listGuru = gurdao.getAllGuru();
            String jsonGuru = gson.toJson(listGuru);
            out.println(jsonGuru);
        }else if(page.equals("tambah")){
            String kodeguru = request.getParameter("kodeguru");
            String namaguru = request.getParameter("namaguru");
            String idmapel = request.getParameter("idmapel");

            if(gurdao.getRecordByAllValidasi(kodeguru,namaguru,idmapel).getKodeguru() != null && gurdao.getRecordByAllValidasi(kodeguru,namaguru,idmapel).getNamaguru() != null && gurdao.getRecordByAllValidasi(kodeguru,namaguru,idmapel).getIdmapel() != null){
                response.setContentType("text/html; charset=UTP-8");
                out.println("Kode guru: '" + kodeguru + " - " + namaguru + " - " + idmapel + "' sudah ada");
            }
            else{
                Guru gur = new Guru();
                gur.setKodeguru(request.getParameter("kodeguru"));
                gur.setNamaguru(request.getParameter("namaguru"));
                gur.setIdmapel(request.getParameter("idmapel"));

                gurdao.simpanData(gur, page);

                response.setContentType("text/html;charset=UTF-8");
                out.println("Data berhasil disimpan");
            }
        }else if(page.equals("tampil")){
            String jSonGuru = gson.toJson(gurdao.getRecordByKodeguru(request.getParameter("kodeguru")));
            response.setContentType("application/json");
            out.println(jSonGuru);
        }else if(page.equals("edit")){
            Guru gur = new Guru();
            String kodeguru = request.getParameter("kodeguru");
            String namaguru = request.getParameter("namaguru");
            String idmapel = request.getParameter("idmapel");
            
            gur.setKodeguru(kodeguru);
            gur.setNamaguru(namaguru);
            gur.setIdmapel(idmapel);
            
            if(gurdao.getRecordByAllValidasi(kodeguru,namaguru,idmapel).getKodeguru() != null && gurdao.getRecordByAllValidasi(kodeguru,namaguru,idmapel).getNamaguru() != null && gurdao.getRecordByAllValidasi(kodeguru,namaguru,idmapel).getIdmapel() != null){
                response.setContentType("text/html; charset=UTP-8");
                out.println("Kode guru: '" + kodeguru + " - " + namaguru + " - " + idmapel + "' sudah ada");
            } else {
                gurdao.simpanData(gur, page);
                response.setContentType("text/html;charset=UTF-8;");
                out.println("Data berhasil diedit");
            }
        }else if(page.equals("hapus")){
            gurdao.hapusData(request.getParameter("kodeguru"));
            response.setContentType("text/html;charset=UTF-8");
            out.println("Data berhasil dihapus");
        } else if (page.equals("tampiledit")) {
            String kodeguru = request.getParameter("kodeguru");
            String namaguru = request.getParameter("namaguru");
            String idmapel = request.getParameter("idmapel");
            String jSonGuru = gson.toJson(gurdao.getRecordByAllValidasi(kodeguru,namaguru,idmapel));
            response.setContentType("application/json");
            out.println(jSonGuru);
        } else if(page.equals("kodeguru")){
            List<Guru> listKodeguru = gurdao.getAllKodeguru();
            String jsonKodeguru = gson.toJson(listKodeguru);
            out.println(jsonKodeguru);
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
