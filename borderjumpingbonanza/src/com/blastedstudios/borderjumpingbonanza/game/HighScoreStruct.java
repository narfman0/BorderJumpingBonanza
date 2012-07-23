package com.blastedstudios.borderjumpingbonanza.game;

public class HighScoreStruct implements Comparable<HighScoreStruct> {
	public final long score;
	public final String name;

	public HighScoreStruct(String name, long score){
		this.name = name;
		this.score = score;
	}
	
	@Override public int compareTo(HighScoreStruct o) {
		return ((Long)score).compareTo(o.score);
	}
	
	public String toString(){
		return name + "-" + score;
	}

	public static HighScoreStruct fromString(String string){
		String name = string.split("-")[0], score = string.split("-")[1].replaceAll( "[^\\d]", "");
		return new HighScoreStruct(name, Long.parseLong(score));
	}
}

