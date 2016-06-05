package net.coderbot.eerv.compression.match;


public interface IMatchFinder
{
	/**
	 * Tries to find a match below posAt and above or equal to minSrcPosition.
	 * @param match A preallocated match object. If it is null, a new one will be allocated.
	 * @param posAt The position the match must start before.
	 * @param minSrcPosition The minimum start position
	 * @param minLength The minimum length of the match.
	 * @return The match object if it was successful, null if it was not.
	 */
	public Match find(Match match, int posAt, int minSrcPosition, int minLength);
}
