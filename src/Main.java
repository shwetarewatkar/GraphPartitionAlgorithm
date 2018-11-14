import java.lang.reflect.Array;
import java.util.*;


public class Main {

    public static int[][] MATRIX = {    { 0, 3, 4, 10, 32, 5, 15, 20 },
                                        { 3, 0, 25, 8, 7, 12, 6, 9 },
                                        { 4, 25, 0, 19, 13, 8, 10, 11 },
                                        { 10, 8, 19, 0, 42, 4, 28, 15 },
                                        { 32, 7, 13, 42, 0, 16, 70, 8 },
                                        { 5, 12, 8, 4, 16, 0, 10, 9 },
                                        { 15, 6, 10, 28, 70, 10, 0, 14 },
                                        { 20, 9, 11, 15, 8, 9, 14, 0 }
                                    };

    static int nSetElements = MATRIX[0].length/2;
    static int length = MATRIX[0].length;
    static ArrayList<Integer> selected_index = new ArrayList<>();
    static int lowerBound = 0;
    static int upperBound = 0;
    static int best_solution = 9999;
    static ArrayList<Integer> solution = new ArrayList<>();

    public static int calculateLowerBound(int[][] matrix)
    {
        int lowerBound = 0;

        int[] col = new int[length];
        int[] sum_col = new int[length];

        for (int i = 0; i < length; i++)
        {
            sum_col[i] = 0;
            for (int j = 0; j < length; j++)
            {
                col[j] = MATRIX[i][j];
            }
            Arrays.sort(col);
            for (int j = 0 + 1; j < nSetElements + 1; j++)
            {
                sum_col[i] += col[j];
            }
        }

        Arrays.sort(sum_col);
        for (int i = 0; i < nSetElements; i++)
        {
            lowerBound += sum_col[i];
        }

        return lowerBound;

    }


    public static int calculateUpperBound(int[][] matrix)
    {
        int upperBound = 0;
        for (int i = 0; i < nSetElements; i++)
        {
            for (int j = nSetElements ; j < MATRIX[0].length ; j++)
            {
                upperBound += MATRIX[i][j];
            }
        }

        return upperBound;
    }

    public static int[] deleteElement(int[] arr,int[] index)
    {

        List<Integer> res = new ArrayList<>();
        for(int i=0;i<length;i++)
        {

            if(!containsElement(index,i)){
                res.add(arr[i]);
            }
        }
        return res.stream().mapToInt(Integer::intValue).toArray();

    }

    public static int lowerBoundElement(int[] elements)
    {
        int lowerBound = 0;
        int remainingEle = nSetElements-elements.length;
        int[] sum = new int[length];
        int[][] rem_arr = MATRIX.clone();
        for(int j=0;j<length;j++)
        {
            sum[j] = 0;
        }

        for(int k=0; k<length; k++){

            for(int i=0; i<elements.length; i++)
            {

                if (containsElement(elements, k))
                {
                    continue;
                }
                sum[k] += MATRIX[k][elements[i]];
            }

            rem_arr[k] = deleteElement(rem_arr[k],elements);

            if (!containsElement(elements, k))
            sum[k] += sumofMin(rem_arr[k],remainingEle);

        }

        lowerBound = sumofMin(sum,nSetElements);
        return lowerBound;
    }

    public static int sumofMin(int[] arr, int n)
    {
        arr = removeZeros(arr);
        int sum=0;
        Arrays.sort(arr);
        for(int i=0;i<n;i++)
        {
            sum += arr[i];
        }
        return sum;
    }

    public static int[] removeZeros(int[] arr)
    {
        int targetIndex = 0;
        for( int sourceIndex = 0;  sourceIndex < arr.length;  sourceIndex++ )
        {
            if( arr[sourceIndex] != 0 )
                arr[targetIndex++] = arr[sourceIndex];
        }
        int[] newArray = new int[targetIndex];
        System.arraycopy( arr, 0, newArray, 0, targetIndex );
        return newArray;
    }


    public static boolean containsElement(int[] arr, int num)
    {
        boolean decision = Arrays.stream(arr).anyMatch(i -> i == num);

        return decision;
    }

