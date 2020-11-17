package MoleServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DB.DBConnection;
import io.netty.channel.ChannelHandlerContext;

public class DBConnect {
	public DBConnect() {
	}
	
	public static void informationDB(String id, ChannelHandlerContext ctx) {	
		try {
		Connection con = DBConnection.makeConnection(); // DB����
		PreparedStatement pstmt = con.prepareStatement("SELECT * FROM gamer");
		ResultSet rs = pstmt.executeQuery();
		String value = "";
		while(rs.next()) {
			if(id.equals(rs.getString("ID"))) {
					value = String.format("������: %s \n�� �÷��̼�: %3d \n�ΰ��¸���: %3d \n�δ����¸���: %3d \n�·�: %.1f \n����: %4d" 
						,rs.getString("ID"),rs.getInt("PLAYCOUNT"),rs.getInt("HUMANWIN"),rs.getInt("MOLEWIN"),((rs.getDouble("HUMANWIN")+rs.getInt("MOLEWIN"))/rs.getInt("PLAYCOUNT"))*100,rs.getInt("SCORES"));
			} else
				continue;
			ctx.writeAndFlush("INFO" + "," + value);
		}
		rs.close();
		pstmt.close();
		con.close();
	} catch (Exception a) {
		a.printStackTrace();
		}
	}
	
	public static void leaderBoardDB(ChannelHandlerContext ctx) {
		int num = 1;
		try {
			Connection con = DBConnection.makeConnection(); // DB����
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM gamer  ORDER BY SCORES DESC");
			ResultSet rs = pstmt.executeQuery();
			String contents = "";
			String l = "";
			while(rs.next()) {
					contents +=  String.format(" %3d \t %s \t %3d \t %3d \t %3d \t %.1f \t %4d \n \n", 
						num++, rs.getString("ID"),rs.getInt("PLAYCOUNT"),rs.getInt("HUMANWIN"),
						rs.getInt("MOLEWIN"),((rs.getDouble("HUMANWIN")+rs.getInt("MOLEWIN"))/rs.getInt("PLAYCOUNT"))*100,rs.getInt("SCORES"));	
				}
			ctx.writeAndFlush("RANKING" + "," + contents);
			num = 1;
			rs.close();
			pstmt.close();
			con.close();
		} catch (Exception a) {
			a.printStackTrace();
			}
	}
}

