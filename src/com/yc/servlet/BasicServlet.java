package com.yc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

public class BasicServlet extends HttpServlet{
	public void send(HttpServletResponse resp,int result) {
		try {
			PrintWriter out=resp.getWriter();
			out.print(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void send(HttpServletResponse resp,String result) {
		try {
			PrintWriter out=resp.getWriter();
			out.print(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void send(HttpServletResponse resp,Object result) {
		try {
			PrintWriter out=resp.getWriter();
			out.print(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void send(HttpServletResponse resp,List<Map<String,String>> list) {
		try {
			PrintWriter out=resp.getWriter();
			Gson gson=new Gson();
			String json=gson.toJson(list);
			out.print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
