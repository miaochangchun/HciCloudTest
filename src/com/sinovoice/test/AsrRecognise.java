package com.sinovoice.test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinovoice.util.HttpCallbackListener;
import com.sinovoice.util.HttpUtil;

/**
 * Servlet implementation class AsrRecognise
 */
@WebServlet("/AsrRecognise")
public class AsrRecognise extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AsrRecognise() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String fileName = (String) request.getAttribute("filename");
//		response.getWriter().append(fileName);
//		response.getWriter().append("<br>");
		String address = "http://test.api.hcicloud.com:8880/asr/recognise";
		HttpUtil.asrSendRequest(address, fileName, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String result) {
				// TODO Auto-generated method stub
//				System.out.println(result);
				result = result.substring(result.indexOf("<Text>") + 6, result.indexOf("</Text>"));
				System.out.println(result);
				try {
					response.getWriter().append("识别结果：").append(result);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
