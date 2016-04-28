package net.coderbot.util;

public class DecoderException extends Exception
{
	private static final long serialVersionUID = 100L;
	
	String decoder;
	String message;
	
	public DecoderException(String decoder, String message)
	{
		super();
		this.decoder = decoder;
		this.message = message;
		//super.setStackTrace(getSpecialStackTrace());
	}
	
	public String getMessage()
	{
		return "["+decoder+"] "+message;
	}
	
	public String toString()
	{
		return getMessage();
	}
	
	StackTraceElement[] getSpecialStackTrace()
	{
		StackTraceElement[] ost = super.getStackTrace();
		StackTraceElement[] st = new StackTraceElement[ost.length-1];
		System.arraycopy(ost, 1, st, 0, st.length);
		return st;
	}
}
