package web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import objects.*;

/**
 * Servlet implementation class UploadPicture
 */
@WebServlet("/manager/uploadPicture")
public class UploadPicture extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadPicture() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.sendRedirect("../rejectVisit.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		Car car = (Car)request.getSession().getAttribute("car");
		request.getSession().removeAttribute("car");
		String uploadPath = request.getSession().getServletContext().getRealPath("/");
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		String success = "fail";
		try{
			List<FileItem> items = upload.parseRequest(request);
			if(!items.get(0).isFormField()){
				String filename = items.get(0).getName();
				System.out.println(filename);
				if(filename==null||filename.indexOf(".jpg")!=filename.length()-4){
					
				}else{
					File file = new File(uploadPath+"img/cars",String.valueOf(car.getCarId())+".jpg");
					items.get(0).write(file);
					success="success";
					//Thread.sleep(10000);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//response.setHeader("Cache-Control","no-store");
			//response.setHeader("Pragrma","no-cache");
			//response.setDateHeader("Expires",0);
			response.sendRedirect("afterUpload.jsp?carId="+car.getCarId()+"&success="+success);
		}
	}

}
