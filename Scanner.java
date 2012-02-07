import Java.util.ArrayList;

public class Scanner
{
	private ArrayList<String> tokens;
	private String contents;
	
	
	public Scanner(String filename)
	{
		tokens = new ArrayList<String>();
		
		//read file into string
		contents = this.readFile(filename);
		
		
		this->break_tokens(filename);
	}
	
	private break_tokens(String filename)
	{
		String[] 1_char_keywords = {"<", ">", "+",
			"-", "*", "/", "{", "}", ";", ".", ",", ":", "(", ")"};
		String[] 2_char_keywords = {"if", ":=", "!=", "<=", ">=", "do"};
	  String[] 3_char_keywords = {"var", "end"};
		String[] 4_char_keywords = {"real", "then", "else"};
		String[] 5_char_keywords = {"begin", "while", "print"};
		String[] 6_char_keywords = {"return"};
		String[] 7_char_keywords = {"program", "integer"};
		String[] 8_char_keywords = {"function"};
		
		//remove comments (all text between and including { } tags)
		contents = contents.replaceAll("{.*}", "");
		contents = contents.trim();
		boolean stop = false;
		
		while (!contents.isEmpty())
		{
			stop = false;
			stop = this.find_element_in_contents(1_char_keywords, 1);
			if (!stop)
				stop = this.find_element_in_contents(2_char_keywords, 2);
			if (!stop)
				stop = this.find_element_in_contents(3_char_keywords, 3);
			if (!stop)
				stop = this.find_element_in_contents(4_char_keywords, 4);
			if (!stop)
				stop = this.find_element_in_contents(5_char_keywords, 5);
			if (!stop)
				stop = this.find_element_in_contents(6_char_keywords, 6);
			if (!stop)
				stop = this.find_element_in_contents(7_char_keywords, 7);
			if (!stop)
				stop = this.find_element_in_contents(8_char_keywords, 8);
			if (!stop) //no keywords found, must be an identifier
				//how do we get the identifiers between the tokens?...
			else
				contents = contents.trim(); //remove whitespace
		}
		
		// //search pieces for tokens not separated by spaces
		// for (int i = 0; i < pieces.size(); i++)
		// {
			// //search for keywords:
			// //program var function integer real begin end if then else while do print 
			// //return := = != < <= > >= + - * / { } ; . , : ( )
			// for (String keyword : keywords)
			// {
				// if (piece.indexOf(keyword) != -1)
				// {
					// //rewrite piece in pieces
					// pieces[i] = piece.substring(0, piece.indexOf(keyword));
					// 
					// //create new piece at the end that is the remainder
					// pieces[pieces.size()] = piece.substring(piece.indexOf(keyword));
					// 
				// }
			// }
		// }
		
		
	}
	
	private Boolean find_element_in_contents(ArrayList<String> list, int characters)
	{
		String stop = false;
		String sub = contents.substring(0, characters);
		for(String keyword : list)
		{
			if (keyword.equals(sub))
			{
				//remove from string
				contents = contents.substring(characters);
				//place into tokens
				tokens.add(keyword);
				stop = true;
			}
			if (stop)
				break;
		}
		return stop;
	}
	
	//function obtained from http://stackoverflow.com/a/326440
	private static String readFile(String path) throws IOException {
  FileInputStream stream = new FileInputStream(new File(path));
  try {
    FileChannel fc = stream.getChannel();
    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
    /* Instead of using default, pass in a decoder. */
    return Charset.defaultCharset().decode(bb).toString();
  }
  finally {
    stream.close();
  }
}
	
}
