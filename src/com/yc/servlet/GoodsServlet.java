package com.yc.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yc.dao.DBHelper;

@WebServlet("/goodsServlet")
public class GoodsServlet extends BasicServlet{
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
		if("findGoodGoods".equals(op)) {
			findGoodGoods(req,resp);
		}else if("findYanxuanGoods".equals(op)){
			findYanxuanGoods(req,resp);
		}else if("findByGid".equals(op)) {
			findByGid(req,resp);
		}else if("addCart".equals(op)) {
			addCart(req,resp);
		}else if("findCart".equals(op)) {
			findCart(req,resp);
		}
    }
	
	
	//查询购物车
	public void findCart(HttpServletRequest req, HttpServletResponse resp) {
		String username=String.valueOf(req.getSession().getAttribute("username"));
		String sql="select * from cartinfo,goods where cartinfo.gid=goods.gid and id=(select id from users where username=?)";
		List<Map<String,String>> list=db.find(sql,username);
		this.send(resp, list);
	}
	
	//模拟支付
	public void pay(HttpServletRequest req, HttpServletResponse resp) {
		
	}
	
	//加入购物车
	public void addCart(HttpServletRequest req, HttpServletResponse resp) {
		String username=String.valueOf(req.getSession().getAttribute("username"));
		String gid=req.getParameter("gid");
		
		String sql="select * from cartinfo where id=(select id from users where username=?) and gid=?";
		List<Map<String,String>> list=db.find(sql,username,gid);
		
		if(list.size()>0) {
			String sql2="update cartinfo set num=num+1 where cid =?";
			db.doUpdate(sql2, list.get(0).get("cid"));
			this.send(resp, 1);
		}else {
			String sql3="insert into cartinfo values(null,(select id from users where username=?),?,1)";
			db.doUpdate(sql3, username,gid);
			this.send(resp, 2);
		}
	}
	
	//根据gid查询商品
	public void findByGid(HttpServletRequest req,HttpServletResponse resp) {
		String gid=req.getParameter("gid");
		String sql="select * from goods,attribute where goods.gid=attribute.gid and goods.gid=?";
		List<Map<String,String>> list=db.find(sql,gid);
		//别急着返回
		if(list.size()>0) {
			this.send(resp, list);
		}else {
			this.send(resp, -1);
		}
	}
	
	
	//查询优质商品数据
	public void findGoodGoods(HttpServletRequest req,HttpServletResponse resp) {
		String sql="select gid,gname,pic,price,type from goods where type=1";		
		List<Map<String,String>> list=db.find(sql);		
		this.send(resp, list);
		
	}
	
	
	//查询严选商品数据
	private void findYanxuanGoods(HttpServletRequest req, HttpServletResponse resp) {
		String sql="select gid,gname,pic,price,type from goods where type=2";
		List<Map<String,String>> list=db.find(sql);
		this.send(resp, list);
	}
}
