public class User {
    private String username;
    private String password;
    private String[] booksHaveBeenRed;
    private String[] booksAreBorrowing;
    public User(){
    }
    public User(String userName, String password,String[] booksHaveBeenRed,String[] booksAreBorrowing){
        this.username=userName;
        this.password=password;
        this.booksHaveBeenRed=booksHaveBeenRed;
        this.booksAreBorrowing=booksAreBorrowing;
    }

    public User(String username, String password) {
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getBooksHaveBeenRed() {
        return booksHaveBeenRed;
    }

    public void setBooksHaveBeenRed(String[] booksHaveBeenRed) {
        this.booksHaveBeenRed = booksHaveBeenRed;
    }

    public String[] getBooksAreBorrowing() {
        return booksAreBorrowing;
    }

    public void setBooksAreBorrowing(String[] booksAreBorrowing) {
        this.booksAreBorrowing = booksAreBorrowing;
    }

}
