public class YakobiMethod {

    private double matrB[][];
    private double vectorG[];
    private double prevX[];
    private double curX[];
    private double epsilon = 0.00001;
    private double matrBRate;
    private double discrepancy[];
    private int realCount;
    private double prioryCount;
    private int n;


    public YakobiMethod(double matrA[][], double vectorB[]){
        n = matrA.length;
        realCount = 0;
        vectorG = new double[n];
        prevX = new double[n];
        curX = new double[n];
        matrB = new double[n][n];
        for(int i = 0; i < n; i++){
            vectorG[i] = vectorB[i] / matrA[i][i];
            prevX[i] = this.vectorG[i];
            for(int j = 0; j < n; j++){
                if(i == j){
                    matrB[i][j] = 0;
                }
                else{
                    matrB[i][j] = -matrA[i][j] / matrA[i][i];
                }
            }
        }
    }

    private double difference(){
        double diff = Math.abs(prevX[0] - curX[0]);
        for(int i = 0; i < n; i++){
            if(Math.abs(prevX[i] - curX[i]) > diff){
                diff = prevX[i] - curX[i];
            }
        }
        return Math.abs(diff);
    }
    private void newPrevX(){
        for(int i = 0; i < n; i++){
            prevX[i] = curX[i];
        }
    }

    public void yakobiMethod(){
        double sum;
        while (difference() > epsilon){
            realCount++;
            newPrevX();
            for(int i = 0; i < n; i++){
                sum = 0;
                for(int j = 0; j < n; j++){
                    if(j != i){
                        sum += matrB[i][j]*prevX[j];
                    }
                }
                sum += vectorG[i];
                curX[i] = sum;
            }
        }
    }
    public void createDiscrepancy(double matrA[][], double vectorB[]){
        discrepancy = new double[n];
        double[] res = new double[n];
        for(int i = 0; i < n; i++){
            res[i] = 0;
            for(int j = 0; j < n; j++){
                res[i] += matrA[i][j]*curX[j];
            }
            discrepancy[i] = vectorB[i] - res[i];
        }
    }

    public void printAll(){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                System.out.format("%25s", matrB[i][j] + "    ");
            }
            System.out.format("%25s", vectorG[i] + "\n");
        }
    }
    public void printX(){
        System.out.println("Vector X");
        for(double item: curX){
            System.out.format("%25s", item + "    ");
        }
        System.out.println("real count = " + realCount);
    }
    public void printDiscrepancy(){
        System.out.println("Discrepancy");
        for(double item: discrepancy){
            System.out.format("%25s", item + "    ");
        }
        System.out.println();
    }

    private double vecotorGRate(){
        double rate = 0;
        for(double item : vectorG){
            if(Math.abs(item) > rate){
                rate = Math.abs(item);
            }
        }
        return rate;
    }

    public void prioryCounter(){
        matrBRate = 0;
        double sum;
        for (int i = 0; i < n; i++) {
            sum = 0;
            for (int j = 0; j < n; j++) {
                sum += Math.abs(matrB[i][j]);
            }
            if(sum > matrBRate) {
                matrBRate = sum;
            }
        }
        prioryCount = Math.ceil((Math.log(epsilon) + Math.log(1 - matrBRate) - Math.log(vecotorGRate())) / Math.log(matrBRate) - 1);
        System.out.println("Priory counter = " + prioryCount);
        System.out.println("Rate of matrix B = " + matrBRate);
    }

}
