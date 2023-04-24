public class Entry {

    private String userEmail;
    private String entryName;

            public Entry(String userEmail, String entryName) {
                this.userEmail = userEmail;
                this.entryName = entryName;
            }

            public void setUserEmail(String userEmail) {
                this.userEmail = userEmail;
            }

            public String getUserEmail() {
                return userEmail;
            }

            public void setEntryName(String entryName) { this.entryName = entryName;}

            public String getEntryName() {
                return entryName;
            }

        }
