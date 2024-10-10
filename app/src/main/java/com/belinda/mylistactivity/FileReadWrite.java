package com.belinda.mylistactivity;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Utility function class that Reads and writes ArrayList<Person> to a file
 */
public class FileReadWrite {
    private static final String textFileName = "persons.txt";

    /**
     * Reads a file from resources
     * @param personArrList
     * @param context
     */
    public static void readFileFromResources(ArrayList<Person> personArrList, Context context) {
        try (InputStream is = context.getResources().openRawResource(R.raw.persons);
             InputStreamReader isr = new InputStreamReader(is, "UTF8");
             BufferedReader reader = new BufferedReader(isr)) {

            personArrList.clear(); //empty the list we are going to readFile
            String strLine = reader.readLine(); // Ignore headers
            strLine = reader.readLine();

            while (strLine != null) {
                Person p = readPerson(strLine);
                personArrList.add(p);
                Log.d("Read Person ", p.toString());
                strLine = reader.readLine();
            }
        }catch (IOException e) {
            Log.e("ReadFromFile", "Error reading from file: persons.txt");
            e.printStackTrace();
        }
    }

    /**
     * Reads a file from external memory
     * (located in emulator explorer under storage/self/primary/Android/data/com.belinda.mylistactivity/files/persons.txt)
     *
     * @param personArrList
     * @param context
     */
    public static void readFile(ArrayList<Person> personArrList, Context context) {
        if (checkExternalStorageState())
        {
            File txtFile = new File(context.getExternalFilesDir(null), textFileName);
            try (FileInputStream fis = new FileInputStream(txtFile);
                 InputStreamReader isr = new InputStreamReader(fis, "UTF8");
                 BufferedReader reader = new BufferedReader(isr)                         )
            {
                personArrList.clear(); // clear the list we are going to readFile
                String strLine = reader.readLine(); // ignore first line containing headers
                strLine = reader.readLine();
                while (strLine != null)
                {
                    Person p = readPerson(strLine);
                    personArrList.add(p);
                    Log.d("Read Person", p.toString());
                    strLine = reader.readLine();
                }
            }
            catch (IOException e) {
                Log.e("ReadFromFile", "Error reading from file: persons.txt");
                e.printStackTrace();
            }
        } // if mExternalStorageAvailable...
        else {
            Log.e("readFile", "Cannot access file '" + textFileName + "' - external storage not available or not readable");
        }
    }

    /**
     * Writes a file to external memory
     * @param personArrList
     * @param context
     */
    public static void writeFile(ArrayList<Person> personArrList, Context context) {
        if (checkExternalStorageState())
        {
            File txtFile = new File(context.getExternalFilesDir(null), textFileName);

            try (OutputStream fos = new FileOutputStream(txtFile);
                 OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                 BufferedWriter writer = new BufferedWriter(osw)                                 )
            {
                writeHeader(writer); // writeFile 1st line
                //for (Person p : personArrList)
                for (int i = 0; i < personArrList.size(); i++)
                {
                    write2File(personArrList.get(i), writer); // writeFile current person to the file
                    Log.d("Write2file", "Written: " + personArrList.get(i).toString());
                }
            }
            catch (Exception e) {
                Log.e("Write2file", "Error writing to file: " + textFileName);
                e.printStackTrace();
            } // catch
        } // if
        else {
            Log.e("Write2file", "Cannot access file '" + textFileName + "' - external storage not available or not writable");
        }

    }

    /////////////Helper Methods//////////////////
    /*
     * reads a line from the file
     */
    private static Person readPerson(String line){
        String[] data = line.split(",");

        Person p = new Person();
        p.firstName = data[0].trim();
        p.lastName = data[1].trim();
        p.phone = data[2].trim();
        p.gender = Gender.valueOf(data[3].trim());
        return p;
    }

    /**
     * writes a the current person to an open BufferedWriter
     */
    private static void write2File(Person p, BufferedWriter writer) throws IOException {
        writer.append(p.firstName  + ",");
        writer.append(p.lastName  + ",");
        writer.append(p.phone + ",");
        writer.append(p.gender.toString()  + "\n");
    }

    /**
     * writeFile 1st line in the file
     */
    private static void writeHeader(BufferedWriter writer) throws IOException {
        writer.append("First name"  + ",");
        writer.append("Last name " + ",");
        writer.append("phone" + ",");
        writer.append("Gender" + "\n");
    }

    /**
     * Utility to verify external storage state
     */
    private static boolean checkExternalStorageState() {
        // Verify that the external storage is available for writing
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
        { // We can readFile and writeFile the media
            return true;
        }
        return false;
    }
}
