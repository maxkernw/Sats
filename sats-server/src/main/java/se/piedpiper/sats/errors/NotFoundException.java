package se.piedpiper.sats.errors;

public class NotFoundException extends RuntimeException
{

	private static final long serialVersionUID = -5538079043453435003L;

	public NotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public NotFoundException(String message)
	{
		super(message);
	}
}