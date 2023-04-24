import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class FileHandler {

    public static void createNewFiles() throws IOException {
        // creates required files if they do not already exist
        File parentDirectory = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage");
        File listOfEntriesDirectory = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries");
        File experiencesTextFile = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Experiences");
        File reviewsTextFile = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Reviews");











        File individualEntriesDirectory = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\Individual Entries");
        File experiencesDirectory = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\Individual Entries\\Experiences");
        File reviewsDirectory = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\Individual Entries\\Reviews");
        if(!parentDirectory.exists()) {
            if(parentDirectory.mkdir()) {
                System.out.println("Directory has been created");
            } else {
                System.out.println("Directory already exists");
            }
        }
        if(!listOfEntriesDirectory.exists()) {
            if(listOfEntriesDirectory.mkdir()) {
                System.out.println("Sub-directory has been created");
            } else {
                System.out.println("Sub-directory already exists");
            }
        }
        if(!individualEntriesDirectory.exists()) {
            if(individualEntriesDirectory.mkdir()) {
                System.out.println("Sub-directory has been created");
            } else {
                System.out.println("Sub-directory already exists");
            }
        }
        if(!experiencesDirectory.exists()) {
            if(experiencesDirectory.mkdir()) {
                System.out.println("Sub-directory has been created");
            } else {
                System.out.println("Sub-directory already exists");
            }
        }
        if(!reviewsDirectory.exists()) {
            if(reviewsDirectory.mkdir()) {
                System.out.println("Sub-directory has been created");
            } else {
                System.out.println("Sub-directory already exists");
            }
        }
    }


    public static boolean checkForLoginDetails(String fileName, String email, String password) {
        // checks login file for particular email address and password
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                if (line.contains(email + " - " + password)) {
                    reader.close();
                    return true;
                }
                line = reader.readLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static boolean checkForEmailAddress(String fileName, String email) {
        // checks file for particular email address
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                String[] loginComponents = line.split(" - ");
                if (loginComponents[0].equals(email)) {
                    reader.close();
                    return true;
                }
                line = reader.readLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static boolean checkForAccountType(String fileName, String email) throws Exception {
        // checks type of particular account
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                if (line.contains(email)) {
                    if (line.contains("Student")) {
                        reader.close();
                        return false;
                    } else if (line.contains("Supervisor")) {
                        reader.close();
                        return true;
                    }
                }
                line = reader.readLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        throw new Exception("ERROR: ACCOUNT TYPE UNDETECTED");
    }

    public static void addLoginDetails(String fileName, String email, String password, boolean isSupervisor, boolean append) {
        // adds new email, password and account type to login file
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, append));
            if (isSupervisor) {
                writer.println(email + " - " + password + " - Supervisor");
            } else {
                writer.println(email + " - " + password + " - Student");
            }
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void sortLoginDetails(String fileName, boolean append) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            ArrayList<String> listOfLogins = new ArrayList<>();
            String line = reader.readLine();
            while (line != null) {
                listOfLogins.add(line);
                line = reader.readLine();
            }
            reader.close();
            Collections.sort(listOfLogins);
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, append));
            for (String i : listOfLogins){
                writer.println(i);
            }
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static ExperienceEntry checkForExperienceEntry(String fileName, String experienceName) throws Exception {
        // checks experience file for particular entry
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                String[] entryComponents = line.split(" - ");
                if (entryComponents[1].equals(experienceName)) {
                    reader.close();
                    return new ExperienceEntry(entryComponents[0], entryComponents[1], entryComponents[2], entryComponents[3], entryComponents[4]);
                }
                line = reader.readLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        throw new Exception("ERROR: ENTRY UNDETECTED");
    }

    public static ReviewEntry checkForReviewEntry(String fileName, String experienceName) throws Exception {
        // checks reviews file for particular entry
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                String[] entryComponents = line.split(" - ");
                if (entryComponents[1].equals(experienceName)) {
                    reader.close();
                    return new ReviewEntry(entryComponents[0], entryComponents[1], entryComponents[2], entryComponents[3]);
                }
                line = reader.readLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        throw new Exception("ERROR: ENTRY UNDETECTED");
    }

    public static boolean checkForEntryName(String fileName, String entryName) {
        // checks file for particular email address
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                String[] entryComponents = line.split(" - ");
                if (entryComponents[1].equals(entryName)) {
                    reader.close();
                    return true;
                }
                line = reader.readLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static ArrayList<ExperienceEntry> readAllExperienceEntries(String fileName) {
        // reads all entries in experience file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            ArrayList<ExperienceEntry> experienceEntries = new ArrayList<>();
            String line = reader.readLine();
            if(line == null) {
                return experienceEntries;
            }
            while (line != null) {
                String[] entryComponents = line.split(" - ");
                ExperienceEntry newEntry = new ExperienceEntry(entryComponents[0], entryComponents[1], entryComponents[2], entryComponents[3], entryComponents[4]);
                experienceEntries.add(newEntry);
                line = reader.readLine();
            }
            reader.close();
            return experienceEntries;
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<ExperienceEntry> experienceEntries = new ArrayList<>();
        ExperienceEntry newEntry = new ExperienceEntry("", "", "", "", "");
        experienceEntries.add(newEntry);
        return experienceEntries;
    }

    public static ArrayList<ReviewEntry> readAllReviewEntries(String fileName) {
        // reads all entries in reviews file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            ArrayList<ReviewEntry> reviewEntries = new ArrayList<>();
            String line = reader.readLine();
            if(line == null) {
                return reviewEntries;
            }
            while (line != null) {
                String[] entryComponents = line.split(" - ");
                ReviewEntry newEntry = new ReviewEntry(entryComponents[0], entryComponents[1], entryComponents[2], entryComponents[3]);
                reviewEntries.add(newEntry);
                line = reader.readLine();
            }
            reader.close();
            return reviewEntries;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        ArrayList<ReviewEntry> reviewEntries = new ArrayList<>();
        ReviewEntry newEntry = new ReviewEntry("", "", "", "");
        reviewEntries.add(newEntry);
        return reviewEntries;
    }

    public static void addExperienceEntry(String fileName, String userEmail, String experienceName, String dates, String description, String learningOutcomes, boolean append) {
        // adds new experience entry to experience file
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, append));
            if (experienceName.isBlank()) {
                experienceName = "<empty>";
            } if (dates.isBlank()) {
                dates = "<empty>";
            } if (description.isBlank()) {
                description = "<empty>";
            } if (learningOutcomes.isBlank()) {
                learningOutcomes = "<empty>";
            }
            writer.println(userEmail + " - " + experienceName + " - " + dates + " - " + description + " - " + learningOutcomes);
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void addReviewEntry(String fileName, String userEmail, String experienceName, String opinionOnSuccess, String additionalComments, boolean append) {
        // adds new review entry to reviews file
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, append));
            if (experienceName.isBlank()) {
                experienceName = "<empty>";
            } if (opinionOnSuccess.isBlank()) {
                opinionOnSuccess = "<empty>";
            } if (additionalComments.isBlank()) {
                additionalComments = "<empty>";
            }
            writer.println(userEmail + " - " + experienceName + " - " + opinionOnSuccess + " - " + additionalComments);
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void editExperienceEntry(String fileName, ExperienceEntry entryToBeReplaced, ExperienceEntry entryToBeAdded, boolean append) {
        // edits existing entry in experience file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            ArrayList<String> listOfEntries = new ArrayList<>();
            String line = reader.readLine();
            while (line != null) {
                if (line.equals(entryToBeReplaced.getUserEmail() + " - " + entryToBeReplaced.getEntryName() + " - " + entryToBeReplaced.getDates() + " - " + entryToBeReplaced.getDescription() + " - " + entryToBeReplaced.getLearningOutcomes())) {
                    line = entryToBeAdded.getUserEmail()  + " - " + entryToBeAdded.getEntryName() + " - " + entryToBeAdded.getDates() + " - " + entryToBeAdded.getDescription() + " - " + entryToBeAdded.getLearningOutcomes();
                }
                listOfEntries.add(line);
                line = reader.readLine();
            }
            reader.close();
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, append));
            for (String i : listOfEntries){
                writer.println(i);
            }
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void editReviewEntry(String fileName, ReviewEntry entryToBeReplaced, ReviewEntry entryToBeAdded, boolean append) {
        // edits existing entry in reviews file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            ArrayList<String> listOfEntries = new ArrayList<>();
            String line = reader.readLine();
            while (line != null) {
                if (line.equals(entryToBeReplaced.getUserEmail() + " - " + entryToBeReplaced.getEntryName() + " - " + entryToBeReplaced.getOpinionOnSuccess() + " - " + entryToBeReplaced.getAdditionalComments())) {
                    line = entryToBeAdded.getUserEmail()  + " - " + entryToBeAdded.getEntryName() + " - " + entryToBeAdded.getOpinionOnSuccess() + " - " + entryToBeAdded.getAdditionalComments();
                }
                listOfEntries.add(line);
                line = reader.readLine();
            }
            reader.close();
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, append));
            for (String i : listOfEntries){
                writer.println(i);
            }
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void deleteExperienceEntry(String fileName, ExperienceEntry entryToBeDeleted, boolean append) {
        // deletes particular entry from experience file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            ArrayList<String> listOfEntries = new ArrayList<>();
            String line = reader.readLine();
            while (line != null) {
                if (!line.equals(entryToBeDeleted.getUserEmail() + " - " + entryToBeDeleted.getEntryName() + " - " + entryToBeDeleted.getDates() + " - " + entryToBeDeleted.getDescription() + " - " + entryToBeDeleted.getLearningOutcomes())) {
                    listOfEntries.add(line);
                }
                line = reader.readLine();
            }
            reader.close();
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, append));
            for (String i : listOfEntries){
                writer.println(i);
            }
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void deleteReviewEntry(String fileName, ReviewEntry entryToBeDeleted, boolean append) {
        // deletes particular entry from experience file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            ArrayList<String> listOfEntries = new ArrayList<>();
            String line = reader.readLine();
            while (line != null) {
                if (!line.equals(entryToBeDeleted.getUserEmail() + " - " + entryToBeDeleted.getEntryName() + " - " + entryToBeDeleted.getOpinionOnSuccess() + " - " + entryToBeDeleted.getAdditionalComments())) {
                    listOfEntries.add(line);
                }
                line = reader.readLine();
            }
            reader.close();
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, append));
            for (String i : listOfEntries){
                writer.println(i);
            }
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void publishExperienceEntry(String fileName, ExperienceEntry experienceEntry, boolean append) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, append));
            writer.println("// EXPERIENCE NAME");
            writer.println("");
            writer.println(experienceEntry.getEntryName());
            writer.println("");
            writer.println("// DATES");
            writer.println("");
            writer.println(experienceEntry.getDates());
            writer.println("");
            writer.println("// DESCRIPTION");
            writer.println("");
            writer.println(experienceEntry.getDescription());
            writer.println("");
            writer.println("// LEARNING OUTCOMES");
            writer.println("");
            writer.println(experienceEntry.getLearningOutcomes());
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void publishReviewEntry(String fileName, ReviewEntry reviewEntry, boolean append) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, append));
            writer.println("// EXPERIENCE NAME");
            writer.println("");
            writer.println(reviewEntry.getEntryName());
            writer.println("");
            writer.println("// OPINION ON SUCCESS");
            writer.println("");
            writer.println(reviewEntry.getOpinionOnSuccess());
            writer.println("");
            writer.println("// ADDITIONAL COMMENTS");
            writer.println("");
            writer.println(reviewEntry.getAdditionalComments());
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
