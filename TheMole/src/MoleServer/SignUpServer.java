package MoleServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import DB.DBConnection;
import io.netty.channel.ChannelHandlerContext;

public class SignUpServer {
	private ArrayList<String> idList = new ArrayList<String>();
	public SignUpServer(String id, ChannelHandlerContext ctx) {
		try {
			Connection con = DBConnection.makeConnection(); // DB����
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM gamer");
			ResultSet rs = pstmt.executeQuery();;
			while (rs.next()) { // �����ͺ��̽� ���̺� ���� ���� Ȯ��
				idList.add(rs.getString("id"));
			}
			int index = Collections.binarySearch(idList, id);
            if(index >= 0 )
                ctx.writeAndFlush(""); //���̵��ߺ�

            else {
                ctx.writeAndFlush(""); //���̵� �ߺ��ƴ�(��������)
            }

			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
