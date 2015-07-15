package tw.edu.chit.struts.action.course.servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.output.OutputException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;

public class getBarcode39 extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		
		String idno = request.getParameter("no");
		response.setContentType("image/jpeg"); 
		
		try {
			byte[] bs = getBarCode(idno, "39");
			OutputStream outs = response.getOutputStream(); 
			outs.write(bs);
			outs.flush();
		} catch (IllegalStateException e) {
			response.sendRedirect("/CIS/pages/images/notFound.gif");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private byte[] getBarCode(String code, String type) {
		
		Barcode barcode = null;
		try {
			barcode = BarcodeFactory.createCode39(code, false);
			barcode.setBarHeight(48);
			barcode.setFont(null);
		} catch (BarcodeException e2) {
			e2.printStackTrace();
		}
		BufferedImage image = new BufferedImage(barcode.getWidth(), barcode.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		try {
			barcode.draw(g2, 0, 0);
		} catch (OutputException e1) {
			e1.printStackTrace();
		}

		ByteArrayOutputStream bas = new ByteArrayOutputStream();
		try {
			ImageIO.write(image,"jpeg", bas);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] data = bas.toByteArray();

		return data;
	}

}
