package com.tan.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tan.db.DBUtil;

public class DBServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String command = req.getParameter("command");
		if (command == null || command.trim().length() == 0) {
			req.getRequestDispatcher("error.jsp").forward(req, resp);
			return;
		}
		DBUtil db = new DBUtil();
		if ("create".equals(command)) {
			db.setSql("create table t_people(id int(11) not null auto_increment, name varchar(50) not null, password varchar(50) default null, pic longblob, remark longtext,primary key (id));");
			db.create();
			resp.getWriter().println("<div style=\"background-color:yellow;color:blue;\">Create table successful</div>");
			resp.flushBuffer();return;
		}
		if ("drop".equals(command)) {
			db.setSql("DROP TABLE t_people");
			if (db.drop()) {
				resp.getWriter().println("<div style=\"background-color:yellow;color:blue;\">Drop table successful</div>");
			}
			resp.flushBuffer();return;
		}
		
		if ("insert".equals(command)) {
			db.setSql("INSERT INTO t_people (name, password, pic) VALUES (?,?,?)");
			// 设置二进制的参数
//			File file = new File("C:\\test.png");
//			InputStream in = new BufferedInputStream(new FileInputStream(file));
			
			URL url = new URL("http://www.baidu.com/img/baidu_logo.gif");
			InputStream in = url.openStream();
			db.insertBlob("tanyuanji", "123", in);
			resp.getWriter().println("<div style=\"background-color:yellow;color:blue;\">Insert successful</div>");
			resp.flushBuffer();return;
		}
		if ("image".equals(command)) {
			db.setSql("SELECT pic FROM t_people WHERE ID = ?");
			String id = req.getParameter("id");
			//check the parameter id is digit 
			if (id != null && id.trim().length() != 0 && id.matches("^\\d+$")) {
				InputStream in = db.selectImage(id);
				if (in == null) {
					return;
				}
				resp.setContentType("image/gif");
				byte[] buf = new byte[2049];
				int len = -1;
				while ((len = in.read(buf, 0, buf.length) ) != -1) {
					resp.getOutputStream().write(buf, 0, len);
				}
				in.close();
			}
			resp.flushBuffer();return;
		} 
		if ("showTables".equals(command)) {
			List<String> tables = db.showTables();
			resp.setContentType("text/html;charset=gb18030");
			PrintWriter out = resp.getWriter();
			if (tables == null || tables.size() == 0) {
				out.println("<span>DB can not connection</span>");
			} else {
				out.println("<div id=\"content\">");
				int i =1;
				for (Iterator<String> iter = tables.iterator(); iter.hasNext(); i++) {
					out.println("<div id=\"table" + i + "\">" + iter.next()+ "</div>");
				}
				out.println("</div>");
			}
			out.flush();
			resp.flushBuffer();return;
		}
	}

}
