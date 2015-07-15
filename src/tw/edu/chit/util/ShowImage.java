package tw.edu.chit.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.StdImage;
import tw.edu.chit.service.MemberManager;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ShowImage extends HttpServlet {

	private static final long serialVersionUID = 4239354987908257899L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		MemberManager mm = (MemberManager) ctx
				.getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		String studentNo = request.getParameter("no");
		StdImage image = new StdImage();
		image.setStudentNo(studentNo);
		image = mm.findStdImageBy(image);
		response.setContentType("image/jpeg");
		if (image != null) {
			try {
				Blob blob = image.getImage();
				BufferedInputStream bis = new BufferedInputStream(blob
						.getBinaryStream());
				BufferedImage bi = ImageIO.read(bis);
				ServletOutputStream sos = response.getOutputStream();
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(sos);
				encoder.encode(bi);
				bis.close();
				sos.close();
			} catch (Exception e) {
				response.sendRedirect("/CIS/pages/images/pic.gif");
			}
		} else {
			response.sendRedirect("/CIS/pages/images/pic.gif");
		}
	}

}
