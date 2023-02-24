package com.yc.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.dao.DBHelper;
import com.yc.servlet.BasicServlet;

@WebServlet("/userServlet")
public class UserServlet extends BasicServlet{
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
		if("login".equals(op)) {
			login(req,resp);
		}
    }
	//登录和注册
	private void login(HttpServletRequest req, HttpServletResponse resp) {
		String uname=req.getParameter("username");
		String pwd=req.getParameter("password");
		
		//先查询看看，用户名存不存在
		String sql="select * from users where username=?";
		List<Map<String,String>> list=db.find(sql, uname);
		
		if(list.size()<=0) {
			//用户名不存在，用注册
			Random r=new Random();
			String nickname="默认用户"+(r.nextInt(100000)+100000);
			String sql2="insert into users values(null,?,?,?,1,'images/cover.jpg')";
			int result=db.doUpdate(sql2, nickname,uname,pwd);
			//存储session
			req.getSession().setAttribute("username", uname);
			if(result>0) {
				this.send(resp, nickname);
			}else {
				this.send(resp, -1);
			}
		}else {
			//判断一下密码
			if(list.get(0).get("pwd").equals(pwd)) {
				req.getSession().setAttribute("username", list.get(0).get("username"));
				this.send(resp, list.get(0).get("nickname"));
			}else {
				this.send(resp, -2);
			}
		}
	}
}