    public static void print(String s)
    {
        System.out.println(s);
    }
    public static void print(boolean s)
    {
        System.out.println(s);
    }


    public static void print(int[] s)
    {
        for(int i=0;i<s.length;i++)
        {
            System.out.print(s[i]+" ");

        }
        System.out.println();

    }

    public static HashMap<ArrayList, Integer> calculateLowerBounds(ArrayList<Integer> selected_index, int k)
    {

        ArrayList<HashMap> res = new ArrayList<>();

        int cur_in = selected_index.get(selected_index.size()-1);
        HashMap<ArrayList,Integer> rmap = new HashMap<>();
        ArrayList<Integer> elements;

        for (int i=cur_in+1;i<=length-nSetElements+k;i++){

            elements = new ArrayList<>();
            if(selected_index.size()>0 && cur_in!=-1)
                elements = (ArrayList<Integer>) selected_index.clone();

            elements.add(i);
            int[] arr = elements.stream().mapToInt(Integer::intValue).toArray();

            rmap.put(elements,lowerBoundElement(arr));
            res.add(rmap);
        }

        return rmap;
    }

    public static void print(HashMap<ArrayList, Integer> choices)
    {

            for (ArrayList<Integer> elements: choices.keySet()) {
                System.out.print("{ ");

                for(int i=0;i<elements.size();i++){
                    System.out.print((elements.get(i)+1)+" ");
                }

                System.out.print("} ");
                System.out.println(choices.get(elements));

            }
            print("");

        }

        public static HashMap<ArrayList, Integer> sortChoices(HashMap<ArrayList, Integer> choices){
            List<HashMap.Entry<ArrayList, Integer>> list = new LinkedList<HashMap.Entry<ArrayList, Integer>>(choices.entrySet());

                Collections.sort(list, new Comparator<HashMap.Entry<ArrayList, Integer>>()
                {
                    public int compare(HashMap.Entry<ArrayList, Integer> o1,
                                       HashMap.Entry<ArrayList, Integer> o2)
                    {
                        if (true)
                        {
                            return o1.getValue().compareTo(o2.getValue());
                        }
                        else
                        {
                            return o2.getValue().compareTo(o1.getValue());

                        }
                    }
                });

                HashMap<ArrayList, Integer>  sortedMap = new LinkedHashMap<ArrayList, Integer>();
                for (HashMap.Entry<ArrayList, Integer> entry : list)
                {
                    sortedMap.put(entry.getKey(), entry.getValue());
                }

                return sortedMap;

        }

    private static void build(int k)
    {
        ArrayList<ArrayList> J = new ArrayList<ArrayList>();
        HashMap<ArrayList, Integer> choiceEstimates = new HashMap<>();

        choiceEstimates = calculateLowerBounds(selected_index,k);

        choiceEstimates = sortChoices(choiceEstimates);

        Integer[] sortedIndex;
        Iterator it = choiceEstimates.entrySet().iterator();

        print(choiceEstimates);

        ArrayList<ArrayList> sortedIndices = new ArrayList<>(choiceEstimates.keySet());
        while(it.hasNext() && k<nSetElements && selected_index.size() <= nSetElements){

            HashMap.Entry pair = (HashMap.Entry)it.next();
            it.remove(); // avoids a ConcurrentModificationException

            selected_index = (ArrayList<Integer>) pair.getKey();
            int bound = (int) pair.getValue();

            if (bound<best_solution && bound>=lowerBound && bound<=upperBound) {
                if (k+1==nSetElements) {
                    best_solution = bound;
                    solution = selected_index;
                } else
                    build(k + 1);
            }

        }

    }

    public static void print(int s)
    {
        System.out.println(s);
    }
    public static void main(String[] args)
    {
        upperBound = calculateUpperBound(MATRIX);
        lowerBound = calculateLowerBound(MATRIX);

        selected_index.add(-1);

        build(0);
        print("Minimum sum of edge weights = "+best_solution);
        System.out.print("Best partition is {");
        for(int i=0;i<solution.size();i++)
        {
            System.out.print(" "+(solution.get(i)+1));
        }
        System.out.print(" }");
    }
}
