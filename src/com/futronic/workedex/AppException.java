

package com.futronic.workedex;


public class AppException extends java.lang.Exception
{
    
    /**
     * Creates a new instance of <code>AppException</code> without detail message.
     */
    public AppException()
    {
    }
    
    /**
     * Constructs an instance of <code>AppException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public AppException(String msg)
    {
        super(msg);
    }
}
