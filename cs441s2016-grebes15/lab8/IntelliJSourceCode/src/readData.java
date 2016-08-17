import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

public class readData {

    static int bufferSize = 1024; //buffered size
    static String fileName = "/tmp/benchmark.dat"; //file name and path
    static String logFileName = "logfile.txt"; //txt file that is being written to
    static int noOfRepetitions = 10; //number of times the experiements are being repeated
    static byte[] emptyBuffer; //btye array
    static long fileSize = 100*1000*bufferSize;; //file size
    static PrintWriter logFile; //printer writer being used to print out the output

    public static void main(String args[]) {

        // program call: readData buffersize filesize filename
        // buffersize: size of buffer in bytes (integer), default:
        // filesize: size of file in bytes (long)
        // filename: name of temporary file including path

        long timeSpend;

        try { //attetmpt the benchmarks
            logFile = new PrintWriter(logFileName);
        } catch (IOException e) {
            System.err.println(e);
        }


        if (args.length>=1) { //if one set one argument, then this is going to be an integer or a buffered size
            try {
                bufferSize = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Argument BufferSize" + args[0] + " must be an integer.");
                System.exit(1);
            }
        }

        if (args.length>=2) {
            //if two arguments are set, then the first will be the buffered size and the second will be the size of the file in bytes
            try {
                fileSize = Long.parseLong(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Argument fileSize" + args[1] + " must be a long.");
                System.exit(1);
            }
        }

        if (args.length>=3) fileName = args[2];
            //if three arguments are set,
            //the first one will be the buffered size
            //the second one will be the size of the file in bytes
            //the three one will be the file path and name

        emptyBuffer = new byte[bufferSize];

        createFile(fileName, fileSize);


    //for loop to do a certain number of repetitions to the experiment
        for (int i =0 ; i<noOfRepetitions; i++){
            timeSpend = readFileBuffered(fileName);
            logFile.println(csvLine(fileName,"read",timeSpend,((fileSize/timeSpend) * 1000),fileSize,bufferSize));
        }
    //for loop to do a certain number of reptitions to the experiment
        for (int i =0 ; i<noOfRepetitions; i++){
            timeSpend = writeFileBuffered(fileName, fileSize);
            logFile.println(csvLine(fileName,"write",timeSpend,((fileSize/timeSpend) * 1000),fileSize,bufferSize));
        }

        deleteFile(fileName);

        logFile.close();
    }
    //custom method to be formatted into a csv file
    public static String csvLine(String fileName, String operation, long timespend, long bytesPersecond, long fileSize, int bufferSize) {
        return String.format("\"%s\",\"%s\",%d,%d,%d,%d", fileName,operation,timespend,bytesPersecond,fileSize,bufferSize);
    }

    public static void createFile(String fileName, long fileSize) {
        //this method creates the file
        {
            try {
                RandomAccessFile file = new RandomAccessFile(fileName, "rw");

                for (long i = 0; i < fileSize; i += bufferSize)
                {
                    file.write(emptyBuffer, 0, bufferSize);
                }
                file.close();
            } catch (IOException e) {
                System.err.println(e);
            }

        }

    }

    public static void deleteFile(String fileName) {
        //this method delete the file once it has been completed
        try {
            Files.deleteIfExists(Paths.get(fileName));
        } catch (IOException e) {
            // File permission problems are caught here.
            System.err.println(e);
        }
    }

    public static long readFileBuffered(String fileName) {
    //this method is being used to read the file in

        long startTime = System.currentTimeMillis();
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream,bufferSize);
            int count = 0;
            int x;
            while ((x = bufferedInputStream.read()) != -1) {
                count++;
            }
            bufferedInputStream.close();


        } catch (IOException e) {
            System.err.println(e);
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        return elapsedTime;
    }

    public static long writeFileBuffered(String fileName,long fileSize) {
        //this method is being used to write to the file
        long startTime = System.currentTimeMillis();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);

            for (long i = 0; i < fileSize; i += bufferSize) {
                fileOutputStream.write(emptyBuffer);

            }
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (IOException e) {
            System.err.println(e);
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;


        return elapsedTime;
    }

}
