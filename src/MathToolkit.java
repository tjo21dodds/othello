public class MathToolkit {
    public static Integer euclidDistance(Integer[] a, Integer[] b){
        Double result =  Math.sqrt((Math.pow((a[0] - b[0]), 2) + Math.pow((a[1] - b[1]),2)));
        return result.intValue();
    }
    public static Integer[] sumVector(Integer[] a, Integer[] b){
        Integer length = Math.min(a.length, b.length);
        Integer[] c = new Integer[length];
        for (int i = 0; i < length; i++){
            c[i] = a[i] + b[i];
        }
        return c;
    }
    public static Integer[] scale(Integer scalar, Double[] a){
        Integer[] b = new Integer[a.length];
        for (int i = 0; i < a.length; i++){
            Double t = a[i] * scalar.doubleValue();
            b[i] = t.intValue();
        }
        return b;
    }

    public static Double singleDistance
}
