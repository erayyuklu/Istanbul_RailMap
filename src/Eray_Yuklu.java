import java.awt.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;


public class Eray_Yuklu {
    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "coordinates.txt";
        File coordinates = new File(fileName);
        if (!coordinates.exists()) {
            System.out.printf("%s can not be found.", fileName);
            System.exit(1); // exit the program
        }

        int pauseDuration=300;
        StdDraw.setCanvasSize(1024, 482);
        StdDraw.setXscale(0.0, 1.0);
        StdDraw.setYscale(0.0, 1.0);
        StdDraw.picture(0.5, 0.5, "background.jpg", 1.0, 1.0, 0.0);
        StdDraw.setPenRadius(0.012);

        Scanner myReader = new Scanner(coordinates);
        int lineNumber = 27;
        String[] allLines = new String[lineNumber];
        int i = 0;
        while (myReader.hasNextLine()) {
            String line = myReader.nextLine();
            allLines[i] = line;
            i += 1;
        }
        //all the data in the file has been exported to the allines array
        myReader.close();
        String[][] allStationNames = new String[10][];
        int[][][] allStationCoordinates = new int[10][][];
        String[] lineNames = new String[10];
        for (int k = 0; k < 20; k++) {
            if (k % 2 == 1) {
                String[] splittedStations = allLines[k].split(" ");
                String[] stationNames = new String[splittedStations.length / 2];
                String[] stationCoordinates = new String[splittedStations.length / 2];
                int[][] intStationCoordinates = new int[splittedStations.length / 2][2];
                for (int l = 0; l < splittedStations.length; l++) {
                    if (l % 2 == 0) {
                        stationNames[l / 2] = splittedStations[l];
                    } else {
                        stationCoordinates[l / 2] = splittedStations[l];

                    }
                }
                for (int p = 0; p < stationCoordinates.length; p++) {
                    int[] twoElements = new int[2];
                    for (int h = 0; h < 2; h++) {
                        String[] twoStringElements = stationCoordinates[p].split(",");
                        int first = Integer.parseInt(twoStringElements[0]);
                        int second = Integer.parseInt(twoStringElements[1]);
                        twoElements[0] = first;
                        twoElements[1] = second;
                    }
                    intStationCoordinates[p] = twoElements;
                }
                allStationNames[k / 2] = stationNames;
                allStationCoordinates[k / 2] = intStationCoordinates;

                for (int r = 0; r < stationNames.length; r++) {
                    if (r == stationNames.length - 1) {
                        break;
                    } else {
                        StdDraw.line(intStationCoordinates[r][0] / 1024d, intStationCoordinates[r][1] / 482d, intStationCoordinates[r + 1][0] / 1024d, intStationCoordinates[r + 1][1] / 482d);
                        //drawing the metroline
                    }
                }
            } else {
                String[] splittedLine = allLines[k].split(" ");
                lineNames[k / 2] = splittedLine[0];
                String[] rgbCodes = splittedLine[1].split(",");
                int redAmount = Integer.parseInt(rgbCodes[0]);
                int greenAmount = Integer.parseInt(rgbCodes[1]);
                int blueAmount = Integer.parseInt(rgbCodes[2]);
                StdDraw.setPenColor(redAmount, greenAmount, blueAmount);
                //determined the color of metroline
            }
        }
        //all station names and coordinates has been exported to two nested list: allStationNmaes and allStationCoordinates
        for (int n = 0; n < allStationCoordinates.length; n++) {
            for (int f = 0; f < allStationCoordinates[n].length; f++) {
                StdDraw.setPenRadius(0.010);
                StdDraw.setPenColor(255, 255, 255);
                StdDraw.point(allStationCoordinates[n][f][0] / 1024d, allStationCoordinates[n][f][1] / 482d);
                Font font = new Font("Helvetica", Font.BOLD, 8);
                StdDraw.setFont(font);
                StdDraw.setPenColor(0, 0, 0);
                if (allStationNames[n][f].startsWith("*")) {
                    StdDraw.text(allStationCoordinates[n][f][0] / 1024d, (allStationCoordinates[n][f][1] + 5) / 482d, allStationNames[n][f].replace("*", ""));
                }
                //Station names are written
            }
        }
        for (int n = 0; n < allStationCoordinates.length; n++) {
            for (int f = 0; f < allStationCoordinates[n].length; f++) {
                if (allStationNames[n][f].startsWith("*")) {
                    allStationNames[n][f] = allStationNames[n][f].replace("*", "");
                }
            }

        }
        String[] breakPointStops= new String[7];
        ArrayList<ArrayList<String>> breakPointLines = new ArrayList<ArrayList<String>>();
        for(int y= 0; y<lineNumber-2*(allStationCoordinates.length);y++){

            int k= allLines[y+ 2*allStationCoordinates.length].split(" ").length;
            String[] currentLineArray= new String[k];
            ArrayList<String> currentLine= new ArrayList<String>();
            currentLineArray=allLines[y+ 2*allStationCoordinates.length].split(" ");
            breakPointStops[y]=currentLineArray[0];
            for(int u=1; u<k;u++){
                currentLine.add(u-1,currentLineArray[u]);
            }
            breakPointLines.add(currentLine);
        }
        //all breakpointstops exported an array with named allbreakpoints
        Scanner starting = new Scanner(System.in);
        String startingPoint = starting.nextLine();
        Scanner finish = new Scanner(System.in);
        String finishPoint = finish.nextLine();
        starting.close();
        finish.close();
        int counter1=0;
        int counter2=0;
        for(int m=0;m<allStationNames.length;m++){
            for(int n=0;n<allStationNames[m].length;n++){
                if(allStationNames[m][n].equals(startingPoint)){
                    counter1+=1;
                }
            }
        }
        for(int m=0;m<allStationNames.length;m++){
            for(int n=0;n<allStationNames[m].length;n++){
                if(allStationNames[m][n].equals(finishPoint)){
                    counter2+=1;
                }
            }
        }

