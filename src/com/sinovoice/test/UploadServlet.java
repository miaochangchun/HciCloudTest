package com.sinovoice.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		//音频上传的路径
		String filePath = this.getServletConfig().getServletContext().getRealPath("/");
		//音频文件名
		String filename = null;
		try {
			// 创建一个解析器工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 得到解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("utf-8");
			// 对相应的请求进行解析，有几个输入项，就有几个FileItem对象
			List<FileItem> lists = upload.parseRequest(request);
			// 要迭代list集合，获取list集合中每一个输入项
			for (FileItem item : lists) {
				// 判断输入的类型
				if (item.isFormField()) {
					// 普通输入项
					String inputName = item.getFieldName();
					String inputValue = item.getString();
					System.out.println(inputName + "::" + inputValue);
				} else {
					// 上传文件输入项
					filename = item.getName();// 上传文件的文件名1.txt
					filename = filename.substring(filename.lastIndexOf("\\") + 1);
					InputStream is = item.getInputStream();
					System.out.println("要上传的目录文件名是：" + filePath + filename);
					FileOutputStream fos = new FileOutputStream(filePath + filename);
					byte[] buff = new byte[1024];
					int len = 0;
					while ((len = is.read(buff)) > 0) {
						fos.write(buff);
					}
					is.close();
					fos.close();
				}
			}
		} catch (FileUploadException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		RequestDispatcher rd = request.getRequestDispatcher("AsrRecognise");
		request.setAttribute("filename", filePath + filename);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
