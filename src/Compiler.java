import java.io.*;
import java.util.*;

public class Compiler {
    public static void main(String[] strings) throws IOException {
        InputStream in = new FileInputStream("Input/data0.txt");
        int size = in.available();
        boolean isWord = false;
        boolean isNum = false;
        boolean isSym = false;
        List<Character> tempWord = new ArrayList<>();
        HashMap<String,String> reserveWords = initReserveWords();
        for(int i=0; i<size; i++){
            int t = in.read();
            if(t>='A'&&t<='Z'){
                t -= 'A'-'a';
            }
            char c = (char) t;
            if(tempWord.isEmpty()){
                if(c<='z'&&c>='a')
                    isWord = true;
                else if(c<='9'&&c>='0')
                    isNum = true;
                else if(c==':'||reserveWords.containsKey(Character.toString(c)))
                    isSym = true;
            }else {
                if(isWord){
                    if(!(c<='z'&&c>='a' || c>='0'&&c<='9')){
                        output(tempWord, reserveWords);
                        tempWord.clear();
                        isWord = false;
                    }
                }
                else if(isNum){
                    if(!(c>='0'&&c<='9')){
                        output(tempWord, reserveWords);
                        tempWord.clear();
                        isNum = false;
                    }
                }
                else if(isSym){
                    if(c!='='){
                        output(tempWord, reserveWords);
                        tempWord.clear();
                        isSym = false;
                    }
                }
                else {
                    continue;
                }
            }
            if(tempWord.isEmpty()){
                if(c<='z'&&c>='a')
                    isWord = true;
                else if(c<='9'&&c>='0')
                    isNum = true;
                else if(c==':'||reserveWords.containsKey(Character.toString(c)))
                    isSym = true;
            }
            if(isWord||isNum||isSym){
                tempWord.add(c);
            }
        }
    }

    static HashMap<String,String> initReserveWords(){
        HashMap<String,String> reverseWords = new HashMap<>();
        reverseWords.put("begin","beginsym");
        reverseWords.put("call","callsym");
        reverseWords.put("const","constsym");
        reverseWords.put("do","dosym");
        reverseWords.put("end","endsym");
        reverseWords.put("if", "ifsym");
        reverseWords.put("odd","oddsym");
        reverseWords.put("procedure","proceduresym");
        reverseWords.put("read","readsym");
        reverseWords.put("then","thensym");
        reverseWords.put("var","varsym");
        reverseWords.put("while","whilesym");
        reverseWords.put("write","writesym");

        reverseWords.put("+","plus");
        reverseWords.put("-","minus");
        reverseWords.put("*","times");
        reverseWords.put("/","slash");
        reverseWords.put("=","eql");
        reverseWords.put("#","neq");
        reverseWords.put("<","lss");
        reverseWords.put("<=","leq");
        reverseWords.put(">","gtr");
        reverseWords.put(">=","geq");
        reverseWords.put(":=","becomes");

        reverseWords.put("(","lparen");
        reverseWords.put(")","rparen");
        reverseWords.put(",","comma");
        reverseWords.put(";","semicolon");
        reverseWords.put(".","period");

        return reverseWords;
    }

    public static String listToString(List list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        return list.isEmpty()?"":sb.toString();
    }

    public static void output(List list, HashMap<String,String> reserveWords) throws IOException{
        String str = listToString(list);
        if(reserveWords.containsKey(str)){
            str = "( " + reserveWords.get(str) + ", " + str +" )\n";
        }
        else if(str.toCharArray()[0]>='0'&&str.toCharArray()[0]<='9'){
            str = "( " + "number" + ", " + str +" )\n";
        }
        else {
            str = "( " + "ident" + ", " + str +" )\n";
        }
        FileWriter fw = new FileWriter("Output/answer.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(str);
        bw.close();
        fw.close();
    }
}
