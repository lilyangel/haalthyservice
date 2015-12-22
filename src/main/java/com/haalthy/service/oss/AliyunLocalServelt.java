package com.haalthy.service.oss;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 阿里云本地读取服务
 * @author john
 * @version 1.0  2015-5-22 12:36
 */
public class AliyunLocalServelt extends HttpServlet {

	private static final long serialVersionUID = 1L;

	AliyunService aliyunService;

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

        String uri = req.getRequestURI().replaceFirst(req.getContextPath() + "/local/", "");
        if (uri != null && uri.lastIndexOf("@") != -1) {
            uri = uri.substring(0, uri.lastIndexOf("@"));
        }
        String download = req.getParameter("d");
        InputStream in = null;
		try {
			in = aliyunService.getFile(uri);
		} catch (Exception e) {
		}
		if (null == in) {
			resp.getWriter().write("file not found!");
			return;
		}

        // 图片则预览，其他就下载
        if (uri.contains(".jpg") || uri.contains(".png") || uri.contains(".gif") || uri.contains(".bmp")) {
            if (download != null) {
                resp.setContentType("application/x-download");
            }
        } else {
            resp.setContentType("application/x-download");
        }
        OutputStream out = resp.getOutputStream();

        String w = req.getParameter("w");
        String h = req.getParameter("h");

        if (w != null && h != null) {
            compressImage(in, out, Integer.valueOf(w), Integer.valueOf(h), false);
        } else {
            IOUtils.copy(in, out);
        }

		in.close();
		out.close();
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		aliyunService = context.getBean(AliyunService.class);
	}

    public static boolean compressImage(InputStream file, OutputStream fileOutputStream,
                                        int width, int height, boolean proportion) {
        boolean ret = false;
//        OutputStream fileOutputStream = null;
        try {
//            if (file == null || directoryFileName == null) {
//                return ret;
//            }
//
//            fileOutputStream = new FileOutputStream(new File(directoryFileName));
            Image image = ImageIO.read(file);
            if (image.getWidth(null) == -1) {
                return ret;
            }

            int newWidth;
            int newHeight;

            if (image.getWidth(null) > width || image.getHeight(null) > height) {
                if (proportion) {
//                    int rate1 = image.getWidth(null) / width;
//                    int rate2 = image.getHeight(null) / height;

                    double rate1 = image.getWidth(null) / width;
                    double rate2 = image.getHeight(null) / height;

                    int rate = (int)(rate1 >= rate2 ? rate1 : rate2);
                    newWidth = image.getWidth(null) / rate;
                    newHeight = image.getHeight(null) / rate;
                } else {
                    newWidth = width;
                    newHeight = height;
                }
            } else {
                newWidth = image.getWidth(null);
                newHeight = image.getHeight(null);
            }

            BufferedImage bufferedImage = new BufferedImage(newWidth,
                    newHeight, BufferedImage.TYPE_INT_RGB);

            bufferedImage.getGraphics().drawImage(
                    image.getScaledInstance(newWidth, newHeight,
                            image.SCALE_SMOOTH), 0, 0, null);

            // 该API ant打包失败
//            JPEGImageEncoder encoder = JPEGCodec
//                    .createJPEGEncoder(fileOutputStream);
//            encoder.encode(bufferedImage);
            fileOutputStream.close();

            ret = true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }
	
}