        if (counter1==0){
            System.out.println("There is no station with the name: "+startingPoint);
            System.exit(-1);
        }
        if (counter2==0){
            System.out.println("There is no station with the name: "+finishPoint);
            System.exit(-1);
        }
        //start-finish point syntax errors handled
        ArrayList<Integer> currentPossibleLines = possibleLines(allStationNames,startingPoint);

        if(isSameLine(finishPoint, currentPossibleLines, allStationNames)){
            drawSameLine(startingPoint,allStationNames,finishPoint,currentPossibleLines,allStationCoordinates);
            //if the two stations are in the same line, it drawn.
        }
        else{
            ArrayList<String> unusedBreakPointStops = new ArrayList<>(Arrays.asList(breakPointStops));
            ArrayList<String> orderedEmptyList=new ArrayList<>();
            addTransferLocations(startingPoint,finishPoint,allStationNames,breakPointStops,unusedBreakPointStops,currentPossibleLines,orderedEmptyList);
            if(orderedBreaks.size()==0){
                System.out.println("There are no breakpoints between these two stations");
                //if there are no breakpoints between two stops this situation printed.
            }

            for(int m=0;m< orderedBreaks.size();m++){
                if (m==0) {
                    drawSameLine(startingPoint, allStationNames, orderedBreaks.get(0), possibleLines(allStationNames, startingPoint), allStationCoordinates);
                }

                if (isSameLine(finishPoint,possibleLines(allStationNames,orderedBreaks.get(m)),allStationNames)){
                    drawSameLine(orderedBreaks.get(m),allStationNames,finishPoint,possibleLines(allStationNames,orderedBreaks.get(m)),allStationCoordinates);
                    break;
                }
                else{
                    drawSameLine(orderedBreaks.get(m),allStationNames,orderedBreaks.get(m+1),possibleLines(allStationNames,orderedBreaks.get(m)),allStationCoordinates);
                }

            }

        }
    }



    public static void drawSameLine(String startingPoint,String[][] allStationNames,String finishPoint,ArrayList<Integer> currentPossibleLines,int[][][] allStationCoordinates){
        int lineIndex=whatLine(finishPoint, currentPossibleLines,allStationNames);
        int startIndex=Arrays.asList(allStationNames[lineIndex]).indexOf(startingPoint);
        int finishIndex=Arrays.asList(allStationNames[lineIndex]).indexOf(finishPoint);
        StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
        if(startIndex>finishIndex){
            for(int g=startIndex;g>finishIndex-1;g--) {
                StdDraw.setPenRadius(0.02);
                StdDraw.point(allStationCoordinates[lineIndex][g][0] / 1024d, allStationCoordinates[lineIndex][g][1] / 482d);
                StdDraw.pause(300);
            }
        }
        else{
            for(int g=startIndex;g<finishIndex+1;g++){
                StdDraw.setPenRadius(0.02);
                StdDraw.point(allStationCoordinates[lineIndex][g][0]/1024d,allStationCoordinates[lineIndex][g][1]/482d);
                StdDraw.pause(300);
            }
        }
        //Points are between two stops in the same line are created.
    }

    public static ArrayList<Integer> possibleLines(String[][] allStationNames, String startingPoint){
        ArrayList<Integer> currentPossibleLines = new ArrayList<Integer>();
        for (int s = 0; s < allStationNames.length; s++) {
            for (int v = 0; v < allStationNames[s].length; v++) {
                if (allStationNames[s][v].equals(startingPoint)) {
                    currentPossibleLines.add(s);
                }

            }

        }
        return currentPossibleLines;
        //all possible metrolines taht contains startingpoint was found
    }

    public static boolean isSameLine(String finishPoint, ArrayList<Integer> currentPossibleLines, String[][] allStationNames) {
        for (int i=0;i< currentPossibleLines.size();i++){
            for(int j=0;j<allStationNames[currentPossibleLines.get(i)].length;j++){
                if (finishPoint.equals(allStationNames[currentPossibleLines.get(i)][j])){
                    return true;
                }
            }
        }
        return false;
    }
    public static int whatLine(String finishPoint, ArrayList<Integer> currentPossibleLines, String[][] allStationNames){
        for (int i=0;i< currentPossibleLines.size();i++) {
            for (int j = 0; j < allStationNames[currentPossibleLines.get(i)].length; j++) {
                if (finishPoint.equals(allStationNames[currentPossibleLines.get(i)][j])) {
                    return currentPossibleLines.get(i);
                }
            }
        }
        return -1;
        //line number was determined
    }

    public static ArrayList<String> orderedBreaks= new ArrayList<>();
    public static void addTransferLocations(String startingPoint,String finishPoint, String[][] allStationNames,String[] allBreakPointStops,ArrayList<String> unusedBreakPointStops,ArrayList<Integer> currentPossibleLines, ArrayList<String>orderedBreakPoints){
        if (isSameLine(finishPoint, currentPossibleLines, allStationNames)){
            orderedBreakPoints.add(finishPoint);
            orderedBreaks=orderedBreakPoints;
        }
        else{
            boolean isBreakPoint=Arrays.asList(allBreakPointStops).contains(startingPoint);
            if(isBreakPoint){
                boolean statement=false;
                int stIndex=-10;
                for(int y=0;y<unusedBreakPointStops.size();y++){
                    if (unusedBreakPointStops.get(y).equals(startingPoint)){
                        statement=true;
                        stIndex=y;
                    }
                }
                if(statement){
                    unusedBreakPointStops.remove(stIndex);
                }
                for(int i=0;i< currentPossibleLines.size();i++){
                    int currentLineIndex=currentPossibleLines.get(i);
                    ArrayList<String> onLineBreakPoints= new ArrayList<>();
                    for(int m=0;m<allStationNames[currentLineIndex].length;m++){
                        for(int n=0; n< unusedBreakPointStops.size();n++){
                            if (allStationNames[currentLineIndex][m].equals(unusedBreakPointStops.get(n))){
                                onLineBreakPoints.add(unusedBreakPointStops.get(n));
                            }
                        }
                    }
                    unusedBreakPointStops.removeAll(onLineBreakPoints);
                    for(int u=0;u<onLineBreakPoints.size();u++){
                        String newStartingPoint=onLineBreakPoints.get(u);
                        ArrayList<String> newOrderedBreakPointsList=new ArrayList<>();
                        newOrderedBreakPointsList.addAll(orderedBreakPoints);
                        newOrderedBreakPointsList.add(newStartingPoint);//recursively breakpoints added
                        ArrayList<Integer> newCurrentPossibleLines= new ArrayList<>();
                        newCurrentPossibleLines=possibleLines(allStationNames,newStartingPoint);
                        addTransferLocations(newStartingPoint,finishPoint,allStationNames,allBreakPointStops,unusedBreakPointStops,newCurrentPossibleLines,newOrderedBreakPointsList);
                    }

                }
            }
            else{
                int currentLineIndex=currentPossibleLines.get(0);
                ArrayList<String> onLineBreakPoints= new ArrayList<>();
                for(int m=0;m<unusedBreakPointStops.size();m++){
                    for(int n=0;n<allStationNames[currentLineIndex].length;n++){
                        if(unusedBreakPointStops.get(m).equals(allStationNames[currentLineIndex][n])){
                            onLineBreakPoints.add(allStationNames[currentLineIndex][n]);
                        }
                    }
                }
                unusedBreakPointStops.removeAll(onLineBreakPoints);
                for(int u=0;u<onLineBreakPoints.size();u++){
                    String newStartingPoint=onLineBreakPoints.get(u);
                    ArrayList<String> newOrderedBreakPointsList=new ArrayList<>();
                    newOrderedBreakPointsList.addAll(orderedBreakPoints);
                    newOrderedBreakPointsList.add(newStartingPoint);//recursively breakpoints added
                    ArrayList<Integer> newCurrentPossibleLines= new ArrayList<>();
                    newCurrentPossibleLines=possibleLines(allStationNames,newStartingPoint);
                    addTransferLocations(newStartingPoint,finishPoint,allStationNames,allBreakPointStops,unusedBreakPointStops,newCurrentPossibleLines,newOrderedBreakPointsList);
                }

            }
        }
    }
}

