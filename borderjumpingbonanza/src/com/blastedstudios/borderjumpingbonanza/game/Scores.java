package com.blastedstudios.borderjumpingbonanza.game;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scores {
	private static ArrayList<HighScoreStruct> highScores = new ArrayList<HighScoreStruct>();
	
	public static void submitScore(String name, long score){
		send(name.equals("") ? "Anonymous" : name, score);
	}
	
	public static List<HighScoreStruct> getHighScores(){
		return send("Anonymous",0);
	}
	
	private static List<HighScoreStruct> send(String name, long score){
		try{
			DatagramSocket socket = new DatagramSocket();
	        byte[] outData = (name + "-" + score).getBytes("UTF8");
	        socket.send(new DatagramPacket(outData, outData.length, InetAddress.getByName("jrob.no-ip.org"), 58392));
	        if(score == 0){
		        byte[] receiveData = new byte[1024];
		        DatagramPacket scoresPacket = new DatagramPacket(receiveData, receiveData.length);
		        socket.receive(scoresPacket);
		        highScores.clear();
		        highScores.addAll(convertScoresBuffer(scoresPacket.getData()));
	        }
	        socket.close();
		}catch(Exception e){}
		return highScores;
	}
	
	private static List<HighScoreStruct> convertScoresBuffer(byte[] buffer){
		try{
			HighScoreStruct[] highScores = new HighScoreStruct[10];
			String[] scoresString = new String(buffer, "UTF8").split("\n");
			for(int i=0; i<highScores.length; i++){
				Long score = Long.parseLong(scoresString[i].split("-")[1]);
				highScores[i] = new HighScoreStruct(scoresString[i].split("-")[0], score);
			}
			return Arrays.asList(highScores);
		}catch(Exception e){
			return new ArrayList<HighScoreStruct>();
		}
	}
}
