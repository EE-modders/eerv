package net.coderbot.eerv.compression.match;

public class Match
{
	/**
	 * Position where the original data starts.
	 */
	int srcPosition;
	
	/**
	 * Position where the duplicated data starts.
	 */
	int dstPosition;
	
	/**
	 * Length of the match.
	 */
	int length;
}
