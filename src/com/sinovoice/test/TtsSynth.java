package com.sinovoice.test;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinovoice.util.ConfigUtil;
import com.sinovoice.util.HttpCallbackListener;
import com.sinovoice.util.HttpUtil;

/**
 * Servlet implementation class TtsSynth
 */
@WebServlet("/TtsSynth")
public class TtsSynth extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TtsSynth() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//保存音频文件所在服务器的路径
		request.setCharacterEncoding("UTF-8");
		String filePath = this.getServletConfig().getServletContext().getRealPath("/") + System.currentTimeMillis() + ".pcm";
//		System.out.println(filePath);
		String text = request.getParameter("text");
//		System.out.println("发送的文本：" + text);
		HttpUtil.ttsSendRequest(ConfigUtil.CLOUD_URL, text, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String result) {
				// TODO Auto-generated method stub
				saveToFile(result, filePath);
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				System.out.println("进入异常了 ~");
			}
		});
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("download.jsp");
		request.setAttribute("filePath", filePath);
		rd.forward(request, response);
	}
	
	/**
	 * 把音频数据保存到文件中
	 * @param response	HTTP返回的音频数据
	 * @param filePath	音频文件名
	 */
	private void saveToFile(String response, String filePath){
		InputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(response.getBytes("iso-8859-1"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath);
			int len = 0;
			byte[] buffer = new byte[1024];
			while((len = inputStream.read(buffer)) != -1){
				fos.write(buffer);
				fos.flush();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
