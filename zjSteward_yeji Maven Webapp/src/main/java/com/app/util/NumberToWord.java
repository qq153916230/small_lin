package com.app.util;

public class NumberToWord {

    /*private static String[] num = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖","拾"};

    private static String[] unit = {"","拾","佰","仟","万","拾","佰","仟","亿"};

    private static String[] result;

    public static String transfer(String input) {
        String out = "";
        result = new String[input.length()];
        for(int i=0; i<result.length; i++) {
            result[i] = String.valueOf(input.charAt(i));    
        }
        for(int i=0; i<result.length; i++) {
            int back;
            if(!result[i].equals("0")) {
                back = result.length - i - 1;
                out += num[Integer.parseInt(result[i])];
                out += unit[back];
            } else {
                //最后一位不考虑
                if(i == (result.length - 1)) {

                } else {
                    //九位数，千万，百万，十万，万位都为0，则不加“万”
                    if(result.length == 9 && result[1].equals("0") && result[2].equals("0") && result[3].equals("0") && result[4].equals("0")) {

                    } else {
                        //大于万位，连着的两个数不为0，万位等于0则加上“万”
                        if(result.length > 4 && !result[i+1].equals("0") && result[result.length-5].equals("0")){
                            out += unit[4];                 
                        }                       
                    }
                    //万位之后的零显示
                    if(i == result.length-4 && !result[i+1].equals("0")) {
                        out += num[0];
                    }
                }
            }
        }
        return out;
    }*/
	
	static String[] units = {"","拾","佰","仟","万","拾","佰","仟","亿","拾","佰","仟","万" };
    static char[] numArray = {'零','壹','贰','叁','肆','伍','陆','柒','捌','玖'};

    //将整数转换成汉字数字
    public static String transfer(String input) {
        char[] val = String.valueOf(input).toCharArray();
        int len = val.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            String m = val[i] + "";
            int n = Integer.valueOf(m);
            boolean isZero = n == 0;
            String unit = units[(len - 1) - i];
            if (isZero) {
                if ('0' == val[i - 1]) {
                    continue;
                } else {
                    if (len - i == 5){
                        sb.append(units[4]);
                    } else {
                        sb.append(numArray[n]);
                    }
                }
            } else {
                sb.append(numArray[n]);
                sb.append(unit);
            }
        }
        String lastStr = sb.substring(sb.length()-1);
        if ("零".equals(lastStr)){
            return sb.substring(0,sb.length()-1);
        }
        return sb.toString();
    }
	
    public static void main(String[] args) {
    	String res = NumberToWord.transfer("5010");
    	System.out.println(res);
	}
}
