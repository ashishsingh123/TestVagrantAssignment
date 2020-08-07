package ui.utils;

public class CompareObject {

    public static int compareTemp(TempratureData obj1,TempratureData obj2){
        String s = obj2.temp;
        double d = Double.parseDouble(s);
        int tempV = (int) d;

        int diff =  Integer.parseInt(obj1.temp.replace(" ","")) - tempV;

        if(diff >0){
            return diff;
        }else
        {
            return -diff;
        }
    }

    public static int compareHumadity(TempratureData obj1,TempratureData obj2){

        int diff =  Integer.parseInt(obj1.humidity.replaceAll(" ",""))
                - Integer.parseInt(obj2.humidity.replaceAll(" ",""));

        if(diff >0){
            return diff;
        }else
        {
            return -diff;
        }
    }
}
