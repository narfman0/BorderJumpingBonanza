package com.blastedstudios.borderjumpingbonanza;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

import com.blastedstudios.borderjumpingbonanza.game.HighScoreStruct;

public class BorderJumpingBonanzaServer {
	static String SCORES_PATH = System.getProperty("user.home") + File.separator + ".borderjumpingbonanza",
		SCORES_FILE = SCORES_PATH + File.separator + "scores.txt"; 

	public static void main(String[] args) {
		HighScoreStruct[] highScores = loadScores();
		DatagramSocket serverSocket = null;
		try {
			serverSocket = new DatagramSocket(58392);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		while(true)	{
			try{
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				HighScoreStruct score = HighScoreStruct.fromString(new String(receivePacket.getData(), "UTF8"));
				System.out.println("RECEIVED: " + score);
				if(score.compareTo(highScores[highScores.length-1]) == 1)
					highScores[highScores.length-1] = score;
				Arrays.sort(highScores);
				saveScores(highScores);

				String sendString = "";
				for(int i=0; i<highScores.length; i++)
					sendString += highScores[i] + "\n";
				sendString.substring(0,sendString.length()-2);

				InetAddress IPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();
				sendData = sendString.getBytes("UTF8");
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	static void saveScores(HighScoreStruct[] highScores){
		new File(SCORES_PATH).mkdirs();
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(SCORES_FILE));
			for(int i=0; i<highScores.length; i++)
				out.write(highScores[i].toString() + (i==highScores.length-1?"\n":""));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	static HighScoreStruct[] loadScores(){
		HighScoreStruct[] highScores = new HighScoreStruct[10];
		for(int i=0; i<highScores.length; i++)
			highScores[i] = new HighScoreStruct("Anonymous", 0);

		try{
			new File(SCORES_PATH).mkdirs();
			DataInputStream in = new DataInputStream(new FileInputStream(SCORES_FILE));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			int i=0;
			while ((strLine = br.readLine()) != null)
				highScores[i++] = HighScoreStruct.fromString(strLine);
			in.close();
		}catch (Exception e){
			System.out.println("Error reading file, generated new scores");
		}
		return highScores;
	}
}
