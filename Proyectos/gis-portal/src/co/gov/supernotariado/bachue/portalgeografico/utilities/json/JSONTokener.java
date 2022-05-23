package co.gov.supernotariado.bachue.portalgeografico.utilities.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import co.gov.supernotariado.bachue.portalgeografico.utilities.json.JSONArray;
import co.gov.supernotariado.bachue.portalgeografico.utilities.json.JSONException;
import co.gov.supernotariado.bachue.portalgeografico.utilities.json.JSONObject;

/**
 * 
 * Clase de componentes JSONTokener
 *
 */
public class JSONTokener
{
	
	/**
	 * Variable index
	 * int que contiene el indice.
	 */
  private int index;
	/**
	 * Variable reader
	 * Reader que contiene el objeto de lectura.
	 */
  private Reader reader;
	/**
	 * Variable lastChar
	 * char que contiene el ultimo caracter.
	 */
  private char lastChar;
	/**
	 * Variable useLastChar
	 * boolean que contiene el estado de uso del ultimo caracter.
	 */
  private boolean useLastChar;
  
  
  /**
   * Constructor de la clase JSONTokener
   * @param Reader reader objeto de lectura
   */
  public JSONTokener(Reader reader)
  {
    this.reader = (reader.markSupported() ? reader : new BufferedReader(reader));
    
    this.useLastChar = false;
    this.index = 0;
  }
  
  /**
   * Constructor de la clase JSONTokener
   * @param String s valor
   */
  public JSONTokener(String s)
  {
    this(new StringReader(s));
  }
  
  /**
   * Metodo que posiciona el ultimo valor
   */
  public void back()
    throws JSONException
  {
    if ((this.useLastChar) || (this.index <= 0)) {
      throw new JSONException("Stepping back two steps is not supported");
    }
    this.index -= 1;
    this.useLastChar = true;
  }
  
  /**
   * Metodo hexadecimal char
   * @param char c caracter
   * @return un int con el valor 
   */
  public static int dehexchar(char c)
  {
    if ((c >= '0') && (c <= '9')) {
      return c - '0';
    }
    if ((c >= 'A') && (c <= 'F')) {
      return c - '7';
    }
    if ((c >= 'a') && (c <= 'f')) {
      return c - 'W';
    }
    return -1;
  }
  
  /**
   * Metodo recurrente
   * @return un boolean con el estado 
   */
  public boolean more()
    throws JSONException
  {
    char nextChar = next();
    if (nextChar == 0) {
      return false;
    }
    back();
    return true;
  }
  
  /**
   * Metodo siguiente valor
   * @return un char con el caracter siguiente 
   */
  public char next()
    throws JSONException
  {
    if (this.useLastChar)
    {
      this.useLastChar = false;
      if (this.lastChar != 0) {
        this.index += 1;
      }
      return this.lastChar;
    }
    
    int c;
    try
    {
      c = this.reader.read();
    }
    catch (IOException exc)
    {
      throw new JSONException(exc);
    }
    if (c <= 0)
    {
      this.lastChar = '\000';
      return '\000';
    }
    this.index += 1;
    this.lastChar = ((char)c);
    return this.lastChar;
  }
  
  /**
   * Metodo siguiente valor
   * @param char c caracter
   * @return un char con el caracter siguiente 
   */
  public char next(char c)
    throws JSONException
  {
    char n = next();
    if (n != c) {
      throw syntaxError("Expected '" + c + "' and instead saw '" + n + "'");
    }
    return n;
  }
  
  /**
   * Metodo siguiente valor
   * @param int n valor
   * @return un String con el valor siguiente 
   */
  public String next(int n)
    throws JSONException
  {
    if (n == 0) {
      return "";
    }
    char[] buffer = new char[n];
    int pos = 0;
    if (this.useLastChar)
    {
      this.useLastChar = false;
      buffer[0] = this.lastChar;
      pos = 1;
    }
    try
    {
      int len;
      while ((pos < n) && ((len = this.reader.read(buffer, pos, n - pos)) != -1)) {
        pos += len;
      }
    }
    catch (IOException exc)
    {
      throw new JSONException(exc);
    }
    this.index += pos;
    if (pos < n) {
      throw syntaxError("Substring bounds error");
    }
    this.lastChar = buffer[(n - 1)];
    return new String(buffer);
  }
  
