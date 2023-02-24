package com.yc.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yc.dao.DBHelper;

@WebServlet("/travelServlet")
public class TravelServlet extends BasicServlet{
	DBHelper db=new DBHelper();
	
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 doPost(req, resp);
    }
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置编码
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String op=req.getParameter("op");
		if("findAlltravels".equals(op)) {
			findAlltravels(req,resp);
		}
		else if("findByAid".equals(op)) {
			findByAid(req,resp);
		}
    }
	
	//查询所有的游记数据
		public void findByAid(HttpServletRequest req,HttpServletResponse resp) {
			String sql="select aic,atitle,auser from travel where aid=1 ";
			List<Map<String,String>> list=db.find(sql);
			this.send(resp, list);
		}
	
		
		
	//查询所有的游记数据
	public void findAlltravels(HttpServletRequest req,HttpServletResponse resp) {
		String sql="select aic,atheme,anum,adr from travel limit 0,10";
		List<Map<String,String>> list=db.find(sql);
		this.send(resp, list);
	}
}
