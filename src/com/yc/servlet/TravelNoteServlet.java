package com.yc.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yc.dao.DBHelper;

@WebServlet("/notesServlet")
public class TravelNoteServlet extends BasicServlet{
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
		if("findAllNotes".equals(op)) {
			findAllNotes(req,resp);
		}else if("findtwoNotes".equals(op)) {
			findtwoNotes(req,resp);
		}
		else if("findByNid".equals(op)) {
			 findByNid(req,resp);
		}
    }
	
	
		//查询所有的游记数据
		public void findByNid(HttpServletRequest req,HttpServletResponse resp) {
			String sql="select nic,ntitle,nuser from travel_notes where nid=1 ";
			List<Map<String,String>> list=db.find(sql);
			this.send(resp, list);
		}
	
		//查询2个游记数据
		public void findtwoNotes(HttpServletRequest req,HttpServletResponse resp) {
			//显示3到4行的记录，即查询2行记录
			String sql="select nic,ntitle,ntheme from travel_notes limit 2,2";
			List<Map<String,String>> list=db.find(sql);
			this.send(resp, list);
		}
		
	//查询所有的游记数据
	public void findAllNotes(HttpServletRequest req,HttpServletResponse resp) {
		String sql="select nic,ntitle,ntheme from travel_notes limit 0,10";
		List<Map<String,String>> list=db.find(sql);
		this.send(resp, list);
	}
}