  /**
   * Metodo siguiente de blanco
   * @return un char caracter neutro 
   */
  public char nextClean()
    throws JSONException
  {
    for (;;)
    {
      char c = next();
      if ((c == 0) || (c > ' ')) {
        return c;
      }
    }
  }
  
  /**
   * Metodo siguiente String
   * @param char quote valor
   * @return un String con el valor siguiente 
   */
  public String nextString(char quote)
    throws JSONException
  {
    StringBuffer sb = new StringBuffer();
    for (;;)
    {
      char c = next();
      switch (c)
      {
      case '\000': 
      case '\n': 
      case '\r': 
        throw syntaxError("Unterminated string");
      case '\\': 
        c = next();
        switch (c)
        {
        case 'b': 
          sb.append('\b');
          break;
        case 't': 
          sb.append('\t');
          break;
        case 'n': 
          sb.append('\n');
          break;
        case 'f': 
          sb.append('\f');
          break;
        case 'r': 
          sb.append('\r');
          break;
        case 'u': 
          sb.append((char)Integer.parseInt(next(4), 16));
          break;
        case '"': 
        case '\'': 
        case '/': 
        case '\\': 
          sb.append(c);
          break;
        default: 
          throw syntaxError("Illegal escape.");
        }
        break;
      default: 
        if (c == quote) {
          return sb.toString();
        }
        sb.append(c);
      }
    }
  }
  
  /**
   * Metodo siguiente valor
   * @param char d valor
   * @return un String con el valor siguiente 
   */
  public String nextTo(char d)
    throws JSONException
  {
    StringBuffer sb = new StringBuffer();
    for (;;)
    {
      char c = next();
      if ((c == d) || (c == 0) || (c == '\n') || (c == '\r'))
      {
        if (c != 0) {
          back();
        }
        return sb.toString().trim();
      }
      sb.append(c);
    }
  }
  
  /**
   * Metodo siguiente valor
   * @param String delimiters delimitador
   * @return un String con el valor siguiente 
   */
  public String nextTo(String delimiters)
    throws JSONException
  {
    StringBuffer sb = new StringBuffer();
    for (;;)
    {
      char c = next();
      if ((delimiters.indexOf(c) >= 0) || (c == 0) || (c == '\n') || (c == '\r'))
      {
        if (c != 0) {
          back();
        }
        return sb.toString().trim();
      }
      sb.append(c);
    }
  }
  
  /**
   * Metodo siguiente valor
   * @return un Object objecto con el valor  
   */
  public Object nextValue()
    throws JSONException
  {
    char c = nextClean();
    switch (c)
    {
    case '"': 
    case '\'': 
      return nextString(c);
    case '{': 
      back();
      return new JSONObject(this);
    case '(': 
    case '[': 
      back();
      return new JSONArray(this);
    }
    StringBuffer sb = new StringBuffer();
    while ((c >= ' ') && (",:]}/\\\"[{;=#".indexOf(c) < 0))
    {
      sb.append(c);
      c = next();
    }
    back();
    
    String s = sb.toString().trim();
    if (s.equals("")) {
      throw syntaxError("Missing value");
    }
    return JSONObject.stringToValue(s);
  }
  
  /**
   * Metodo pasar a
   * @param char to caracter
   * @return un char con el caracter 
   */
  public char skipTo(char to)
    throws JSONException
  {
	char c = '\0';
    try
    {
      int startIndex = this.index;
      this.reader.mark(Integer.MAX_VALUE);
      
      do
      {
        c = next();
        if (c == 0)
        {
          this.reader.reset();
          this.index = startIndex;
          return c;
        }
      } while (c != to);
    }
    catch (IOException exc)
    {
      throw new JSONException(exc);
    }
    back();
    return c;
  }
  
  /**
   * Metodo de error en sintasis
   * @param String message mensaje
   * @return un JSONException excepcion json
   */
  public JSONException syntaxError(String message)
  {
    return new JSONException(message + toString());
  }
  
  /**
   * Metodo pasar a String
   * @return un String
   */
  public String toString()
  {
    return " at character " + this.index;
  }
}
